package com.example.model.tag;

public class Tag {
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

}
