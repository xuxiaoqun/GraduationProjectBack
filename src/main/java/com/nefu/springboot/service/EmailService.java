package com.nefu.springboot.service;

import java.util.Map;

public interface EmailService {

	/**
	 * 注册账户时的邮箱验证码校验模板内容
	 * @return
	 * @throws Exception 
	 */
	public String emailValidate(Map<String, Object> model) throws Exception;

	/**
	 * 预订酒店时的通知用户模板内容
	 * @param model 邮件中插入的信息
	 * @return
	 */
	public String emailInformConsumer(Map<String, Object> model) throws Exception;
	
	/**
	 * 预订酒店时的通知商家模板内容
	 * @param model 邮件中插入的信息
	 * @return
	 */
	public String emailInformBusiness(Map<String, Object> model) throws Exception;
}
