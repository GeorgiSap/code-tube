package com.codetube.test.comment;

import java.util.Set;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.codetube.model.comment.Comment;
import com.codetube.model.comment.CommentDAO;
import com.codetube.model.comment.CommentException;
import com.codetube.model.user.User;
import com.codetube.model.user.UserDAO;
import com.codetube.model.videoclip.VideoClip;
import com.codetube.model.videoclip.VideoClipDAO;
import com.codetube.model.videoclip.VideoClipException;

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
//			try {
//				user = new User(0, "Ivan", "Ivanov", "Ivan1883", "ivan33@gmail.com", "pass1234");
//			} catch (UserException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			user = userJDBCTemplate.get(6);
//			int userId = (int) userJDBCTemplate.register(user);
			user.setId(6);
			videoClip = videoClipJDBCTemplate.getClip(12);
			videoClip.setId(12);

//			int commentId = commentJDBCTemplate.addCommentToVideo(videoClip,
//					new Comment(0, "Hello i love this clip", LocalDateTime.now(), 2), user);
			System.out.println("Succesfully added a comment to video ");
			Set<Comment> comment = commentJDBCTemplate.getComments(12);
			System.out.println(comment);

		} catch (VideoClipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
