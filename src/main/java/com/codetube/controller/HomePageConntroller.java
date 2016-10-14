package com.codetube.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jboss.netty.handler.codec.http.HttpRequest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codetube.model.tag.Tag;
import com.codetube.model.tag.TagDAO;

@Controller
@RequestMapping(value="/index")
public class HomePageConntroller {
	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	TagDAO tagJDBCTemplate = (TagDAO) context.getBean("TagJDBCTemplate");
	
	@RequestMapping(method = RequestMethod.GET)
	public String sayHello(Model model, HttpServletRequest request) {

		List<Tag> allTags = tagJDBCTemplate.getTags();
		request.setAttribute("allTags", allTags);
		
		return "index";
	}	

}
