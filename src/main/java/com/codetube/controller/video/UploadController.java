package com.codetube.controller.video;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.codetube.WebInitializer;
import com.codetube.controller.ControllerManager;
import com.codetube.controller.user.UserController;
import com.codetube.model.search.IndexVideoClipDAO;
import com.codetube.model.tag.Tag;
import com.codetube.model.tag.TagDAO;
import com.codetube.model.user.User;
import com.codetube.model.user.UserJDBCTemplate;
import com.codetube.model.videoclip.VideoClip;
import com.codetube.model.videoclip.VideoClipException;
import com.codetube.model.videoclip.VideoClipJDBCTemplate;

@Controller
public class UploadController extends UserController {

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String showUploadPage(HttpServletRequest request) {
		try {
			if (request.getSession(false) == null) {
				return "home";
			}

			List<Tag> tags = (List<Tag>) tagJDBCTemplate.getTags();
			request.setAttribute("tags", tags);
			return "upload";
		} catch (Exception e) {
			return "home";
		}
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String singleFileUpload(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request,
			@RequestParam("artist") String performerOfVideo, @RequestParam("tag") List<String> tags, ModelMap model)
			throws IOException {
		try {
			int clipId;
			int userId;

			String[] path;
			String fileName;

			VideoClip clip;
			User user;
			Tag tagObject;

			// incoming validation, IF NULL, everything is ok!
			String page = validation(request, performerOfVideo, tags);

			if (page != null) {
				System.out.println("Error occured, did't add the video");
				return page;
			}

			path = multipartFile.getOriginalFilename().split("/");
			fileName = path[path.length - 1];

			userId = (int) request.getSession().getAttribute("user_id");
			System.out.println("Got user form session with id " + userId);
			user = userJDBCTemplate.get(userId);

			if (user == null) {
				request.setAttribute("messageLogging", "Fill the user");
				return "home";
			}

			String name = Integer.toString(userId) + "" + (new Date().getTime());

			VideoClip videoClip = new VideoClip(0, fileName, performerOfVideo, name);
			clipId = (int) videoClipJDBCTemplate.addVideoClip(videoClip, user);
			videoClip.setId(clipId);

			// Add videoClip to current user session
			addVideoClipToSession(request, videoClip);

			System.out.println("I added successfully clip with " + clipId);
			clip = videoClipJDBCTemplate.getClip(clipId);

			List<Tag> existingTags = tagJDBCTemplate.getTags();
			List<Tag> currentVideoTags = new ArrayList<Tag>();
			for (String tag : tags) {
				if (!doesTagExists(tag, existingTags)) {
					return "home";
				}
				tagObject = tagJDBCTemplate.getTag(tag);
				if (tagObject == null) {
					System.out.println("TagObject is null!");
					return "home";
				}
				currentVideoTags.add(tagObject);
				System.out.println("I got tag with id " + tagObject.getId());
			}

			// index VideoClip for search
			new IndexVideoClipDAO().index(videoClip, user, currentVideoTags);

			for (Tag tagObj : currentVideoTags) {
				videoClipJDBCTemplate.addTagToVideo(tagObj.getId(), clipId);
				System.out.println("Added tag " + tagObj.getKeyword() + " in video has tags");
			}

			FileCopyUtils.copy(multipartFile.getBytes(), new File(WebInitializer.LOCATION + name));
		} catch (VideoClipException e) {
			request.setAttribute("messageLogging", "Something went wrong");
			return "home";
		}
		request.setAttribute("messageLogging", "Successfully uploaded video");

		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			List<VideoClip> userVideos = user.getVideos();
			List<VideoClip> userVideosOrdered = new ArrayList<VideoClip>();
			for (int video = userVideos.size() - 1; video >= 0; video--) {
				userVideosOrdered.add(userVideos.get(video));
			}
			for (VideoClip videoClip : userVideosOrdered) {
				videoClip.setUser(user);
			}
			request.setAttribute("videosToLoad", userVideosOrdered);
		}
		loadUserVideos(request);
		loadTags(request);
		return "home";
	}

	private void addVideoClipToSession(HttpServletRequest request, VideoClip videoClip) {
		User currentUser = (User) request.getSession().getAttribute("user");
		if (currentUser != null) {
			currentUser.addToVideos(videoClip);
		}
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

	private String validation(HttpServletRequest request, String performerOfVideo, List<String> tags) {
		if (request.getSession(false) == null) {
			request.setAttribute("messageLogging", "Log!");
			return "home";
		}

		if ((performerOfVideo == null) || (performerOfVideo.trim().equals(""))
				|| (performerOfVideo.matches(".*\\d+.*"))) {
			request.setAttribute("messageLogging", "Fill the performer!");
			return "home";
		}

		for (String tag : tags) {
			if (tag == null || tag.trim().equals("")) {
				request.setAttribute("messageLogging", "Fill tags!");
				return "home";
			}
		}

		if (request.getSession().getAttribute("user_id") == null) {
			return "home";
		}
		return null;
	}

}
