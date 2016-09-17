/**
 * Copyright (c) 2011-2015, Mobangjack 莫帮杰 (mobangjack@foxmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.aipao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import me.aipao.model.Run;
import me.aipao.util.DateUtil;
import me.aipao.util.JsonUtil;
import me.aipao.util.RandUtil;

import com.jfinal.log.Log;


/**
 * @author 帮杰<br>
 * 日期：2016年2月22日——6月26日<br>
 * 有效时间：<br>
 * <ul>
 * <li>早上6:00—8:30<br>
 * <li>下午16:00—18:30<br>
 * <li>晚上20:00—23:00<br>
 * </ul>
 * 说明：<br>
 * <ul>
 * <li>指定时间外系统关闭，成绩不记录<br>
 * <li>每天只记录一次有效长跑成绩，一天内跑多次的按一次计算<br>
 * <ul>
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class Runner implements Runnable {

	private static final Log LOG = Log.getLog(Runner.class);
	
	private Timeslots timeslots = Const.Runner.timeslots;
	
	private Integer coins = Const.Runner.coins;
	private Integer scores = Const.Runner.scores;
	private Integer minTime = Const.Runner.minTime;
	private Integer maxTime = Const.Runner.maxTime;
	private Integer length = Const.Runner.length;
	
	public boolean login(Run run) {
		
		String msg = "";
		
		String result = HttpMgr.me.login(run.getImei());
		
		Map<String, Object> map = JsonUtil.parse(result);
		
		//{"Success":false,"ErrCode":7,"ErrMsg":"无此验证码"}
		Boolean success = (Boolean) map.get("Success");
		
		if (!success) {
			msg = "runner.login failed.INFO: imei="+run.getImei()+",result="+result;
			LOG.info(msg);
			System.out.println(msg);
			return false;
		}
		
		Map<String, Object> data = (Map) map.get("Data");
		
		String token = (String) data.get("Token");
		
		Integer userId = (Integer) data.get("UserId");
		
		/*
		result = HttpMgr.me.setLastLatLng(token, userId, run.getFieldId(), run.getLastLat(), run.getLastLng());
		
		map = JsonUtil.parse(result);
		
		success = (Boolean) map.get("Success");
		
		if (!success) {
			msg = "runner.setLastLatLng failed.result="+result+",task pending...";
			LOG.info(msg);
			System.out.println(msg);
			return false;
		}
		*/
		
		result = HttpMgr.me.startSchoolRun(token, run.getLat(), run.getLng());
		
		map = JsonUtil.parse(result);
		
		success = (Boolean) map.get("Success");
		
		//Power weak:{Success:false,ErrCode:11,ErrMsg:体力不足}
		if (!success) {
			msg = "runner.startSchoolRun failed.result="+result+"";
			LOG.info(msg);
			System.out.println(msg);
			
			Integer errCode = (Integer) map.get("ErrCode");
			String errMsg = (String) map.get("ErrMsg");
			if (errCode == 11 && errMsg.equals("体力不够")) {
				msg = "Runner INFO: power weak,trying to buy power...";
				LOG.info(msg);
				System.out.println(msg);
				
				result = HttpMgr.me.buyPower(token, 10);
				map = JsonUtil.parse(result);
				success = (Boolean) map.get("Success");
				
				if (success) {
					
					msg = "Runner INFO: buyPower success.result="+result+",trying to startSchoolRun again...";
					LOG.info(msg);
					System.out.println(msg);
					
					result = HttpMgr.me.startSchoolRun(token, run.getLat(), run.getLng());
					
					map = JsonUtil.parse(result);
					
					success = (Boolean) map.get("Success");
					
					if (!success) {
						msg = "Runner INFO: startSchoolRun failed again (after buying power).result="+result+",this run is going to be suspendeding...";
						LOG.info(msg);
						System.out.println(msg);
						return false;
					}
					
				}else {
					msg = "Runner INFO: buyPower failed.result="+result+",this run is going to be suspendeding...";
					LOG.info(msg);
					System.out.println(msg);
					return false;
				}
			}
			return false;
		}
		
		data = (Map) map.get("Data");
		
		String runId = (String) data.get("RunId");
		Integer fieldId = (Integer) data.get("FieldId");
		
		run.setToken(token);
		run.setUserId(userId);
		run.setRunId(runId);
		run.setFieldId(fieldId);
		run.setScores(scores);
		run.setCoins(coins);
		run.setTime(RandUtil.nextInt(minTime, maxTime));
		run.setLength(length);
		run.setStartTime(new Date());
		run.setEndTime(null);
		run.setResult(result);
		run.update();
		
		return true;
	}
	
	public boolean submit(Run run) {
		
		String result = HttpMgr.me.endSchoolRun(run.getToken(), run.getRunId(), run.getScores(), run.getCoins(), run.getTime(), run.getLength());
		
		run.setEndTime(new Date());
		run.setResult(result);
		run.update();
		
		return true;
	}
	
	public boolean onDuty() {
		for (Timeslot timeslot : timeslots) {
			if(timeslot.millisLeft() > 1000*maxTime)
				return true;
		}
		return false;
	}
	
	public boolean due(Run run) {
		return (new Date().getTime()-run.getStartTime().getTime() >= run.getTime()*1000);
	}
	
	public int run(Run run) {
		
		if (!onDuty()) {
			System.out.println("////////////////////////// runner idle //////////////////////////");
			return 0;
		}
		
		if (DateUtil.notToday(run.getStartTime())) {
			System.out.println("////////////////////////// runner login //////////////////////////");
			login(run);
			return 1;
		}
		
		if(due(run)) {
			System.out.println("////////////////////////// runner submit //////////////////////////");
			submit(run);
			return 2;
		}
		
		return 3;
	}
	
	@Override
	public void run() {
		printMsg();
		List<Run> runs = Run.dao.find("select * from run where endTime is null or TO_DAYS(endTime)<TO_DAYS(CURRENT_DATE)");
		for (Run run : runs) {
			try {
				run(run);
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error("Exception occured in Runner.run", e);
			}
			
		}
	}
	
	private static void printMsg() {
		String bound = "|----------------------------------------------------------------------|";
		StringBuilder sb = new StringBuilder();
		sb.append(bound);
		sb.append("\n|**************");
		sb.append(DateUtil.formatCurrent());
		sb.append(": Runner Scanning...");
		sb.append("*****************|\n");
		sb.append(bound);
		System.out.println(sb);
	}
	
	public static void main(String[] args) {
		printMsg();
		Runner runner = new Runner();
		System.out.println(runner.onDuty());
	}
	
}
