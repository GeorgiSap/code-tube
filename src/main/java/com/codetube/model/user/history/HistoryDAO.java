package com.codetube.model.user.history;

import java.util.List;

import javax.sql.DataSource;

import com.codetube.model.user.UserException;

public interface HistoryDAO {

	void setDataSource(DataSource dataSource);

	void addToHistory(int videoClipId, int userId) throws UserException;

	List<History> getHistory(int userId);

}