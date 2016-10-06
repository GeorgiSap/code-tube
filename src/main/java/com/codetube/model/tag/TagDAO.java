package com.codetube.model.tag;

import java.util.List;

import javax.sql.DataSource;

public interface TagDAO {

	void setDataSource(DataSource dataSource);

	int addTag(Tag tag);

	List<Tag> getTags();

	int deleteTag(int id);

	int deleteAllTags();

	Tag getTag(String tag);

}