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
package me.aipao.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import me.aipao.model.User;

import com.jfinal.aop.Clear;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.ehcache.CacheKit;

/**
 * @author 帮杰
 */
public class UserController extends BaseController {

	@Clear
	public void register() {
		String name = getPara("name");
		String pass = getPara("pass");
		if (StrKit.isBlank(name) || StrKit.isBlank(pass)) {
			failure("name or pass can not be blank");
			return;
		}
		User user = new User();
		user.setName(name);
		user.setPass(pass);
		user.save();
		success();
	}

	@Clear
	public void login() {
		String name = getPara("name");
		String pass = getPara("pass");
		if (StrKit.isBlank(name) || StrKit.isBlank(pass)) {
			failure("name or pass can not be blank");
			return;
		}
		User user = User.dao.findFirst("select * from user where name=? and pass=?", name, pass);
		if (user == null) {
			failure("user not exits");
		}else {
			String token = user.getToken();
			if (StrKit.notBlank(token)) {
				CacheKit.remove("user", token);
			}
			token = UUID.randomUUID().toString().replace("-", "");
			user.setToken(token);
			user.setLogin(new Date());
			user.update();
			CacheKit.put("user", token, user);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("token", token);
			success(map);
		}
	}
	
	public void index() {
		User user = getUser();
		if (user.getAdmin()) {
			List<User> users = User.dao.find("select * from user");
			success(users);
		}else {
			success(user);
		}
	}
}
