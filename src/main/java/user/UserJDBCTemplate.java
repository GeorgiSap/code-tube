package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.codetube.controller.User;

public class UserJDBCTemplate {
	
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
	
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
		System.out.println("User with Email " + user.getEmail() +" registered successfully with ID " + (Integer) keyHolder.getKey());
		return (Integer) keyHolder.getKey();
	}
	
	public User login(String email, String password) throws UserException {
		String SQL = "select * from users where email = ? AND password = md5(?)";
		User user = jdbcTemplateObject.queryForObject(SQL, new Object[] { email, password }, new UserMapper());
		if (user != null) {
			System.out.println("User with Email " + email +" logged in successfully");
			return user;
		} else {
			throw new UserException("Wrong Email/Password");
		}
	}
	
	public User get(int id) {
		String SQL = "select * from users where user_id = ?";
		User user = jdbcTemplateObject.queryForObject(SQL, new Object[] { id }, new UserMapper());
		return user;
	}
	
	public List<User> listUsers() {
		String SQL = "select * from users";
		List<User> users = jdbcTemplateObject.query(SQL, new UserMapper());
		return users;
	}
	
	public void update(User user) {
		String SQL = "update users set first_name = ?, last_name = ?, user_name = ?, email = ?, password = md5(?) where user_id = ?";
		jdbcTemplateObject.update(SQL, user.getFirstName(), user.getLastName(), user.getUserName(), user.getEmail(), user.getPassword());
		System.out.println("Updated User Record with ID = " + user.getId());
	}
	
	public void delete(int id) {
		String SQL = "delete from users where user_id = ?";
		jdbcTemplateObject.update(SQL, id);
		System.out.println("Deleted User Record with ID = " + id );
	}
	
	public void deleteAll() {
		String SQL = "delete from users";
		jdbcTemplateObject.update(SQL);
		System.out.println("Deleted All User Records");
	}

}
