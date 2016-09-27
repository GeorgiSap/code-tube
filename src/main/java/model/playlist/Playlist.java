package model.playlist;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import model.videoclip.VideoClip;

public class Playlist {
	private int id;
	private List<VideoClip> videoClips = new LinkedList<VideoClip>();
	private String title;

	public Playlist(String title) throws PlaylistException {
		if (title == null || title.trim().equals("")) {
			throw new PlaylistException("Bad data!");
		}
		this.title = title;
	}

	public void addVideoClip(VideoClip videoClip) throws PlaylistException {
		if (videoClip == null) {
			throw new PlaylistException("Bad data in video clip adding");
		}
		videoClips.add(videoClip);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<VideoClip> getVideoClip() {
		return Collections.unmodifiableList(videoClips);
	}

	public String getTitle() {
		return title;
	}

	@Override
	public String toString() {
		return "Playlist [id=" + id + ", videoClips=" + videoClips + ", title=" + title + "]";
	}

}
