<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.codetube.model.user.IUser"%>
<%@ page session="false" %>	

				<div class="signin">
					<a href="#small-dialog" class="play-icon popup-with-zoom-anim">Sign
						In</a>
					<div id="small-dialog" class="mfp-hide">
						<h3>Login</h3>

						<div class="signup">
							<form action="./Login">
								<input name="email" type="email" class="email"
									placeholder="Enter email" required="required"
									pattern=".{<%=IUser.MIN_EMAIL_LENGTH%>,<%=IUser.MAX_FIELD_LENGTH%>}"
									required
									title="<%=IUser.MIN_EMAIL_LENGTH%> to <%=IUser.MAX_FIELD_LENGTH%> characters" />
								<input name="password" type="password" placeholder="Password"
									required="required"
									pattern=".{<%=IUser.MIN_PASSWORD_LENGTH%>,<%=IUser.MAX_FIELD_LENGTH%>}"
									required
									title="<%=IUser.MIN_PASSWORD_LENGTH%> to <%=IUser.MAX_FIELD_LENGTH%> characters"
									autocomplete="off" /> <input type="submit" value="LOGIN" />
							</form>

							<div class="button-bottom">
								<p>
									New account? <a href="#small-dialog3"
										class="play-icon popup-with-zoom-anim">Signup</a>
								</p>
							</div>


						</div>
						<div class="clearfix"></div>
					</div>
				</div>