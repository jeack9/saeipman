package com.saeipman.app.commom.paging;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PagingDTO {
	private int page; // 현재 페이지
    private int recordSize; // 페이지당 출력 행수
    private int total; // 총 데이터 수
    private int viewPage; // 화면에 보여줄 페이지 개수
    private int startPage; // 시작 페이지
    private int endPage; // 마지막 페이지
    private int realEnd; // 실제 마지막 페이지
    private boolean next, prev; // 이전, 다음 페이지 체크
    
    public PagingDTO(int page, int recordSize, int total, int viewPage) {
        this.page = page;
        this.recordSize = recordSize;
        this.total = total;
        this.viewPage = viewPage;
        
        this.endPage = (int) (Math.ceil((double) page / viewPage) * viewPage);
        this.startPage = this.endPage - (viewPage - 1);
        this.realEnd = (int) Math.ceil((double) total / recordSize);
        
        this.endPage = this.endPage > this.realEnd ? this.realEnd : this.endPage;
        
        this.prev = this.startPage > 1;
        this.next = this.endPage < this.realEnd;
    }
}
