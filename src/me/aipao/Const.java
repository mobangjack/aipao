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

/**
 * @author 帮杰
 */
public interface Const {

	String ApiRootUrl = "http://client2.aipao.me/api/";
	String WeiXinAppID = "wx668004190386482e";
	
	String IMEI = "2ea1bc86fc4c4050a36a9126bfdcb770";
	
	interface Grade {
		Integer Score = 5000;
		Integer GoldCoins = 2000;
		Integer Times = 535;
		Integer Len = 2000;
	}
	
	interface RunState {
		String Running = "running";
		String Stopped = "stopped";
	}
}
