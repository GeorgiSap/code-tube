package model.tag;

public class Tag {
	private int id;
	private String keyword;

	public Tag(String keyword) throws TagException {
		if (keyword == null || keyword.trim().equals("")) {
			throw new TagException("Bad Data - tag construcyor");
		}
		this.keyword = keyword;
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
