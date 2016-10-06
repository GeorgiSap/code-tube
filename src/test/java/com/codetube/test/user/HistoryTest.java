package com.codetube.test.user;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.codetube.model.user.User;
import com.codetube.model.user.UserDAO;
import com.codetube.model.user.UserException;
import com.codetube.model.user.history.History;
import com.codetube.model.user.history.HistoryDAO;
import com.codetube.model.videoclip.VideoClip;
import com.codetube.model.videoclip.VideoClipDAO;
import com.codetube.model.videoclip.VideoClipException;

public class HistoryTest {
	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	UserDAO userJDBCTemplate = (UserDAO) context.getBean("UserJDBCTemplate");
	HistoryDAO historyJDBCTemplate = (HistoryDAO) context.getBean("HistoryJDBCTemplate");
	VideoClipDAO videoClipJDBCTemplate = (VideoClipDAO) context.getBean("VideoClipJDBCTemplate");
	@Test
	public void test() {
		System.out.println("------User Records Deletion--------");
		userJDBCTemplate.deleteAll();
		System.out.println("------User Records Creation--------");

		try {
			User ivan = new User(0, "Ivan", "Ivanov", "Ivan188", "ivan1@gmail.com", "pass1234");
			User petar = new User(0, "Petar", "Petrov", "Petar194", "petar1@gmail.com", "pass5678");
			int userId1 = userJDBCTemplate.register(ivan);
			ivan.setId(userId1);
			int userId2 = userJDBCTemplate.register(petar);
			petar.setId(userId2);
			
			VideoClip clip1 = new VideoClip(0, "mladen.mp4", "mladen", "mladen.mp4");
			VideoClip clip2 = new VideoClip(0, "georgi.mp4", "Georgi", "georgi.mp4");
			VideoClip clip3 = new VideoClip(0, "niki.mp4", "niki", "niki.mp4");
			VideoClip clip4 = new VideoClip(0, "tomi.mp4", "tom", "tompot.mp4");
			
			int videoClipId1 = videoClipJDBCTemplate.addVideoClip(new VideoClip(0, "mladen.mp4", "mladen", "mladen.mp4"), ivan);
			clip1.setId(videoClipId1);
			int videoClipId2 = videoClipJDBCTemplate.addVideoClip(new VideoClip(0, "georgi.mp4", "Georgi", "georgi.mp4"), ivan);
			clip2.setId(videoClipId2);
			int videoClipId3 = videoClipJDBCTemplate.addVideoClip(new VideoClip(0, "niki.mp4", "niki", "niki.mp4"), petar);
			clip3.setId(videoClipId3);
			int videoClipId4 = videoClipJDBCTemplate.addVideoClip(new VideoClip(0, "tomi.mp4", "tom", "tompot.mp4"), petar);
			clip4.setId(videoClipId4);
			
			try {
				historyJDBCTemplate.addToHistory(clip1.getId(), ivan.getId());
				Thread.sleep(500);
				historyJDBCTemplate.addToHistory(clip2.getId(), ivan.getId());
				Thread.sleep(500);
				historyJDBCTemplate.addToHistory(clip3.getId(), ivan.getId());
				Thread.sleep(500);
				historyJDBCTemplate.addToHistory(clip4.getId(), petar.getId());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println("------Listing User ID " + userId1 + " History--------");
			List<History> history = historyJDBCTemplate.getHistory(userId1);
			for (History entry : history) {
				System.out.print("Video Clip ID" + entry.getVideoClipId());
				System.out.println(" : " + entry.getLastViewed());
			}
			
			System.out.println("------Listing User ID " + userId2 + " History--------");
			List<History> history2 = historyJDBCTemplate.getHistory(userId2);
			for (History entry : history2) {
				System.out.print("Video Clip ID" + entry.getVideoClipId());
				System.out.println(" : " + entry.getLastViewed());
			}

		} catch (UserException | VideoClipException e) {
			e.printStackTrace();
		}
	}

}
