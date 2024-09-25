package com.saeipman.app.minwon.mapper;

import java.util.List;

import com.saeipman.app.minwon.service.Criteria;
import com.saeipman.app.minwon.service.MinwonVO;
import com.saeipman.app.minwon.service.ReplyVO;

public interface MinwonMapper {

	//전체조회
	public List<MinwonVO> selectMinwonAll(Criteria cri);
	
	//단건조회
	public MinwonVO selectMinwonInfo(MinwonVO minwonVO);
	
	//등록(postNo, title, content, category, chumbuimage, roomNo, visitsDate)
	public int insertMinwon(MinwonVO minwonVO);
	
	//수정(title, content, category, chumbuImage, visitsDate)
	public int updateMinwon(MinwonVO minwonVO);
	
	//삭제
	public int deleteMinwon(int postNo);
	
	//카테고리 전체조회
	public List<MinwonVO> selectCategory();
	
	//토탈
	public int getTotalCount(Criteria cri);
	
	//민원상태처리 업로드
	public int updateAcceptState(MinwonVO minwonVO);
	
	//임대인별 건물 리스트
	public List<MinwonVO> selectBuilding(Criteria cri);
	
	
	public List<String> selectFileName(int postNo);
	
	//파일
	public int selectDeleteFileName(String fileName);
	public int selectFileNamesByGroupId(String groupId);
	
	public String selectimchainId(int postNo);	
}
