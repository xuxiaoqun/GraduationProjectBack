package com.nefu.springboot.service;

import java.util.Map;

import com.nefu.springboot.vo.Account;
import com.nefu.springboot.vo.Consumer;
import com.nefu.springboot.vo.Credit;

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

	/**
	 * 根据用户id查找用户的信用信息
	 * @param id
	 * @return
	 */
	public Credit getCredit(int id);
	
	/**
	 * 保存用户的信用信息
	 * @param credit
	 */
	public void saveCredit(Credit credit);
	
	/**
	 * 更新用户信用信息
	 * @param credit
	 */
	public void updateCredit(Credit credit);
	
	/**
	 * 根据用户id查找当前账户信息和账单信息
	 * @param id
	 * @return
	 */
	public Map<String, Object> getAccount(int consumer_id);
	
	/**
	 * 保存账户和账户信息
	 * @param account
	 */
	public void updateAccountandBill(Account account);
	
	/**
	 * 获取账户余额
	 * @param consumer_id
	 * @return
	 */
	public Account getAccountBalance(int consumer_id);
	
}
