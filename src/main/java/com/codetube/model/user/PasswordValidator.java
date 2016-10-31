package com.codetube.model.user;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codetube.model.user.IUser.MIN_PASSWORD_LENGTH;
import static com.codetube.model.user.IUser.MAX_PASSWORD_LENGTH;

public class PasswordValidator{

	  private Pattern pattern;
	  private Matcher matcher;

	  private static final String PASSWORD_PATTERN =
              "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{" + MIN_PASSWORD_LENGTH + "," + MAX_PASSWORD_LENGTH + "})";

	  public PasswordValidator(){
		  pattern = Pattern.compile(PASSWORD_PATTERN);
	  }

	  public boolean validate(final String password){

		  matcher = pattern.matcher(password);
		  return matcher.matches();

	  }
}
