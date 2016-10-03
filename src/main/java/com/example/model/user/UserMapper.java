package com.example.model.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserMapper implements RowMapper<User> {
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = null;
		try {
			user = new User(
					rs.getInt("user_id"),
					rs.getString("first_name"), 
					rs.getString("last_name"),
					rs.getString("user_name"),
					rs.getString("email"), 
					rs.getString("password"));
		} catch (UserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
}