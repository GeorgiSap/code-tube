package com.codetube.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
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

import com.codetube.model.comment.Comment;
import com.codetube.model.comment.CommentDAO;
import com.codetube.model.tag.Tag;
import com.codetube.model.tag.TagDAO;
import com.codetube.model.user.User;
import com.codetube.model.user.UserJDBCTemplate;
import com.codetube.model.user.history.History;
import com.codetube.model.user.history.HistoryDAO;
import com.codetube.model.videoclip.VideoClip;
import com.codetube.model.videoclip.VideoClipException;
import com.codetube.model.videoclip.VideoClipJDBCTemplate;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Controller
@SessionAttributes("player")
public class VideoController {
	private ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

	public VideoClipJDBCTemplate videoClipJDBCTemplate = (VideoClipJDBCTemplate) context
			.getBean("VideoClipJDBCTemplate");
	public CommentDAO commentJDBCTemplate = (CommentDAO) context.getBean("CommentJDBCTemplate");
	public HistoryDAO historyJDBCTemplate = (HistoryDAO) context.getBean("HistoryJDBCTemplate");
	public UserJDBCTemplate userJDBCTemplate = (UserJDBCTemplate) context.getBean("UserJDBCTemplate");
	public TagDAO tagJDBCTemplate = (TagDAO) context.getBean("TagJDBCTemplate");

	@RequestMapping(value = "/player/{video_id}", method = RequestMethod.GET)
	public String products(Model model, @PathVariable("video_id") Integer videoId, HttpServletRequest request) {
		try {
			if (request.getSession(false) == null) {
				return "index";
			}

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
				user.addToHistory(new History(videoId, lastViewed));
				System.err.println("Successfully added to history?");
			} else {
				// Add cookie for non-registered users to keep record of last
				// viewed
			}

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

	@RequestMapping(value = "/getComments/{video_id}", method = RequestMethod.GET)
	protected void getComments(@PathVariable("video_id") Integer videoId, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		
		/*Set<Comment> comments = commentJDBCTemplate.getComments(videoId);
	
		if (comments != null)
			response.getWriter().print(new Gson().toJson(comments));
		else
			response.getWriter().print("[]");
			*/
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

	@RequestMapping(value = "/comment", method = RequestMethod.PUT)
	protected void putCommentIn(HttpServletRequest request, HttpServletResponse response) {
		Scanner sc;
		User user;
		VideoClip clip;

		try {

			sc = new Scanner(request.getInputStream());

			StringBuilder buf = new StringBuilder();
			while (sc.hasNextLine()) {
				buf.append(sc.nextLine());
			}

			System.out.println(request.getRequestURI());
			JsonObject object = (JsonObject) new JsonParser().parse(buf.toString());
			String text = object.get("text").getAsString();
			text = text.trim();
			int clipId = object.get("id").getAsInt();
			clip = videoClipJDBCTemplate.getClip(clipId);

			int userId = (int) request.getSession().getAttribute("user_id");
			user = userJDBCTemplate.get(userId);

			int commetId = commentJDBCTemplate.addCommentToVideo(clip, new Comment(0, text, LocalDateTime.now(), 1),
					user);
			System.out.println("Added a comment with id " + commetId);

		} catch (Exception e) {
		}
	}

}