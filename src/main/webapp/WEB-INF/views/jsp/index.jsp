<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<!DOCTYPE HTML>
<html>
<head>
<title>CodeTube</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords"
	content="My Play Responsive web template, Bootstrap Web Templates, Flat Web Templates, Andriod Compatible web template, 
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyErricsson, Motorola web design" />
<script type="application/x-javascript">
	 var pathData = '<c:url value="/data" />';
	 var pathVideo = '<c:url value="/videos/" />';
	 var pathPlayer ='<c:url value="/player/" />';
	 
	 addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); }
	 addEventListener("load",refreshMovies);
	
	 
	 function refreshMovies() {
			$("#randomVideosHome").empty();

			$.get(pathData,
					function(data) {
						if (data.length > 0) {
							
								
							for ( var index in data) {
								var video = data[index];
								var div  = document.createElement("div");
								var performer = document.createElement("h6");
								var nameOfFile = document.createElement("h5");
								var path = document.createElement("p");
								var videoControl = document.createElement("video");
								var link = document.createElement("a");
								
								
								performer.innerHTML = "Performer: " + video.performer;
								nameOfFile.innerHTML  = "Video name: "+ video.name;
								path.innerHTML = video.path;
							
								videoControl.src = pathVideo + path.innerHTML;
								videoControl.controls = false;
								videoControl.width = 300;
								videoControl.height = 200;
								videoControl.id = "videoControl";
								videoControl.style.borderRadius  = "25px";
								videoControl.style.border = "2px solid #73AD21";
								videoControl.style.backgroundColor = "black";
								link.href = pathPlayer+video.id;
								
								link.appendChild(videoControl);
								div.appendChild(link);
								div.appendChild(nameOfFile);
								div.appendChild(performer);
								div.style.position = "relative";
								div.style.left = "1%";
								div.style.display= "inline-block";
								div.style.margin = "1%"
								div.style.backgroundColor = "white";
								div.style.padding = "1%";
								div.style.borderRadius  = "25px";
								div.style.border = "2px solid #73AD21";
								$("#randomVideosHome").append(div);
							}
						}
						
					});	
		}








</script>
<!-- bootstrap -->
<link href='<c:url value="/css/bootstrap.min.css"/>' rel='stylesheet'
	type='text/css' media="all" />
<!-- //bootstrap -->
<link href='<c:url value="/css/dashboard.css"/>' rel="stylesheet">
<!-- Custom Theme files -->
<link href='<c:url value="/css/style.css"/>' rel='stylesheet'
	type='text/css' media="all" />
<script src='<c:url value="/js/jquery-1.11.1.min.js"/>'></script>
<!--start-smoth-scrolling-->
<!-- fonts -->
<link
	href='//fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800'
	rel='stylesheet' type='text/css'>
<link href='//fonts.googleapis.com/css?family=Poiret+One'
	rel='stylesheet' type='text/css'>
<!-- //fonts -->
</head>
<body>

	<%@ include file="./header.jsp"%>
	<%@ include file="./sitebar.jsp"%>

	<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
		<div class="main-grids">
			<div class="recommended">
				<div class="recommended-grids">
					<div class="recommended-info">
						<div class="heading">
							<%
								if (request.getAttribute("messageLogging") != null) {
							%>
								<h3><%= request.getAttribute("messageLogging") %> </h3>
							<%
								}
							%>
							<h3>Hello There traveler</h3>
							<%
								if (request.getAttribute("userProfilePage") != null) {
							%>
							</div>
							<%
								}
							%>
							
							<%
								if (request.getAttribute("userProfilePage") != null) {
							%>
						<div class="heading-right">
							<a href="subscribe/<%=request.getAttribute("userProfilePage")%>"
								class="play-icon popup-with-zoom-anim"><%=request.getAttribute("subscribe_button")%></a>
						</div>
						<div class="clearfix"></div>
						<%
							}
						%>
						
						<div id ="randomVideosHome"></div>
					</div>
					<div class="clearfix"></div>
				</div>


				<div class="clearfix"></div>

			</div>
		</div>
	</div>
	<%@ include file="./footer.jsp"%>

	<div class="clearfix"></div>
	<div class="drop-menu">
		<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu4">
			<li role="presentation"><a role="menuitem" tabindex="-1"
				href="#">Regular link</a></li>
			<li role="presentation" class="disabled"><a role="menuitem"
				tabindex="-1" href="#">Disabled link</a></li>
			<li role="presentation"><a role="menuitem" tabindex="-1"
				href="#">Another link</a></li>
		</ul>
	</div>

	<script src='<c:url value="/js/bootstrap.min.js"/>'></script>
</body>
</html>