package com.codetube.model.user.history;

import java.time.LocalDateTime;

public class History implements Comparable<History>{
	private int videoClipId;
	private LocalDateTime lastViewed;
	
	public History(int videoClipId, LocalDateTime lastViewed) {
		this.videoClipId = videoClipId;
		this.lastViewed = lastViewed;
	}

	public int getVideoClipId() {
		return videoClipId;
	}

	public void setVideoClipId(int videoClipId) {
		this.videoClipId = videoClipId;
	}

	public LocalDateTime getLastViewed() {
		return lastViewed;
	}

	public void setLastViewed(LocalDateTime lastViewed) {
		this.lastViewed = lastViewed;
	}

	@Override
	public int compareTo(History entry) {
		if (this.videoClipId == entry.getVideoClipId()) {
			entry.setLastViewed(this.lastViewed);
			return 0;
		}
		return entry.getLastViewed().compareTo(this.lastViewed);
	}
	
	
}


