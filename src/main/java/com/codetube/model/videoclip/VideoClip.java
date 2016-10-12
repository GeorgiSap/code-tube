package com.codetube.model.videoclip;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.codetube.model.comment.Comment;
import com.codetube.model.tag.Tag;
import com.codetube.model.user.User;

@Component
public class VideoClip {

	public VideoClip() {
		super();
		// TODO Auto-generated constructor stub
	}

	private Set<Tag> tagsOfClip = new TreeSet<Tag>((o1, o2) -> o1.getKeyword().compareTo(o2.getKeyword()));
	private Map<LocalDateTime, Comment> comments = new TreeMap<LocalDateTime, Comment>();

	private int id;
	private String name;
	private String performer;
	private AtomicLong viewCount;
	private String path;
	
	private User user;
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	

	public VideoClip(int id, String name, String performer, String path) throws VideoClipException {
		if (name == null || name.trim().equals("") || performer == null || performer.trim().equals("") || path == null
				|| path.trim().equals("")) {
			throw new VideoClipException("Bad Data - constructor");
		}
		this.id = id;
		this.name = name;
		this.performer = performer;
		this.path = path;
		this.user = new User();
		this.viewCount = new AtomicLong();
	}
	
	public VideoClip(int id, String name, String performer, String path, long viewCount, int userId) throws VideoClipException {
		this(id, name, performer, path);
		this.viewCount = new AtomicLong(viewCount);
		this.user.setId(userId);
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
		return viewCount.get();
	}
	
	public void increaseViewCount() {
		this.viewCount.incrementAndGet();
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

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		VideoClip other = (VideoClip) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}

}
