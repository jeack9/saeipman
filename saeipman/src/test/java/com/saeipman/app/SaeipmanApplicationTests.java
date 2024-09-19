package com.saeipman.app;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.saeipman.app.room.mapper.ConstractMapper;
import com.saeipman.app.room.mapper.RoomMapper;

@SpringBootTest
class SaeipmanApplicationTests {
	
	@Autowired
	RoomMapper roomMapper;
	@Autowired
	ConstractMapper constractMapper;
	
	@Test
	public void test() {
		List<Map<String, Object>> constractList = constractMapper.selectRoomConstract("ZIP000392");
		System.out.println(constractList.get(0).get("M_RENT"));
	}


}
