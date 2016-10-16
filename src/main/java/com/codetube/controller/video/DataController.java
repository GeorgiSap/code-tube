package com.codetube.controller.video;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codetube.controller.ControllerManager;
import com.codetube.model.tag.Tag;
import com.codetube.model.user.User;
import com.codetube.model.videoclip.VideoClip;
import com.codetube.model.videoclip.VideoClipException;
import com.google.gson.Gson;

@Controller
public class DataController extends ControllerManager{
	@RequestMapping(value = "/data", method = RequestMethod.GET)
	protected void getDataJSon(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		try {
			List<VideoClip> list = videoClipJDBCTemplate.getClips();

			for (VideoClip clip : list) {
				User user = userJDBCTemplate.get(clip.getUser().getId());
				clip.getUser().setFirstName(user.getFirstName());
				Set<Tag> tags = videoClipJDBCTemplate.getVideoTags(clip);

				for (Tag tag : tags) {
					clip.addTag(tag);
				}
			}

			if (list != null) {
				response.getWriter().print(new Gson().toJson(list));
			} else {
				response.getWriter().print("[]");
			}

		} catch (VideoClipException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/data/{video_id}", method = RequestMethod.GET)
	protected void getDataJSonToSingleVideo(@PathVariable("video_id") Integer videoId, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		try {
			VideoClip clip = videoClipJDBCTemplate.getClip(videoId);

			Set<Tag> tags = videoClipJDBCTemplate.getVideoTags(clip);

			for (Tag tag : tags) {
				clip.addTag(tag);
			}

			if (clip != null)
				response.getWriter().print(new Gson().toJson(clip));
			else
				response.getWriter().print("[]");

		} catch (VideoClipException e) {
			e.printStackTrace();
		}
	}
}
