<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.codetube.model.user.IUser"%>
<%@ page session="false"%>

<div class="signin">
	<a href='<c:url value="#small-dialog"/>'
		class="play-icon popup-with-zoom-anim">Sign In</a>
	<div id="small-dialog" class="mfp-hide">
		<h3>Login</h3>

		<div class="signup">

			<form action='<c:url value="/login"/>' method="POST">
				<input name="email" type="email" class="email" placeholder="Email"
					maxlength="<%=IUser.MAX_FIELD_LENGTH%>" required="required"
					pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$"
					title="Enter a valid email" />
				<input name="password" type="password" placeholder="Password"
					maxlength="<%=IUser.MAX_PASSWORD_LENGTH%>" required="required"
					pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{<%=IUser.MIN_PASSWORD_LENGTH%>,<%=IUser.MAX_PASSWORD_LENGTH%>}"
					title="<%=IUser.MIN_PASSWORD_LENGTH%> to <%=IUser.MAX_PASSWORD_LENGTH%> character string with at least one digit, one upper case letter, one lower case letter and one special symbol [@#$%]"
					autocomplete="off" /> 
				<input type="submit" value="LOGIN" />
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