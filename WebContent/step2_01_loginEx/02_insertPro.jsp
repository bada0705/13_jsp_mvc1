<%@page import="step2_00_loginEx.MemberDto"%>
<%@page import="step2_00_loginEx.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>insertPro</title>
</head>
<body>

	<% 
	
		request.setCharacterEncoding("utf-8");
	
		String id   = request.getParameter("id");
		String pwd  = request.getParameter("pwd");
		String name = request.getParameter("name");
		
		boolean isFirstMember = MemberDao.getInstance().insertMember(new MemberDto(id, pwd, name));
				
		if (isFirstMember) {
	%>
			<script>
				alert("You are now a member");
				location.href = "00_main.jsp";
			</script>
	<%		
		}
		else {
	%>
			<script>
				alert("This is a duplicated Id");
				history.go(-1);
			</script>
	<% 
		}
	%>

</body>
</html>



