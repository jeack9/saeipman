package com.saeipman.app.minwon.service;

import org.springframework.data.relational.core.query.Criteria;

import groovy.transform.ToString;
import lombok.Getter;

@Getter
@ToString
public class PageDTO {
	private int startPate;
	private int endPage;
	private boolean prev, next;
	
	private int total;
	private Criteria cri;
	
	public PageDTO(Criteria cri, int total) {
		
		this.cri = cri;
		this.total = total;
		
		this.endPage = this.endPage - 9;
		
		//int realEnd = (int)(Math.ceil(total * 1.0)/cri.getAmount());
	}

}
