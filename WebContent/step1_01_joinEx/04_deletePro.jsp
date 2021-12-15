<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>deletePro</title>
</head>
<body>
	
	<% 
	
		request.setCharacterEncoding("utf-8");
	
		String id     = request.getParameter("id");
		String passwd = request.getParameter("passwd");
	
		
		Connection conn 		= null;
		PreparedStatement pstmt = null;
		ResultSet rs 		    = null;	// select문의 결과를 저장할 객체	
		
		try {
			
			String jdbcUrl = "jdbc:mysql://localhost:3306/login_ex?serverTimezone=UTC";
			String dbId    = "root";
			String dbPass  = "1234";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPass);
			
			pstmt = conn.prepareStatement("SELECT * FROM MEMBER WHERE ID=? AND PASSWD=?");
			pstmt.setString(1, id);
			pstmt.setString(2, passwd);
			
			rs = pstmt.executeQuery();
			
			// .next()메서드 :  db에서 select문으로 조회된 1줄의 쿼리결과의 존재 유무를 판별한다.
			// 반환된 결과물이 있으면 true 없으면 false를 반환한다.
			if (rs.next()) {
				
				pstmt = conn.prepareStatement("DELETE FROM MEMBER WHERE ID=?");
				pstmt.setString(1, id);
				pstmt.executeUpdate();
	%>
				<script>
					alert("회원 탈퇴 되었습니다.");
					location.href = "00_main.jsp";
				</script>
	<%				
			} 
			else {
	%>
				<script>
					alert("아이디와 비밀번호를 확인하세요.");
					// location.href = "03_delete.jsp";
					
					// 한페이지 뒤로 이동 history.back(); , history.go(-1);
					history.back();
				</script>	
	<%
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rs.close();
			pstmt.close();
			conn.close();
		}
		
	%>
	
</body>
</html>