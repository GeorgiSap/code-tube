package playlists;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import model.playlist.Playlist;
import model.playlist.PlaylistDAO;
import model.playlist.PlaylistException;
import model.user.User;
import model.user.UserDAO;
import model.videoclip.VideoClip;
import model.videoclip.VideoClipDAO;

public class PlayListTest {
	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	PlaylistDAO playlistJDBCTemplate = (PlaylistDAO) context.getBean("PlaylistJDBCTemplate");
	UserDAO userJDBCTemplate = (UserDAO) context.getBean("UserJDBCTemplate");

	@Test
	public void test() {
		try {
			User user = new User(0, "Ivan", "Ivanov", "Ivan88", "ivan@gmail.com", "pass1234");
			Playlist list = new Playlist(0, "Javascript");

			int userId = (int) userJDBCTemplate.register(user);
			user.setId(userId);
			int playlistID = (int) playlistJDBCTemplate.addPlayList(list, user);
			Playlist playlist = playlistJDBCTemplate.getPlaylist(playlistID);
			
			System.out.println(playlist);
			System.out.println("Added successfully");
		} catch (PlaylistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
