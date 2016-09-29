<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="model.user.iUser" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="initial-scale=1, maximum-scale=1">
<link rel='stylesheet'
	href='../webjars/bootstrap/3.2.0/css/bootstrap.min.css'>
<link rel="stylesheet" type="text/css" href="../stylesheets/register.css">
<script src="../javascript/register.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">

			<div class="col-md-8 col-md-offset-2">
				<form role="form" method="POST" action="../Login">

					<legend class="text-center">Login</legend>

					<fieldset>

						<div class="form-group col-md-12">
							<label for="">Email</label> <input type="email"
								class="form-control" name="email" id="" placeholder="Email"
								pattern=".{<%= iUser.MIN_EMAIL_LENGTH %>,<%= iUser.MAX_FIELD_LENGTH %>}" required title="<%= iUser.MIN_EMAIL_LENGTH %> to <%= iUser.MAX_FIELD_LENGTH %> characters">
						</div>
						
						<div class="form-group col-md-12">
							<label for="password">Password</label> <input type="password"
								class="form-control" name="password" id="password"
								placeholder="Password" 
								pattern=".{<%= iUser.MIN_PASSWORD_LENGTH %>,<%= iUser.MAX_FIELD_LENGTH %>}" required title="<%= iUser.MIN_PASSWORD_LENGTH %> to <%= iUser.MAX_FIELD_LENGTH %> characters">
						</div>

					</fieldset>

					<fieldset>
						<div class="form-group col-md-12 hidden">
							<label for="specify">Please Specify</label>
							<textarea class="form-control" id="specify" name=""></textarea>
						</div>
					</fieldset>

					<div class="form-group">
						<div class="col-md-12">
							<button type="submit" class="btn btn-primary">Login</button>
							<a href="register.jsp">Don't have an account?</a>
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