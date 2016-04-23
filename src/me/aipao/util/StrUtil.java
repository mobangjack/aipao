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

import java.util.UUID;

/**
 * A small String tool kit.
 * @author 帮杰
 *
 */
public class StrUtil {

	public static String uuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	public static boolean isBlank(String s) {
		return s==null||s.trim().equals("");
	}

	public static String firstCharToLowerCase(String s) {
		if(isBlank(s))
			return s;
		return s.substring(0, 1).toLowerCase()+s.substring(1, s.length());
	}
	
	public static String firstCharToUpperCase(String s) {
		if(isBlank(s))
			return s;
		return s.substring(0, 1).toUpperCase()+s.substring(1, s.length());
	}
	
	public static String humpToUnderline(String s) {
		if(isBlank(s))
			return s;
		String str = "";
		for(int i=0;i<s.length();i++){
			char c = s.charAt(i);
			if(c>='A'&&c<='Z'){
				str += (i==0?(""+c):("_"+c)).toLowerCase();
			}else {
				str += c;
			}
		}
		return str;
	}
}
