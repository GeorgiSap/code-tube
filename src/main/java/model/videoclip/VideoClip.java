package model.videoclip;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import model.comment.Comment;
import model.tag.Tag;

public class VideoClip {

	private Set<Tag> tagsOfClip = new TreeSet<Tag>((o1, o2) -> o1.getKeyword().compareTo(o2.getKeyword()));
	private Map<LocalDateTime, Comment> comments = new TreeMap<LocalDateTime, Comment>();

	private int id;
	private String name;
	private String performer;
	private long viewCount;
	private String path;

	public VideoClip(int id, String name, String performer, String path) throws VideoClipException {
		if (name == null || name.trim().equals("") || performer == null || performer.trim().equals("") || path == null
				|| path.trim().equals("")) {
			throw new VideoClipException("Bad Data - constructor");
		}

		this.id = id;
		this.name = name;
		this.performer = performer;
		this.path = path;
		this.viewCount = 0;

	}

	public void setViewCount(long viewCount) {
		this.viewCount = viewCount;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPerformer() {
		return performer;
	}

	public long getViewCount() {
		return viewCount;
	}

	public String getPath() {
		return path;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void addComment(Comment comment) throws VideoClipException {
		if (comment != null) {
			comments.put(comment.getDateOfComment(), comment);
		} else {
			throw new VideoClipException("Bad data- comments");
		}
	}

	public void addTag(Tag tag) throws VideoClipException {
		if (tag != null) {
			tagsOfClip.add(tag);
		} else {
			throw new VideoClipException("Bad data- tags");
		}
	}

	public Set<Tag> getTagsOfClip() {
		return Collections.unmodifiableSet(tagsOfClip);
	}

	public Map<LocalDateTime, Comment> getComments() {
		return Collections.unmodifiableMap(comments);
	}

	@Override
	public String toString() {
		return "VideoClip [tagsOfClip=" + tagsOfClip + ", comments=" + comments + ", id=" + id + ", name=" + name
				+ ", performer=" + performer + ", viewCount=" + viewCount + ", path=" + path + "]";
	}

}
