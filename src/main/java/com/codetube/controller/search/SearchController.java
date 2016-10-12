package com.codetube.controller.search;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.codetube.model.search.SearchQueryDAO;
import com.codetube.model.user.User;
import com.codetube.model.user.UserDAO;
import com.codetube.model.videoclip.VideoClip;
import com.codetube.model.videoclip.VideoClipDAO;

@Controller
@RequestMapping(value = "/search")
public class SearchController {
	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	VideoClipDAO videoClipJDBCTemplate = (VideoClipDAO) context.getBean("VideoClipJDBCTemplate");
	UserDAO userJDBCTemplate = (UserDAO) context.getBean("UserJDBCTemplate");
	
	@RequestMapping(method = RequestMethod.POST)	
	public String searchQuery(HttpServletRequest request) {
		String searchQuery = request.getParameter("searchQuery");
		String jsonString = new SearchQueryDAO().search(searchQuery);
		
		JSONObject json = new JSONObject(jsonString);
		JSONObject hitsObj = json.getJSONObject("hits");
		JSONArray hitsArr = hitsObj.getJSONArray("hits");
		
		List<VideoClip> searchResults = new ArrayList<VideoClip>();
		
		for (int index = 0; index < hitsArr.length(); index++) {
			JSONObject jsonObj = hitsArr.getJSONObject(index);
			JSONObject source = jsonObj.getJSONObject("_source");
			int videoClipId = source.getInt("id");
			VideoClip videoClip =  videoClipJDBCTemplate.getClip(videoClipId);
			User user = userJDBCTemplate.get(videoClip.getUser().getId());
			videoClip.setUser(user);
			searchResults.add(videoClip);
		}
		
		request.setAttribute("title", "Results for " + searchQuery);
		request.setAttribute("videosToLoad", searchResults);
		return "home";
	}
	
}
