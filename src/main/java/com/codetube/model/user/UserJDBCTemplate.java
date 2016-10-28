package com.codetube.model.user;

import static com.codetube.model.user.IUser.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public class UserJDBCTemplate implements UserDAO {

	private JdbcTemplate jdbcTemplateObject;

	@Override
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public int register(final User user) {
		final String SQL = "insert into users (first_name, last_name, user_name, email, password) values (?, ?, ?, ?, md5(?))";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.jdbcTemplateObject.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pst = con.prepareStatement(SQL, new String[] { "id" });
				pst.setString(1, user.getFirstName());
				pst.setString(2, user.getLastName());
				pst.setString(3, user.getUserName());
				pst.setString(4, user.getEmail());
				pst.setString(5, user.getPassword());
				return pst;
			}
		}, keyHolder);
		System.out.println("User with Email " + user.getEmail() + " registered successfully with ID "
				+ keyHolder.getKey().intValue());
		return keyHolder.getKey().intValue();
	}

	@Override
	public User login(String email, String password) throws UserException {
		if (email == null || email.trim().length() < MIN_EMAIL_LENGTH && email.length() > MAX_FIELD_LENGTH) {
			throw new UserException("Email not valid");
		}
		if (password == null || password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_FIELD_LENGTH) {
			throw new UserException("Password not valid");
		}

		String SQL = "select * from users where email = ? AND password = md5(?)";
		User user = jdbcTemplateObject.queryForObject(SQL, new Object[] { email, password }, new UserMapper());
		if (user != null) {
			System.out.println("User with Email " + email + " logged in successfully");
			return user;
		} else {
			throw new UserException("Wrong Email/Password");
		}
	}

	@Override
	public User get(int id) {
		String SQL = "select * from users where user_id = ?";
		User user = jdbcTemplateObject.queryForObject(SQL, new Object[] { id }, new UserMapper());
		return user;
	}

	@Override
	public User get(String email) {
		String SQL = "select * from users where email = ?";
		User user = jdbcTemplateObject.queryForObject(SQL, new Object[] { email }, new UserMapper());
		return user;
	}

	@Override
	public boolean userNameExists(String userName) {
		String SQL = "SELECT count(*) FROM users WHERE user_name = ?";
		Integer cnt = jdbcTemplateObject.queryForObject(SQL, Integer.class, userName);
		return cnt != null && cnt > 0;
	}

	@Override
	public boolean emailExists(String email) {
		String SQL = "SELECT count(*) FROM users WHERE email = ?";
		Integer cnt = jdbcTemplateObject.queryForObject(SQL, Integer.class, email);
		return cnt != null && cnt > 0;
	}

	@Override
	public List<User> listUsers() {
		String SQL = "select * from users";
		List<User> users = jdbcTemplateObject.query(SQL, new UserMapper());
		return users;
	}

	@Override
	public void update(User user) {
		String SQL = "update users set first_name = ?, last_name = ?, user_name = ?, email = ?, password = md5(?) where user_id = ?";
		jdbcTemplateObject.update(SQL, user.getFirstName(), user.getLastName(), user.getUserName(), user.getEmail(),
				user.getPassword(), user.getId());
		System.out.println("Updated User Record with ID = " + user.getId());
	}

	@Override
	public void delete(int id) {
		String SQL = "delete from users where user_id = ?";
		jdbcTemplateObject.update(SQL, id);
		System.out.println("Deleted User Record with ID = " + id);
	}

	@Override
	public void deleteAll() {
		String SQL = "delete from users";
		jdbcTemplateObject.update(SQL);
		System.out.println("Deleted All User Records");
	}

}
