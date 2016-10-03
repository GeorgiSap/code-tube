package com.example.model.playlist;

import javax.sql.DataSource;

import com.example.model.user.User;

public interface PlaylistDAO {

	void setDataSource(DataSource dataSource);

	int addPlayList(Playlist list, User user);

	public Playlist getPlaylist(int id);
}