package model.playlist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class PlaylistJDBCTemplate implements PlaylistDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	/* (non-Javadoc)
	 * @see model.playlist.PlaylistDAO#setDataSource(javax.sql.DataSource)
	 */
	@Override
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	/* (non-Javadoc)
	 * @see model.playlist.PlaylistDAO#addPlayList(model.playlist.Playlist)
	 */
	@Override
	public int addPlayList(Playlist list) {
		final String SQL = "insert into playlists (title) values (?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		this.jdbcTemplateObject.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pst = con.prepareStatement(SQL, new String[] { "playlist_id" });
				pst.setString(1, list.getTitle());
				return pst;
			}
		}, keyHolder);

		System.out.println(list + " was created");
		return keyHolder.getKey().intValue();
	}
}
