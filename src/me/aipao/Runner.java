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

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Map;

import me.aipao.model.Run;
import me.aipao.util.DateUtil;
import me.aipao.util.JsonUtil;
import me.aipao.util.RandomUtil;

import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;


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

	private final Prop runProp = PropKit.use("run.txt");
	private final Prop timeProp = PropKit.use("time.txt");
	
	private final Time time1 = Time.valueOf(timeProp.get("time1", "6:00:00"));
	private final Time time2 = Time.valueOf(timeProp.get("time2", "8:30:00"));
	private final Time time3 = Time.valueOf(timeProp.get("time3", "16:00:00"));
	private final Time time4 = Time.valueOf(timeProp.get("time4", "18:30:00"));
	private final Time time5 = Time.valueOf(timeProp.get("time5", "20:00:00"));
	private final Time time6 = Time.valueOf(timeProp.get("time6", "23:00:00"));
	
	private final Integer coins = runProp.getInt("coins", 2000);
	private final Integer scores = runProp.getInt("scores", 5000);
	private final Integer minTimes = runProp.getInt("minTimes", 480);
	private final Integer maxTimes = runProp.getInt("maxTimes", 600);
	private final Integer length = runProp.getInt("length", 2000);
	
	private final long minDutyMsLeft = maxTimes*1000;
	
	public long dutyMsLeft() {
		Time time = new Time(new Date().getTime());
		if (time.after(time1)&&time.before(time2)) {
			return time2.getTime()-time.getTime();
		}
		if (time.after(time3)&&time.before(time4)) {
			return time4.getTime()-time.getTime();
		}
		if (time.after(time5)&&time.before(time6)) {
			return time6.getTime()-time.getTime();
		}
		return 0;
	}
	
	public boolean onDuty() {
		return dutyMsLeft() > minDutyMsLeft;
	}
	
	public boolean due(Run run) {
		return (new Date().getTime()-run.getStartTime().getTime() >= run.getTimes()*1000);
	}
	
	public boolean login(Run run) {
		
		String result = HttpMgr.me.login(run.getImei());
		
		Map<String, Object> map = JsonUtil.parse(result);
		
		Map<String, Object> data = (Map) map.get("Data");
		
		//{"Success":false,"ErrCode":7,"ErrMsg":"无此验证码"}
		
		String token = (String) data.get("Token");
		
		Integer userId = (Integer) data.get("UserId");
		
		result = HttpMgr.me.setLastLatLng(token, userId, run.getFieldId(), run.getLastLat(), run.getLastLng());
		
		result = HttpMgr.me.startSchoolRun(token, run.getLat(), run.getLng());
		
		map = JsonUtil.parse(result);
		
		//Power weak
		data = (Map) map.get("Data");
		
		String runId = (String) data.get("RunId");
		Integer fieldId = (Integer) data.get("FieldId");
		
		run.setToken(token);
		run.setUserId(userId);
		run.setRunId(runId);
		run.setFieldId(fieldId);
		run.setScores(scores);
		run.setCoins(coins);
		run.setTimes(RandomUtil.nextInt(minTimes, maxTimes));
		run.setLength(length);
		run.setStartTime(new Date());
		run.setEndTime(null);
		run.setResult(result);
		run.update();
		
		return true;
	}
	
	public boolean apply(Run run) {
		
		String result = HttpMgr.me.endSchoolRun(run.getToken(), run.getRunId(), run.getScores(), run.getCoins(), run.getTimes(), run.getLength());
		
		run.setEndTime(new Date());
		run.setResult(result);
		run.update();
		
		return true;
	}

	public int run(Run run) {
		
		if (!onDuty()) {
			return 0;
		}
		
		if (!DateUtil.isToday(run.getEndTime())) {
			
			login(run);
			return 1;
			
		}else {
			
			apply(run);
			return 2;
			
		}

	}
	
	public static void main(String[] args) {
		printMsg();
	}
	
	@Override
	public void run() {
		printMsg();
		List<Run> runs = Run.dao.find("select * from run where startTime is null or endTime is null or TO_DAYS(endTime)<TO_DAYS(CURRENT_DATE)");
		for (Run run : runs) {
			run(run);
		}
	}
	
	private static void printMsg() {
		String bound = "|----------------------------------------------------------------------|";
		StringBuilder sb = new StringBuilder();
		sb.append(bound);
		sb.append("\n|*************{");
		sb.append(DateUtil.formatCurrent());
		sb.append(":Runner scanning...");
		sb.append("}*****************|\n");
		sb.append(bound);
		System.out.println(sb);
	}
	
	
}
