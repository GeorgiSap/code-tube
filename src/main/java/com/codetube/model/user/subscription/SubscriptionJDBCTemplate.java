package com.codetube.model.user.subscription;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.codetube.model.user.UserException;

public class SubscriptionJDBCTemplate implements SubscriptionDAO {
	private JdbcTemplate jdbcTemplateObject;

	@Override
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public void subscribe(int userId, int subscriberId) throws UserException {
		if (userId > 0 && subscriberId > 0) {
			String SQL = "insert into user_has_subscribers (user_id, subscriber_id) values (?, ?)";
			jdbcTemplateObject.update(SQL, userId, subscriberId);
			System.out.println("User " + subscriberId + " subscribed to " + userId);
		} else {
			throw new UserException("Unable to subscribe");
		}
	}

	@Override
	public void unsubscribe(int userId, int subscriberId) throws UserException {
		if (userId > 0 && subscriberId > 0) {
			String SQL = "delete from user_has_subscribers where user_id = ? AND subscriber_id = ?";
			jdbcTemplateObject.update(SQL, userId, subscriberId);
			System.out.println("User " + subscriberId + " unsubscribed from " + userId);
		} else {
			throw new UserException("Unable to unsubscribe");
		}
	}

	@Override
	public boolean checkIfSubscribed(int userId, int subscriberId) {
		String SQL = "select * from user_has_subscribers where user_id = ? AND subscriber_id = ?";
		List<Subscription> users = jdbcTemplateObject.query(SQL, new Object[] { userId, subscriberId },
				new SubscriptionMapper());
		if (users != null && users.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<Subscription> listSubscibers(int userId) {
		String SQL = "select * from user_has_subscribers where user_id = ?";
		List<Subscription> users = jdbcTemplateObject.query(SQL, new Object[] { userId }, new SubscriptionMapper());
		return users;
	}

	@Override
	public List<Subscription> listSubscriptions(int subscriberId) {
		String SQL = "select * from user_has_subscribers where subscriber_id = ?";
		List<Subscription> users = jdbcTemplateObject.query(SQL, new Object[] { subscriberId },
				new SubscriptionMapper());
		return users;
	}
}
