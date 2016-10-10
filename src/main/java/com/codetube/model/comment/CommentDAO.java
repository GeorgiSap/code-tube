package com.codetube.model.comment;

import java.util.Set;

import javax.sql.DataSource;

import com.codetube.model.user.User;
import com.codetube.model.videoclip.VideoClip;

public interface CommentDAO {

	void setDataSource(DataSource dataSource);

	int addCommentToVideo(VideoClip video, Comment comment, User user);

	Set<Comment> getComments(int userId);

}