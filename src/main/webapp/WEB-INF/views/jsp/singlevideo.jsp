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
		List<VideoClip> blogs = (List<VideoClip>) request.getAttribute("videos");
	%>
	<p><%=blogs%>
	</p>

	<video width="320" height="240" controls>
		<source src="../videos/1.mp4" type="video/mp4">
	</video>

</body>


</html>