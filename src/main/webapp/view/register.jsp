<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ page import="com.codetube.model.user.IUser" %>
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
				<form role="form" method="POST" action="../Register">

					<legend class="text-center">Register</legend>

					<fieldset>
						<legend>Account Details</legend>

						<div class="form-group col-md-6">
							<label for="first_name">First name</label> <input type="text"
								class="form-control" name="first_name" id="first_name"
								placeholder="First Name"
								pattern=".{<%= IUser.MIN_NAME_LENGTH %>,<%= IUser.MAX_FIELD_LENGTH %>}" required title="<%= IUser.MIN_NAME_LENGTH %> to <%= IUser.MAX_FIELD_LENGTH %> characters">
						</div>

						<div class="form-group col-md-6">
							<label for="last_name">Last name</label> <input type="text"
								class="form-control" name="last_name" id=""
								placeholder="Last Name"
								pattern=".{<%= IUser.MIN_NAME_LENGTH %>,<%= IUser.MAX_FIELD_LENGTH %>}" required title="<%= IUser.MIN_NAME_LENGTH %> to <%= IUser.MAX_FIELD_LENGTH %> characters">
						</div>

						<div class="form-group col-md-6">
							<label for="">Email</label> <input type="email"
								class="form-control" name="email" id="" placeholder="Email"
								pattern=".{<%= IUser.MIN_EMAIL_LENGTH %>,<%= IUser.MAX_FIELD_LENGTH %>}" required title="<%= IUser.MIN_EMAIL_LENGTH %> to <%= IUser.MAX_FIELD_LENGTH %> characters">
						</div>

						<div class="form-group col-md-6">
							<label for="">User name</label> <input type="text"
								class="form-control" name="user_name" id="" placeholder="user_name"
								pattern=".{<%= IUser.MIN_NAME_LENGTH %>,<%= IUser.MAX_FIELD_LENGTH %>}" required title="<%= IUser.MIN_NAME_LENGTH %> to <%= IUser.MAX_FIELD_LENGTH %> characters">
						</div>

						<div class="form-group col-md-6">
							<label for="password">Password</label> <input type="password"
								class="form-control" name="password" id="password"
								placeholder="Password"
								pattern=".{<%= IUser.MIN_PASSWORD_LENGTH %>,<%= IUser.MAX_FIELD_LENGTH %>}" required title="<%= IUser.MIN_PASSWORD_LENGTH %> to <%= IUser.MAX_FIELD_LENGTH %> characters">
						</div>

						<div class="form-group col-md-6">
							<label for="confirm_password">Confirm Password</label> <input
								type="password" class="form-control" name="confirm_password"
								id="confirm_password" placeholder="Confirm Password"
								pattern=".{<%= IUser.MIN_PASSWORD_LENGTH %>,<%= IUser.MAX_FIELD_LENGTH %>}" required title="<%= IUser.MIN_PASSWORD_LENGTH %> to <%= IUser.MAX_FIELD_LENGTH %> characters">
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
							<button type="submit" class="btn btn-primary">Register</button>
							<a href="login.jsp">Already have an account?</a>
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