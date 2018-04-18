package com.nefu.springboot.dao;

import com.nefu.springboot.vo.Consumer;

/**
 * 对用户操作的相关方法
 * @author   xzc
 * @since    2018/03/19
 */
public interface ConsumerDao {
	/**
	 * 用户注册
	 * @param consumer
	 */
	public void addConsumer(Consumer consumer);
	
	/**
	 * 更新用户的信息
	 * @param consumer
	 */
	public void updateConsumer(Consumer consumer);
	
	/**
	 * 查找对应用户的信息
	 * @param email
	 */
	public Consumer getConsumer(String email);
	
	
}
