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
	private int pagesize = 6;
	
	public PageDTO(Criteria cri, int total) {
		
		this.cri = cri;
		this.total = total;
		
		this.endPage = (int)(Math.ceil(cri.getPageNum() / (double)pagesize)) * pagesize;
		
		this.startPage = this.endPage - pagesize+1;
		
		 realEnd = (int)(Math.ceil((total * 1.0)/cri.getAmount()));
		
		if(realEnd < this.endPage) {
			this.endPage = realEnd;
		}
		
		this.prev = this.startPage > 1;
		
		this.next = this.endPage < realEnd;
	}

}
