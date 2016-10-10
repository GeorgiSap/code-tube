package com.codetube.model.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

import com.codetube.model.tag.UserTag;
import com.codetube.model.user.history.History;
import com.codetube.model.videoclip.VideoClip;

@Component
public class User implements IUser {

	private int id;
	private String firstName;
	private String lastName;
	private String userName;
	private String email;
	private String password;
	private List<VideoClip> videos = new ArrayList<VideoClip>();
	private List<User> subscriptions = new ArrayList<User>();
	private HashMap<Integer, LocalDateTime> history = new HashMap<Integer, LocalDateTime>();
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
		 if (lastName != null && lastName.trim().length() >=MIN_NAME_LENGTH &&
		 lastName.length() <= MAX_FIELD_LENGTH) {
		this.lastName = lastName;
		 } else {
		 throw new UserException("Last name not valid");
		 }
		 if (userName != null && userName.trim().length() >= MIN_NAME_LENGTH
		 && userName.length() <= MAX_FIELD_LENGTH) {
		this.userName = userName;
		 } else {
		 throw new UserException("User name not valid");
		 }
		 if (email != null && email.trim().length() >= MIN_EMAIL_LENGTH &&
		 email.length() <= MAX_FIELD_LENGTH) {
		this.email = email;
		 } else {
		 throw new UserException("Email not valid");
		 }
		// TODO fix
		// if (password != null && password.length() >= MIN_PASSWORD_LENGTH &&
		// password.length() <= MAX_FIELD_LENGTH) {
		this.password = password;
		// } else {
		// throw new UserException("Password not valid");
		// }
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
		videos.add(videoClip);
	}
	
	public void addToHistory(int videoClipId, LocalDateTime lastViewed) {
		history.put(videoClipId, lastViewed);
	}
	
	public void addToSubscriptions(User user) {
		subscriptions.add(user);
	}

	public List<VideoClip> getVideos() {
		return Collections.unmodifiableList(videos);
	}
	
	public Map<Integer, LocalDateTime> getHistory() {
		return Collections.unmodifiableMap(history);
	}

	public List<User> getSubscribtions() {
		return Collections.unmodifiableList(subscriptions);
	}

	
	

}
