package unit.test;

import java.time.LocalDateTime;

import junit.framework.TestCase;
import model.comment.Comment;
import model.comment.CommentException;

public class CommentTest extends TestCase {
	
	public void test() throws CommentException{
		Comment comment = new Comment("Mladen", LocalDateTime.now(), 2);
		System.out.println(comment);
		assertNotNull(comment);
	}
}
