package com.codetube.model.comment;

import java.time.LocalDateTime;

public class Comment {
	private int id;
	private String message;

	private int userId;
	

	private LocalDateTime dateOfComment;
	private int rating;

	public Comment(int id, String message, LocalDateTime dateOfComment,int userId, int rating) throws CommentException {
		if ((message == null) || (message.trim().equals("")) || (rating > 5) || (rating < 1)) {
			throw new CommentException("Bad data in comment");
		}
		this.message = message;
		this.dateOfComment = dateOfComment;
		this.rating = rating;
		this.id = id;
		this.userId = userId;
	}

	public int getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}

	public LocalDateTime getDateOfComment() {
		return dateOfComment;
	}

	public int getRating() {
		return rating;
	}
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", message=" + message + ", userId=" + userId + ", dateOfComment=" + dateOfComment
				+ ", rating=" + rating + "]";
	}

	
}
