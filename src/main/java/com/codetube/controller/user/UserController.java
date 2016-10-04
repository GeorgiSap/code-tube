package com.codetube.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.codetube.model.user.User;

public class UserController {
	private static final int SESSION_LENGTH = 3*60/24;
	
	public void createSession(HttpServletRequest request, User user) {
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(SESSION_LENGTH);
		session.setAttribute("user_name", user.getUserName());
	}
	
	public void disableCache(HttpServletResponse response) {
		response.setHeader("Pragma", "No-cache");
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-cache");
	}
}
