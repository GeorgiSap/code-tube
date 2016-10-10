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
	public void addToHistory(int videoClipId, int userId, LocalDateTime lastViewed) throws UserException {
		if (!checkIfRecordExists(videoClipId, userId)) {
			addNewRecord(videoClipId, userId, lastViewed);
		} else {
			updateRecord(videoClipId, userId, lastViewed);
		}
	}

	private void addNewRecord(int videoClipId, int userId, LocalDateTime lastViewed) throws UserException {
		if (videoClipId > 0 && userId > 0) {
			 String SQL = "insert into user_has_history (video_clip_id, user_id, last_viewed) values (?, ?, ?)";
			 Timestamp timestamp = Timestamp.valueOf(lastViewed);
			 jdbcTemplateObject.update( SQL, videoClipId, userId, timestamp);
			 System.out.println("VideoClip " + videoClipId +" added to User " + userId + " history: " + timestamp);
			} else {
				throw new UserException("Unable to add to history");
			}
	}
	
	private void updateRecord(int videoClipId, int userId, LocalDateTime lastViewed) throws UserException {
		if (videoClipId > 0 && userId > 0) {
			 String SQL = "update user_has_history set last_viewed = ? where video_clip_id = ? AND user_id = ?";
			 Timestamp timestamp = Timestamp.valueOf(lastViewed);
			 jdbcTemplateObject.update(SQL, timestamp, videoClipId, userId);
				System.out.println("Updated History Record: VideoClip " + videoClipId +", User " + userId + " : " + timestamp);
			} else {
				throw new UserException("Unable to update history record");
			}
	}

	private boolean checkIfRecordExists(int videoClipId, int userId) {
		String SQL = "select * from user_has_history where video_clip_id = ? AND user_id = ?";
		List<History> history = jdbcTemplateObject.query(SQL, new Object[] {videoClipId, userId},new HistoryMapper());
		if (history.size() <= 0) {
			System.out.println("History Record for Video Clip ID " + videoClipId + " User ID " + userId + " does not exist");
			return false;
		}
		System.out.println("History Record for Video Clip ID " + videoClipId + " User ID " + userId + " already exists");
		return true;
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
