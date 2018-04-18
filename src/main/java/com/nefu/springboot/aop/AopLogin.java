package com.nefu.springboot.aop;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import com.nefu.springboot.exception.LoginException;

//@Aspect
//@Component
//public class AopLogin {
//
//	private static final Logger log = LoggerFactory.getLogger(AopLogin.class);
//
//	@Before("execution(* com.nefu.springboot.controller.*.*(..)) && !execution(* com.nefu.springboot.controller.FirstWebController.*(..)) ")
//	public void login() throws Throwable {
//		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//		HttpServletRequest request = attributes.getRequest();
//		String user = (String) request.getSession().getAttribute("user");
//		if (user != null) {
//			return;
//		}
//		throw new LoginException();
//	}
//}
