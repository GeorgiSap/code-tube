package com.example.model.videoclip;

import org.springframework.stereotype.Component;

public class VideoClipException extends Exception {

	private static final long serialVersionUID = 1L;

	public VideoClipException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VideoClipException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public VideoClipException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public VideoClipException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public VideoClipException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}