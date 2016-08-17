<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Spam User's Detection Application</title>
</head>
<body>
	<center>
		<H1>Spam User Detector</H1>
	</center> 
	
	<form action="ShowAppletServlet" method="post" enctype="multipart/form-data">
		<table align="center">
			<tr><td><h4>To Identify the spam uses star the Application</h4></td></tr>
			<tr><td>Select a file with .tsv extention (example: Attached tempTest.tsv) <input id="path" name="path" type="file" border="2" /></td></tr>
			<tr><td><INPUT TYPE="submit" id="pathButton" name="pathButton" VALUE="Start View" size="50"></td></tr>
			<tr><td>Spam Users : 
				 	<b><%
					 String spamUserNames= (String) request.getSession().getAttribute("spamUserNames");
				 	 if(spamUserNames!=null)
				 		out.println(spamUserNames.toString().trim());
					%></b>
			</td></tr>
		</table>
 	
 </form>
<br><br><br><br>
	<form action="InitializerServlet" method="post">
		<table align="center">
		<tr><td><h4>In order to Initialize the Learner, Enter User name and Password (For only Admin's)</h4></td></tr>
		<tr><td>Username: <input id="username" name="username" type="text" size="30" /></td></tr>
		<tr><td>Password:  <input id="password" name="password" type="password" size="30" /> </td></tr>
		<tr><td align="center"><INPUT TYPE="submit" id="Login" name="actionButton" VALUE="Start Initialization"></td></tr>
		<tr><td>
			<%
 			String InitializationStatus= (String) request.getSession().getAttribute("InitializationStatus");
		 	 if(InitializationStatus!=null)
		 		out.println("<h5><b>----" + InitializationStatus+"----</b> </h5>");
			%>
		</td></tr>
	</table>	
	</form></body>
</html>