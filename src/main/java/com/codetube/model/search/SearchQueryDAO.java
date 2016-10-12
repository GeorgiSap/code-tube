package com.codetube.model.search;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SearchQueryDAO {

	public String search(String searchQuery) {
		HttpURLConnection connection = null;
		String jsonString = null;
		String URL = "http://localhost:9200/_search";
		String str = "{\"query\": {\"query_string\": {\"query\": \"" + searchQuery + "\"}}}'";

		try {
			connection = (HttpURLConnection) new URL(URL).openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);
			byte[] outputInBytes = str.getBytes("UTF-8");
			OutputStream os = connection.getOutputStream();
			os.write(outputInBytes);
			os.close();

			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

				byte[] bytes = null;
				try (BufferedInputStream bis = new BufferedInputStream(connection.getInputStream())) {
					bytes = new byte[connection.getContentLength()];
					bis.read(bytes);
				}
				jsonString = new String(bytes, "UTF-8");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			connection.disconnect();
		}
		return jsonString;
	}
}
