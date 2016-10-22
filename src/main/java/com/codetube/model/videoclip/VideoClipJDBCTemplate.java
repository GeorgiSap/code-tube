package com.codetube.model.videoclip;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.codetube.model.tag.Tag;
import com.codetube.model.tag.TagMapper;
import com.codetube.model.user.User;

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
	public void increaseViewCount(VideoClip videoClip) {
		String SQL = "update video_clips set view_count = view_count + 1 where video_clip_id = ?";
		jdbcTemplateObject.update(SQL, videoClip.getId());
		System.out.println("Incremented view count");
	}

	@Override
	public List<VideoClip> getClips() {
		String SQL = "select * from video_clips";
		System.out.println(jdbcTemplateObject);
		List<VideoClip> videoClips = jdbcTemplateObject.query(SQL, new VideoClipMapper());
		return videoClips;
	}

	@Override
	public List<VideoClip> getClips(int userId) {
		String SQL = "select * from video_clips where user_id = ?";
		System.out.println(jdbcTemplateObject);
		List<VideoClip> videoClips = jdbcTemplateObject.query(SQL, new Object[] { userId }, new VideoClipMapper());
		return videoClips;
	}

	@Override
	public List<VideoClip> getClipsByTag(int tagId) {
		String SQL = "select * from video_clips v " + "join video_clip_has_tags t "
				+ "on v.video_clip_id = t.video_clip_id" + "  where t.tag_id = ? " + "order by v.video_clip_id desc";
		System.out.println(jdbcTemplateObject);
		List<VideoClip> videoClips = jdbcTemplateObject.query(SQL, new Object[] { tagId }, new VideoClipMapper());
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

	@Override
	public List<VideoClip> getNewestVideos(int countOfVideos) {
		String SQL = "select * from video_clips order by video_clip_id desc limit " + countOfVideos;
		System.out.println(jdbcTemplateObject);
		List<VideoClip> videoClips = jdbcTemplateObject.query(SQL, new VideoClipMapper());
		return videoClips;
	}

	@Override
	public List<VideoClip> getMostViewedVideos(int countOfVideos) {
		String SQL = "select * from video_clips order by view_count desc limit " + countOfVideos;
		List<VideoClip> videoClips = jdbcTemplateObject.query(SQL, new VideoClipMapper());
		return videoClips;
	}

	@Override
	public List<VideoClip> getMostCommentedVideos(int numberOfVideos) {
		String SQL = "select v.video_clip_id, count(v.video_clip_id) from video_clips v "
				+ "join comments c on (v.video_clip_id = c.video_clip_id) "
				+ "group by v.video_clip_id "
				+ "order by count(v.video_clip_id) desc "
				+ "limit " + numberOfVideos;
		
		List<Integer> commentedVideoIds = jdbcTemplateObject.query(SQL, (rs, rowNum) -> rs.getInt("v.video_clip_id"));
		List<VideoClip> videoClips = new ArrayList<VideoClip>();
		commentedVideoIds.forEach(videoId -> videoClips.add(this.getClip(videoId)));
		return videoClips;
	}

	public VideoClipJDBCTemplate(JdbcTemplate jdbcTemplateObject, DataSource dataSource) {
		super();
		this.jdbcTemplateObject = jdbcTemplateObject;
		this.dataSource = dataSource;
	}

}
