package model.videoclip;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import model.user.User;
import model.user.UserMapper;

public class VideoClipJDBCTemplate {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	public int addVideoClip(VideoClip videoClip) {
		final String SQL = "insert into video_clips (name, path, performer, view_count) values (?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		this.jdbcTemplateObject.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pst = con.prepareStatement(SQL, new String[] { "video_clip_id" });
				pst.setString(1, videoClip.getName());
				pst.setString(2, videoClip.getPath());
				pst.setString(3, videoClip.getPerformer());
				pst.setLong(4, videoClip.getViewCount());
				return pst;
			}
		}, keyHolder);

		System.out.println(keyHolder + " was created");
		return keyHolder.getKey().intValue();
	}

	public int increaseViewCount(VideoClip videoClip, int numberOfViews) {
		String SQL = "update video_clips set view_count = view_count + ? where video_clip_id = ?";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		this.jdbcTemplateObject.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pst = con.prepareStatement(SQL, new String[] { "view_count" });
				pst.setInt(1, numberOfViews);
				pst.setInt(2, videoClip.getId());
				return pst;
			}
		}, keyHolder);
		jdbcTemplateObject.update(SQL, numberOfViews, videoClip.getId());
		System.out.println("Upadeted! , the new views of the clip are " + keyHolder.getKey().intValue());
		return keyHolder.getKey().intValue();
	}

	public List<VideoClip> getClips() {
		String SQL = "select * from video_clips";
		List<VideoClip> videoClips = jdbcTemplateObject.query(SQL, new VideoClipMapper());
		return videoClips;
	}
}
