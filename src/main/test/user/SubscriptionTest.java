package user;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import model.user.User;
import model.user.UserDAO;
import model.user.UserException;
import model.user.subscription.Subscription;
import model.user.subscription.SubscriptionDAO;

public class SubscriptionTest {
	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	UserDAO userJDBCTemplate = (UserDAO) context.getBean("UserJDBCTemplate");
	SubscriptionDAO subscriptionJDBCTemplate = (SubscriptionDAO) context.getBean("SubscriptionJDBCTemplate");
	@Test
	public void test() {
//		System.out.println("------User Records Deletion--------");
//		userJDBCTemplate.deleteAll();

		System.out.println("------User Records Creation--------");
		int userId1 = userJDBCTemplate.register(
				new User(0, "Ivan", "Ivanov", "Ivan88", "ivan@gmail.com", "pass1234"));
		int userId2 = userJDBCTemplate.register(
				new User(0, "Petar", "Petrov", "Petar94", "petar@gmail.com", "pass5678"));
		int userId3 = userJDBCTemplate.register(
				new User(0, "Georgi", "Ivanov", "Georgi92", "georgi@gmail.com", "password"));
		int userId4 = userJDBCTemplate.register(
				new User(0, "Maria", "Ivanova", "Maria90", "maria@gmail.com", "password"));
		try {
			subscriptionJDBCTemplate.subscribe(userId1, userId2);
			subscriptionJDBCTemplate.subscribe(userId1, userId3);
			subscriptionJDBCTemplate.subscribe(userId1, userId4);
			subscriptionJDBCTemplate.subscribe(userId3, userId2);
			subscriptionJDBCTemplate.subscribe(userId4, userId2);
			subscriptionJDBCTemplate.unsubscribe(userId3, userId2);
			
			System.out.println("------Listing User ID " + userId2 + "Subscriptions--------");
			List<Subscription> subscriptions = subscriptionJDBCTemplate.listSubscriptions(userId2);
			for (Subscription subscription : subscriptions) {
				System.out.print("User ID : " + subscription.getUserId());
				System.out.println(", Subscriber ID : " + subscription.getSubscriberId());
			}
			
			System.out.println("------Listing User ID " + userId1 + " Subscibers--------");
			List<Subscription> subscribers = subscriptionJDBCTemplate.listSubscibers(userId1);
			for (Subscription subscription : subscribers) {
				System.out.print("User ID : " + subscription.getUserId());
				System.out.println(", Subscriber ID : " + subscription.getSubscriberId());
			}
		} catch (UserException e) {
			e.printStackTrace();
		}
	}

}
