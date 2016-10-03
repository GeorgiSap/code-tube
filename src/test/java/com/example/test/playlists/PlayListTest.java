package com.example.test.playlists;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.example.model.playlist.Playlist;
import com.example.model.playlist.PlaylistDAO;
import com.example.model.playlist.PlaylistException;
import com.example.model.user.User;
import com.example.model.user.UserDAO;
import com.example.model.user.UserException;

public class PlayListTest {
	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	PlaylistDAO playlistJDBCTemplate = (PlaylistDAO) context.getBean("PlaylistJDBCTemplate");
	UserDAO userJDBCTemplate = (UserDAO) context.getBean("UserJDBCTemplate");

	@Test
	public void test() {
		try {
			User user = null;
			try {
				user = new User(0, "Ivan", "Ivanov", "Ivan882", "ivan2@gmail.com", "pass1234");
			} catch (UserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
