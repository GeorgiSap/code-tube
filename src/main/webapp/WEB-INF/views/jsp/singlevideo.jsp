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
	<script type='text/javascript'>
	
	
		window.addEventListener("load", start, false);
	
		function start() {
			var video = document.createElement('video');

			video.src = "../videos/<%=clip.getPath()%>";
			video.autoPlay = true;
			var thecanvas = document.getElementById('thecanvas');
			var img = document.getElementById('thumbnail_img');

			video.addEventListener('loadeddata', function() {

				draw(video, thecanvas, img);

			}, false);

		};

		function draw(video, thecanvas, img) {

			// get the canvas context for drawing
			var context = thecanvas.getContext('2d');

			// draw the video contents into the canvas x, y, width, height
			context.drawImage(video, 0, 0, thecanvas.width, thecanvas.height);

			// get the image data from the canvas object
			var dataURL = thecanvas.toDataURL();

			// set the source of the img tag
			img.setAttribute('src', dataURL);

		}
	</script>


	The Video
	<br />

	<br /> The Canvas
	<br />
	<canvas id="thecanvas">
        </canvas>
	<br /> The Image
	<br />
	<img id="thumbnail_img" alt="Right click to save" />
	<br />

</body>


</html>