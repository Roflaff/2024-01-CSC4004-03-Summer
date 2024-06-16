package com.dgu_csc.akomanager_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin; //프론트 연결용 CrossOrigin 애너테이션 추가

@SpringBootApplication
//프론트 연결 (수빈) 프론트엔드와의 CORS 허용
@CrossOrigin(origins = "http://localhost:3000")

public class AkoManagerBackApplication {
	public static void main(String[] args) {
		SpringApplication.run(AkoManagerBackApplication.class, args);
	}
}