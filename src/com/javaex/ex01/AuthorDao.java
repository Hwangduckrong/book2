package com.javaex.ex01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AuthorDao {

	// 필드-고정된 값들은 위로 뺐다.
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/book_db";
	private String id = "book";
	private String pw = "book";

	// 생성자

	// 메소드 gs

	// 메소드 일반
	private void getConnection() {

		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	private void close() {
		// 5. 자원정리
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	// 인서트
	  {

		int count = -1;

		// (1)(2) 번 getConnection메소드로 따로 정의하고 불러 쓴다
		// DB연결메소드 호출
		this.getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			// *sql문 준비
			String query = "";
			query += " insert into author ";
			query += " values(null, ?, ?) ";

			// *바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, desc);

			// *실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 등록되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		// 자원정리 메소드 호출
		this.close();

		return count;
	}

	// 딜리트
	public int deleteAuthor(int authorId) {
		System.out.println(authorId);
		System.out.println("삭제 로직");

		int count = -1;

		// 0. import java.sql.*;

		this.getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행

			// 쿼리문 준비
			String query = "";
			query += " delete from author ";
			query += " where author_id = ? ";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, authorId);

			// 실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 등록되었습니다.");

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {

			// 5. 자원정리
			this.close();

		}
		return count;
	}

	// 작가전체리스트
	public List<AuthorVo> selectAuthorAll() {
		System.out.println("작가전체리스트");

		List<AuthorVo> authorList = new ArrayList<AuthorVo>();

		// 0. import java.sql.*;

		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기

			conn = DriverManager.getConnection(url, id, pw);

			// 3. SQL문 준비 / 바인딩 / 실행
			// *sql문 준비
			String query = "";
			query += " select 	 author_id, ";
			query += "		     author_name, ";
			query += "           author_desc, ";
			query += " from author, ";
			query += " order by author_id asc";

			// *바인딩
			pstmt = conn.prepareStatement(query);

			// *실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {
				int id = rs.getInt("author_id");
				String name = rs.getNString("author_name");
				String desc = rs.getNString("author_desc");

				AuthorVo authorVo = new AuthorVo(id, name, desc);
				authorList.add(authorVo);
			}

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {

			// 5. 자원정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}
		return authorList; // 리스트의 *주소를 리턴해준다

	}

	public int updateAuthor(String name, String desc, int authorId) {
		System.out.println("업데이트 로직");

		int count = 0; // count 변수를 try 블록 외부에서 선언

		try {
			Class.forName(driver);

			conn = DriverManager.getConnection(url, id, pw);

			String query = "";
			query += " update author ";
			query += " set author_name = ?, ";
			query += "     author_desc = ? ";
			query += " where author_id = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name); // 매개변수 사용
			pstmt.setString(2, desc); // 매개변수 사용
			pstmt.setInt(3, authorId);

			count = pstmt.executeUpdate(); // 수정된 row 수를 count에 저장

			System.out.println(count + "건이 수정되었습니다.");

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}
		return count; // 여기서 count 값을 반환
	}

	// 작가한명
	public AuthorVo selectAuthor(int authorId) {
		System.out.println("작가한명");

		AuthorVo authorVo = null;

		// 0. import java.sql.*;

		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기

			conn = DriverManager.getConnection(url, id, pw);

			// 3. SQL문 준비 / 바인딩 / 실행
			// *sql문 준비
			String query = "";
			query += " select 	author_id, ";
			query += "		    author_name, ";
			query += "          author_desc ";
			query += " from author ";
			query += " where author_id = ? ";

			// *바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, authorId);

			// *실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			// 리스트로 만들기
			while (rs.next()) {
				int aId = rs.getInt("author_id");
				String name = rs.getString("author_name");
				String desc = rs.getString("author_desc");

				authorVo = new AuthorVo(authorId, name, desc);
			}

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {

			// 5. 자원정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}

		return authorVo; // 리스트의 *주소를 리턴해준다

	}// 작가한명
}