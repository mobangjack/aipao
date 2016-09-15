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

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import me.aipao.util.HttpUtil;

import com.jfinal.kit.PropKit;


/**
 * @author 帮杰
 */
public class HttpMgr {

	//private static final Log LOG = Log.getLog(HttpMgr.class);
	
	private String rootUrl = "http://client3.aipao.me/api/";
	private String version = "1.27";

	public static final HttpMgr me = new HttpMgr();
	
	public HttpMgr() {
		rootUrl = PropKit.use("config.txt").get("httpMgr.rootUrl", rootUrl);
		version = PropKit.use("config.txt").get("httpMgr.version", version);
	}
	
	public Map<String, String> getHeader() {
		Map<String, String> header = new HashMap<String, String>();
		header.put("version", version);
		return header;
	}
	
	public String get(String url) {
		url = rootUrl+url;
		System.out.println("url="+url);
		String ret = HttpUtil.get(url, null, getHeader());
		System.out.println("result="+ret);
		return ret;
	}
	
	public String login(String wxCode, String imei) {
		System.out.println("-------------------HttpMgr.login---------------------");
		String url = wxCode + "/QM_Users/Login?wxCode=" + wxCode + "&IMEI=" + imei;
		String ret = get(url);
		return ret;
	}
	
	public String login(String imei) {
		System.out.println("-------------------HttpMgr.login---------------------");
		String url = "%7Btoken%7D/QM_Users/Login_AndroidSchool?IMEICode="+imei;
		String ret = get(url);
		return ret;
	}
	
	public String getLoginInfo(String token) {
		System.out.println("-------------------HttpMgr.getLoginInfo---------------------");
		String url = token + "/QM_Users/GetLoginInfoByUserId";
		String ret = get(url);
		return ret;
	}
	
	public String setLastLatLng(String token, int userId, int fieldId, float lat, float lng) {
		System.out.println("-------------------HttpMgr.setLastLatLng---------------------");
		String url = new StringBuilder(token)
				.append("/QM_User_Field/SetLastLatLngByField")
				.append("?UserId=").append(userId)
				.append("&FieldId=").append(fieldId)
				.append("&Lat=").append(lat)
				.append("&Lng=").append(lng)
				.toString();
		String ret = get(url);
		return ret;
	}
	
	public String startSchoolRun(String token, float lat, float lng) {
		System.out.println("-------------------HttpMgr.startSchoolRun---------------------");
		String url = new StringBuilder(token)
				.append("/QM_Runs/StartRunForSchool")
				.append("?Lat=").append(lat)
				.append("&Lng=").append(lng)
				.append("&RunType=").append("1")
				.append("&RunMode=").append("1")
				.append("&FUserId=").append("0")
				.append("&Level_Length=").append("2000")
				.append("&IsSchool=").append("1")
				.toString();
		String ret = get(url);
		return ret;
	}
	
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
	
	public String encrypt(int num, String key) {
		String str = String.valueOf(num);
		String val = "";
		for (int i = 0; i < str.length(); i++) {
			val = val + key.charAt(Integer.parseInt(""+str.charAt(i)));
		}
		return val;
	}
	
	public String endSchoolRun(String token, String runId, int scores, int coins, int times, int length) {
		System.out.println("-------------------HttpMgr.endSchoolRun---------------------");
		String key = generateKey();
		String encryptScores = encrypt(scores, key);
		String encryptCoins = encrypt(coins, key);
		String encryptTimes = encrypt(times, key);
		String encryptLength = encrypt(length, key);
		String url = new StringBuilder(token)
				.append("/QM_Runs/EndRunForSchool")
				.append("?S1=").append(runId)
				.append("&S2=").append(encryptScores)
				.append("&S3=").append(encryptCoins)
				.append("&S4=").append(encryptTimes)
				.append("&S5=").append(encryptLength)
				.append("&S6=").append("")
				.append("&S7=").append("1")
				.append("&S8=").append(key)
				.toString();
		String ret = get(url);
		return ret;
	}

	public String buyDiamond(String token, int diamond) {
	    return get(token + "/QM_User_Static/BuyDiamond?Diamond=" + diamond);
	}

	public String buyGold(String token, int gold) {
		return get(token + "/QM_User_Static/BuyGold?Gold=" + gold);
	}

	public String buyPerson(String token, int person) {
		return get(token + "/QM_User_Person/BuyPerson?PersonId=" + person);
	}

	public String buyPower(String token, int power) {
		return get(token + "/QM_User_Static/BuyPower?Power=" + power);
	}
  
	public String getRootUrl() {
		return rootUrl;
	}

	public void setRootUrl(String rootUrl) {
		this.rootUrl = rootUrl;
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	public static void main(String[] args) {
		System.out.println(me.login("29108b15533a45a0b75ce83763e818d9"));
	}
}
