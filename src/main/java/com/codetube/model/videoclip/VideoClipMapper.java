package com.codetube.model.videoclip;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class VideoClipMapper implements RowMapper<VideoClip> {

	public VideoClip mapRow(ResultSet rs, int rowNum) throws SQLException {

		VideoClip videoClip = null;
		try {
			videoClip = new VideoClip(rs.getInt("video_clip_id"), 
					rs.getString("name"), 
					rs.getString("performer"),
					rs.getString("path"),
					rs.getInt("view_count"));
		} catch (VideoClipException e) {
			e.printStackTrace();
		}
		return videoClip;
	}
}
