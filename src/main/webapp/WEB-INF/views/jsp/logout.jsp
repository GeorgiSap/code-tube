<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<% String userName =  (String)request.getSession().getAttribute("user_name");  %>

<div class="signin">
	<a href="index.jsp" style="background: red; font-weight: bold">Hi, <%= userName %></a>
	<a href="./logout">Logout</a>
	<div id="small-dialog" class="mfp-hide">
		<div class="clearfix"></div>
	</div>
</div>