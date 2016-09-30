<%@ page session="false" %>
<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="index.html"><h1>
					<img src="images/logo.png" alt="" />
				</h1></a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<div class="top-search">
				<form class="navbar-form navbar-right">
					<input type="text" class="form-control" placeholder="Search...">
					<input type="submit" value=" ">
				</form>
			</div>
			<div class="header-top-right">
				

				<% if (request.getSession(false) == null) { %>
				<%@ include file="./register.jsp"%>
				<%@ include file="./login.jsp"%>
				<% } else { %>
				<div class="file">
					<a href="upload.jsp">Upload</a>
				</div>
				
				<%@ include file="./logout.jsp"%>
				<% } %>
				<div class="clearfix"></div>
			</div>
		</div>
		<div class="clearfix"></div>
	</div>
</nav>