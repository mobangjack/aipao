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

import me.aipao.Ctx;
import me.aipao.model.Admin;
import me.aipao.model.Run;
import me.aipao.model.User;
import me.aipao.util.CacheUtil;
import me.aipao.util.StrUtil;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;

/**
 * @author 帮杰
 */

@Before(AdminInterceptor.class)
public class AdminController extends BaseController {

	@Clear
	public void login() {
		String name = getPara("name");
		String pass = getPara("pass");
		if (StrUtil.isBlank(name) || StrUtil.isBlank(pass)) {
			failure("name or pass can not be blank");
			return;
		}
		Admin admin = Admin.dao.findFirst("select * from admin where name=? and pass=?", name, pass);
		if (admin == null) {
			failure("admin not exits");
		}else {
			String token = admin.getToken();
			if (StrUtil.notBlank(token)) {
				CacheUtil.remove(Ctx.Cache.admin, token);
			}
			token = UUID.randomUUID().toString().replace("-", "");
			admin.setToken(token);
			admin.setLogin(new Date());
			admin.update();
			CacheUtil.put(Ctx.Cache.admin, token, admin);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("token", token);
			success(map);
		}
	}

	public void index() {
		Admin admin = getAttr(Ctx.Attr.admin);
		Admin master = new Admin()._setAttrs(admin).remove("pass");
		success(master);
	}
	
	public void checkUsers() {
		List<User> users = User.dao.find("select * from user");
		success(users);
	}
	
	public void checkRuns() {
		List<Run> runs = Run.dao.find("select * from run");
		success(runs);
	}
	
	public void deleteUser() {
		String id = getPara("id");
		if (StrUtil.isBlank(id)) {
			failure("id missing");
			return;
		}
		User user = User.dao.findById(id);
		if (user == null) {
			failure("user not exits");
			return;
		}
		user.delete();
		success();
	}
	
	public void deleteRun() {
		String id = getPara("id");
		if (StrUtil.isBlank(id)) {
			failure("id missing");
			return;
		}
		Run run = Run.dao.findById(id);
		if (run == null) {
			failure("run not exits");
			return;
		}
		run.delete();
		success();
	}
}
