<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>

<div class="col-sm-3 col-md-2 sidebar">
	<div class="top-navigation">
		<div class="t-menu">MENU</div>
		<div class="t-img">
			<img src="images/lines.png" alt="" />
		</div>
		<div class="clearfix"></div>
	</div>
	<div class="drop-navigation drop-navigation">
		<ul class="nav nav-sidebar">
		
			<li class="active"><a href='<c:url value="/home"/>'
				class="home-icon"><span class="glyphicon glyphicon-home"
					aria-hidden="true"></span>Home</a></li>

			<%
				if (request.getSession(false) != null) {
			%>
<<<<<<< HEAD
			<li><a href="playlists" class="user-icon"><span
					class="glyphicon glyphicon-home glyphicon-blackboard"
					aria-hidden="true"></span>My Playlist</a></li>
			<li><a href="./" class="user-icon"><span
=======
			<li><a href="./videos" class="user-icon"><span
					class="glyphicon glyphicon-home glyphicon-blackboard"
					aria-hidden="true"></span>My Videos</a></li>
			<li><a href="./subscriptions" class="user-icon"><span
>>>>>>> 67f170ade2bcbb1c905dd930188a5b483480a1cc
					class="glyphicon glyphicon-home glyphicon-blackboard"
					aria-hidden="true"></span>My Subscriptions</a></li>
			<%
				}
			%>

			<li><a href="./history" class="sub-icon"><span
					class="glyphicon glyphicon-home glyphicon-hourglass"
					aria-hidden="true"></span>History</a></li>
			<li><a href="#" class="menu1"><span
					class="glyphicon glyphicon-film" aria-hidden="true"></span>Categories<span
					class="glyphicon glyphicon-menu-down" aria-hidden="true"></span></a></li>
			<ul class="cl-effect-2">
				<li><a href="./">Java</a></li>
				<li><a href="./">JavaScript</a></li>
				<li><a href="./">C#</a></li>
				<li><a href="./">C</a></li>
				<li><a href="./">C++</a></li>
			</ul>
			<!-- script-for-menu -->
			<script>
				$("li a.menu1").click(function() {
					$("ul.cl-effect-2").slideToggle(300, function() {
						// Animation complete.
					});
				});
			</script>
			<!-- 				<li><a href="#" class="menu"><span
						class="glyphicon glyphicon-film glyphicon-king" aria-hidden="true"></span>Sports<span
						class="glyphicon glyphicon-menu-down" aria-hidden="true"></span></a></li> -->
			<!-- 				<ul class="cl-effect-1">
					<li><a href="sports.html">Football</a></li>
					<li><a href="sports.html">Cricket</a></li>
					<li><a href="sports.html">Tennis</a></li>
					<li><a href="sports.html">Shattil</a></li>
				</ul> -->
			<!-- script-for-menu -->
			<script>
				$("li a.menu").click(function() {
					$("ul.cl-effect-1").slideToggle(300, function() {
						// Animation complete.
					});
				});
			</script>
			<li><a href="./" class="news-icon"><span
					class="glyphicon glyphicon-envelope" aria-hidden="true"></span>News</a></li>
			<li><a href="./" class="news-icon"><span
					class="glyphicon glyphicon-envelope" aria-hidden="true"></span>About</a></li>
		</ul>
		<!-- script-for-menu -->
		<script>
			$(".top-navigation").click(function() {
				$(".drop-navigation").slideToggle(300, function() {
					// Animation complete.
				});
			});
		</script>
	</div>
</div>