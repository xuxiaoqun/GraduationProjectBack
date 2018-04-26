package com.nefu.springboot.dao;

import java.util.List;

import com.nefu.springboot.vo.Account;
import com.nefu.springboot.vo.Bill;
import com.nefu.springboot.vo.Consumer;
import com.nefu.springboot.vo.Credit;

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
	 * 获取账户信息
	 * @param consumer_id
	 * @return
	 */
	public Account getAccount(int consumer_id);
	
	/**
	 * 保存账户信息
	 * @param account
	 */
	public void saveAccount(Account account);
	
	/**
	 * 更新账户信息
	 * @param account
	 */
	public void updateAccount(Account account);
	
	/**
	 * 保存账单信息
	 * @param bill
	 */
	public void saveBill(Bill bill);
	
	/**
	 * 获取账号的账单
	 * @param account_id
	 */
	public List<Bill> getBill(int account_id);
	
}
