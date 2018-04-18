package com.nefu.springboot.vo;

/**
 * 用户实体类
 * @author   xzc
 * @since    2018/03/19
 */
public class Consumer {
	private int id;
	private String name;
	private String email;
	private String password;
	private String identifyingCode;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIdentifyingCode() {
		return identifyingCode;
	}
	public void setIdentifyingCode(String identifyingCode) {
		this.identifyingCode = identifyingCode;
	}
	@Override
	public String toString() {
		return "Consumer [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", identifyingCode=" + identifyingCode + "]";
	}
	
}
