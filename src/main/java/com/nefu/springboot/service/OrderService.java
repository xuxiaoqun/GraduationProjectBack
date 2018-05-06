package com.nefu.springboot.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.nefu.springboot.vo.Order;
import com.nefu.springboot.vo.PayOrder;

/**
 * 用户下订单的相关业务
 */
public interface OrderService {
	
	/**
	 * 存储订单信息
	 * @param order
	 */
	public void takeOrder(Order order);
	
	/**
	 * 根据订单支付状态获取订单信息
	 * @param is_payment
	 * @return
	 */
	public Map<String, List<Map<String, Object>>> getOrder(int conusmer_id);
	
	/**
	 * 
	 * @param consumer_id 用户id
	 * @param payAmount 支付金额
	 * @param payType  支付方式
	 * @param order_id 订单id
	 * @param hotel_id 酒店id
	 * @return payStatus 0:余额不够；1:未开通信用；2:信用指数不够；3:支付成功
	 */
	public Map<String, Object> goPay(String consumer_id, String payAmount, String payType, String order_id, String hotel_id);
	
	/**
	 * 根据商家的id，找到在他家酒店下的订单
	 * @param consumer_id
	 * @return
	 */
	public Map<String, Object> getPayment(String consumer_id);

	/**
	 * 查看订单是否确认成功
	 * @param order
	 * @return
	 */
	public Map<String, Object> isSureOrder(PayOrder payOrder) throws ParseException;
	
	/**
	 * 更新订单表和支付表的状态
	 * @param payOrder
	 */
	public void updateStatus(int order_id, String status, String order_status);
	
	/**
	 * 获取当前用户的所有已完成的订单
	 * @param consumer_id
	 * @return
	 */
	public Map<String, Object> getUsedOrder(String consumer_id);
	
	/**
	 * 保存对应订单的评价
	 * @param parms
	 */
	public void saveEvaluation(int order_id, int hotel_id, String msg, Float grade);	
	
	/**
	 * 根据订单id完成退款工作（只要待入住的订单才能申请退款操作）
	 * @param order_id
	 */
	public void applyapplyRefund(int order_id) throws Exception;
}
