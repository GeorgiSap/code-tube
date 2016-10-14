package com.codetube.test.videoClips;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.codetube.model.videoclip.VideoClip;
import com.codetube.model.videoclip.VideoClipDAO;

public class MostViewedAndCommentedVideosTest {
	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	VideoClipDAO videoClipJDBCTemplate = (VideoClipDAO) context.getBean("VideoClipJDBCTemplate");
	
	@Test
	public void test() {
		 List<VideoClip> mostViewed = videoClipJDBCTemplate.getMostViewedVideos(5);
		 mostViewed.forEach(videoClip -> System.out.println(videoClip.getId() + " : " + videoClip.getViewCount()));
		 List<VideoClip> mostCommented = videoClipJDBCTemplate.getMostCommentedVideos(5);
		 mostCommented.forEach(videoClip -> System.out.println(videoClip.getId()));
	}

}
