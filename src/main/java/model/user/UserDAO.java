package model.user;

import java.util.List;

import javax.sql.DataSource;

public interface UserDAO {

	void setDataSource(DataSource dataSource);

	int register(User user);

	User login(String email, String password) throws UserException;

	User get(int id);

	List<User> listUsers();

	void update(User user);

	void delete(int id);

	void deleteAll();

	User get(String email);

	boolean userNameExists(String userName);

	boolean emailExists(String email);

}