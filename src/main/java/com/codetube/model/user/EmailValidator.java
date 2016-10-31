package com.codetube.model.user;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator{

	  private Pattern pattern;
	  private Matcher matcher;

	  private static final String EMAIL_PATTERN =
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,3})$";
	  
	  public EmailValidator(){
		  pattern = Pattern.compile(EMAIL_PATTERN);
	  }

	  public boolean validate(final String password){

		  matcher = pattern.matcher(password);
		  return matcher.matches();

	  }
}
