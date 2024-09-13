package com.saeipman.app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.saeipman.app.room.mapper.RoomMapper;
import com.saeipman.app.room.service.ConstractVO;
import com.saeipman.app.room.service.RoomVO;

@SpringBootTest
class SaeipmanApplicationTests {
	
	@Autowired
	RoomMapper roomMapper;
	
	@Test
	public void test() {
		RoomVO vo = roomMapper.selectRoomInfo("ZIP000438101");
		List<ConstractVO> list = vo.getConstractList();
		
		assertEquals(list.size(), 0);
	}


}
