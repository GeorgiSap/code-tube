package model.tag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class TagJDBCTemplate implements TagDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	/* (non-Javadoc)
	 * @see model.tag.TagDAO#setDataSource(javax.sql.DataSource)
	 */
	@Override
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	/* (non-Javadoc)
	 * @see model.tag.TagDAO#addTag(model.tag.Tag)
	 */
	@Override
	public int addTag(Tag tag) {
		final String SQL = "insert into tags (key_word) values (?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		this.jdbcTemplateObject.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pst = con.prepareStatement(SQL, new String[] { "id_tag" });
				pst.setString(1, tag.getKeyword());
				return pst;
			}
		}, keyHolder);

		System.out.println(tag + " was created");
		return keyHolder.getKey().intValue();
	}

	/* (non-Javadoc)
	 * @see model.tag.TagDAO#deleteTag(int)
	 */
	@Override
	public int deleteTag(int id) {
		final String SQL = "delete from tags where id_tag = ?";
		int number = this.jdbcTemplateObject.update(SQL, id);
		System.out.println("Deletion succesfull for row with id " + number);
		return number;
	}

	/* (non-Javadoc)
	 * @see model.tag.TagDAO#deleteAllTags()
	 */
	@Override
	public int deleteAllTags() {
		final String SQL = "delete from tags";
		int number = this.jdbcTemplateObject.update(SQL);
		System.out.println("Deletion succesfull. Deleted Rows: " + number);
		return number;

	}
}
