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

import java.util.Random;


/**
 * @author 帮杰
 */
public class HttpMgr {

	public static final String ROOT_URL = Const.ApiRootUrl;
	
	public static final HttpMgr me = new HttpMgr();
	
	private HttpMgr() {}

	
	public String login(String wxCode, String IMEI) {
		System.out.println("-------------------HttpMgr.login---------------------");
		String url = ROOT_URL + wxCode + "/QM_Users/Login?wxCode=" + wxCode + "&IMEI=" + IMEI;
		System.out.println("url="+url);
		String str = HttpUtil.get(url);
		System.out.println("result="+str);
		return str;
	}
	
	
	public String IMEILogin(String IMEICode) {
		System.out.println("-------------------HttpMgr.IMEILogin---------------------");
		String url = ROOT_URL + "%7Btoken%7D/QM_Users/Login_Android?IMEICode="+IMEICode;
		System.out.println("url="+url);
		String str = HttpUtil.get(url);
		System.out.println("result="+str);
		return str;
	}
	
	public String getLoginInfo(String token) {
		System.out.println("-------------------HttpMgr.getLoginInfo---------------------");
		String url = ROOT_URL + token + "/QM_Users/GetLoginInfoByUserId";
		System.out.println("url="+url);
		String str = HttpUtil.get(url);
		System.out.println("result="+str);
		return str;
	}
	
	public String setLastLatLng(String token, int userId, int fieldId, double lat, double lng) {
		System.out.println("-------------------HttpMgr.setLastLatLng---------------------");
		String url = new StringBuilder(ROOT_URL).append(token)
				.append("/QM_User_Field/SetLastLatLngByField")
				.append("?UserId=").append(userId)
				.append("&FieldId=").append(fieldId)
				.append("&Lat=").append(lat)
				.append("&Lng=").append(lng)
				.toString();
		System.out.println("url="+url);
		String str = HttpUtil.get(url);
		System.out.println("result="+str);
		return str;
	}
	
	public String startSchoolRun(String token, double lat, double lng) {
		System.out.println("-------------------HttpMgr.startSchoolRun---------------------");
		String url = new StringBuilder(ROOT_URL).append(token)
				.append("/QM_Runs/StartRunForSchool")
				.append("?Lat=").append(lat)
				.append("&Lng=").append(lng)
				.append("&RunType=").append("1")
				.append("&RunMode=").append("1")
				.append("&FUserId=").append("0")
				.append("&Level_Length=").append("2000")
				.append("&IsSchool=").append("1")
				.toString();
		System.out.println("url="+url);
		String str = HttpUtil.get(url);
		System.out.println("result="+str);
		return str;
	}
	
	public class Cypher {

		public String generateKey() {
			String tab = "abcdefghijklmnopqrstuvwxyz";
			String key = "";
			Random random = new Random();
			for (int i = 0; i < 10; i++) {
			  int j = random.nextInt(tab.length());
			  key = key + tab.charAt(j);
			  tab = tab.replace(""+tab.charAt(j), "");
			}
			return key;
		}
		
		public String encrypt(Integer num, String key) {
			String str = String.valueOf(num);
			String val = "";
			for (int i = 0; i < str.length(); i++) {
				val = val + key.charAt(Integer.parseInt(""+str.charAt(i)));
			}
			return val;
		}
	}
	
	public String endSchoolRun(String token, String runId, int scores, int goldCoins, int times, int length) {
		System.out.println("-------------------HttpMgr.endSchoolRun---------------------");
		Cypher cypher = new Cypher();
		String key = cypher.generateKey();
		String encryptScores = cypher.encrypt(scores, key);
		String encryptGoldCoins = cypher.encrypt(goldCoins, key);
		String encryptLength = cypher.encrypt(length, key);
		String encryptTimes = cypher.encrypt(times, key);
		String url = new StringBuilder(ROOT_URL).append(token)
				.append("/QM_Runs/EndRunForSchool")
				.append("?S1=").append(runId)
				.append("&S2=").append(encryptScores)
				.append("&S3=").append(encryptGoldCoins)
				.append("&S4=").append(encryptLength)
				.append("&S5=").append(encryptTimes)
				.append("&S6=").append("")
				.append("&S7=").append("1")
				.append("&S8=").append(key)
				.toString();
		System.out.println("url="+url);
		String str = HttpUtil.get(url);
		System.out.println("result="+str);
		return str;
	}
	
}
