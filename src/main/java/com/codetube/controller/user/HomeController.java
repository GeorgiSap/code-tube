package com.codetube.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codetube.model.tag.Tag;

@Controller
public class HomeController extends UserController {
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(HttpServletRequest request) {
		if (request.getSession(false) == null) {
			List<Tag> allTags = tagJDBCTemplate.getTags();
			request.setAttribute("allTags", allTags);
			return "index";
		}
		loadNewestVideos(request);
		List<Tag> allTags = tagJDBCTemplate.getTags();
		request.setAttribute("allTags", allTags);
		return "home";
	}
}
