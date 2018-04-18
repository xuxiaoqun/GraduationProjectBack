package com.nefu.springboot.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.nefu.springboot.service.EmailService;

public class Test1 {
	
	
	
	@Autowired
	EmailService emailService;

	@Test
	public void test() throws Exception {
//		int identifyingCode = new Random().nextInt(10000)%(10000-1000+1) + 1000;
//		System.out.println(identifyingCode);
//		String str = "2018-04-02T16:00:00.000 UTC";
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");//注意格式化的表达式
//		Date d = format.parse(str );
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("s");
		System.out.println(buffer.toString());
		
		
	}

}
