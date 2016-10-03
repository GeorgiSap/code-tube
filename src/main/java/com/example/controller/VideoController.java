package com.example.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.model.videoclip.VideoClip;
import com.example.model.videoclip.VideoClipJDBCTemplate;

@Controller
@SessionAttributes("video")
public class VideoController {
	private ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	@Autowired
	public VideoClipJDBCTemplate videoClipJDBCTemplate = (VideoClipJDBCTemplate) context.getBean("VideoClipJDBCTemplate");
	
	@RequestMapping(value = "/videos/{video_id}", method = RequestMethod.GET)
	public String products(Model model , HttpServletRequest request) {
		List<VideoClip> list = videoClipJDBCTemplate.getClips();
		System.out.println(list);
		request.setAttribute("videos", list);
		return "singlevideo";
	}
}
