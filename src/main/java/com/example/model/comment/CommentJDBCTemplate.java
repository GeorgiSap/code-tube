package com.example.model.comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.example.model.user.User;
import com.example.model.videoclip.VideoClip;

public class CommentJDBCTemplate implements CommentDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	/* (non-Javadoc)
	 * @see model.comment.CommentDAO#setDataSource(javax.sql.DataSource)
	 */
	@Override
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	/* (non-Javadoc)
	 * @see model.comment.CommentDAO#addCommentToVideo(model.videoclip.VideoClip, model.comment.Comment, model.user.User)
	 */
	@Override
	public int addCommentToVideo(VideoClip video, Comment comment, User user) {
		final String SQL = "insert into comments (message, time, video_clip_id, user_id, rating) values (?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		this.jdbcTemplateObject.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pst = con.prepareStatement(SQL, new String[] { "comment_id" });
				pst.setString(1, comment.getMessage());
				pst.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
				pst.setInt(3, video.getId());
				pst.setInt(4, user.getId());
				pst.setInt(5, comment.getRating());
				return pst;
			}
		}, keyHolder);

		System.out.println("Comment was created");
		return keyHolder.getKey().intValue();

	}
}
