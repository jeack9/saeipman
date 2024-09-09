package com.saeipman.app.noticeBuilding.utils;

import lombok.Data;

@Data
public class PagingSearchDTO {
	
	private int nowPage; //페이지 번호
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
	public PagingSearchDTO () {
		this.nowPage = 1;
		this.pageData = 10;
				
	}
	
	public void setTotal (int totalPage) {
		this.totalPage = totalPage;
		pageCount();
	}
	
	private void pageCount() {
		// 전체 페이지 개수 구하기
		totalPage = (int)Math.ceil((double)totalBoard / pageData);
		
		// 페이지 유효성 검사(페이지 번호가 0이거나 페이지 번호가 전체 페이지 수보다 클 경우 같은)
		if(nowPage <= 0 || nowPage > totalPage) {
			nowPage = 1;
			
		// 시작, 끝 행 번호
//			firstRow = (nowPage-1)*pageData+1; // ex) 현재 페이지 5, 10개씩 출력된다는 가정하에 => 첫 글번호 : 41 / 끝 글번호 : 50
//			lastRow = nowPage * pageData;
//			
//			
//			if(lastRow > totalBoard) { // 출력하기로 한 글의 개수보다 실제 글 개수가 모자라면 마지막 글번호를 실제 끝 글번호로 대체.
//				lastRow = totalBoard;
//			}
			
			// 한 페이지에 출력되는 페이지 번호 개수를 기준으로 페이지 번호 계산
			startPage = (nowPage - 1) / pageCnt * pageCnt+1; // ex) 현재 페이지 13이라면 시작페이지 11(소수점 버리고 정수부분만 생각함)
			endPage = startPage + pageCnt - 1;
						
			if(endPage > totalPage) {
				endPage = totalPage;
			}
			
			// 이전(prevPage), 다음(nextPage) 페이지
			prevPage = startPage - pageCnt;
			nextPage = startPage + pageCnt;
			
			
		}
	}

}
