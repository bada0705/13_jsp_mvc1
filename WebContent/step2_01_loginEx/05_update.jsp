<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>update</title>
</head>
<body>
	
	<% 
		String id = (String)session.getAttribute("id");
	%>
	
	<form method="post" action="06_updatePro.jsp">
		<fieldset>
			<legend>'<%=id %>' 정보 수정</legend>
			<p>Id : <input type="text" name="id" value="<%=id%>" readonly></p>
			<p>Password : <input type="password" name="pwd"></p>
			<p>Name : <input type="text" name="name"></p>
			<p><input type="submit" value="update"></p>
		</fieldset>
	</form>	

</body>
</html>




