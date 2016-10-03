package com.example.model.user.subscription;

import java.util.List;

import javax.sql.DataSource;

import com.example.model.user.User;
import com.example.model.user.UserException;

public interface SubscriptionDAO {

	void setDataSource(DataSource dataSource);

	void subscribe(int userId, int subscriberId) throws UserException;

	void unsubscribe(int userId, int subscriberId) throws UserException;

	List<Subscription> listSubscibers(int userId);

	List<Subscription> listSubscriptions(int subscriberId);

}