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

import java.util.ArrayList;

/**
 * @author 帮杰
 */
public class Timeslots extends ArrayList<Timeslot>{

	private static final long serialVersionUID = -8007278862466816220L;

	public static Timeslots parse(String s) {
		Timeslots timeslots = new Timeslots();
		String[] strings = s.split(",");
		for (String string : strings) {
			timeslots.add(Timeslot.parse(string));
		}
		return timeslots;
	}
	
	public static void main(String[] args) {
		Timeslots timeslots = Timeslots.parse("6:00:00-8:30:00,16:00:00-18:30:00,20:00:00-23:00:00");
		System.out.println(timeslots);
	}
}
