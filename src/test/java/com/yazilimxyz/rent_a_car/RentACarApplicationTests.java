package com.yazilimxyz.rent_a_car;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.amazonaws.services.s3.model.Region;


@SpringBootTest
class RentACarApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void test() {
		System.out.println(Region.EU_North_1.toString());
	}

}
