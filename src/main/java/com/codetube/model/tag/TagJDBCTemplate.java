package com.codetube.model.tag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public class TagJDBCTemplate implements TagDAO {
	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	@Override
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public int addTag(Tag tag) {
		final String SQL = "insert into tags (keyword) values (?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		this.jdbcTemplateObject.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pst = con.prepareStatement(SQL, new String[] { "tag_id" });
				pst.setString(1, tag.getKeyword());
				return pst;
			}
		}, keyHolder);

		System.out.println(tag + " was created");
		return keyHolder.getKey().intValue();
	}

	public List<Tag> getTags() {
		String SQL = "select * from tags";
		System.out.println(jdbcTemplateObject);
		List<Tag> tags = jdbcTemplateObject.query(SQL, new TagMapper());
		return tags;
	}

	public Tag getTag(String keyword) {
		String SQL = "select * from tags where keyword like ?";
		System.out.println(jdbcTemplateObject);
		
		List<Tag> tags = (List<Tag>) jdbcTemplateObject.query(SQL, new Object[] { keyword }, new TagMapper());
		for (Tag tag : tags) {
			if(tag.getKeyword().equals(keyword)){
				return tag;
			}
		}
		return null;
	}

	@Override
	public int deleteTag(int id) {
		final String SQL = "delete from tags where tag_id = ?";
		int number = this.jdbcTemplateObject.update(SQL, id);
		System.out.println("Deletion succesfull for row with id " + number);
		return number;
	}

	@Override
	public int deleteAllTags() {
		final String SQL = "delete from tags";
		int number = this.jdbcTemplateObject.update(SQL);
		System.out.println("Deletion succesfull. Deleted Rows: " + number);
		return number;

	}
}
