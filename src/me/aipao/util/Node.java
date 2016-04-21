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

import java.util.List;
import java.util.Map;


/**
 * Node can resolve JSON path and extract the target data from a JsonObject directly.
 * This is helpful when what you need in a JsonObject is just a piece of data.<br>
 * @author 帮杰
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class Node {

	private Node() {}
	
	private static <T> T nextNode(Map currentNode, String path) {
		if (path.matches("\\w+\\[\\d+\\]")) {
			String key = path.substring(0,path.indexOf('['));
			List list = (List) currentNode.get(key);
			int index = Integer.parseInt(path.substring(path.indexOf('[')+1, path.lastIndexOf(']')));
			return (T) list.get(index);
		}else {
			return (T) currentNode.get(path);
		}
	}
	
	public static <T> T get(Map root,String path) {
		String[] array = path.replaceAll("\\s", "").split("\\.");
		Map<String, Object> currentNode = root;
		for (int i = 0; i < array.length-1; i++) {
			currentNode = nextNode(currentNode, array[i]);
		}
		return nextNode(currentNode, array[array.length-1]);
	}
	
}
