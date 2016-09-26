package user;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import model.user.User;
import model.user.UserDAO;
import model.user.UserException;

public class UserTest {
	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	UserDAO userJDBCTemplate = (UserDAO) context.getBean("UserJDBCTemplate");

	@Test
	public void test() {
		System.out.println("------Records Deletion--------");
		userJDBCTemplate.deleteAll();

		System.out.println("------Records Creation--------");
		int userId = (int) userJDBCTemplate.register(
				new User(0, "Ivan", "Ivanov", "Ivan88", "ivan@gmail.com", "pass1234"));
		userJDBCTemplate.register(
				new User(0, "Petar", "Petrov", "Petar94", "petar@gmail.com", "pass5678"));
		userJDBCTemplate.register(
				new User(0, "Georgi", "Ivanov", "Georgi92", "georgi@gmail.com", "password"));

		System.out.println("------Listing Multiple Records--------");
		List<User> users = userJDBCTemplate.listUsers();
		for (User user : users) {
			System.out.print("FirstName : " + user.getFirstName());
			System.out.print(", LastName : " + user.getLastName());
			System.out.print(", UserName : " + user.getUserName());
			System.out.print(", Email : " + user.getEmail());
			System.out.println(", Password : " + user.getPassword());
		}

		System.out.println("----Updating Record with ID = " + userId + " -----");
		userJDBCTemplate.update(
				new User(userId, "Ivan", "Ivanov", "Ivan1988", "ivan@gmail.com", "pass1234"));

		System.out.println("----Listing Record with ID = " + userId + " -----");
		User user = userJDBCTemplate.get(userId);
		System.out.print("FirstName : " + user.getFirstName());
		System.out.print(", LastName : " + user.getLastName());
		System.out.print(", UserName : " + user.getUserName());
		System.out.print(", EMail : " + user.getEmail());
		System.out.println(", Password : " + user.getPassword());
		
		System.out.println("----Trying to Login Users -----");
		try {
			userJDBCTemplate.login("ivan@gmail.com", "pass1234");
			userJDBCTemplate.login("petar@gmail.com", "pass5678");
			userJDBCTemplate.login("georgi@gmail.com", "password");
		} catch (UserException e) {
			System.out.println("Wrong email/password");
			e.printStackTrace();
		}
	}

}