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

import java.sql.Time;
import java.util.Date;

import me.aipao.model._MappingKit;
import me.aipao.web.AdminController;
import me.aipao.web.GlobalInterceptor;
import me.aipao.web.RunController;
import me.aipao.web.UserController;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.json.FastJsonFactory;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.plugin.scheduler.SchedulerPlugin;

/**
 * @author 帮杰<br>
 * 日期：2016年2月22日——6月26日<br>
 * 有效时间：<br>
 * <ul>
 * <li>早上6:00—8:30<br>
 * <li>下午16:00—18:30<br>
 * <li>晚上20:00—23:00<br>
 * </ul>
 * 说明：<br>
 * <ul>
 * <li>指定时间外系统关闭，成绩不记录<br>
 * <li>每天只记录一次有效长跑成绩，一天内跑多次的按一次计算<br>
 * <ul>
 */
public class Aipao extends JFinalConfig {

	public Aipao() {
		loadPropertyFile(Ctx.cfgFileName);
	}

	@Override
	public void configConstant(Constants me) {
		me.setJsonDatePattern(Ctx.datePattern);
		me.setJsonFactory(new FastJsonFactory());
		me.setDevMode(Ctx.JF.devMode);
	}

	@Override
	public void configRoute(Routes me) {
		me.add("admin", AdminController.class);
		me.add("user", UserController.class);
		me.add("run", RunController.class);
	}

	@Override
	public void configPlugin(Plugins me) {
		C3p0Plugin c3p0 = new C3p0Plugin(Ctx.Jdbc.url, Ctx.Jdbc.user, Ctx.Jdbc.pass);
		me.add(c3p0);
		
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0);
		arp.setShowSql(true);
		_MappingKit.mapping(arp);
		me.add(arp);
		
		me.add(new EhCachePlugin());
		
		me.add(new SchedulerPlugin(1, "runner.txt"));
	}

	@Override
	public void configInterceptor(Interceptors me) {
		me.addGlobalActionInterceptor(new GlobalInterceptor());
	}

	@Override
	public void configHandler(Handlers me) {
		// TODO Auto-generated method stub
	}

	public static void main(String[] args) {
		Time time = new Time(new Date().getTime());
		
		System.out.println(time);
		//System.out.println(time1.);
		//JFinal.start("webapp", 8080, "/", 5);
		//2ea1bc86fc4c4050a36a9126bfdcb770
		//redmin:869055025453345
		//vivo:862624026488076
		//String imei = "869055025453345";
		//String key = Cypher.generateKey();
		//String enc = Cypher.encrypt(imei, key);
		//System.out.println(HashKit.md5("862624026488076"));
	}

}
