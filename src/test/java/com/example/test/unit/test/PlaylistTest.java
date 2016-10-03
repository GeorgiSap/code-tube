package com.example.test.unit.test;

import java.time.LocalDateTime;

import com.example.model.comment.Comment;
import com.example.model.comment.CommentException;
import com.example.model.playlist.Playlist;
import com.example.model.playlist.PlaylistException;
import com.example.model.tag.Tag;
import com.example.model.tag.TagException;
import com.example.model.videoclip.VideoClip;
import com.example.model.videoclip.VideoClipException;

import junit.framework.TestCase;

public class PlaylistTest extends TestCase {
	public void test() throws VideoClipException, PlaylistException, TagException, CommentException {
		VideoClip videoClip =new VideoClip(0, "Gosho","Pesho", "asd.mp4");
		System.out.println(videoClip);
		videoClip.addTag(new Tag(0, "javaScript"));
		videoClip.addComment(new Comment(0, "Kvo stava tuka be ", LocalDateTime.now(), 1));
		
		Playlist playlist = new Playlist(0, "NA gosho listata");
		playlist.addVideoClip(videoClip);
		
		System.out.println(playlist);
		assertNotNull(videoClip);
	}
}
