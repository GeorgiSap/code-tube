package model.videoclip;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import model.project.ProjectException;
import model.tag.TagException;
import model.user.User;

public class VideoClipMapper implements RowMapper<VideoClip>{
	public VideoClip mapRow(ResultSet rs, int rowNum) throws SQLException {
		VideoClip videoClip = null;
		try {
			videoClip = new VideoClip(rs.getInt("video_clip_id"), rs.getString("name"), rs.getString("performer"),
					rs.getString("path"));
			videoClip.setViewCount(rs.getInt("view_count"));
		} catch (VideoClipException e) {
			e.printStackTrace();
		}
		return videoClip;
	}
}
