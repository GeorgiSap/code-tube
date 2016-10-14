<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<%@page import="com.codetube.model.tag.Tag"%>
<%@page import="java.util.List"%>

<div class="col-sm-3 col-md-2 sidebar">
	<div class="top-navigation">
		<div class="t-menu">MENU</div>
		<div class="t-img">
			<img src='<c:url value="/images/lines.png"/>' alt="" />
		</div>
		<div class="clearfix"></div>
	</div>
	<div class="drop-navigation drop-navigation">
		<ul class="nav nav-sidebar">

			<li class="active"><a
				href='<c:url value="http://localhost:8080/codetube/home"/>'
				class="home-icon"><span class="glyphicon glyphicon-home"
					aria-hidden="true"></span>Home</a></li>



			<%
				if (request.getSession(false) != null) {
			%>

			<li><a href="http://localhost:8080/codetube/videos"
				class="user-icon"><span
					class="glyphicon glyphicon-home glyphicon-blackboard"
					aria-hidden="true"></span>My Videos</a></li>

			<!-- 	<li><a href="http://localhost:8080/codetube/playlists" class="user-icon"><span
					class="glyphicon glyphicon-home glyphicon-blackboard"
					aria-hidden="true"></span>My Playlists</a></li> -->

			<li><a href="http://localhost:8080/codetube/subscriptions"
				class="user-icon"><span
					class="glyphicon glyphicon-home glyphicon-blackboard"
					aria-hidden="true"></span>My Subscriptions</a></li>


			<li><a href="http://localhost:8080/codetube/history"
				class="sub-icon"><span
					class="glyphicon glyphicon-home glyphicon-hourglass"
					aria-hidden="true"></span>History</a></li>
			<%
				}
			%>




			<li><a href="http://localhost:8080/codetube/viewed"
				class="user-icon"><span class="glyphicon glyphicon-home glyphicon-expand"
					aria-hidden="true"></span>Most Viewed</a></li>
					
								<li><a href="http://localhost:8080/codetube/commented"
				class="user-icon"><span class="glyphicon glyphicon-home glyphicon-expand"
					aria-hidden="true"></span>Most Commented</a></li>

			<!-- <li><a class="sub-icon"><span
					class="glyphicon glyphicon-home glyphicon-expand" aria-hidden="true"></span>Tags</a></li>
 -->
<li><a class="sub-icon"><span
					class="glyphicon glyphicon-home glyphicon-chevron-down" aria-hidden="true"></span>Tags</a></li>

 
			<c:forEach items="${allTags}" var="element">
				<li><a
					href="http://localhost:8080/codetube/tag/${element.keyword}"
					class="sub-icon"><span aria-hidden="true"></span>#${element.keyword}</a></li>
				<%-- <li><a href="http://localhost:8080/codetube/tag/${element.keyword}">${element.keyword}</a></li> --%>
			</c:forEach>

			<!-- script-for-menu -->
			<script>
				$("li a.menu1").click(function() {
					$("ul.cl-effect-2").slideToggle(300, function() {
						// Animation complete.
					});
				});
			</script>

			script-for-menu
			<script>
				$("li a.menu").click(function() {
					$("ul.cl-effect-1").slideToggle(300, function() {
						// Animation complete.
					});
				});
			</script>
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