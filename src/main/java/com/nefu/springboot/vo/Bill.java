package com.nefu.springboot.vo;

/**
 * 账单实体类
 * @author   xzc
 * @since
 */
public class Bill {

	private int bill_id;
	private int account_id;
	private String recharge;
	private String consume;
	public int getBill_id() {
		return bill_id;
	}
	public void setBill_id(int bill_id) {
		this.bill_id = bill_id;
	}
	public int getAccount_id() {
		return account_id;
	}
	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}
	public String getRecharge() {
		return recharge;
	}
	public void setRecharge(String recharge) {
		this.recharge = recharge;
	}
	public String getConsume() {
		return consume;
	}
	public void setConsume(String consume) {
		this.consume = consume;
	}
	@Override
	public String toString() {
		return "Bill [bill_id=" + bill_id + ", account_id=" + account_id + ", recharge=" + recharge + ", consume="
				+ consume + "]";
	}
	
}
