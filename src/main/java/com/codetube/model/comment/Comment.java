package com.codetube.model.comment;

import java.time.LocalDateTime;

import com.codetube.model.user.User;

public class Comment {
	private int id;
	private String message;
	private User user;
	private LocalDateTime dateOfComment;
	private int rating;

	public Comment(int id, String message, LocalDateTime dateOfComment, int rating) throws CommentException {
		if ((message == null) || (message.trim().equals("")) || (rating > 5) || (rating < 1)) {
			throw new CommentException("Bad data in comment");
		}
		this.message = message;
		this.dateOfComment = dateOfComment;
		this.rating = rating;
		this.id = id;
		this.user = new User();
	}

	public Comment(int id, String message, LocalDateTime dateOfComment, int rating, int userId)
			throws CommentException {
		this(id, message, dateOfComment, rating);
		user.setId(userId);
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		if (user != null)
			this.user = user;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", message=" + message + ", user=" + user + ", dateOfComment=" + dateOfComment
				+ ", rating=" + rating + "]";
	}

}
