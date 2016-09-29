package model.user;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import model.tag.UserTag;
import model.videoclip.VideoClip;

public class User implements iUser{

	private int id;
	private String firstName;
	private String lastName;
	private String userName;
	private String email;
	private String password;
	private Set<VideoClip> history = new TreeSet<VideoClip>();
	private Set<UserTag> tags = new TreeSet<UserTag>();
	private List<User> subscribtions = new LinkedList<User>();

	public User(int id, String firstName, String lastName, String userName, String email, String password) throws UserException {
		this.id = id;
		//if (firstName != null && firstName.trim().length() >= MIN_NAME_LENGTH && firstName.length() <= MAX_FIELD_LENGTH) {
			this.firstName = firstName;
		//} else {
		//	throw new UserException("First name not valid");
		//}
		//if (lastName != null && lastName.trim().length() >=MIN_NAME_LENGTH && lastName.length() <= MAX_FIELD_LENGTH) {
			this.lastName = lastName;
		//} else {
		//	throw new UserException("Last name not valid");
		//}
		//if (userName != null && userName.trim().length() >= MIN_NAME_LENGTH && userName.length() <= MAX_FIELD_LENGTH) {
			this.userName = userName;
		//} else {
		//	throw new UserException("User name not valid");
		//}
		//if (email != null && email.trim().length() >= MIN_EMAIL_LENGTH && email.length() <= MAX_FIELD_LENGTH) {
			this.email = email;
		//} else {
		//	throw new UserException("Email not valid");
		//}
		//TODO fix
//		if (password != null && password.length() >= MIN_PASSWORD_LENGTH && password.length() <= MAX_FIELD_LENGTH) {
			this.password = password;
//		} else {
//			throw new UserException("Password not valid");
//		}
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


}
