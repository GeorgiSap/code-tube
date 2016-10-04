package com.codetube.test.unit.test;

import java.time.LocalDateTime;

import com.codetube.model.comment.Comment;
import com.codetube.model.comment.CommentException;

import junit.framework.TestCase;

public class CommentTest extends TestCase {
	
	public void test() throws CommentException{
		Comment comment = new Comment(0, "Mladen", LocalDateTime.now(), 2);
		System.out.println(comment);
		assertNotNull(comment);
	}
}
