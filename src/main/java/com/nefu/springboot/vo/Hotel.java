package com.nefu.springboot.vo;

import java.util.Arrays;

/**
 * 酒店实体类
 * @author   xzc
 * @since
 */
public class Hotel {
	
	private int hotel_id;
	private String name;
	private String address;
	private String star;
	private String phone;
	private String openTime;
	private String[] picture;
	private String[] flag;
	private String consumer_id;
	
	public int getHotel_id() {
		return hotel_id;
	}
	public void setHotel_id(int hotel_id) {
		this.hotel_id = hotel_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStar() {
		return star;
	}
	public void setStar(String star) {
		this.star = star;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getOpenTime() {
		return openTime;
	}
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
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
	public String getConsumer_id() {
		return consumer_id;
	}
	public void setConsumer_id(String consumer_id) {
		this.consumer_id = consumer_id;
	}
	
	@Override
	public String toString() {
		return "Hotel [hotel_id=" + hotel_id + ", name=" + name + ", address=" + address + ", star=" + star + ", phone=" + phone
				+ ", openTime=" + openTime + ", picture=" + Arrays.toString(picture) + ", flag=" + Arrays.toString(flag)
				+ ", consumer_id=" + consumer_id + "]";
	}
	
}
