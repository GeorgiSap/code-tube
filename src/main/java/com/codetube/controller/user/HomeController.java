package com.codetube.controller.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController extends UserController {

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(HttpServletRequest request) {
		try {
			loadNewestVideos(request);
			loadTags(request);
			return "home";
		} catch (Exception e) {
			return "home";
		}
	}
}
