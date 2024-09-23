package com.saeipman.app;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.saeipman.app.room.mapper.ConstractMapper;
import com.saeipman.app.room.mapper.RoomMapper;
import com.saeipman.app.room.service.RoomVO;

@SpringBootTest
class SaeipmanApplicationTests {

	@Autowired
	RoomMapper roomMapper;
	@Autowired
	ConstractMapper constractMapper;
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	//@Test
	public void test() {
		LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
		LocalDate nextPayLocalDate = today.plusDays(7);
		Date nextPayDate = Date.from(nextPayLocalDate.atStartOfDay(ZoneId.of("Asia/Seoul")).toInstant());
		System.out.println(nextPayDate);
	}
	
	//@Test
	public void passEncrypt() {
		String s = passwordEncoder.encode("1234");
		System.out.println(s);
	}
	@Test
	public void test2() {
		RoomVO vo = roomMapper.selectRoomInfo("ZIP000392101");
		System.out.println(vo.getConstractList());
	}

}
