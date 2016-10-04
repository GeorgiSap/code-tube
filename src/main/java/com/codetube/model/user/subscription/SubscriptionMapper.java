package com.codetube.model.user.subscription;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class SubscriptionMapper implements RowMapper<Subscription>{

		public Subscription mapRow(ResultSet rs, int rowNum) throws SQLException {
			Subscription subscription = new Subscription(
					rs.getInt("user_id"),
					rs.getInt("subscriber_id"));
			return subscription;
		}
	
}
