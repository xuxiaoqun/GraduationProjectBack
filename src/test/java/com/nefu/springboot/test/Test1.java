package com.nefu.springboot.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.nefu.springboot.service.EmailService;
import com.nefu.springboot.service.HotelService;

public class Test1 {
	
	
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	HotelService hotelService;

	@Test
	public void test() throws Exception {
//		int identifyingCode = new Random().nextInt(10000)%(10000-1000+1) + 1000;
//		System.out.println(identifyingCode);
//		String str = "2018-04-02T16:00:00.000 UTC";
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");//注意格式化的表达式
//		Date d = format.parse(str );
//		
//		StringBuffer buffer = new StringBuffer();
//		buffer.append("s");
//		System.out.println(buffer.toString());
		"a".compareTo("b");
		System.out.println(hotelService.getProInfoById("13", "2018-05-21", "2018-05-30"));
	}

}
