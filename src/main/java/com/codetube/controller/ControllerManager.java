package com.codetube.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.codetube.model.tag.Tag;
import com.codetube.model.tag.TagDAO;
import com.codetube.model.user.UserDAO;
import com.codetube.model.videoclip.VideoClipDAO;

public class ControllerManager {
	protected ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	protected VideoClipDAO videoClipJDBCTemplate = (VideoClipDAO) context.getBean("VideoClipJDBCTemplate");
	protected UserDAO userJDBCTemplate = (UserDAO) context.getBean("UserJDBCTemplate");
	protected TagDAO tagJDBCTemplate = (TagDAO) context.getBean("TagJDBCTemplate");

	protected void loadTags(HttpServletRequest request) {
		List<Tag> allTags = tagJDBCTemplate.getTags();
		request.setAttribute("allTags", allTags);
	}
}
