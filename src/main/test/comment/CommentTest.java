package comment;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import model.comment.Comment;
import model.comment.CommentException;
import model.comment.CommentJDBCTemplate;
import model.user.User;
import model.user.UserDAO;
import model.videoclip.VideoClip;
import model.videoclip.VideoClipDAO;
import model.videoclip.VideoClipException;

public class CommentTest {
	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	VideoClipDAO videoClipJDBCTemplate = (VideoClipDAO) context.getBean("VideoClipJDBCTemplate");
	CommentJDBCTemplate commentJDBCTemplate = (CommentJDBCTemplate) context.getBean("CommentJDBCTemplate");
	UserDAO userJDBCTemplate = (UserDAO) context.getBean("UserJDBCTemplate");

	@Test
	public void test() throws CommentException {
		try {
			
			VideoClip videoClip = new VideoClip(0, "mladen.mp4", "mladen", "mladen.mp4");
			User user = new User(0, "Ivan", "Ivanov", "Ivan88", "ivan@gmail.com", "pass1234");
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
