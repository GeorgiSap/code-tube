<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<%@ page import="com.codetube.model.user.User"%>
<% User user =  (User)request.getSession().getAttribute("user");  %>

<div class="signin">
	<a href="./home" style="background: red; font-weight: bold">Hi, <%= user.getUserName() %></a>
	<a href="./logout">Logout</a>
	<div id="small-dialog" class="mfp-hide">
		<div class="clearfix"></div>
	</div>
</div>