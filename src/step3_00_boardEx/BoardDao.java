package step3_00_boardEx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BoardDao {

	private BoardDao() {}
	private static BoardDao instance = new BoardDao();
	public static BoardDao getInstance() {
		return instance;
	}
	
	private Connection conn 		= null;
	private PreparedStatement pstmt = null;
	private ResultSet rs 			= null;
	
	public Connection getConnection() {
	
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			String jdbcUrl = "jdbc:mysql://localhost:3306/step3_board_ex?serverTimezone=UTC";
			String dbId    = "root";
			String dbPass  = "1234";
			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPass);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
		
	}
	
	// 게시글을 추가하는 DAO
	public void insertBoard(BoardDto boardDto) {
		
		try {
			
			conn = getConnection();
			
			String sql = "INSERT INTO BOARD(WRITER,EMAIL,SUBJECT,PASSWORD,REG_DATE,READ_COUNT,CONTENT) ";
			      sql += "VALUES(?,?,?,?,NOW(),0,?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardDto.getWriter());
			pstmt.setString(2, boardDto.getEmail());
			pstmt.setString(3, boardDto.getSubject());
			pstmt.setString(4, boardDto.getPassword());
			pstmt.setString(5, boardDto.getContent());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			if (conn != null)  try {conn.close();}  catch (SQLException e) {e.printStackTrace();}
		}
		
	}
	
	// 전체 게시글을 조회하는 DAO
	public ArrayList<BoardDto> getAllBoard(){
		
		ArrayList<BoardDto> boardList = new ArrayList<BoardDto>();
		BoardDto bdto = null;
		
		try {
						
			conn = getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM BOARD");
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				bdto = new BoardDto();
				bdto.setNum(rs.getInt("NUM"));  		    // bdto.setNum(rs.getInt(1));
				bdto.setWriter(rs.getString("WRITER"));     // bdto.setWriter(rs.getString(2));
				bdto.setEmail(rs.getString("EMAIL"));       // bdto.setEmail(rs.getString(3));
				bdto.setSubject(rs.getString("SUBJECT"));   // bdto.setSubject(rs.getString(4));
				bdto.setPassword(rs.getString("PASSWORD")); // bdto.setPassword(rs.getString(5));
				bdto.setRegDate(rs.getDate("REG_DATE"));	// bdto.setRegDate(rs.getDate(6));
				bdto.setReadCount(rs.getInt("READ_COUNT")); // bdto.setReadCount(rs.getInt(7));
				bdto.setContent(rs.getString("CONTENT"));   // bdto.setContent(rs.getString(8));
				
				boardList.add(bdto);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)    try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			if (pstmt != null) try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			if (conn != null)  try {conn.close();}  catch (SQLException e) {e.printStackTrace();}
		}
		
		return boardList;
		
	}
	
	// 하나의 게시글을 조회하는 DAO
	public BoardDto getOneBoard(int num) {
		
		BoardDto bdto = new BoardDto();
		
		try {
			
			conn = getConnection();
			
			pstmt = conn.prepareStatement("UPDATE BOARD SET READ_COUNT = READ_COUNT + 1 WHERE NUM = ?");
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
			
			pstmt = conn.prepareStatement("SELECT * FROM BOARD WHERE NUM = ?");
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				
				bdto.setNum(rs.getInt("NUM"));
				bdto.setWriter(rs.getString("WRITER"));
				bdto.setEmail(rs.getString("EMAIL"));
				bdto.setSubject(rs.getString("SUBJECT"));
				bdto.setPassword(rs.getString("PASSWORD"));
				bdto.setRegDate(rs.getDate("REG_DATE"));
				bdto.setReadCount(rs.getInt("READ_COUNT"));
				bdto.setContent(rs.getString("CONTENT"));
				
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)    try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			if (pstmt != null) try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			if (conn != null)  try {conn.close();}  catch (SQLException e) {e.printStackTrace();}
		}
		
		return bdto;
		
	}
	
	// 하나의 게시글을 조회하는 DAO ( 조회수 증가 x )
	public BoardDto getOneUpdateBoard(int num) {
		
		BoardDto bdto = new BoardDto();
		
		try {
			
			conn = getConnection();
			
			pstmt = conn.prepareStatement("SELECT * FROM BOARD WHERE NUM = ?");
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				
				bdto.setNum(rs.getInt("NUM"));
				bdto.setWriter(rs.getString("WRITER"));
				bdto.setEmail(rs.getString("EMAIL"));
				bdto.setSubject(rs.getString("SUBJECT"));
				bdto.setPassword(rs.getString("PASSWORD"));
				bdto.setRegDate(rs.getDate("REG_DATE"));
				bdto.setReadCount(rs.getInt("READ_COUNT"));
				bdto.setContent(rs.getString("CONTENT"));
				
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)    try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			if (pstmt != null) try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			if (conn != null)  try {conn.close();}  catch (SQLException e) {e.printStackTrace();}
		}
		
		return bdto;
		
	}
	
	// 비밀번호를 인증하는 DAO
	public boolean validMemberCheck(BoardDto bdto) {
		
		boolean isValidMember = false;
		
		try {
			
			conn = getConnection();
			
			pstmt = conn.prepareStatement("SELECT * FROM BOARD WHERE NUM = ? AND PASSWORD = ?");
			pstmt.setInt(1, bdto.getNum());
			pstmt.setString(2, bdto.getPassword());
			rs = pstmt.executeQuery();
			
			if (rs.next()) isValidMember = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)    try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			if (pstmt != null) try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			if (conn != null)  try {conn.close();}  catch (SQLException e) {e.printStackTrace();}
		}
		
		return isValidMember;
		
	}
	
	
	// 게시글을 수정하는 DAO
	public boolean updateBoard(BoardDto bdto) {
		
		boolean isUpdate = false;
		
		try {
		
			if (validMemberCheck(bdto)) {
				
				conn = getConnection();
				pstmt = conn.prepareStatement("UPDATE BOARD SET SUBJECT = ? , CONTENT = ? WHERE NUM = ?");
				pstmt.setString(1, bdto.getSubject());
				pstmt.setString(2, bdto.getContent());
				pstmt.setInt(3, bdto.getNum());
				pstmt.executeUpdate();
				isUpdate = true;
						
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
			if (conn != null)  try {conn.close();}  catch (SQLException e) {e.printStackTrace();}
		}
		
		return isUpdate;
		
	} 
	
	
	
	
	
	
}
