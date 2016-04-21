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
public class TimePoint {

	private Integer hours;
	private Integer minutes;
	
	public TimePoint() {
	}

	public TimePoint(Integer hours, Integer minutes) {
		this.hours = hours;
		this.minutes = minutes;
	}
	
	public Integer getHours() {
		return hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}

	public Integer getMinutes() {
		return minutes;
	}

	public void setMinutes(Integer minutes) {
		this.minutes = minutes;
	}

	public Boolean before(TimePoint timePoint) {
		if(hours<timePoint.hours)
			return true;
		if (hours==timePoint.hours)
			if(minutes<timePoint.minutes)
				return true;
		return false;		
	}
	
	public Boolean after(TimePoint timePoint) {
		if(hours>timePoint.hours)
			return true;
		if (hours==timePoint.hours)
			if(minutes>timePoint.minutes)
				return true;
		return false;
	}
	
	public Boolean between(TimePoint start, TimePoint end) {
		return after(start)&&before(end);
	}
	
	public Boolean between(TimeLine timeLine) {
		return between(timeLine.getStartTimePoint(), timeLine.getEndTimePoint());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TimePoint) {
			TimePoint timePoint = (TimePoint) obj;
			return hours==timePoint.hours&&minutes==timePoint.minutes;
		}
		return super.equals(obj);
	}
	
	public static TimePoint parse(String timePoint) {
		String[] s = timePoint.split(":");
		if (s.length != 2) {
			throw new RuntimeException("timePoint is invalid!");
		}
		int hours = Integer.parseInt(s[0]);
		int minutes = Integer.parseInt(s[1]);
		return new TimePoint(hours, minutes);
	}
}
