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
public class Timeslot {

	private Time startTime;
	private Time endTime;
	
	public Timeslot() {
		// TODO Auto-generated constructor stub
	}

	public Timeslot(Time startTime, Time endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

	public Long millis() {
		return startTime.getTime() - endTime.getTime();
	}
	
	public Long second() {
		return millis()/1000;
	}
	
	public Boolean contains(Time time) {
		return time.after(startTime) && time.before(endTime);
	}
	
	private Time currentTime() {
		return Time.valueOf(new Time(new Date().getTime()).toString());
	}
	
	public Boolean containsNow() {
		return contains(currentTime());
	}
	
	public Long millisLeft() {
		if (containsNow()) {
			return endTime.getTime() - currentTime().getTime();
		}
		return 0L;
	}
	
	public Long secondLeft() {
		return millisLeft()/1000;
	}
	
	private static final String INFO = "The given string should be like this: \"hh:mm:ss-hh:mm:ss\",such as \"6:00:00-8:30:00\"";
	/**
	 * The given string should be like this: "hh:mm:ss-hh:mm:ss",such as "6:00:00-8:30:00".
	 * @param timescale "hh:mm:ss-hh:mm:ss"
	 * @return
	 */
	public static Timeslot parse(String s) {
		String[] strings = s.split("-");
		if (strings.length != 2) {
			throw new IllegalArgumentException(INFO);
		}
		Time startTime = Time.valueOf(strings[0]);
		Time endTime = Time.valueOf(strings[1]);
		return new Timeslot(startTime, endTime);
	}
	
	@Override
	public String toString() {
		return startTime.toString()+"-"+endTime.toString();
	}
	
	public static void main(String[] args) {
		Timeslot timeslot = Timeslot.parse("06:00:00-00:55:00");
		System.out.println(timeslot);
		System.out.println(timeslot.millisLeft());
	}
}
