package videoClips;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import model.videoclip.VideoClip;
import model.videoclip.VideoClipDAO;
import model.videoclip.VideoClipException;

public class VideoClipTest {
	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	VideoClipDAO videoClipJDBCTemplate = (VideoClipDAO) context.getBean("VideoClipJDBCTemplate");

	@Test
	public void test() {
		try {

			int userId = (int) videoClipJDBCTemplate
					.addVideoClip(new VideoClip(0, "mladen.mp4", "mladen", "mladen.mp4"));
			userId = (int) videoClipJDBCTemplate.addVideoClip(new VideoClip(0, "georgi.mp4", "Georgi", "georgi.mp4"));
			userId = (int) videoClipJDBCTemplate.addVideoClip(new VideoClip(0, "niki.mp4", "niki", "niki.mp4"));
			userId = (int) videoClipJDBCTemplate.addVideoClip(new VideoClip(0, "tomi.mp4", "tom", "tompot.mp4"));
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
		}
	}

}
