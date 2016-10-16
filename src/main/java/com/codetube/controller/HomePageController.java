package com.codetube.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codetube.controller.user.UserController;
import com.codetube.model.tag.TagDAO;

@Controller
@RequestMapping(value="/index")
public class HomePageController extends UserController{
	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	TagDAO tagJDBCTemplate = (TagDAO) context.getBean("TagJDBCTemplate");
	
	@RequestMapping(method = RequestMethod.GET)
	public String sayHello(Model model, HttpServletRequest request) {
		loadNewestVideos(request);
		loadTags(request);
		return "home";
	}	

}
