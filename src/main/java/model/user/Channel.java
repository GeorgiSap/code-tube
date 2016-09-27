package model.user;

import java.util.LinkedList;
import java.util.List;

import model.playlist.Playlist;
import model.videoclip.VideoClip;

public class Channel {
	private int id;
	private String description;
	private User user;
	private List<Playlist> playlists = new LinkedList<Playlist>();
	private List<VideoClip> videoClips = new LinkedList<VideoClip>();
}
