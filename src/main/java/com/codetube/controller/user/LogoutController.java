package com.codetube.controller.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codetube.controller.ControllerManager;
import com.codetube.model.tag.Tag;
import com.codetube.model.tag.TagDAO;

@Controller
@RequestMapping(value = "/logout")
public class LogoutController extends UserController {

	@RequestMapping(method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().invalidate();
		loadNewestVideos(request);
		loadTags(request);
		return "home";
	}

}
