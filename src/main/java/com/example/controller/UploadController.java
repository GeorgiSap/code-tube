package com.example.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.model.videoclip.VideoClip;
import com.example.model.videoclip.VideoClipDAO;
import com.example.model.videoclip.VideoClipException;
import com.example.model.videoclip.VideoClipJDBCTemplate;

@Controller
@RequestMapping(value = "/upload")
public class UploadController {

	private static final long serialVersionUID = 1L;
	private static final int MAX_FILE_SIZE_UPLOADED = 500_000 * 1024;
	private static final String CONSTANT_PATH = "C:\\temp\\";
	private boolean isMultipart;
	private String filePath;
	private int maxFileSize = 500_000 * 1024;
	private int maxMemSize = 40 * 1024;
	private File file;
	
	private ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	@Autowired
	public VideoClipJDBCTemplate videoClipJDBCTemplate = (VideoClipJDBCTemplate) context.getBean("VideoClipJDBCTemplate");

	@RequestMapping(method = RequestMethod.GET)
	public String showUploadPage(){
		return "upload";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String uploadVideo(Model model, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		int clipId = 0;
		String message = null;
		try {

			// Check that we have a file upload request
			checksForUploadRequest(request, response, clipId);

			File afFile = new File(CONSTANT_PATH);
			DiskFileItemFactory factory = new DiskFileItemFactory(MAX_FILE_SIZE_UPLOADED, afFile);

			// maximum size that will be stored in memory
			factory.setSizeThreshold(maxMemSize);
			// Location to save data that is larger than maxMemSize.
			factory.setRepository(new File(CONSTANT_PATH));

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);
			// maximum file size to be uploaded.
			upload.setSizeMax(maxFileSize);

			// Parse the request to get file items.
			List<FileItem> fileItems = upload.parseRequest(request);

			// Process the uploaded file items
			Iterator<FileItem> i = fileItems.iterator();

			while (i.hasNext()) {
				FileItem fi = (FileItem) i.next();
				if (!fi.isFormField()) {
					// Get the uploaded file parameters
					String fieldName = fi.getFieldName();
					String fileName = fi.getName();
					String contentType = fi.getContentType();
					boolean isInMemory = fi.isInMemory();
					long sizeInBytes = fi.getSize();

					// only mp4
					if (!contentType.equals("video/mp4")) {
						System.out.println("Format is bad");
						message = "Invalid data! Only vide/mp4 format";
						sendToUploadResultPage(request, response, message);
						return null;
					}

					// Write the file
					clipId = writingTheFile(fi, fileName);
				}
			}

			System.out.println(videoClipJDBCTemplate.getClip(clipId));
			message = "Upload successfully";
			sendToUploadResultPage(request, response, message);

		} catch (Exception ex) {
			System.out.println(ex);
			message = "Invalid data! Only vide/mp4 format";
			try {
				sendToUploadResultPage(request, response, message);
			} catch (ServletException e) {
				System.out.println(e);
				response.sendRedirect("./Home");
			}
		}
		return "index";
	}

	private void checksForUploadRequest(HttpServletRequest request, HttpServletResponse response, int clipId)
			throws IOException {
		String message;
		isMultipart = ServletFileUpload.isMultipartContent(request);
		response.setContentType("text/html");

		if (!isMultipart) {
			System.out.println(videoClipJDBCTemplate.getClip(clipId));
			message = "Upload successfully";
			try {
				sendToUploadResultPage(request, response, message);
			} catch (ServletException e) {
				System.out.println(e);
				response.sendRedirect("./Home");
			}
		}
	}

	private int writingTheFile(FileItem fi, String fileName) throws VideoClipException, Exception {
		int clipId;
		if (fileName.lastIndexOf("\\") >= 0) {
			file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\")));
		} else {
			file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\") + 1));
		}
		clipId = (int) videoClipJDBCTemplate
				.addVideoClip(new VideoClip(0, fileName, "mladen", CONSTANT_PATH + fileName));
		System.out.println("FIle with id " + clipId + "was successfully added");
		fi.write(file);
		return clipId;
	}

	private void sendToUploadResultPage(HttpServletRequest request, HttpServletResponse response, String message)
			throws ServletException, IOException {
		request.setAttribute("message", message);
		System.out.println(request.getAttribute("message"));
		request.getRequestDispatcher("./view/uploadedResult.jsp").forward(request, response);
	}
}
