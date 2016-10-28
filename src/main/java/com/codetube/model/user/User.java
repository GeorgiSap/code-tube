package com.codetube.model.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

import com.codetube.model.tag.UserTag;
import com.codetube.model.videoclip.VideoClip;

@Component
public class User implements IUser {

	private int id;
	private String firstName;
	private String lastName;
	private String userName;
	private String email;
	private String password;
	private LinkedList<VideoClip> videos = new LinkedList<VideoClip>();
	private Map<User, List<VideoClip>> subscriptions = new HashMap<User, List<VideoClip>>();
	private Set<VideoClip> history = new LinkedHashSet<VideoClip>();
	private Set<UserTag> tags = new TreeSet<UserTag>();

	public User() {
	}

	public User(int id, String firstName, String lastName, String userName, String email, String password)
			throws UserException {
		this.id = id;
		if (firstName != null && firstName.trim().length() >= MIN_NAME_LENGTH
				&& firstName.length() <= MAX_FIELD_LENGTH) {
			this.firstName = firstName;
		} else {
			throw new UserException("First name not valid");
		}
		if (lastName != null && lastName.trim().length() >= MIN_NAME_LENGTH && lastName.length() <= MAX_FIELD_LENGTH) {
			this.lastName = lastName;
		} else {
			throw new UserException("Last name not valid");
		}
		if (userName != null && userName.trim().length() >= MIN_NAME_LENGTH && userName.length() <= MAX_FIELD_LENGTH) {
			this.userName = userName;
		} else {
			throw new UserException("User name not valid");
		}
		if (email != null && email.trim().length() >= MIN_EMAIL_LENGTH && email.length() <= MAX_FIELD_LENGTH) {
			this.email = email;
		} else {
			throw new UserException("Email not valid");
		}
		if (password != null) {
			this.password = password;
		} else {
			throw new UserException("Password not valid");
		}
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", userName=" + userName
				+ ", email=" + email + ", password=" + password + ", history=" + history + ", tags=" + tags
				+ ", subscribtions=" + subscriptions + "]";
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void addToVideos(VideoClip videoClip) {
		videos.addLast(videoClip);
	}
	
	public void addNewVideo(VideoClip videoClip) {
		videos.addFirst(videoClip);
	}

	public void addToHistory(VideoClip videoClip) {
		if (history.contains(videoClip)) {
			history.remove(videoClip);
		}
		history.add(videoClip);
	}

	public void addToSubscriptions(User user, List<VideoClip> videoClips) {
		subscriptions.put(user, videoClips);
	}

	public void removeFromSubscriptions(User user) {
		subscriptions.remove(user);
	}

	public List<VideoClip> getVideos() {
		return Collections.unmodifiableList(videos);
	}

	public List<VideoClip> getHistory() {
		List<VideoClip> historyAsList = new ArrayList<VideoClip>(history);
		Collections.reverse(historyAsList);
		return historyAsList;
	}

	public Map<User, List<VideoClip>> getSubscribtions() {
		return subscriptions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

}
