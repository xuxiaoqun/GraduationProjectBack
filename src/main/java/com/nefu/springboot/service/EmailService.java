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
	 * 预订酒店时的通知模板内容
	 * @param model 邮件中插入的信息
	 * @return
	 */
	public String emailInform(Map<String, Object> model) throws Exception;
}
