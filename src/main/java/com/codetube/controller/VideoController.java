package com.codetube.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.codetube.model.comment.CommentDAO;
import com.codetube.model.tag.Tag;
import com.codetube.model.tag.TagDAO;
import com.codetube.model.user.User;
import com.codetube.model.user.UserJDBCTemplate;
import com.codetube.model.user.history.HistoryDAO;
import com.codetube.model.videoclip.VideoClip;
import com.codetube.model.videoclip.VideoClipException;
import com.codetube.model.videoclip.VideoClipJDBCTemplate;
import com.google.gson.Gson;

@Controller
@SessionAttributes("player")
public class VideoController extends ControllerManager {
	private static final int OLDER_HISTORY_ENTRY_INDEX = 0;
	private static final int COOKIES_EXPIRY_PERIOD = 60 * 60 * 24 * 30;
	private static final int COUNT_OF_HISTORY_ENTRIES = 10;
	public CommentDAO commentJDBCTemplate = (CommentDAO) context.getBean("CommentJDBCTemplate");
	public HistoryDAO historyJDBCTemplate = (HistoryDAO) context.getBean("HistoryJDBCTemplate");

	@RequestMapping(value = "/player/{video_id}", method = RequestMethod.GET)
	public String showVideo(Model model, @PathVariable("video_id") Integer videoId, HttpServletRequest request,
			HttpServletResponse response) {

		if (videoId == 0) {
			List<Tag> allTags = tagJDBCTemplate.getTags();
			request.setAttribute("allTags", allTags);
			String history = null;
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				System.out.println("COOKIES != NULL");
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("history")) {
						history = cookie.getValue();
					}
				}
			}

			List<Integer> entryIds = new ArrayList<Integer>();
			List<VideoClip> historyEntries = new ArrayList<VideoClip>();
			if (history != null) {
				// Retrieve history from cookie
				String[] entries = history.split(",");
				for (int index = 0; index < entries.length; index++) {
					entryIds.add(Integer.parseInt(entries[index]));
				}
				for (int entry = entryIds.size() - 1; entry >= 0; entry--) {
					historyEntries.add(videoClipJDBCTemplate.getClip(entryIds.get(entry)));
				}
				for (VideoClip videoClip : historyEntries) {
					User publisher = userJDBCTemplate.get(videoClip.getUser().getId());
					videoClip.setUser(publisher);
				}
			} else {
				System.err.println("NULL");
			}

			loadTags(request);
			request.setAttribute("title", "Last viewed");
			request.setAttribute("videosToLoad", historyEntries);
			return "home";
		}

		try {

			VideoClip clip = videoClipJDBCTemplate.getClip(videoId);
			Set<Tag> tags = videoClipJDBCTemplate.getVideoTags(clip);
			System.out.println(tags);
			for (Tag tag : tags) {
				clip.addTag(tag);
			}

			model.addAttribute("video", clip);

			// Add to history
			LocalDateTime lastViewed = LocalDateTime.now();
			if (request.getSession(false) != null) {
				User user = (User) request.getSession().getAttribute("user");
				historyJDBCTemplate.addToHistory(clip.getId(), user.getId(), lastViewed);
				user.addToHistory(clip.getId(), lastViewed);
			} else {

				// Find history cookie
				String history = null;
				Cookie[] cookies = request.getCookies();
				if (cookies != null) {
					for (Cookie cookie : cookies) {
						if (cookie.getName().equals("history")) {
							history = cookie.getValue();
							System.err.println(history);
						}
					}
				} else {
					System.out.println("COOKIES == NULL");
				}

				List<Integer> entryIds = new ArrayList<Integer>();
				if (history != null) {
					// Retrieve history from cookie
					String[] entries = history.split(",");
					for (int index = 0; index < entries.length; index++) {
						entryIds.add(Integer.parseInt(entries[index]));
					}
					// Check if new history entry already exists and delete it
					for (int entry = 0; entry < entryIds.size(); entry++) {
						if (entryIds.get(entry) == videoId) {
							entryIds.remove(entry);
						}
					}
				} else {
					System.out.println("HISTORY == NULL");
				}

				// Add new history entry
				entryIds.add(videoId);
				System.out.println("ADDED " + videoId);
				// Check if entries exceed max count
				if (entryIds.size() > COUNT_OF_HISTORY_ENTRIES) {
					entryIds.remove(OLDER_HISTORY_ENTRY_INDEX);
				}

				// Generate new cookie string
				StringBuilder builder = new StringBuilder();
				for (Integer entryId : entryIds) {
					builder.append(entryId + ",");
				}
				builder.setLength(builder.length() - 1);
				System.out.println(builder.toString().toUpperCase());

				// Update cookie
				Cookie cookie = new Cookie("history", builder.toString());
				cookie.setMaxAge(COOKIES_EXPIRY_PERIOD);
				response.addCookie(cookie);
			}

			// Increase view count
			videoClipJDBCTemplate.increaseViewCount(clip);
			clip.increaseViewCount();
			loadTags(request);
			return "single";

		} catch (Exception e) {
			e.printStackTrace();
			return "index";
		}

	}

	@RequestMapping(value = "/data", method = RequestMethod.GET)
	protected void getDataJSon(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		try {
			List<VideoClip> list = videoClipJDBCTemplate.getClips();

			for (VideoClip clip : list) {
				User user = userJDBCTemplate.get(clip.getUser().getId());
				clip.getUser().setFirstName(user.getFirstName());
				Set<Tag> tags = videoClipJDBCTemplate.getVideoTags(clip);

				for (Tag tag : tags) {
					clip.addTag(tag);
				}
			}

			if (list != null) {
				response.getWriter().print(new Gson().toJson(list));
			} else {
				response.getWriter().print("[]");
			}

		} catch (VideoClipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/data/{video_id}", method = RequestMethod.GET)
	protected void getDataJSonToSingleVideo(@PathVariable("video_id") Integer videoId, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		try {
			VideoClip clip = videoClipJDBCTemplate.getClip(videoId);

			Set<Tag> tags = videoClipJDBCTemplate.getVideoTags(clip);

			for (Tag tag : tags) {
				clip.addTag(tag);
			}

			if (clip != null)
				response.getWriter().print(new Gson().toJson(clip));
			else
				response.getWriter().print("[]");

		} catch (VideoClipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}