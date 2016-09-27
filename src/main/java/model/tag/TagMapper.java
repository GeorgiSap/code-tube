package model.tag;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import model.project.ProjectException;

public class TagMapper implements RowMapper<Tag>{
	public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tag tag = null;
		try {
			tag = new Tag(rs.getInt("tag_id"), rs.getString("keyword"));
		} catch (TagException e) {
			e.printStackTrace();
		}
		return tag;
	}
}
