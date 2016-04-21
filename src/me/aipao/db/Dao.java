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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import me.aipao.Run;

/**
 * @author 帮杰
 */
public class Dao {
	
	private static final Object[] NULL = new Object[0];
	
	public static final Dao me = new Dao(new C3p0());
	
	private DB db;
	
	public Dao(DB db) {
		this.db = db;
	}
	
	public List<Run> getRuns() {
		List<Run> runs = new ArrayList<Run>();
		String sql = "select * from Run";
		Connection con = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		try {
			con = db.getConnection();
			sm = prepareStatement(con, sql, NULL);
			rs = sm.executeQuery();
			while (rs.next()) {
				Run run = new Run();
				run.setId(rs.getString("id"));
				run.setImei(rs.getString("imei"));
				run.setToken(rs.getString("token"));
				runs.add(run);
			}
		} catch (SQLException e) {
			close(rs, sm, con);
		}
		return runs;
	}

	public static PreparedStatement prepareStatement(Connection con, String sql, Object...params) throws SQLException {
		PreparedStatement sm = con.prepareStatement(sql);;
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				sm.setObject(i+1, params[i]);
			}
		}
		return sm;
	}
	
	public static void close(ResultSet rs, Statement sm, Connection con) {
		try {
			if(rs != null) {
				rs.close();
				rs = null;
			}
			if(sm != null) {
				sm.close();
				sm = null;
			}
			if(con != null) {
				con.close();
				con = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static Boolean notEmpty(String s) {
		return ((s!=null) && (!s.trim().equals("")));
	}
}
