package com.codetube.model.user.history;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class HistoryMapper implements RowMapper<History> {

	public History mapRow(ResultSet rs, int rowNum) throws SQLException {
		History history = new History(rs.getInt("video_clip_id"), rs.getTimestamp("last_viewed").toLocalDateTime());
		return history;
	}
}
