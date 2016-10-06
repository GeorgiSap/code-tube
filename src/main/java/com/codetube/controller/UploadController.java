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

			VideoClip clip;
			User user;
			Tag tagObject;

			// incoming validation, IF NULL, evryting is ok!
			String page = validation(request, performerOfVideo, tag);

			if (page != null) {
				System.out.println("Error occured, did't add the video");
				return page;
			}

			userId = (int) request.getSession().getAttribute("user_id");
			path = multipartFile.getOriginalFilename().split("/");
			fileName = path[path.length - 1];

			System.out.println("Added user succesfully with id " + userId);
			user = userJDBCTemplate.get(userId);

			if (user == null) {
				return "index";
			}

			clipId = (int) videoClipJDBCTemplate.addVideoClip(new VideoClip(0, fileName, performerOfVideo, path[0]),
					user);
			System.out.println("I added successfully clip with " + clipId);
			clip = videoClipJDBCTemplate.getClip(clipId);

			List<Tag> tags = tagJDBCTemplate.getTags();
			if (!doesTagExists(tag, tags)) {
				return "index";
			}
			tagObject = tagJDBCTemplate.getTag(tag);
			if (tagObject == null) {
				System.out.println("TagObject is null!");
				return "index";
			}
			
			System.out.println("I got tag wit id " + tagObject.getId());
			
			videoClipJDBCTemplate.addTagToVideo(tagObject.getId(), clipId);
			System.out.println("Added tag in video has tags");
			FileCopyUtils.copy(multipartFile.getBytes(), new File(WebInitializer.LOCATION + fileName));
		} catch (VideoClipException e) {
			return "index";
		}
		return "index";
	}

	private boolean doesTagExists(String tag, List<Tag> tags) {
		boolean contained = false;
		for (Tag tagInner : tags) {
			if (tagInner.getKeyword().equals(tag)) {
				contained = true;
			}
		}
		return contained;
	}

	private String validation(HttpServletRequest request, String performerOfVideo, String tag) {
		if (request.getSession(false) == null) {
			return "index";
		}

		if ((performerOfVideo == null) || (performerOfVideo.trim().equals(""))
				|| (performerOfVideo.matches(".*\\d+.*"))) {
			return "index";
		}

		if (tag == null || tag.trim().equals("")) {
			return "index";
		}

		if (request.getSession().getAttribute("user_id") == null) {
			return "index";
		}
		return null;
	}

}
