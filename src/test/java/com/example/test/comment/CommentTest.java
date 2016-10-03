package com.example.test.comment;


import java.time.LocalDateTime;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.example.model.comment.Comment;
import com.example.model.comment.CommentDAO;
import com.example.model.comment.CommentException;
import com.example.model.user.User;
import com.example.model.user.UserDAO;
import com.example.model.user.UserException;
import com.example.model.videoclip.VideoClip;
import com.example.model.videoclip.VideoClipDAO;
import com.example.model.videoclip.VideoClipException;

public class CommentTest {
	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	VideoClipDAO videoClipJDBCTemplate = (VideoClipDAO) context.getBean("VideoClipJDBCTemplate");
	CommentDAO commentJDBCTemplate = (CommentDAO) context.getBean("CommentJDBCTemplate");
	UserDAO userJDBCTemplate = (UserDAO) context.getBean("UserJDBCTemplate");

	@Test
	public void test() throws CommentException {
		try {
			
			VideoClip videoClip = new VideoClip(0, "mladen.mp4", "mladen", "mladen.mp4");
			User user = null;
			try {
				user = new User(0, "Ivan", "Ivanov", "Ivan883", "ivan3@gmail.com", "pass1234");
			} catch (UserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int videoClipId = (int) videoClipJDBCTemplate.addVideoClip(videoClip);
			int userId = (int) userJDBCTemplate.register(user);
			user.setId(userId);
			videoClip.setId(videoClipId);
			
			
			int commentId = commentJDBCTemplate.addCommentToVideo(videoClip,
					new Comment(0, "Hello i love this clip", LocalDateTime.now(), 2), user);
			System.out.println("Succesfully added a comment to video ");

		} catch (VideoClipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
