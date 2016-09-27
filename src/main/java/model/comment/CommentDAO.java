package model.comment;

import javax.sql.DataSource;

import model.user.User;
import model.videoclip.VideoClip;

public interface CommentDAO {

	void setDataSource(DataSource dataSource);

	int addCommentToVideo(VideoClip video, Comment comment, User user);

}