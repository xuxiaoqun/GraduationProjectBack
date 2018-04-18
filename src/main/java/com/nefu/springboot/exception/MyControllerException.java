package com.nefu.springboot.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 全局捕获异常，所有用@RequestMapping修饰的方法都会被捕获
 * @author   xzc
 * @since
 */
@ControllerAdvice
public class MyControllerException {
	
	private static final Logger log = LoggerFactory.getLogger(MyControllerException.class);

	@ResponseBody
	@ExceptionHandler(value = Exception.class)
	public Boolean errorHandler(Exception e) {
		log.info("全局捕获的异常信息：",e);
		return false;
	}
	
	@ResponseBody
	@ExceptionHandler(value = MyException.class)
	public Map<String, Object> errorHandler(MyException e) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", e.getCode());
		map.put("msg", e.getMsg());
		return map;
	}
	
	@ExceptionHandler(value = LoginException.class)
	public String loginHandler(){
		return "login";
		
	}
}
