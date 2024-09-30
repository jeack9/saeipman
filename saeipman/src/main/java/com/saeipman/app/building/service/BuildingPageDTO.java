package com.saeipman.app.building.service;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Data
public class BuildingPageDTO {
	private int startPage;
	private int endPage;
	private int realEnd;
	private boolean prev, next;
	private int pageNum;
	private int amount;

	private int total;
	
	private String buildingId;
	private Integer paymentState;//월세 납부 상태
	private Integer roomNo;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date paymentDate1;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date paymentDate2;
	
	public BuildingPageDTO() {
		this.pageNum = 1;
		this.amount = 5;
		//this.amount = amount;
	}

	public void setTotal(int total) {
		this.total = total;
		buildingPage();
	}

	private void buildingPage() {
		this.endPage = (int) (Math.ceil(pageNum / 5.0)) * 5;
	

		this.startPage = this.endPage - 4;

		realEnd = (int) (Math.ceil((total * 1.0) / amount));
	

		if (realEnd < this.endPage) {
			this.endPage = realEnd;
		}

		this.prev = this.startPage > 1;

		this.next = this.endPage < realEnd;
	}
}
