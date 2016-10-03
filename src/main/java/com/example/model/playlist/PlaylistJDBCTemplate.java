package com.example.model.playlist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.example.model.user.User;
import com.example.model.user.UserMapper;
import com.example.model.videoclip.VideoClip;

public class PlaylistJDBCTemplate implements PlaylistDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.playlist.PlaylistDAO#setDataSource(javax.sql.DataSource)
	 */
	@Override
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.playlist.PlaylistDAO#addPlayList(model.playlist.Playlist)
	 */
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

	public Playlist getPlaylist(int id ){
		String SQL = "select * from playlists where playlist_id = ?";
		Playlist playlist = jdbcTemplateObject.queryForObject(SQL, new Object[] { id }, new PlaylistMapper());
		return playlist;
	}

}
