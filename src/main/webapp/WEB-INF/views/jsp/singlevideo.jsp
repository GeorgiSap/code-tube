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
	List<VideoClip> videoclips = (List<VideoClip>) request.getAttribute("videos");
%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<script type='text/javascript'>
	var index = 0;
	var clipPaths = new Array(<%=videoclips.size() -1%>);
	<%for (VideoClip clip : videoclips) {%>
		clipPaths[index] = <%=clip.getPath()%>;
		index = index + 1;
	<%}%>
	
		window.addEventListener("load", function() {

			start(clipPaths);

		}, false);
	
		function start(clipPaths) {
			for(index = 0; index < clipPaths.length;index++){
			var video = document.createElement('video');
			video.src = "videos/" + clipPaths[index];
				video.autoPlay = true;
				var thecanvas = document.getElementById('thecanvas');

				video.addEventListener('loadeddata', function() {

					draw(video, thecanvas);

				}, false);
			}
		};

		function draw(video, thecanvas) {
			$thecanvas = thecanvas;
			// get the canvas context for drawing
			var context = thecanvas.getContext('2d');

			// draw the video contents into the canvas x, y, width, height
			context.drawImage(video, 0, 0, thecanvas.width, thecanvas.height);

			// get the image data from the canvas object
			var dataURL = thecanvas.toDataURL();

			// set the source of the img tag
			var img = document.createElement("img");
			img.setAttribute('src', dataURL);
			$("thecanvas").prepend(img);
		}
	</script>


	The Video
	<br />

	<br /> The Canvas
	<br />
	<canvas id="thecanvas">
        </canvas>
	<br />


</body>


</html>