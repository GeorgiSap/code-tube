package com.codetube.test.search;

import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import com.codetube.model.search.SearchQueryDAO;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SearchTest {

	@Test
	public void test() {
		
		Scanner sc = new Scanner(System.in);
		String searchQuery = sc.nextLine();
		String jsonString = new SearchQueryDAO().search(searchQuery);
		
		JSONObject json = new JSONObject(jsonString);
		JSONObject hitsObj = json.getJSONObject("hits");
		JSONArray hitsArr = hitsObj.getJSONArray("hits");
		
		for (int index = 0; index < hitsArr.length(); index++) {
			JSONObject jsonObj = hitsArr.getJSONObject(index);
			JSONObject source = jsonObj.getJSONObject("_source");
			int id = source.getInt("id");
			String title = source.getString("title");
			String tag = source.getString("tag");
			String userName = source.getString("username");
			System.out.println(id + " " + title + " " + tag + " " + userName);
			System.out.println();
		}
	}

}
