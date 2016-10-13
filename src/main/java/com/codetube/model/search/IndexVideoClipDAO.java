package com.codetube.model.search;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.codetube.model.tag.Tag;
import com.codetube.model.user.User;
import com.codetube.model.videoclip.VideoClip;

public class IndexVideoClipDAO {
	public String index(VideoClip videoClip, User user, Tag tag) {
		HttpURLConnection connection = null;
		String jsonString = null;
		String localURL = "http://localhost:9200/videos/video/" + videoClip.getId();
		String URL = "http://1ae7caf5273f0f6555d2619c07dfabb4.eu-west-1.aws.found.io:9200/videos/video/" + videoClip.getId();
		String str ="{\"id\": " + videoClip.getId() + 
				", \"tag\": \"" + tag.getKeyword() + 
				"\", \"title\": \"" + videoClip.getPerformer() + 
				"\", \"username\": \"" + user.getUserName() + "\"}";
		System.out.println(str);
		
		try {
			connection = (HttpURLConnection) new URL(URL).openConnection();
			connection.setRequestMethod("PUT");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);
			byte[] outputInBytes = str.getBytes("UTF-8");
			OutputStream os = connection.getOutputStream();
			os.write(outputInBytes);
			os.close();

			//if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

				byte[] bytes = null;
				try (BufferedInputStream bis = new BufferedInputStream(connection.getInputStream())) {
					bytes = new byte[connection.getContentLength()];
					bis.read(bytes);
				}
				jsonString = new String(bytes, "UTF-8");
			//}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			connection.disconnect();
		}
		return jsonString;
	}
}
