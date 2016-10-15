package com.codetube.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codetube.model.comment.Comment;
import com.codetube.model.comment.CommentDAO;
import com.codetube.model.user.User;
import com.codetube.model.user.UserJDBCTemplate;
import com.codetube.model.videoclip.VideoClip;
import com.codetube.model.videoclip.VideoClipJDBCTemplate;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Controller
public class CommentsController {
	private ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	public VideoClipJDBCTemplate videoClipJDBCTemplate = (VideoClipJDBCTemplate) context
			.getBean("VideoClipJDBCTemplate");
	public CommentDAO commentJDBCTemplate = (CommentDAO) context.getBean("CommentJDBCTemplate");
	public UserJDBCTemplate userJDBCTemplate = (UserJDBCTemplate) context.getBean("UserJDBCTemplate");

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
			System.out.println(object);
			String text = object.get("textAreaAddingComment").getAsString();
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

	@RequestMapping(value = "/getComments/{video_id}", method = RequestMethod.GET)
	protected void getComments(@PathVariable("video_id") Integer videoId, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");

		Set<Comment> comments = commentJDBCTemplate.getComments(videoId);
		System.out.println("They called me " + comments);
		for (Comment comment : comments) {
			User user = userJDBCTemplate.get(comment.getUser().getId());
			comment.getUser().setUserName(user.getUserName());
		}
		if (comments != null)
			response.getWriter().print(new Gson().toJson(comments));
		else
			response.getWriter().print("[]");

	}
}
