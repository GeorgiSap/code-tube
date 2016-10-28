package com.codetube.model.videoclip;

import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import com.codetube.model.tag.Tag;
import com.codetube.model.user.User;

public interface VideoClipDAO {
	void setDataSource(DataSource dataSource);

	int addVideoClip(VideoClip videoClip, User user);

	List<VideoClip> getClips();

	VideoClip getClip(int id);

	void addTagToVideo(int tagId, int videId);

	Set<Tag> getVideoTags(VideoClip clip);

	List<VideoClip> getClips(int userId);

	void increaseViewCount(VideoClip videoClip);

	List<VideoClip> getClipsByTag(int tagId);

	List<VideoClip> getMostViewedVideos(int count);

	List<VideoClip> getMostCommentedVideos(int numberOfVideos);

	List<VideoClip> getNewestVideos(int countOfVideos);

	List<VideoClip> getClips(int userId, int countOfVideos);

}