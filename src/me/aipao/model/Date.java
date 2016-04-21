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
public class Date {
	
	private Integer years;
	private Integer months;
	private Integer days;
	
	public Date() {
		this(Calendar.getInstance());
	}

	public Date(Calendar calendar) {
		years = calendar.get(Calendar.YEAR);
		months = calendar.get(Calendar.MONTH);
		days = calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	public Date(Integer years, Integer months, Integer days) {
		this.years = years;
		this.months = months;
		this.days = days;
	}
	
	public Integer getYears() {
		return years;
	}

	public void setYears(Integer years) {
		this.years = years;
	}

	public Integer getMonths() {
		return months;
	}

	public void setMonths(Integer months) {
		this.months = months;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Boolean before(Date date) {
		if(years<date.years)
			return true;
		if(years==date.years) {
			if (months<date.months)
				return true;
			if (months==date.months)
				if (days<date.days)
					return true;
		}
		return false;	
	}
	
	public Boolean after(Date date) {
		if(years>date.years)
			return true;
		if(years==date.years) {
			if (months>date.months)
				return true;
			if (months==date.months)
				if (days>date.days)
					return true;
		}
		return false;	
	}
	
	public Boolean between(Date start, Date end) {
		return after(start)&&before(end);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Date) {
			Date date = (Date) obj;
			return years==date.years&&months==date.months&&days==date.days;
		}
		return super.equals(obj);
	}
	
	public static Date parse(String date) {
		String s[] = date.split("/");
		if (s.length != 3) {
			throw new RuntimeException("date is invalid!");
		}
		int years = Integer.parseInt(s[0]);
		int months = Integer.parseInt(s[1]);
		int days = Integer.parseInt(s[2]);
		return new Date(years, months, days);
	}
}
