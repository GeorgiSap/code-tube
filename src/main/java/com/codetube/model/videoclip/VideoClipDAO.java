package com.codetube.model.videoclip;

import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.codetube.model.tag.Tag;
import com.codetube.model.user.User;

public interface VideoClipDAO {
	void setDataSource(DataSource dataSource);

	int addVideoClip(VideoClip videoClip, User user);

	int increaseViewCount(VideoClip videoClip, int numberOfViews);

	List<VideoClip> getClips();

	VideoClip getClip(int id);

	void addTagToVideo(int tagId, int videId);

	Set<Tag> getVideoTags(VideoClip clip);

	List<VideoClip> getClips(int userId);

}