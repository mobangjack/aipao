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

import me.aipao.util.PropUtil;

import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;

/**
 * @author 帮杰
 */
public interface Const {

	String configFileName = "config.txt";
	String runnerFileName = "runner.txt";
	String datePattern = "yyyy-MM-dd hh:mm:ss";
	
	Prop config = PropKit.use(configFileName);
	Prop runner = PropKit.use(runnerFileName);
	
	interface JF {
		boolean devMode = config.getBoolean("jfinal.devMode", false);
	}
	
	interface Jdbc {
		String url = config.get("jdbc.url");
		String user = config.get("jdbc.user");
		String pass = config.get("jdbc.pass");
	}
	
	interface CacheName {
		String admin = "admin";
		String user = "user";
	}
	
	interface AttrName {
		String token = "token";
		String admin = "admin";
		String user = "user";
	}
	
	interface Runner {
		Timeslots timeslots = Timeslots.parse(runner.get("runner.timeslots", "6:00:00-8:30:00,16:00:00-18:30:00,20:00:00-23:00:00"));
		Integer fieldId = runner.getInt("runner.fieldId", 99);
		float lastLat = PropUtil.getFloat(runner, "runner.lastLat", 30.545273f);
		float lastLng = PropUtil.getFloat(runner, "runner.lastLng", 114.365715f);
		float lat = PropUtil.getFloat(runner, "runner.lat", 30.544746f);
		float lng = PropUtil.getFloat(runner, "runner.lng", 114.365715f);
		Integer coins = runner.getInt("runner.coins", 2000);
		Integer scores = runner.getInt("runner.scores", 5000);
		Integer minTime = runner.getInt("runner.minTime", 480);
		Integer maxTime = runner.getInt("runner.maxTime", 600);
		Integer length = runner.getInt("runner.length", 2000);
	}
}
