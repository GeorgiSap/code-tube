package com.example.model.videoclip;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
public interface VideoClipDAO {
	void setDataSource(DataSource dataSource);

	int addVideoClip(VideoClip videoClip);

	int increaseViewCount(VideoClip videoClip, int numberOfViews);

	List<VideoClip> getClips();

	VideoClip getClip(int id);

}