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

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.aipao.db.Dao;
import me.aipao.model.TimePoint;

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
public class Runner extends TimerTask {
	
	private Timer timer;
	private TimePoint timePoint;
	
	class PullTask extends TimerTask {

		@Override
		public void run() {
			List<Run> runs = Dao.me.getRuns();
			for (Run run : runs) {
				if (run.done()) {
					
				}
			}
			
		}
		
	}
	
	class PushTask extends TimerTask {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	@Override
	public void run() {
		List<Run> runs = Dao.me.getRuns();
		for (Run run : runs) {
			if (run.done()) {
				
			}
		}
	}
	
	public void stop() {
		List<Run> runs = Dao.me.getRuns();
	}
	
}
