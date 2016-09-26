package unit.test;

import java.time.LocalDateTime;

import junit.framework.TestCase;
import models.Comment;
import models.CommentException;
import models.Playlist;
import models.PlaylistException;
import models.Tag;
import models.TagException;
import models.VideoClip;
import models.VideoClipException;

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
