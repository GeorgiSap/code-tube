package com.codetube.test.videoClips;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.codetube.model.user.User;
import com.codetube.model.user.UserDAO;
import com.codetube.model.user.UserException;
import com.codetube.model.videoclip.VideoClip;
import com.codetube.model.videoclip.VideoClipDAO;
import com.codetube.model.videoclip.VideoClipException;

public class VideoClipTest {
	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	VideoClipDAO videoClipJDBCTemplate = (VideoClipDAO) context.getBean("VideoClipJDBCTemplate");
	UserDAO userJDBCTemplate = (UserDAO) context.getBean("UserJDBCTemplate");

	@Test
	public void test() {
		User user = null;
		
		try {
			user = new User(0, "Ivan", "Ivanov", "Ivan883", "ivan3@gmail.com", "pass1234");
			
			int userId = (int) userJDBCTemplate.register(user);
			user.setId(userId);
			
			int clipId = (int) videoClipJDBCTemplate
					.addVideoClip(new VideoClip(0, "mladen.mp4", "mladen", "mladen.mp4"), user);
			clipId = (int) videoClipJDBCTemplate.addVideoClip(new VideoClip(0, "georgi.mp4", "Georgi", "georgi.mp4"),
					user);
			clipId = (int) videoClipJDBCTemplate.addVideoClip(new VideoClip(0, "niki.mp4", "niki", "niki.mp4"), user);
			clipId = (int) videoClipJDBCTemplate.addVideoClip(new VideoClip(0, "tomi.mp4", "tom", "tompot.mp4"), user);
			System.out.println("Added succesfully data in database!");

			List<VideoClip> clips = videoClipJDBCTemplate.getClips();
			System.out.println("All clips!");
			for (VideoClip clip : clips) {
				System.out.println(clip);
			}

			System.out.println("Succesfully adding a videoClip");
		} catch (VideoClipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
