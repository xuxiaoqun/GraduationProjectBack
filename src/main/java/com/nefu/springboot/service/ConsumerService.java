package com.nefu.springboot.service;

import com.nefu.springboot.vo.Consumer;

public interface ConsumerService {
	
	/**
	 * 将验证码和对应的邮箱写入数据库
	 * @param consumer
	 */
	public void addConsumer(Consumer consumer);
	
	/**
	 * 更新对应用户的信息
	 * @param consumer
	 */
	public void updateConsumer(Consumer consumer);
	
	/**
	 * 查找当前邮箱对应的用户信息
	 * @param email
	 * @return
	 */
	public Consumer getConsumerByEmail(String email);
	
	/**
	 * 判断当前邮箱是否注册过
	 * @param email
	 * @return
	 */
	public Boolean isRegister(String email);
	
	/**
	 * 判断当前用户是否可以登录
	 * @param consumer
	 * @return
	 */
	public Boolean isLogin(Consumer consumer);

}
