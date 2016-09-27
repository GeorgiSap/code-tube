package model.videoclip;

import java.util.List;

import javax.sql.DataSource;

public interface VideoClipDAO {

	void setDataSource(DataSource dataSource);

	int addVideoClip(VideoClip videoClip);

	int increaseViewCount(VideoClip videoClip, int numberOfViews);

	List<VideoClip> getClips();

}