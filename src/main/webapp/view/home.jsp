<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
           <%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="initial-scale=1, maximum-scale=1">
<link rel='stylesheet'
	href='../webjars/bootstrap/3.2.0/css/bootstrap.min.css'>
<link rel="stylesheet" type="text/css" href="../stylesheets/register.css">
<script src="../javascript/register.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
<h2><% response.getWriter().println("Logged in as " + request.getSession().getAttribute("user_name")); %></h2>
 <h3 class="text-left">Logged in as <c:out value='${session.user_name}'/></h3> 
			<div class="col-md-8 col-md-offset-2">
				<form role="form" method="POST" action="../Logout">
					<div class="form-group">
						<div class="col-md-12">
							<button type="submit" class="btn btn-primary">Logout</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="../webjars/jquery/2.1.1/jquery.min.js"></script>
	<script type="text/javascript"
		src="../webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</body>
</html>