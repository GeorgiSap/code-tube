package com.codetube.controller.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codetube.model.tag.Tag;
import com.codetube.model.tag.TagDAO;
import com.codetube.model.user.User;
import com.codetube.model.user.UserDAO;
import com.codetube.model.user.history.History;
import com.codetube.model.videoclip.VideoClip;
import com.codetube.model.videoclip.VideoClipDAO;

@Controller
public class HistoryController {
	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	VideoClipDAO videoClipJDBCTemplate = (VideoClipDAO) context.getBean("VideoClipJDBCTemplate");
	UserDAO userJDBCTemplate = (UserDAO) context.getBean("UserJDBCTemplate");
	TagDAO tagJDBCTemplate = (TagDAO) context.getBean("TagJDBCTemplate");
	
	@RequestMapping(value = "/history", method = RequestMethod.GET)
	public String showHistory(HttpServletRequest request) {
		if (request.getSession(false) == null) {
			List<Tag> allTags = tagJDBCTemplate.getTags();
			request.setAttribute("allTags", allTags);
			return "index";
		}
		request.setAttribute("title", "Last viewed");
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
		Map<Integer, LocalDateTime> history = user.getHistory();
		Set<History> historySet = new TreeSet<History>();
		for (Entry<Integer, LocalDateTime> entry : history.entrySet()) {
			historySet.add(new History(entry.getKey(), entry.getValue()));
		}
		List<VideoClip> historyEntries = new ArrayList<VideoClip>();
		for (History entry : historySet) {
			historyEntries.add(videoClipJDBCTemplate.getClip(entry.getVideoClipId()));
		}
		for (VideoClip videoClip : historyEntries) {
			User publisher = userJDBCTemplate.get(videoClip.getUser().getId());
			videoClip.setUser(publisher);
		}
		request.setAttribute("videosToLoad", historyEntries);
		}
		List<Tag> allTags = tagJDBCTemplate.getTags();
		request.setAttribute("allTags", allTags);
		return "home";
	}
}
