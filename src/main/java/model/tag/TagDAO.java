package model.tag;

import javax.sql.DataSource;

public interface TagDAO {

	void setDataSource(DataSource dataSource);

	int addTag(Tag tag);

	int deleteTag(int id);

	int deleteAllTags();

}