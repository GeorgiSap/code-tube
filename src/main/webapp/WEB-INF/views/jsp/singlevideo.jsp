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
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>

<%
	VideoClip clip = (VideoClip) request.getAttribute("video");
%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<script>
		(function() {
			"use strict";

			var video, $output;
			var scale = 0.25;

			var initialize = function() {
				$output = $("#output");
				video = $("#video").get(0);
				$("#capture").click(captureImage);
			};

			var captureImage = function() {
				var canvas = document.createElement("canvas");
				canvas.width = video.videoWidth * scale;
				canvas.height = video.videoHeight * scale;
				canvas.getContext('2d').drawImage(video, 0, 0, canvas.width,
						canvas.height);

				var img = document.createElement("img");
				img.src = canvas.toDataURL();
				$output.prepend(img);
			};

			$(initialize);

		}());
	</script>

	<div id="thumbnailContainer"></div>


	<p><%=clip.getPerformer()%>
	</p>

	<video width="320" height="240" id="video" controls="controls">
		<source src="../videos/<%=clip.getPath()%>" type="video/mp4">
	</video>
	<button id="capture">Capture</button>

	<div id="output"></div>


</body>


</html>