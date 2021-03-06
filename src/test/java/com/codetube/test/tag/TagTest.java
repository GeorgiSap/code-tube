package com.codetube.test.tag;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.codetube.model.tag.Tag;
import com.codetube.model.tag.TagDAO;
import com.codetube.model.tag.TagException;

public class TagTest {
	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	TagDAO tagJDBCTemplate = (TagDAO) context.getBean("TagJDBCTemplate");

	@Test
	public void test() {
		try {
			int tagId = 0;
			System.out.println("Deleting all Tags...");
			tagJDBCTemplate.deleteAllTags();
			System.out.println("Inserting new Tags");
			tagJDBCTemplate.addTag(new Tag(0, "Java"));
			tagJDBCTemplate.addTag(new Tag(0, "Basics"));
			tagJDBCTemplate.addTag(new Tag(0, "OOP"));
			tagJDBCTemplate.addTag(new Tag(0, "Collections"));
			tagJDBCTemplate.addTag(new Tag(0, "Threads"));
			tagJDBCTemplate.addTag(new Tag(0, "MySQL"));	
			tagJDBCTemplate.addTag(new Tag(0, "Web"));
			tagId = tagJDBCTemplate.addTag(new Tag(0, "C#"));
			tagJDBCTemplate.deleteTag(tagId);
			System.out.println("Deleted C# Tag!");
			
			
			System.out.println(tagId);
		} catch (TagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
