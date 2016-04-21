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
package me.aipao.db;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * C3p0.
 * @author 帮杰
 *
 */
public class C3p0 extends Jdbc implements DB {

	private int maxPoolSize = 100;
	private int minPoolSize = 10;
	private int initialPoolSize = 10;
	private int maxIdleTime = 20;
	private int acquireIncrement = 2;
	
	private ComboPooledDataSource dataSource;
	
	public C3p0() {
		super();
	}
	
	public C3p0(String jdbcUrl, String user, String password, String driverClass, Integer maxPoolSize, Integer minPoolSize, Integer initialPoolSize, Integer maxIdleTime, Integer acquireIncrement) {
		super(jdbcUrl, user, password, driverClass);
		this.maxPoolSize = maxPoolSize != null ? maxPoolSize : this.maxPoolSize;
		this.minPoolSize = minPoolSize != null ? minPoolSize : this.minPoolSize;
		this.initialPoolSize = initialPoolSize != null ? initialPoolSize : this.initialPoolSize;
		this.maxIdleTime = maxIdleTime != null ? maxIdleTime : this.maxIdleTime;
		this.acquireIncrement = acquireIncrement != null ? acquireIncrement : this.acquireIncrement;
	}
	
	public C3p0(String jdbcUrl, String user, String password) {
		this(jdbcUrl, user, password, null);
	}
	
	public C3p0(String jdbcUrl, String user, String password, String driverClass) {
		this(jdbcUrl, user, password, driverClass, null, null, null, null, null);
	}
	
	
	@Override
	public Boolean open() {
		if(dataSource==null){
			dataSource = new ComboPooledDataSource();
			dataSource.setJdbcUrl(getJdbcUrl());
			dataSource.setUser(getUser());
			dataSource.setPassword(getPassword());
			try {dataSource.setDriverClass(getDriverClass());}
			catch (PropertyVetoException e) {dataSource = null;e.printStackTrace(); throw new RuntimeException(e);} 
			dataSource.setMaxPoolSize(maxPoolSize);
			dataSource.setMinPoolSize(minPoolSize);
			dataSource.setInitialPoolSize(initialPoolSize);
			dataSource.setMaxIdleTime(maxIdleTime);
			dataSource.setAcquireIncrement(acquireIncrement);
		}
		return true;
	}
	
	@Override
	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	@Override
	public Boolean close() {
		dataSource.close();
		return true;
	}
	
	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public int getMinPoolSize() {
		return minPoolSize;
	}

	public void setMinPoolSize(int minPoolSize) {
		this.minPoolSize = minPoolSize;
	}

	public int getInitialPoolSize() {
		return initialPoolSize;
	}

	public void setInitialPoolSize(int initialPoolSize) {
		this.initialPoolSize = initialPoolSize;
	}

	public int getMaxIdleTime() {
		return maxIdleTime;
	}

	public void setMaxIdleTime(int maxIdleTime) {
		this.maxIdleTime = maxIdleTime;
	}

	public int getAcquireIncrement() {
		return acquireIncrement;
	}

	public void setAcquireIncrement(int acquireIncrement) {
		this.acquireIncrement = acquireIncrement;
	}

}
