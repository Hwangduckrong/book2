package com.javaex.ex01;

import java.util.List;

public class AuthorApp {
	
public static void main(String[] args) {
		
	AuthorDao authorDao	= new AuthorDao();
	
	int count= authorDao.deleteAuthor(7);
	
	List<AuthorVo>authorList=authorDao.selectAuthorAll();
	
	for(int i=0; i<authorList.size(); i++) {
		
		AuthorVo aVo = authorList.get(i);

		System.out.print(authorList.get(i));
	}

	}

}
