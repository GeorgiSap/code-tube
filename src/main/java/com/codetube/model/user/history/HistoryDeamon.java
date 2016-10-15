package com.codetube.model.user.history;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.codetube.model.user.User;
import com.codetube.model.user.UserDAO;

public class HistoryDeamon implements Runnable {

	private static final int DEAMON_SLEEP_DURATION = 1000 * 60 * 60 * 24;
	public static final int MAX_HISTORY_ENTRIES_PER_USER = 10;
	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	UserDAO userJDBCTemplate = (UserDAO) context.getBean("UserJDBCTemplate");
	HistoryDAO historyJDBCTemplate = (HistoryDAO) context.getBean("HistoryJDBCTemplate");

	@Override
	public void run() {
		while (true) {
			lowerHistoryVolume();
			try {
				Thread.sleep(DEAMON_SLEEP_DURATION);
			} catch (InterruptedException e) {
				return;
			}
		}
	}

	private void lowerHistoryVolume() {
		List<User> users = userJDBCTemplate.listUsers();
		for (User user : users) {
			List<History> history = historyJDBCTemplate.getHistory(user.getId());
			if (history.size() > HistoryDeamon.MAX_HISTORY_ENTRIES_PER_USER) {
				for (int entry = 0; entry < history.size() - HistoryDeamon.MAX_HISTORY_ENTRIES_PER_USER; entry++) {
					historyJDBCTemplate.removeFromHistory(history.get(entry).getVideoClipId(), user.getId());
				}
			}
		}
	}

}
