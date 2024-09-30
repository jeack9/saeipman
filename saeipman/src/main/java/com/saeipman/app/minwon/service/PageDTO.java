package com.saeipman.app.minwon.service;


import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageDTO {
	private int startPage;
	private int endPage;
	private int realEnd;
	private boolean prev, next;
	
	private int total;
	private Criteria cri;
	private int pagesize = 10;
	
	public PageDTO(Criteria cri, int total) {
		
		this.cri = cri;
        this.total = total;

        // 한 페이지에 표시할 항목 수 (예: 10개)
        int amount = cri.getAmount();

        // 실제 마지막 페이지 계산 (총 데이터 수 / 페이지당 항목 수)
        this.realEnd = (int) Math.ceil((double) total / amount);

        // 현재 페이지 그룹의 마지막 페이지 계산
        this.endPage = (int) Math.ceil(cri.getPageNum() / (double) pagesize) * pagesize;

        // 현재 페이지 그룹의 시작 페이지 계산
        this.startPage = this.endPage - pagesize + 1;

        // 실제 마지막 페이지가 endPage보다 작으면 endPage를 realEnd로 설정
        if (realEnd < endPage) {
            this.endPage = realEnd;
        }

        // 이전 페이지 여부
        this.prev = this.startPage > 1;

        // 다음 페이지 여부
        this.next = this.endPage < realEnd;
		
		
		System.out.println("Start Page: " + startPage);
	    System.out.println("End Page: " + endPage);
	    System.out.println("Real End: " + realEnd);
	    System.out.println("Prev: " + prev);
	    System.out.println("Next: " + next);
	}

}
