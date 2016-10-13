package com.codetube.model.search;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SearchQueryDAO {
	private ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	public String pathComponent = (String) context.getBean("pathComponent");
	
	public String search(String searchQuery) {
		
		HttpURLConnection connection = null;
		String jsonString = null;
		String searchLocalURL = "http://localhost:9200/_search";
		String searchCloudURL = "http://1ae7caf5273f0f6555d2619c07dfabb4.eu-west-1.aws.found.io:9200/" + pathComponent + "/_search";
		String searchQueryOutput = "{\"query\": {\"query_string\": {\"query\": \"" + searchQuery + "\"}}}'";

		try {
			connection = (HttpURLConnection) new URL(searchCloudURL).openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);
			byte[] outputInBytes = searchQueryOutput.getBytes("UTF-8");
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
