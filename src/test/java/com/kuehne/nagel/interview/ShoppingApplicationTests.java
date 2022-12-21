package com.kuehne.nagel.interview;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kuehne.nagel.interview.config.JwtTokenProvider;

@SpringBootTest
class ShoppingApplicationTests {

	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@Test
	void contextLoads() {
		 
  String jwt = jwtTokenProvider.createToken("888888e9877e4848848844");
  System.out.println(jwt);
  System.out.println("Jesus");

	}

}
