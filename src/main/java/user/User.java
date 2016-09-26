package user;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class User {
	private int id;
	private String firstName;
	private String lastName;
	private String userName;
	private String email;
	private String password;
	private Set<VideoClip> history = new TreeSet<VideoClip>();
	private Set<UserTag> tags = new TreeSet<UserTag>();
	private List<Channel> subscribtions = new LinkedList<Channel>();
	private Channel channel;

	public User(int id, String firstName, String lastName, String userName, String email, String password) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.email = email;
		this.password = password;
		//TODO validate, throw UserException
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
