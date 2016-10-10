package com.codetube.controller.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codetube.model.user.User;

@Controller
public class HomeController {
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(HttpServletRequest request) {
		if (request.getSession(false) == null) {
			return "index";
		}
		request.setAttribute("title", "Recommended");
		return "home";
	}
	
	@RequestMapping(value = "/videos", method = RequestMethod.GET)
	public String showMyVideos(HttpServletRequest request) {
		if (request.getSession(false) == null) {
			return "index";
		}
		request.setAttribute("title", "My Videos");
		
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
		request.setAttribute("videos", user.getVideos());
		}
		return "home";
	}

	
	@RequestMapping(value = "/subscriptions", method = RequestMethod.GET)
	public String showSubscriptions(HttpServletRequest request) {
		if (request.getSession(false) == null) {
			return "index";
		}
		request.setAttribute("title", "Subscriptions");
		//User user = (User) request.getSession().getAttribute("user");
		//TODO get videos
		//request.setAttribute("videos", user.getSubscribtions());
		return "home";
	}
	
	
	@RequestMapping(value = "/history", method = RequestMethod.GET)
	public String showHistory(HttpServletRequest request) {
		if (request.getSession(false) == null) {
			return "index";
		}
		request.setAttribute("title", "Last viewed");
		//TODO
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
		request.setAttribute("videos", user.getHistory());
		}
		return "home";
	}
}
