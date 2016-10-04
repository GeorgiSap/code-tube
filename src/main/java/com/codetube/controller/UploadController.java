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

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String showUploadPage() {
		return "upload";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String singleFileUpload(@RequestParam("file") MultipartFile multipartFile,
			// @RequestParam("artist") String artist,
			ModelMap model) throws IOException {
		int clipId;
		String[] path = multipartFile.getOriginalFilename().split("/");
		String fileName = path[path.length - 1];
		try {
			clipId = (int) videoClipJDBCTemplate
					.addVideoClip(new VideoClip(0, fileName, "mladen", path[0]));
		} catch (VideoClipException e) {
			return "index";
		}
		FileCopyUtils.copy(multipartFile.getBytes(), new File(WebInitializer.LOCATION + fileName));

		return "index";
	}

}
