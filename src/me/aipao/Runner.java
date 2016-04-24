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

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import me.aipao.model.Run;
import me.aipao.util.DateUtil;
import me.aipao.util.JsonUtil;
import me.aipao.util.RandomUtil;


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
	
	public boolean isInDuty() {
		int hours = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		return ((hours >= 6 && hours <= 8) || (hours >= 16 && hours <= 18) || (hours >= 20 && hours <= 22));
	}
	
	private void printMsg() {
		System.out.println("\n/************************{"+DateUtil.formatCurrent()+":Runner is in duty..."+"}************************/");
	}
	
	@Override
	public void run() {
		if (!isInDuty()) {
			return;
		}
		printMsg();
		List<Run> runs = Run.dao.find("select * from run where endTime=null or TO_DAYS(endTime)<TO_DAYS(CURRENT_DATE)");
		for (Run run : runs) {
			
			if (run.getState() != 1) {
				
				String result = HttpMgr.me.login(run.getImei());
				
				Map<String, Object> map = JsonUtil.parse(result);
				
				Map<String, Object> data = (Map) map.get("Data");
				
				//{"Success":false,"ErrCode":7,"ErrMsg":"无此验证码"}
				if (data == null) {
					run.delete();
					continue;
				}
				
				String token = (String) data.get("Token");
				
				Integer userId = (Integer) data.get("UserId");
				
				result = HttpMgr.me.setLastLatLng(token, userId, run.getFieldId(), run.getLastLat(), run.getLastLng());
				
				result = HttpMgr.me.startSchoolRun(token, run.getLat(), run.getLng());
				
				map = JsonUtil.parse(result);
				
				//Power weak
				data = (Map) map.get("Data");
				
				String runId = (String) data.get("RunId");
				Integer fieldId = (Integer) data.get("FieldId");
				
				Integer coins = 2000;
				Integer scores = 5000;
				Integer times = RandomUtil.nextInt(480, 600);
				Integer length = 2000;
				
				run.setToken(token);
				run.setUserId(userId);
				run.setRunId(runId);
				run.setFieldId(fieldId);
				run.setScores(scores);
				run.setCoins(coins);
				run.setTimes(times);
				run.setLength(length);
				run.setState(1);
				run.setMsg(result);
				run.setStartTime(new Date());
				run.setLastModify(new Date());
				run.update();
				
			}else if (new Date().getTime()-run.getStartTime().getTime() >= run.getTimes()*1000) {
				
				String result = HttpMgr.me.endSchoolRun(run.getToken(), run.getRunId(), run.getScores(), run.getCoins(), run.getTimes(), run.getLength());
				
				run.setState(0);
				run.setMsg(result);
				run.setEndTime(new Date());
				run.setLastModify(new Date());
				run.update();
				
			}else {
				run.setMsg("pending");
				run.setLastModify(new Date());
				run.update();
			}
			
		}
	}

}
