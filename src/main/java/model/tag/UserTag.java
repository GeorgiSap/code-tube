package model.tag;

import java.time.LocalDateTime;

public class UserTag extends Tag {
	private int counterOfClicks;
	private LocalDateTime lastViewdOn;

	

	public UserTag(int id, String keyword) throws TagException {
		super(id, keyword);
	}

	public void upVoteTag(LocalDateTime time) {
		counterOfClicks++;
		this.lastViewdOn = time;
	}

	public int getCounterOfClicks() {
		return counterOfClicks;
	}

	public LocalDateTime getLastViewdOn() {
		return lastViewdOn;
	}

}
