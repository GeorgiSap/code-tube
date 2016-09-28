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

public class VideoClipJDBCTemplate implements VideoClipDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	/* (non-Javadoc)
	 * @see model.videoclip.VideoClipDAO#setDataSource(javax.sql.DataSource)
	 */
	@Override
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	/* (non-Javadoc)
	 * @see model.videoclip.VideoClipDAO#addVideoClip(model.videoclip.VideoClip)
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see model.videoclip.VideoClipDAO#increaseViewCount(model.videoclip.VideoClip, int)
	 */
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

	/* (non-Javadoc)
	 * @see model.videoclip.VideoClipDAO#getClips()
	 */
	@Override
	public List<VideoClip> getClips() {
		String SQL = "select * from video_clips";
		List<VideoClip> videoClips = jdbcTemplateObject.query(SQL, new VideoClipMapper());
		return videoClips;
	}

	@Override
	public VideoClip getClip(int id) {
		String SQL = "select * from video_clips where video_clip_id = ?";
		VideoClip videoClip = jdbcTemplateObject.queryForObject(SQL, new Object[] { id }, new VideoClipMapper());
		return videoClip;
	}
}
