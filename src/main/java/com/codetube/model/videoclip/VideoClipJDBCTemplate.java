package com.codetube.model.videoclip;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.codetube.model.tag.Tag;
import com.codetube.model.tag.TagMapper;
import com.codetube.model.user.User;
import com.codetube.model.user.UserMapper;

@Component
@Repository
public class VideoClipJDBCTemplate implements VideoClipDAO {

	public DataSource dataSource;

	public JdbcTemplate jdbcTemplateObject;

	@Autowired
	@Override
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	public VideoClipJDBCTemplate() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VideoClipJDBCTemplate(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public int addVideoClip(VideoClip videoClip, User user) {
		final String SQL = "insert into video_clips (name, path, performer, user_id, view_count) values (?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		this.jdbcTemplateObject.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pst = con.prepareStatement(SQL, new String[] { "video_clip_id" });
				pst.setString(1, videoClip.getName());
				pst.setString(2, videoClip.getPath());
				pst.setString(3, videoClip.getPerformer());
				pst.setInt(4, user.getId());
				pst.setLong(5, videoClip.getViewCount());
				return pst;
			}
		}, keyHolder);

		System.out.println(keyHolder + " was created");
		return keyHolder.getKey().intValue();
	}

	@Override
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

	@Override
	public List<VideoClip> getClips() {
		String SQL = "select * from video_clips";
		System.out.println(jdbcTemplateObject);
		List<VideoClip> videoClips = jdbcTemplateObject.query(SQL, new VideoClipMapper());
		return videoClips;
	}

	@Override
	public VideoClip getClip(int id) {
		String SQL = "select * from video_clips where video_clip_id = ?";
		VideoClip videoClip = jdbcTemplateObject.queryForObject(SQL, new Object[] { id }, new VideoClipMapper());
		return videoClip;
	}

	@Override
	public void addTagToVideo(int tagId, int videoId) {
		final String SQL = "insert into video_clip_has_tags (tag_id, video_clip_id) values (?,?)";

		this.jdbcTemplateObject.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pst = con.prepareStatement(SQL, new String[] { "video_clip_id" });
				pst.setInt(1, tagId);
				pst.setInt(2, videoId);
				return pst;
			}
		});
		return;
	}

	@Override
	public Set<Tag> getVideoTags(VideoClip clip) {
		String SQL = "SELECT t.tag_id, t.keyword FROM codetube.video_clip_has_tags vt "
				+ "join tags t on vt.tag_id = t.tag_id " + "join  video_clips v on v.video_clip_id = vt.video_clip_id "
				+ "where v.video_clip_id =?";
		List<Tag> tags = jdbcTemplateObject.query(SQL, new Object[] { clip.getId() }, new TagMapper());
		System.out.println(tags);
		return new HashSet<Tag>(tags);
	}
}
