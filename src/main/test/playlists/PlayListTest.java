package playlists;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import model.playlist.Playlist;
import model.playlist.PlaylistDAO;
import model.playlist.PlaylistException;
import model.videoclip.VideoClip;
import model.videoclip.VideoClipDAO;

public class PlayListTest {
	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	PlaylistDAO videoClipJDBCTemplate = (PlaylistDAO) context.getBean("PlaylistJDBCTemplate");
	
	@Test
	public void test() {
		try {
			int userId = (int) videoClipJDBCTemplate.addPlayList(new Playlist(0,"Music"));
			System.out.println("Added successfully");
		} catch (PlaylistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
