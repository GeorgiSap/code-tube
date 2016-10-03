package com.example.model.user.subscription;

public class Subscription {
	private int userId;
	private int subscriberId;
	
	public Subscription(int userId, int subscriberId) {
		this.userId = userId;
		this.subscriberId = subscriberId;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getSubscriberId() {
		return subscriberId;
	}
	
	public void setSubscriberId(int subscriberId) {
		this.subscriberId = subscriberId;
	}
	
}
