<%@page import="step2_00_loginEx.MemberDto"%>
<%@page import="step2_00_loginEx.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>updatePro</title>
</head>
<body>

	<% 
	
		request.setCharacterEncoding("utf-8");
	
		String id   = request.getParameter("id");
		String pwd  = request.getParameter("pwd");
		String name = request.getParameter("name");
		
		boolean isUpdate = MemberDao.getInstance().updateMember(new MemberDto(id,pwd,name));
		
		if (isUpdate) {
	%>
			<script>
				alert("Information has changed");
				location.href = "00_main.jsp";
			</script>
	<% 		
		}
		else {
	%>
			<script>
				alert("Check you Id and Password");
				history.go(-1);
			</script>	
	<%		
		}
	%>


</body>
</html>