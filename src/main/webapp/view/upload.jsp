<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<FORM ENCTYPE="multipart/form-data" method="POST"
		action="http://localhost:8080/Library_API_Test/uploadvideo">
		Video name: <INPUT TYPE="text" NAME="name" value="video name" /><br />
		Description: <INPUT TYPE="text" NAME="desc" value="description" /><br />
		File: <INPUT TYPE="file" NAME="file" accept="video/mp4" /> <INPUT TYPE="submit"
			VALUE="Upload" />
	</FORM>
</body>
</html>