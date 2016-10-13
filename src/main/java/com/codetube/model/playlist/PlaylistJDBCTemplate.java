package com.codetube.model.playlist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.codetube.model.user.User;

public class PlaylistJDBCTemplate implements PlaylistDAO {
	private JdbcTemplate jdbcTemplateObject;

	@Override
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public int addPlayList(Playlist list, User user) {
		final String SQL = "insert into playlists (title, user_id) values (?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		this.jdbcTemplateObject.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pst = con.prepareStatement(SQL, new String[] { "playlist_id" });
				pst.setString(1, list.getTitle());
				pst.setInt(2, user.getId());
				return pst;
			}
		}, keyHolder);

		System.out.println(list + " was created");
		return keyHolder.getKey().intValue();
	}

	public Playlist getPlaylist(int userId ){
		String SQL = "select * from playlists where user_id = ?";
		Playlist playlist = jdbcTemplateObject.queryForObject(SQL, new Object[] { userId }, new PlaylistMapper());
		return playlist;
	}

}
