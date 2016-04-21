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

import java.util.Calendar;


/**
 * @author 帮杰
 */
public class DutyCycle {

	private Date startDate;
	private Date endDate;
	private TimeLine[] timeLines;
	
	public DutyCycle() {
	}

	public DutyCycle(Date startDate, Date endDate, TimeLine[] timeLines) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.timeLines = timeLines;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public TimeLine[] getTimeLines() {
		return timeLines;
	}

	public void setTimeLines(TimeLine[] timeLines) {
		this.timeLines = timeLines;
	}

	public static DutyCycle parse(String date, String time) {
		String s[] = date.split("-");
		if (s.length != 2) {
			throw new RuntimeException("date is invalid!");
		}
		Date startDate = Date.parse(s[0]);
		Date endDate = Date.parse(s[1]);
		s = time.split(";");
		TimeLine[] timeLines = new TimeLine[s.length];
		for (int i = 0; i < s.length; i++) {
			timeLines[i] = TimeLine.parse(s[i]);
		}
		return new DutyCycle(startDate, endDate, timeLines);
	}

	public boolean isNow() {
		int years = Calendar.getInstance().get(Calendar.YEAR);
		int months = Calendar.getInstance().get(Calendar.MONTH);
		int days = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		Date date = new Date(years, months, days);
		if (date.between(startDate, endDate)) {
			int hours = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
			int minutes = Calendar.getInstance().get(Calendar.MINUTE);
			TimePoint timePoint = new TimePoint(hours, minutes);
			for (TimeLine timeLine : timeLines) {
				if (timePoint.between(timeLine)) {
					return true;
				}
			}
		}
		return false;
	}
}
