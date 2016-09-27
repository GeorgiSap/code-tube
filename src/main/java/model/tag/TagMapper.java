package model.tag;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.project.ProjectException;

public class TagMapper {
	public Tag mapRow(ResultSet rs, int rowNum) throws SQLException, ProjectException {
		Tag tag = null;
		try {
			tag = new Tag(rs.getInt("id_tag"), rs.getString("key_word"));
		} catch (TagException e) {
			throw new ProjectException("Something went wrong");
		}
		return tag;
	}
}
