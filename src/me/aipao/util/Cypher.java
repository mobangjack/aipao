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

import java.util.Random;

/**
 * @author 帮杰
 */
public class Cypher {

	public static String generateKey() {
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
	
	public static String encrypt(String str, String key) {
		String val = "";
		for (int i = 0; i < str.length(); i++) {
			val = val + key.charAt(Integer.parseInt(""+str.charAt(i)));
		}
		return val;
	}
	
	public static String encrypt(long num, String key) {
		String str = String.valueOf(num);
		return encrypt(str, key);
	}

}
