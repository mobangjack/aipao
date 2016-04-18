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

import com.google.gson.Gson;

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
@SuppressWarnings("rawtypes")
public class Runner implements Runnable {

	public static final String TOKEN = "TOKEN";
	public static final String RUNID = "RUNID";
	
	public static final String API_ROOT_URL = "http://client2.aipao.me/api/";
	
	public static final String IMEI = "2ea1bc86fc4c4050a36a9126bfdcb770";
	
	public static final String LOGIN_URL = API_ROOT_URL
			+ "%7Btoken%7D/QM_Users/Login_Android?"
			+ "IMEICode="
			+ IMEI;
	
	public static final String GET_INFO_URL = API_ROOT_URL
			+ TOKEN
			+ "/QM_Users/GetLoginInfoByUserId";
	
	public static final String SET_LOC_URL = API_ROOT_URL
			+ TOKEN
			+ "/QM_User_Field/SetLastLatLngByField?"
			+ "UserId=27187&"
			+ "FieldId=0&Lat=30.545273&Lng=114.365715";
	
	public static final String START_RUN_URL = API_ROOT_URL
			+ TOKEN
			+ "/QM_Runs/StartRunForSchool?"
			+ "Lat=30.544746&Lng=114.366612&RunType=1&RunMode=1&FUserId=0&"
			+ "Level_Length=2000&IsSchool=1";
	
	public static final String STOP_RUN_URL = API_ROOT_URL
			+ TOKEN
			+ "/QM_Runs/EndRunForSchool?"
			+ "S1="
			+ RUNID
			+ "&S2=cuuu&S3=xuuu&"
			+ "S4=csc&S5=xuus&S6=&S7=1&S8=utxsocwdjy";
	
	
	Gson gson = new Gson();
	
	@Override
	public void run() {
		
		/*
		System.out.println("--------------------Login--------------------");
		String url = LOGIN_URL;
		System.out.println("url:"+url);
		String s = HttpUtil.get(url);
		System.out.println(s);
		
		Map map = gson.fromJson(s, Map.class);
		Map data = (Map) map.get("Data");
		final String token = (String) data.get("Token");
		
		
		System.out.println("--------------------Get User Info--------------------");
		url = GET_INFO_URL.replace(TOKEN, token);
		System.out.println("url:"+url);
		s = HttpUtil.get(url);
		System.out.println(s);
		
		System.out.println("--------------------Set Last Location--------------------");
		url = SET_LOC_URL.replace(TOKEN, token);
		System.out.println("url:"+url);
		s = HttpUtil.get(url);
		System.out.println(s);
		
		
		System.out.println("--------------------Start School Run--------------------");
		url = START_RUN_URL.replace(TOKEN, token);
		System.out.println("url:"+url);
		s = HttpUtil.get(url);
		System.out.println(s);
		
		map = gson.fromJson(s, Map.class);
		data = (Map) map.get("Data");
		String runId = (String) data.get("RunId");
		
		System.out.println("--------------------Stop School Run---------------------");
		url = STOP_RUN_URL.replace(TOKEN, token).replace(RUNID, runId);
		System.out.println("url:"+url);
		
		try {Thread.sleep(8*3600*1000);} catch (InterruptedException e) {}
		
		s = HttpUtil.get(url);
		System.out.println(s);
		*/
		/*
		System.out.println("--------------------Stop School Run---------------------");
		url = STOP_RUN_URL.replace(TOKEN, token).replace(RUNID, runId);
		System.out.println("url:"+url);
		s = HttpUtil.get(url);
		System.out.println(s);
		*/
		
		
		String url = "http://client2.aipao.me/api/f7e37acb66af419dac3b4ad2e1f19770/QM_Runs/EndRunForSchool?S1=aae9021849e04507804fcffffdfa9a81&S2=cuuu&S3=xuuu&S4=csc&S5=xuus&S6=&S7=1&S8=utxsocwdjy";
		System.out.println("url:"+url);
		String s = HttpUtil.get(url);
		System.out.println(s);
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Runner runner = new Runner();
		runner.run();
		
	}
	
}
