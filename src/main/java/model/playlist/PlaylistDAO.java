package model.playlist;

import javax.sql.DataSource;

public interface PlaylistDAO {

	void setDataSource(DataSource dataSource);

	int addPlayList(Playlist list);

}