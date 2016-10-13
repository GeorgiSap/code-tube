package com.codetube.model.comment;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CommentMapper implements RowMapper<Comment> {

	@Override
	public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
		Comment comment = null;
		
		try {
			comment = new Comment(rs.getInt("comment_id"), rs.getString("message"),
					rs.getTimestamp("time").toLocalDateTime(), rs.getInt("rating"));
		} catch (CommentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return comment;
	}

}
