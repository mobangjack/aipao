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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author 帮杰
 *
 */
/**
 * Prop loads properties from files.
 * @author 帮杰
 *
 */
public class Prop {

	private Properties prop = null;
	
	public Prop() {}
	
	public Prop(String file) {
		load(file);
	}
	
	public Prop(String file,String encoding) {
		load(file, encoding);
	}
	
	public Prop load(String file) {
		return load(file, "UTF-8");
	}
	
	public Prop load(String file,String encoding) {
		InputStream input = null;
		try {
			input = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
			if(input==null)
				throw new RuntimeException("fail to load file '"+file+"' from class path.");
			prop = new Properties();
			prop.load(new InputStreamReader(input, encoding));
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if(input!=null){try {input.close();input = null;} catch (IOException e) {e.printStackTrace();}}
		}
		return this;
	}
	
	public Properties getProperties() {
		return prop;
	}
	
	public String get(String key) {
		return prop.getProperty(key);
	}
	
}
