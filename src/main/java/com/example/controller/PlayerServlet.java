package com.example.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PlayerServlet
 */
public class PlayerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("video/mp4");
		System.out.println(request.getRequestURI());
		byte[] data = getBytesFromFile(new File("C:\\temp\\1.mp4"));
		
	}

	private byte[] getBytesFromFile(File file) throws IOException {
		try (InputStream is = new FileInputStream(file)) {

			// Get the size of the file
			long length = file.length();

			if (length > Integer.MAX_VALUE) {
				System.out.println("File is too large to process");
				return null;
			}

			// Create the byte array to hold the data
			byte[] bytes = new byte[(int) length];
			// Read in the bytes
			int offset = 0;
			int numRead = 0;
			while ((offset < bytes.length) && ((numRead = is.read(bytes, offset, bytes.length - offset)) >= 0)) {
				offset += numRead;
			}

			// Ensure all the bytes have been read in
			if (offset < bytes.length) {
				throw new IOException("Could not completely read file " + file.getName());
			}
			return bytes;
		}

	}

}
