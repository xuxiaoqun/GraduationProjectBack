package com.nefu.springboot.vo;

/**
 * 用户支付订单的实体类
 * @author   xzc
 * @since
 */
public class PayOrder {
	//下单人
	private int consumer_id;

	private String arrivalDate;
	
	private int hotel_id;
	
	private String houseType;
	
	private String leaveDate;

	//酒店名称
	private String name;

	private String  occupant_email;

	private String occupant_name;
	
	private int order_id;
	
	private String  order_status;

	private int order_total;
	
	private float payAmount;

	private String payType;

	//下单的房间数
	private int produce_amount;

	private int produce_id;
	
	private String status;

	public int getConsumer_id() {
		return consumer_id;
	}

	public void setConsumer_id(int consumer_id) {
		this.consumer_id = consumer_id;
	}

	public String getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
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

	public String getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOccupant_email() {
		return occupant_email;
	}

	public void setOccupant_email(String occupant_email) {
		this.occupant_email = occupant_email;
	}

	public String getOccupant_name() {
		return occupant_name;
	}

	public void setOccupant_name(String occupant_name) {
		this.occupant_name = occupant_name;
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public String getOrder_status() {
		return order_status;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	public int getOrder_total() {
		return order_total;
	}

	public void setOrder_total(int order_total) {
		this.order_total = order_total;
	}

	public float getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(float payAmount) {
		this.payAmount = payAmount;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public int getProduce_amount() {
		return produce_amount;
	}

	public void setProduce_amount(int produce_amount) {
		this.produce_amount = produce_amount;
	}

	public int getProduce_id() {
		return produce_id;
	}

	public void setProduce_id(int produce_id) {
		this.produce_id = produce_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "PayOrder [consumer_id=" + consumer_id + ", arrivalDate=" + arrivalDate + ", hotel_id=" + hotel_id
				+ ", houseType=" + houseType + ", leaveDate=" + leaveDate + ", name=" + name + ", occupant_email="
				+ occupant_email + ", occupant_name=" + occupant_name + ", order_id=" + order_id + ", order_status="
				+ order_status + ", order_total=" + order_total + ", payAmount=" + payAmount + ", payType=" + payType
				+ ", produce_amount=" + produce_amount + ", produce_id=" + produce_id + ", status=" + status + "]";
	}

	
}
