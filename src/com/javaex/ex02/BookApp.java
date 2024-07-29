package com.javaex.ex02;

import java.util.List;

public class BookApp {

	public static void main(String[] args) {
		
		BookDao bookDao = new BookDao();

//등록

		/*int count = bookDao.insertBook("디지몬어드벤처", "반다이니뽄", "2022-02-22", 1);*/
//삭제
		//bookDao.deleteBook(4);
		
//작가전체리스트
		//List<BookVo> BookList = bookDao.selectBookAll();
		BookVo BookVo = bookDao.selectBook(4);
	}
}