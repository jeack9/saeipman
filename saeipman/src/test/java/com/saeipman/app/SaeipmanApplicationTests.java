package com.saeipman.app;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.saeipman.app.gwanlibi.mapper.MonthGwanlibiMapper;
import com.saeipman.app.gwanlibi.service.MonthGwanlibiVO;

@SpringBootTest
class SaeipmanApplicationTests {
	
	
	@Autowired
	MonthGwanlibiMapper monthGwanlibiMapper;
	
	@Test
	void buildingList() {
		List<MonthGwanlibiVO> list = monthGwanlibiMapper.selectAllMonGwanlibiList("10");
		assertTrue(!list.isEmpty());
		System.err.println(list.size());
	}

}
