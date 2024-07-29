package com.javaex.ex02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.ex01.AuthorVo;

public class BookDao {

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/book_db";
	private String id = "book";
	private String pw = "book";

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
	public int insertBook(String title, String pubs, String pubDate, int authorId) {
		System.out.println("입력 로직");

		int count = -1;

		this.getConnection();

		try {

			// sql문 준비
			String query = "";
			query += " insert into book ";
			query += " values(null, ? , ? , ? ,?) ";

			// 바인딩

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.setString(2, pubs);
			pstmt.setString(3, pubDate);
			pstmt.setInt(4, authorId);

			// 실행

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 등록되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
		return count;
	}

//삭제
	public int deleteBook(int BookId) {
		int count = -1;

		this.getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			// *sql문 준비
			String query = "";
			query += " delete from book ";
			query += " where book_id = ? ";

			// *바인딩 ?
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, BookId);

			// *실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 삭제되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return count;

	}

	public int updateAuthor(int authorId, String name, String desc) {
		int count = -1;

		this.getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			// *sql문 준비
			String query = "";
			query += " update author ";
			query += " set author_name = ?, ";
			query += "     author_desc = ? ";
			query += " where author_id = ? ";

			// *바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, desc);
			pstmt.setInt(3, authorId);

			// *실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 수정되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return count;
	}// 작가수정

	// 작가전체리스트

	public List<BookVo> selectBookAll() {

		List<BookVo> BookList = new ArrayList<BookVo>();

		this.getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			// *sql문 준비
			String query = "";
			query += " select 	book_id, ";
			query += "		    title, ";
			query += "          pubs, ";
			query += "		    pub_date, ";
			query += "		    author_id ";
			query += " from book ";
			query += " order by author_id asc ";

			// *바인딩
			pstmt = conn.prepareStatement(query);

			// *실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			// 리스트로 만들기
			while (rs.next()) {
				int id = rs.getInt("book_id");
				String title = rs.getString("title");
				String pubs = rs.getString("pubs");
				String pubdate = rs.getString("pub_date");
				int aId = rs.getInt("author_id");
				BookVo bookVo = new BookVo(id, title, pubs, pubdate, aId);
				BookList.add(bookVo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();
		return BookList;
	}
	public BookVo selectBook(int authorId) {
		BookVo bookVo = null;
		
		this.getConnection();
		
		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			// *sql문 준비
			String query = "";
			query += " select 	title, ";
			query += "		    pubs, ";
			query += "          pub_date ";
			query += " from book ";
			query += " where author_id = ? ";

			// *바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, authorId);

			// *실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			// 리스트로 만들기
			while (rs.next()) {
				String title = rs.getString("title");
				String pubs = rs.getString("pubs");
				String pubdate = rs.getString("pub_date");
				int aId = rs.getInt("author_id");
				BookVo bookvo2 = new BookVo(title, pubs, pubdate, aId);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		this.close();

		return bookVo; // 리스트의 *주소를 리턴해준다

}
}