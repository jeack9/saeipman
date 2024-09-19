package com.saeipman.app;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
		LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
		LocalDate nextPayLocalDate = today.plusDays(7);
		Date nextPayDate = Date.from(nextPayLocalDate.atStartOfDay(ZoneId.of("Asia/Seoul")).toInstant());
		System.out.println(nextPayDate);
	}

}
