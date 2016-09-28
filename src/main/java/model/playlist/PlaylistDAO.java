package model.playlist;

import javax.sql.DataSource;

import model.user.User;

public interface PlaylistDAO {

	void setDataSource(DataSource dataSource);

	int addPlayList(Playlist list, User user);

	public Playlist getPlaylist(int id);
}