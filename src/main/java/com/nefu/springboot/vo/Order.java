package com.nefu.springboot.vo;

/**
 * 订单实体类
 * @author   xzc
 * @since
 */
public class Order {
	private int order_id;
	private int hotel_id;
	private int consumer_id;
	private String occupant_name;
	private String occupant_email;
	private int produce_id;
	private String arrivalDate;
	private String leaveDate;
	private String order_time;
	private String is_payment;
	private int order_total;
	private int produce_amount;
	private String order_status;
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	
	public int getHotel_id() {
		return hotel_id;
	}
	public void setHotel_id(int hotel_id) {
		this.hotel_id = hotel_id;
	}
	public int getConsumer_id() {
		return consumer_id;
	}
	public void setConsumer_id(int consumer_id) {
		this.consumer_id = consumer_id;
	}
	public String getOccupant_name() {
		return occupant_name;
	}
	public void setOccupant_name(String occupant_name) {
		this.occupant_name = occupant_name;
	}
	public String getOccupant_email() {
		return occupant_email;
	}
	public void setOccupant_email(String occupant_email) {
		this.occupant_email = occupant_email;
	}
	public int getProduce_id() {
		return produce_id;
	}
	public void setProduce_id(int produce_id) {
		this.produce_id = produce_id;
	}
	public String getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public String getLeaveDate() {
		return leaveDate;
	}
	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}
	public String getOrder_time() {
		return order_time;
	}
	public void setOrder_time(String order_time) {
		this.order_time = order_time;
	}
	public String getIs_payment() {
		return is_payment;
	}
	public void setIs_payment(String is_payment) {
		this.is_payment = is_payment;
	}
	public int getOrder_total() {
		return order_total;
	}
	public void setOrder_total(int order_total) {
		this.order_total = order_total;
	}
	public int getProduce_amount() {
		return produce_amount;
	}
	public void setProduce_amount(int produce_amount) {
		this.produce_amount = produce_amount;
	}
	
	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	@Override
	public String toString() {
		return "Order [order_id=" + order_id + ", hotel_id=" + hotel_id + ", consumer_id=" + consumer_id
				+ ", occupant_name=" + occupant_name + ", occupant_email=" + occupant_email + ", produce_id="
				+ produce_id + ", arrivalDate=" + arrivalDate + ", leaveDate=" + leaveDate + ", order_time="
				+ order_time + ", is_payment=" + is_payment + ", order_total=" + order_total + ", produce_amount="
				+ produce_amount + ", order_status=" + order_status + "]";
	}
	
}
