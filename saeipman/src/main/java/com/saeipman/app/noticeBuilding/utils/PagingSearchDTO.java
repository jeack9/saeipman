package com.saeipman.app.noticeBuilding.utils;

import lombok.Data;

@Data
public class PagingSearchDTO {
	
	private int nowPage; //현재 페이지 번호
	private int totalBoard; //총 게시글 수
	private int pageData; // 한 페이지에 출력되는 게시글 수
	private int pageCnt; //화면 하단 페이지 수
	
    private int totalPage; //총 페이지
    private int firstRow; // 출력되는 첫 게시물 글번호
    private int lastRow; // 출력되는 마지막 게시물 글번호
    
    private int startPage; // 하단의 페이징 시작 번호
    private int endPage; // 하단의 페이징 끝 번호
	private int prevPage; // 이전 페이징 시작 페이지 번호 
	private int nextPage; // 다음 페이징 시작 페이지 번호
   
	private String keyword; 
	private String scType;
	
	//페이지 번호, 총 게시물, 출력되는 게시글 개수, 하단 페이지 수
	public PagingSearchDTO (int nowPage, int totalBoard, int pageData, int pageCnt) {
		super();
		this.nowPage = nowPage;
		this.totalBoard = totalBoard;
		this.pageData = pageData;
		this.pageCnt = pageCnt;
				
	}
	
	private void pageCount() {
		// 전체 페이지 개수 구하기
		totalPage = (int)Math.ceil((double)totalBoard / pageData);
		
		// 
	}
	
	
	
	public PagingSearchDTO() {
		this.nowPage = 1;
		this.pageData = 10;
		this.pageCnt = 10;
	}
	
	public int startData() {
		return (nowPage-1) * pageData;
	}
	

}
