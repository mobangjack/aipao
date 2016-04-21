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
package me.aipao.util;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

/**
 * @author 帮杰
 */
@SuppressWarnings({"unchecked"})
public class Json {

	private Map<String, Object> map;
	
	public Json() {
		map = new HashMap<String, Object>();
	}

	public Json(String json) {
		map = new Gson().fromJson(json, Map.class);
	}
	
	public Json put(String key, Object value) {
		map.put(key, value);
		return this;
	}
	
	public <T> T get(String key) {
		return (T) Node.get(map, key);
	}
	
	public <T> T get(String key, T defaultValue) {
		T target = get(key);
		return target==null?defaultValue:target;
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(map);
	}
	
}
