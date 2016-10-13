package com.codetube.test.search;

import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import com.codetube.model.search.SearchQueryDAO;

public class SearchTest {

	@Test
	public void test() {
		
//		Scanner sc = new Scanner(System.in);
//		String searchQuery = sc.nextLine();
//		sc.close();
		String searchQuery = "java";
		
		String jsonString = new SearchQueryDAO().search(searchQuery);
		JSONObject json = new JSONObject(jsonString);
		JSONObject hitsObj = json.getJSONObject("hits");
		JSONArray hitsArr = hitsObj.getJSONArray("hits");
		
		for (int index = 0; index < hitsArr.length(); index++) {
			JSONObject jsonObj = hitsArr.getJSONObject(index);
			JSONObject source = jsonObj.getJSONObject("_source");
			int id = source.getInt("id");
			String title = source.getString("title");
			
			

			JSONArray arrJson= source.getJSONArray("tags");
			String[] arr= new String[arrJson.length()];
			for(int i=0;i<arrJson.length();i++)
			    arr[i]=arrJson.getString(i);

			String userName = source.getString("username");
			
			StringBuilder builder = new StringBuilder("[");
			for (int element = 0; element < arr.length; element++) {
				builder.append(arr[element]);
				if (element < arr.length - 1) {
					builder.append(", ");
				}
			}
			builder.append("]");
			
			
			System.out.println(id + " " + title + " " + builder + " " + userName);
			System.out.println();
		}
	}

}
