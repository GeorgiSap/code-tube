package com.codetube.controller.user;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codetube.model.tag.Tag;
import com.codetube.model.tag.TagDAO;
import com.codetube.model.user.User;
import com.codetube.model.videoclip.VideoClip;

@Controller
public class UserVideosController extends UserController {
	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	TagDAO tagJDBCTemplate = (TagDAO) context.getBean("TagJDBCTemplate");

	@RequestMapping(value = "/videos", method = RequestMethod.GET)
	public String showMyVideos(HttpServletRequest request) {
		try {
			if (request.getSession(false) == null) {
				return "index";
			}
			request.setAttribute("title", "My Videos");
			loadUserVideos(request);
			loadTags(request);
			return "home";
		} catch (Exception e) {
			return "home";
		}
	}

}
