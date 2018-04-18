package com.nefu.springboot.vo;

import java.util.Arrays;

/**
 * 酒店房型的实体类
 * @author   xzc
 * @since
 */
public class Produce {
	
	private int produce_id;
	private int hotel_id;
	private String houseType;
	private String bedType;
	private String price;
	private String amount;
	private String capacity;
	private String[] date;
	private String[] picture;
	private String[] flag;
	
	public int getProduce_id() {
		return produce_id;
	}
	public void setProduce_id(int produce_id) {
		this.produce_id = produce_id;
	}
	public int getHotel_id() {
		return hotel_id;
	}
	public void setHotel_id(int hotel_id) {
		this.hotel_id = hotel_id;
	}
	public String getHouseType() {
		return houseType;
	}
	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}
	public String getBedType() {
		return bedType;
	}
	public void setBedType(String bedType) {
		this.bedType = bedType;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCapacity() {
		return capacity;
	}
	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	public String[] getDate() {
		return date;
	}
	public void setDate(String[] date) {
		this.date = date;
	}
	public String[] getPicture() {
		return picture;
	}
	public void setPicture(String[] picture) {
		this.picture = picture;
	}
	public String[] getFlag() {
		return flag;
	}
	public void setFlag(String[] flag) {
		this.flag = flag;
	}
	@Override
	public String toString() {
		return "Produce [produce_id=" + produce_id + ", hotel_id=" + hotel_id + ", houseType=" + houseType + ", bedType=" + bedType
				+ ", price=" + price + ", amount=" + amount + ", capacity=" + capacity + ", date="
				+ Arrays.toString(date) + ", picture=" + Arrays.toString(picture) + ", flag=" + Arrays.toString(flag)
				+ "]";
	}
	
}
