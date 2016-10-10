package com.codetube.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
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

import com.codetube.model.tag.Tag;
import com.codetube.model.tag.TagDAO;
import com.codetube.model.user.User;
import com.codetube.model.user.history.History;
import com.codetube.model.user.history.HistoryDAO;
import com.codetube.model.videoclip.VideoClip;
import com.codetube.model.videoclip.VideoClipException;
import com.codetube.model.videoclip.VideoClipJDBCTemplate;
import com.google.gson.Gson;

@Controller
@SessionAttributes("player")
public class VideoController {
	private ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

	public VideoClipJDBCTemplate videoClipJDBCTemplate = (VideoClipJDBCTemplate) context
			.getBean("VideoClipJDBCTemplate");
	HistoryDAO historyJDBCTemplate = (HistoryDAO) context.getBean("HistoryJDBCTemplate");

	public TagDAO tagJDBCTemplate = (TagDAO) context.getBean("TagJDBCTemplate");

	@RequestMapping(value = "/player/{video_id}", method = RequestMethod.GET)
	public String products(Model model, @PathVariable("video_id") Integer videoId, HttpServletRequest request) {
		try {
			System.out.println("dafaq");
			if (request.getSession(false) == null) {
				return "index";
			}
			System.out.println("tyk");
			VideoClip clip = videoClipJDBCTemplate.getClip(videoId);
			Set<Tag> tags = videoClipJDBCTemplate.getVideoTags(clip);
			System.out.println(tags);
			for (Tag tag : tags) {
				clip.addTag(tag);
			}
			// clip.addTag(tag);
			System.out.println(clip);
			model.addAttribute("video", clip);

			// Add to history
			if (request.getSession(false) != null) {
				User user = (User) request.getSession().getAttribute("user");
				LocalDateTime lastViewed = LocalDateTime.now();
				historyJDBCTemplate.addToHistory(clip.getId(), user.getId(), lastViewed);
				user.addToHistory(clip.getId(), lastViewed);
			} else {
				// Add cookie for non-registered users to keep record of last viewed
			}
			videoClipJDBCTemplate.increaseViewCount(clip);
			clip.increaseViewCount();
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

			String username = request.getParameter("user");
			List<VideoClip> list = videoClipJDBCTemplate.getClips();
			for (VideoClip clip : list) {
				Set<Tag> tags = videoClipJDBCTemplate.getVideoTags(clip);

				for (Tag tag : tags) {
					clip.addTag(tag);
				}
			}

			if (list != null)
				response.getWriter().print(new Gson().toJson(list));
			else
				response.getWriter().print("[]");

		} catch (VideoClipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}