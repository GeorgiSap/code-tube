package com.codetube.model.tag;

import org.springframework.stereotype.Component;

@Component
public class Tag {

	public Tag() {
		super();
	}

	private int id;
	private String keyword;

	public Tag(int id, String keyword) throws TagException {
		if (keyword == null || keyword.trim().equals("")) {
			throw new TagException("Bad Data - tag construcyor");
		}
		this.keyword = keyword;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return keyword;
	}
	

}
