package com.example.model.comment;

import javax.sql.DataSource;

import com.example.model.user.User;
import com.example.model.videoclip.VideoClip;

public interface CommentDAO {

	void setDataSource(DataSource dataSource);

	int addCommentToVideo(VideoClip video, Comment comment, User user);

}