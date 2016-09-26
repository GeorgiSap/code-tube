package unit.test;

import java.time.LocalDateTime;

import junit.framework.TestCase;
import model.Comment;
import model.CommentException;
import model.Playlist;
import model.PlaylistException;
import model.Tag;
import model.TagException;
import model.VideoClip;
import model.VideoClipException;

public class PlaylistTest extends TestCase {
	public void test() throws VideoClipException, PlaylistException, TagException, CommentException {
		VideoClip videoClip =new VideoClip("Gosho","Pesho", "asd.mp4");
		System.out.println(videoClip);
		videoClip.addTag(new Tag("javaScript"));
		videoClip.addComment(new Comment("Kvo stava tuka be ", LocalDateTime.now(), 1));
		
		Playlist playlist = new Playlist("NA gosho listata");
		playlist.addVideoClip(videoClip);
		
		System.out.println(playlist);
		assertNotNull(videoClip);
	}
}
