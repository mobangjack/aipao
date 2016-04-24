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

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Table;
import com.jfinal.plugin.activerecord.TableMapping;
import com.jfinal.plugin.ehcache.CacheKit;

/**
 * @author 帮杰
 *
 */
public class ActiveModel {

	@SuppressWarnings("rawtypes")
	public static <M extends Model> void save(M model) {
		if (model.save()) {
			Table table = TableMapping.me().getTable(model.getClass());
			CacheKit.put(table.getName(), model.get(table.getPrimaryKey()[0]), model);
		}
	}

	@SuppressWarnings("rawtypes")
	public static <M extends Model> void delete(M model) {
		if (model.delete()) {
			Table table = TableMapping.me().getTable(model.getClass());
			CacheKit.remove(table.getName(), model.get(table.getPrimaryKey()[0]));
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static <M extends Model> void update(M model) {
		if (model.update()) {
			Table table = TableMapping.me().getTable(model.getClass());
			CacheKit.put(table.getName(), model.get(table.getPrimaryKey()[0]), model);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <M extends Model> M find(Class<M> modelClass,Object key) {
		Table table = TableMapping.me().getTable(modelClass);
		M model = CacheKit.get(table.getName(), key);
		if (model==null) {
			try {
				model = (M) modelClass.newInstance().findById(key);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return model;
	}
	
	@SuppressWarnings("rawtypes")
	public static <M extends Model> long getExpire(Class<M> modelClass) {
		Table table = TableMapping.me().getTable(modelClass);
		return CacheKit.getCacheManager().getCache(table.getName()).getCacheConfiguration().getTimeToLiveSeconds();
	}
	
}
