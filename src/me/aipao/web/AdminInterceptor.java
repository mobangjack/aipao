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

import me.aipao.Const;
import me.aipao.model.Admin;
import me.aipao.util.CacheUtil;
import me.aipao.util.DateUtil;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

/**
 * @author 帮杰
 */
public class AdminInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {
		Controller c = inv.getController();
		String token = c.getAttr(Const.AttrName.token);
		Admin admin = CacheUtil.get(Const.CacheName.admin, token);
		if (admin == null) {
			admin = Admin.dao.findFirst("select * from admin where token=? limit 1", token);
			if (admin == null) {
				Result.unauth("token invalid").render(c);
			}else {
				if (admin.getLogin() == null) {
					Result.unauth("token out of date").render(c);
				}else if (DateUtil.isExpire(admin.getLogin(), CacheUtil.getTimeToLiveSeconds(Const.CacheName.admin)*1000)) {
					Result.unauth("token expired").render(c);
				}else {
					CacheUtil.put(Const.AttrName.admin, token, admin);
					c.setAttr(Const.AttrName.admin, admin);
					inv.invoke();
				}
			}
		}else {
			c.setAttr(Const.AttrName.admin, admin);
			inv.invoke();
		}
	}

}
