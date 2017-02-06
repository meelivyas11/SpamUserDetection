<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/w3.css">
<link rel="stylesheet" href="/lib/w3-theme-black.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<title>Spam User's Detection Application</title>
</head>
<body>

	<!-- Image Header -->
	<div class="w3-display-container w3-animate-opacity">
		<img src="images/background.png" alt="boat" style="width: 100%; min-height: 300px; max-height: 400px;">
	</div>

	<br />
	<br />
	<div class="w3-row-padding">
		<div class="w3-half">
			<h2>Spam User Finder</h2>
			<form action="ShowAppletServlet" method="post"
				enctype="multipart/form-data" class="w3-container w3-card-4">
				<table width="100%">
					<tr>
						<td align="center"><h4>Choose a File(*.tsv) and start the Finder</h4></td>
					</tr>
					<tr>
						<td align="center"><input id="path" name="path" type="file" border="2" /> 
						<INPUT TYPE="submit" id="pathButton"name="pathButton" VALUE="Find" size="100"></td>
					</tr>
					<tr>
						<td><br /></td>
					</tr>
					<tr>
						<td>Click <a href="https://github.com/meelivyas11/SpamUserDetection/blob/master/ApplicationTestTweets.tsv">here</a> to download sample input file
						</td>
					</tr>
				</table>
			</form>
		</div>

		<div class="w3-half">
			<h2>Spam User Initiator</h2>
			<form action="InitializerServlet" method="post"	class="w3-container w3-card-4">
				<table width="100%">
					<tr>
						<td align="center"><h4>For only Admin User Only</h4></td>
					</tr>
					<tr>
						<td align="center"><input id="username" name="username"	type="text" size="30" placeholder="Username" /></td>
					</tr>
					<tr>
						<td align="center"><input id="password" name="password"	type="password" size="30" placeholder="Password" /></td>
					</tr>
					<tr>
						<td align="center"><INPUT TYPE="submit" id="Login"	name="actionButton" VALUE="Start"></td>
					</tr>
					<tr>
						<td align="left">
							<%
								String InitializationStatus = (String) request.getSession().getAttribute("InitializationStatus");
								if (InitializationStatus != null)
									out.println("<h5><b>" + InitializationStatus + "</b></h5>");
							%>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>

	<div class="w3-container">
		<hr>
		<div class="w3-center">
			<h2>Spam Detector Results</h2>
			<p w3-class="w3-large">
				<%
					 String spamUserNames= (String) request.getSession().getAttribute("spamUserNames");
				 	 if(spamUserNames!=null)
				 		out.println("<b>Spam User's Are: </b>" + spamUserNames.toString().trim());
					%>
			</p>
		</div>
	</div>
</body>

</html>