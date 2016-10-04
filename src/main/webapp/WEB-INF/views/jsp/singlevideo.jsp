<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.*"%>
<%@page import="com.codetube.model.videoclip.*"%>

<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
		VideoClip clip = (VideoClip) request.getAttribute("video");
	%>


	<video width="320" height="240" controls>
		<source src="../videos/<%=clip.getPath()%>" type="video/mp4">
	</video>
	<p><%=clip.getPerformer()%>
	</p>
</body>


</html>