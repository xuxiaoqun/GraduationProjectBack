package com.nefu.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.nefu.springboot.dao")
@EnableCaching
@EnableTransactionManagement
@EnableScheduling
public class StartSpringBootMain {

	public static void main(String[] args) {
		SpringApplication.run(StartSpringBootMain.class, args);
	}
	
//	@Bean(name = "multipartResolver")
//	public CommonsMultipartResolver getCommonsMultipartResolver() {
//	    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//	    multipartResolver.setMaxUploadSize(20971520);   
//	    multipartResolver.setMaxInMemorySize(1048576);
//	    return multipartResolver;
//	}
}
