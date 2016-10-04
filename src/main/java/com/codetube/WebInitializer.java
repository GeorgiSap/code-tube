package com.codetube;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	public static final String LOCATION = "C:/temp/"; // Temporary
														// location
														// where
														// files
														// will
														// be
														// stored

	private static final long MAX_FILE_SIZE = 1004288000; // 5MB : Max file size.
														// Beyond that size
														// spring will throw
														// exception.
	private static final long MAX_REQUEST_SIZE = 2009715200; // 20MB : Total
															// request size
															// containing Multi
															// part.

	private static final int FILE_SIZE_THRESHOLD = 0; // Size threshold after
														// which files will be
														// written to disk

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { SpringWebConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/", "*.html", "*.mp4" };
	}

	@Override
	protected void customizeRegistration(ServletRegistration.Dynamic registration) {
		registration.setMultipartConfig(getMultipartConfigElement());
	}

	private MultipartConfigElement getMultipartConfigElement() {
		MultipartConfigElement multipartConfigElement = new MultipartConfigElement(LOCATION, MAX_FILE_SIZE,
				MAX_REQUEST_SIZE, FILE_SIZE_THRESHOLD);
		return multipartConfigElement;
	}
}