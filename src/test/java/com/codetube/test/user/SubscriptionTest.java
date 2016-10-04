package com.codetube.test.user;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.codetube.model.user.User;
import com.codetube.model.user.UserDAO;
import com.codetube.model.user.UserException;
import com.codetube.model.user.subscription.Subscription;
import com.codetube.model.user.subscription.SubscriptionDAO;

public class SubscriptionTest {
	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	UserDAO userJDBCTemplate = (UserDAO) context.getBean("UserJDBCTemplate");
	SubscriptionDAO subscriptionJDBCTemplate = (SubscriptionDAO) context.getBean("SubscriptionJDBCTemplate");
	@Test
	public void test() {
//		System.out.println("------User Records Deletion--------");
//		userJDBCTemplate.deleteAll();

		System.out.println("------User Records Creation--------");

		try {
			int userId1 = userJDBCTemplate.register(
					new User(0, "Ivan", "Ivanov", "Ivan188", "ivan1@gmail.com", "pass1234"));
			int userId2 = userJDBCTemplate.register(
					new User(0, "Petar", "Petrov", "Petar194", "petar1@gmail.com", "pass5678"));
			int userId3 = userJDBCTemplate.register(
					new User(0, "Georgi", "Ivanov", "Georgi192", "georgi1@gmail.com", "password"));
			int userId4 = userJDBCTemplate.register(
					new User(0, "Maria", "Ivanova", "Maria190", "maria1@gmail.com", "password"));
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
