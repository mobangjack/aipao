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

import java.util.Timer;

import me.aipao.db.C3p0;
import me.aipao.db.DB;
import me.aipao.db.Dao;
import me.aipao.model.DutyCycle;
import me.aipao.util.Prop;

/**
 * @author 帮杰
 */
public class Aipao {

	private Timer timer;
	private DutyCycle dutyCycle;
	
	private Thread thread;
	private Prop prop;
	
	private DB db;
	private Dao dao;
	private Runner runner;
	
	public Aipao() {
		dutyCycle = DutyCycle.parse("2016/2/22-2016/6/26", "6:00-8:30;16:00-18:30;20:00-23:00");
		prop = new Prop("aipao.propertiies");
		db = new C3p0(prop.get("jdbcUrl"), prop.get("user"), prop.get("password"), prop.get("driverClass"));
		dao = new Dao(db);
		runner = new Runner();
		thread = new Thread(runner);
	}
	
	public void start() {
		thread.start();
	}
	
	public static void main(String[] args) {
		Aipao aipao = new Aipao();
		aipao.start();
	}
}
