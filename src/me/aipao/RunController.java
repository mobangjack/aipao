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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.aipao.model.Run;
import me.aipao.model.User;
import me.aipao.util.JsonUtil;

import com.jfinal.kit.StrKit;

/**
 * @author 帮杰
 */
public class RunController extends BaseController {

	public void index() {
		User user = getUser();
		if (user.getAdmin()) {
			List<Run> runs = Run.dao.find("select * from run");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("runs", runs);
			success(map);
		}else {
			show();
		}
	}
	
	public void show() {
		User user = getUser();
		String imei = getPara("imei");
		if (user.getAdmin()) {
			success(Run.dao.findById(imei));
			return;
		}
		if (StrKit.isBlank(user.getImei())) {
			success("you havn't registered your run task yet");
			return;
		}
		success(Run.dao.findById(user.getImei()));
	}
	
	public void add() {
		String imei = getPara("imei");
		if (StrKit.isBlank(imei)) {
			failure("imei can not be blank");
			return;
		}
		if (imei.trim().length() != 32) {
			failure("imei length should be 32");
			return;
		}
		Run run = Run.dao.findById(imei);
		if (run != null) {
			failure("this imei has been registerd");
			return;
		}
		String result = HttpMgr.me.login(imei);
		if (result == null) {
			failure("something wrong happened,please try again later");
			return;
		}
		Map<String, Object> map = JsonUtil.parse(result);
		Boolean success = (Boolean) map.get("Success");
		if (!success) {
			failure("this imei is not valid");
			return;
		}
		
		User user = getUser();
		user.setImei(imei);
		user.update();
		
		run = new Run();
		run.setImei(imei);
		run.save();
		
		success("imei registered");
	}
	
	public void delete() {
		User user = getUser();
		if (StrKit.isBlank(user.getImei())) {
			failure("you have not registerd your imei code yet");
			return;
		}
		String imei = getPara("imei");
		if (StrKit.isBlank(imei)) {
			failure("imei can not be blank");
			return;
		}
		if (!user.getImei().equals(imei)) {
			failure("this imei code doesn't look like your previous one");
			return;
		}
		Run.dao.deleteById(imei);
		success();
	}
	
}
