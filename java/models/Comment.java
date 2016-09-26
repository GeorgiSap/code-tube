package models;

import java.time.LocalDateTime;

public class Comment {
	private int id;
	private String message;

	// private User user;
	private LocalDateTime dateOfComment;
	private int rating;

	public Comment(String message, LocalDateTime dateOfComment, int rating) throws CommentException {
		if ((message == null) || (message.trim().equals("")) || (rating > 5) || (rating < 1)) {
			throw new CommentException("Bad data in comment");
		}
		this.message = message;
		this.dateOfComment = dateOfComment;
		this.rating = rating;
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

	@Override
	public String toString() {
		return "Comment [id=" + id + ", message=" + message + ", dateOfComment=" + dateOfComment + ", rating=" + rating
				+ "]";
	}
}
