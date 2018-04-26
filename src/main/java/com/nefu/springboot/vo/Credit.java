package com.nefu.springboot.vo;

/**
 * 信用实体类
 * @author   xzc
 * @since
 */
public class Credit {
	
	private int id;
	private int consumer_id;
	private String phone;
	private String idCard;
	private String bankCard;
	private int creditIndex;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getConsumer_id() {
		return consumer_id;
	}
	public void setConsumer_id(int consumer_id) {
		this.consumer_id = consumer_id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getBankCard() {
		return bankCard;
	}
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
	public int getCreditIndex() {
		return creditIndex;
	}
	public void setCreditIndex(int creditIndex) {
		this.creditIndex = creditIndex;
	}
	@Override
	public String toString() {
		return "Credit [id=" + id + ", consumer_id=" + consumer_id + ", phone=" + phone + ", idCard=" + idCard
				+ ", bankCard=" + bankCard + ", creditIndex=" + creditIndex + "]";
	}
	
	
}
