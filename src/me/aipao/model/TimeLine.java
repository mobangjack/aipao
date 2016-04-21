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
package me.aipao.model;


/**
 * @author 帮杰
 */
public class TimeLine {

	private TimePoint startTimePoint;
	private TimePoint endTimePoint;
	
	public TimeLine() {
	}
	
	public TimeLine(TimePoint startTimePoint, TimePoint endTimePoint) {
		this.startTimePoint = startTimePoint;
		this.endTimePoint = endTimePoint;
	}

	public TimeLine(Integer hours1, Integer minutes1, Integer hours2, Integer minutes2) {
		startTimePoint = new TimePoint(hours1, minutes1);
		endTimePoint = new TimePoint(hours2, minutes2);
	}
	
	public TimePoint getStartTimePoint() {
		return startTimePoint;
	}

	public void setStartTimePoint(TimePoint startTimePoint) {
		this.startTimePoint = startTimePoint;
	}

	public TimePoint getEndTimePoint() {
		return endTimePoint;
	}

	public void setEndTimePoint(TimePoint endTimePoint) {
		this.endTimePoint = endTimePoint;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TimeLine) {
			TimeLine timeLine = (TimeLine) obj;
			return timeLine.startTimePoint.equals(startTimePoint)&&timeLine.endTimePoint.equals(endTimePoint);
		}
		return super.equals(obj);
	}
	
	public static TimeLine parse(String timeLine) {
		String[] s = timeLine.split("-");
		if (s.length != 2) {
			throw new RuntimeException("timeLine is invalid!");
		}
		TimePoint startTimePoint = TimePoint.parse(s[0]);
		TimePoint endTimePoint = TimePoint.parse(s[1]);
		return new TimeLine(startTimePoint, endTimePoint);
	}
	
}
