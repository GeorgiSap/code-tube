package com.codetube.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.codetube.WebInitializer;
import com.codetube.model.tag.Tag;
import com.codetube.model.tag.TagDAO;
import com.codetube.model.user.User;
import com.codetube.model.user.UserDAO;
import com.codetube.model.user.UserJDBCTemplate;
import com.codetube.model.videoclip.VideoClip;
import com.codetube.model.videoclip.VideoClipDAO;
import com.codetube.model.videoclip.VideoClipException;
import com.codetube.model.videoclip.VideoClipJDBCTemplate;

@Controller
public class UploadController {
	private ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	@Autowired
	public VideoClipJDBCTemplate videoClipJDBCTemplate = (VideoClipJDBCTemplate) context
			.getBean("VideoClipJDBCTemplate");
	@Autowired
	public UserJDBCTemplate userJDBCTemplate = (UserJDBCTemplate) context.getBean("UserJDBCTemplate");

	@Autowired
	public TagDAO tagJDBCTemplate = (TagDAO) context.getBean("TagJDBCTemplate");

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String showUploadPage(HttpServletRequest request) {
		if (request.getSession(false) == null) {
			return "index";
		}

		List<Tag> tags = (List<Tag>) tagJDBCTemplate.getTags();
		request.setAttribute("tags", tags);
		return "upload";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String singleFileUpload(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request,
			@RequestParam("artist") String performerOfVideo, @RequestParam("tag") String tag, ModelMap model)
			throws IOException {
		try {
			int clipId;
			int userId;
			String[] path;
			String fileName;
			System.out.println(tag);
			if (request.getSession(false) == null) {
				return "index";
			}

			if ((performerOfVideo == null) || (performerOfVideo.trim().equals(""))
					|| (performerOfVideo.matches(".*\\d+.*"))) {
				return "index";
			}

			if (request.getSession().getAttribute("user_id") == null) {
				return "index";
			}

			userId = (int) request.getSession().getAttribute("user_id");
			path = multipartFile.getOriginalFilename().split("/");
			fileName = path[path.length - 1];

			System.out.println("Do tuk dobre " + userId + "template " + userJDBCTemplate);
			User user = userJDBCTemplate.get(userId);
			if (user == null) {
				return "index";
			}

			clipId = (int) videoClipJDBCTemplate.addVideoClip(new VideoClip(0, fileName, performerOfVideo, path[0]),
					user);
			System.out.println("I added successfully clip with " + clipId);
			FileCopyUtils.copy(multipartFile.getBytes(), new File(WebInitializer.LOCATION + fileName));
		} catch (VideoClipException e) {
			return "index";
		}
		return "index";
	}

}
