package com.kuehne.nagel.interview;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kuehne.nagel.interview.config.JwtTokenProvider;

@SpringBootTest
class CityListApplicationTests {

	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@Test
	void getToken() {
		 


	}

}
