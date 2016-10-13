package com.codetube.model.comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.codetube.model.user.User;
import com.codetube.model.videoclip.VideoClip;

public class CommentJDBCTemplate implements CommentDAO {
	private JdbcTemplate jdbcTemplateObject;

	@Override
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

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

	@Override
	public Set<Comment> getComments(int video_clip_id) {
		String SQL = "select * from comments where video_clip_id = ?";
		List<Comment> comments = jdbcTemplateObject.query(SQL, new Object[] { video_clip_id }, new CommentMapper());
		Set<Comment> set = new TreeSet<Comment>((o1, o2) -> o1.getDateOfComment().compareTo(o2.getDateOfComment()));
		set.addAll(comments);
		return set;
	}

}
