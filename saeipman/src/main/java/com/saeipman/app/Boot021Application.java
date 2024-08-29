package com.saeipman.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.saeipman.app.**.mapper")
public class Boot021Application {

	public static void main(String[] args) {
		SpringApplication.run(Boot021Application.class, args);
	}

}
