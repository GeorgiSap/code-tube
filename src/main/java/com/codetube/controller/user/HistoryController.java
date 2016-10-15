package com.codetube.controller.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codetube.controller.ControllerManager;
import com.codetube.model.user.User;
import com.codetube.model.user.history.History;
import com.codetube.model.videoclip.VideoClip;

@Controller
public class HistoryController extends ControllerManager {

	@RequestMapping(value = "/history", method = RequestMethod.GET)
	public String showHistory(HttpServletRequest request) {
		if (request.getSession(false) == null) {
			loadTags(request);
			return "index";
		}

		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			Map<Integer, LocalDateTime> history = user.getHistory();
			Set<History> historySet = sortUserHistory(history);
			List<VideoClip> historyEntries = extractVideosFromHistory(historySet);
			request.setAttribute("videosToLoad", historyEntries);
		}
		loadTags(request);
		request.setAttribute("title", "Last viewed");
		return "home";
	}

	private List<VideoClip> extractVideosFromHistory(Set<History> historySet) {
		List<VideoClip> historyEntries = new ArrayList<VideoClip>();
		for (History entry : historySet) {
			VideoClip videoClip = videoClipJDBCTemplate.getClip(entry.getVideoClipId());
			historyEntries.add(videoClip);
			User publisher = userJDBCTemplate.get(videoClip.getUser().getId());
			videoClip.setUser(publisher);
		}
		return historyEntries;
	}

	private Set<History> sortUserHistory(Map<Integer, LocalDateTime> history) {
		Set<History> historySet = new TreeSet<History>();
		for (Entry<Integer, LocalDateTime> entry : history.entrySet()) {
			historySet.add(new History(entry.getKey(), entry.getValue()));
		}
		return historySet;
	}
}
