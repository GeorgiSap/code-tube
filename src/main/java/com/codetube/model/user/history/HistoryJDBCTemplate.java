package com.codetube.model.user.history;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.codetube.model.user.UserException;


public class HistoryJDBCTemplate implements HistoryDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	@Override
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
	
	@Override
	public void addToHistory(int videoClipId, int userId) throws UserException {
		if (videoClipId > 0 && userId > 0) {
			 String SQL = "insert into user_has_history (video_clip_id, user_id, last_viewed) values (?, ?, ?)";
			 Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
			 jdbcTemplateObject.update( SQL, videoClipId, userId, timestamp);
			 System.out.println("VideoClip " + videoClipId +" added to User " + userId + " history: " + timestamp);
			} else {
				throw new UserException("Unable to add to history");
			}
	}
	
	@Override
	public void removeFromHistory(int videoClipId, int userId) {
		String SQL = "delete from user_has_history where video_clip_id = ? and user_id = ?";
		jdbcTemplateObject.update(SQL, videoClipId, userId);
		System.out.println("Deleted History Record with User ID = " + userId +" and " + "VideoClip ID = " + videoClipId);
	}
	
	@Override
	public List<History> getHistory(int userId) {
		String SQL = "select video_clip_id, last_viewed from user_has_history where user_id = ?";
		List<History> history = jdbcTemplateObject.query(SQL, new Object[] {userId},new HistoryMapper());
		return history;
	}
}
