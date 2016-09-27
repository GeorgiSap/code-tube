package model.playlist;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import model.tag.Tag;
import model.tag.TagException;

public class PlaylistMapper implements RowMapper<Playlist>{

	@Override
	public Playlist mapRow(ResultSet rs, int rowNum) throws SQLException {
		Playlist list = null;
		try {
			list = new Playlist(rs.getInt("playlist_id"), rs.getString("title"));
		} catch (PlaylistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}

}
