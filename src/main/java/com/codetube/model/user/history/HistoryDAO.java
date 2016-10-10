package com.codetube.model.user.history;

import java.time.LocalDateTime;
import java.util.List;

import javax.sql.DataSource;

import com.codetube.model.user.UserException;

public interface HistoryDAO {

	void setDataSource(DataSource dataSource);

	List<History> getHistory(int userId);

	void removeFromHistory(int videoClipId, int userId);

	void addToHistory(int videoClipId, int userId, LocalDateTime lastViewed) throws UserException;

}