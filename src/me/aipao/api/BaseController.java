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
package me.aipao.api;

import me.aipao.Const;
import me.aipao.model.User;

import com.jfinal.core.Controller;

/**
 * @author 帮杰
 */
public class BaseController extends Controller {

	public User getUser() {
		return getAttr(Const.AttrName.user);
	}
	
	public void success(String msg, Object data) {
		Result.success(msg, data).render(this);
	}
	
	public void success(Object data) {
		Result.create(0, "ok", data).render(this);
	}
	
	public void success() {
		Result.success("ok").render(this);
	}
	
	public void failure(String msg) {
		Result.failure(msg).render(this);
	}
	
	public void failure() {
		Result.failure("err").render(this);
	}

}
