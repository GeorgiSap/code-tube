<!--
Author: W3layouts
Author URL: http://w3layouts.com
License: Creative Commons Attribution 3.0 Unported
License URL: http://creativecommons.org/licenses/by/3.0/
-->
<%@page import="com.codetube.model.videoclip.*"%>
<!DOCTYPE HTML>
<html>
<head>

<title>My Play a Entertainment Category Flat Bootstrap
	Responsive Website Template | single :: w3layouts</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords"
	content="My Play Responsive web template, Bootstrap Web Templates, Flat Web Templates, Andriod Compatible web template, 
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyErricsson, Motorola web design" />
<script type="application/x-javascript">
	 addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); }
	 
	 addEventListener("load",refreshMovies);
	 
	 function refreshMovies() {
			$("#randomVideos").empty();

			$.get("../data",
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
							
								
								videoControl.src = "../videos/" + path.innerHTML;
								videoControl.controls = false;
								videoControl.width = 500;
								videoControl.height = 300;
								videoControl.id = "videoControl";
								
								link.href = "./"+video.id;
								
								link.appendChild(videoControl);
								div.appendChild(link);
								div.appendChild(nameOfFile);
								div.appendChild(performer);
								$("#randomVideos").append(div);
							}
						}
						
					});
		}
</script>
<!-- bootstrap -->
<link href="../css/bootstrap.min.css" rel='stylesheet' type='text/css'
	media="all" />
<!-- //bootstrap -->
<link href="../css/dashboard.css" rel="stylesheet">
<!-- Custom Theme files -->
<link href="../css/style.css" rel='stylesheet' type='text/css'
	media="all" />
<script src="../js/jquery-1.11.1.min.js"></script>
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
	<%
		VideoClip clip = (VideoClip) request.getAttribute("video");
	%>
	<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
		<div class="show-top-grids">
			<div class="col-sm-8 single-left">
				<div class="song">
					<div class="song-info">
						<h3><%=clip.getName()%></h3>
					</div>
					<div class="video-grid">
						<video width="800" height="480" controls>
							<source src="../videos/<%=clip.getPath()%>" type="video/mp4">
						</video>
					</div>
				</div>

				<div class="clearfix"></div>
				<div class="published">
					<script src="jquery.min.js"></script>
					<script>
						$(document).ready(function() {
							size_li = $("#myList li").size();
							x = 1;
							$('#myList li:lt(' + x + ')').show();
							$('#loadMore').click(function() {
								x = (x + 1 <= size_li) ? x + 1 : size_li;
								$('#myList li:lt(' + x + ')').show();
							});
							$('#showLess').click(function() {
								x = (x - 1 < 0) ? 1 : x - 1;
								$('#myList li').not(':lt(' + x + ')').hide();
							});
						});
					</script>
					<div class="load_more">
						<ul id="myList">
							<li>
								<h4>
									Tagged as:
									<%=clip.getTagsOfClip()%></h4>
								<p>Nullam fringilla sagittis tortor ut rhoncus. Nam vel
									ultricies erat, vel sodales leo. Maecenas pellentesque, est
									suscipit laoreet tincidunt, ipsum tortor vestibulum leo, ac
									dignissim diam velit id tellus. Morbi luctus velit quis semper
									egestas.</p>
							</li>

						</ul>
					</div>
				</div>
				<div class="all-comments">
					<div class="media-grids">

						<c:forEach begin="0" end="6" varStatus="loop">
							<div class="media">
								<h5>Ivan Ivanov</h5>
								<div class="media-left">
									<a href="#"> </a>
								</div>
								<div class="media-body">
									<p>Maecenas ultricies rhoncus tincidunt maecenas imperdiet
										ipsum id ex pretium hendrerit maecenas imperdiet ipsum id ex
										pretium hendrerit</p>
									<span>View all posts by :<a href="#"> Admin </a></span>
								</div>
							</div>
						</c:forEach>

					</div>
				</div>
			</div>
			<div class="col-md-4 single-right">
				<h3>Up Next</h3>


				<div class="single-grid-right">

					<div id="randomVideos">
					</div>

				</div>
			</div>
			<div class="clearfix"></div>
		</div>
		<!-- footer -->
		<%@ include file="./footer.jsp"%>
		<!-- //footer -->
	</div>
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
	<script src="js/bootstrap.min.js"></script>
</body>
</html>