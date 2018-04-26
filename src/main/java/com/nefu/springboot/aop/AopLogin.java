/*package com.nefu.springboot.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nefu.springboot.exception.LoginException;
import com.nefu.springboot.vo.Consumer;

@Aspect
@Component
public class AopLogin {

	private static final Logger log = LoggerFactory.getLogger(AopLogin.class);

	@Before("execution(* com.nefu.springboot.controller.FirstWebController.updateConsumer(..)) or"
			+ " execution(* com.nefu.springboot.controller.TakeOrderController.*(..)) or"
			+ " execution(* com.nefu.springboot.controller.BusinessController.*(..)) &&"
			+ "!execution(* com.nefu.springboot.controller.BusinessController.getHotelProduceInfo(..)) &&"
			+ "!execution(* com.nefu.springboot.controller.BusinessController.getHotelProInfoById(..))")
	public void login() throws Throwable {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		Consumer consumer = (Consumer) request.getSession().getAttribute("consumer");
		log.info("consumer:"+consumer);
		if (consumer == null) {
			throw new LoginException();
		}
	}
}
*/