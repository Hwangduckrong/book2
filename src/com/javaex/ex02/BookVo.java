package com.javaex.ex02;

import java.util.List;

public class BookVo {

		private int bookId;
		private String title;
		private String pubs;
		private String pubDate;
		private int authorId;
		public BookVo() {
			super();
		}
		public BookVo(int bookId, String title, String pubs, String pubDate, int authorId) {
			super();
			this.bookId = bookId;
			this.title = title;
			this.pubs = pubs;
			this.pubDate = pubDate;
			this.authorId = authorId;
		}
		public BookVo(String title2, String pubs2, String pubdate2, int aId) {
			
		}
		public int getBookId() {
			return bookId;
		}
		public void setBookId(int bookId) {
			this.bookId = bookId;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getPubs() {
			return pubs;
		}
		public void setPubs(String pubs) {
			this.pubs = pubs;
		}
		public String getPubDate() {
			return pubDate;
		}
		public void setPubDate(String pubDate) {
			this.pubDate = pubDate;
		}
		public int getAuthorId() {
			return authorId;
		}
		public void setAuthorId(int authorId) {
			this.authorId = authorId;
		}
		@Override
		public String toString() {
			return "BookVo [bookId=" + bookId + ", title=" + title + ", pubs=" + pubs + ", pubDate=" + pubDate
					+ ", authorId=" + authorId + "]";
		}
		

	}


