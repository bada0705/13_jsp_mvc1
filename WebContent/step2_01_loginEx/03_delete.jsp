<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>delete</title>
</head>
<body>
	
	<% 
		String id = (String)session.getAttribute("id");
	%>
	
	<form method="post" action="04_deletePro.jsp">
		<fieldset>
			<legend>'<%=id %>' 회원탈퇴</legend>
			<p>Id : <input type="text" name="id" value="<%=id%>" readonly></p>
			<p>Password : <input type="password" name="pwd"></p>
			<p><input type="submit" value="delete"></p>
		</fieldset>
	</form>

</body>
</html>