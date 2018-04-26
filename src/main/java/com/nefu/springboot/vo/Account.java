package com.nefu.springboot.vo;

/**
 * 个人账户实体类
 * @author   xzc
 * @since
 */
public class Account {
	
	private int account_id;
	private int consumer_id;
	//账户余额
	private float balance;
	//此次账户余额的变化
	private float change;
	public int getAccount_id() {
		return account_id;
	}
	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}
	public int getConsumer_id() {
		return consumer_id;
	}
	public void setConsumer_id(int consumer_id) {
		this.consumer_id = consumer_id;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	
	public float getChange() {
		return change;
	}
	public void setChange(float change) {
		this.change = change;
	}
	@Override
	public String toString() {
		return "Account [account_id=" + account_id + ", consumer_id=" + consumer_id + ", balance=" + balance
				+ ", change=" + change + "]";
	}
	
}
