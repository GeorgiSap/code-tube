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

	public Channel(int id, String description, User user) {
		super();
		this.id = id;
		this.description = description;
		this.user = user;
		// to do validation
	}

	public int getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public User getUser() {
		return user;
	}

	public List<Playlist> getPlaylists() {
		return playlists;
	}

	public List<VideoClip> getVideoClips() {
		return videoClips;
	}

}
