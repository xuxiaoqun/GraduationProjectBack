package com.nefu.springboot.service;

import java.util.List;
import java.util.Map;

import com.nefu.springboot.vo.Order;

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
	 * 支付订单
	 * @param consumer_id 用户id	
	 * @param payAmount 支付金额
	 * @param payType	支付方式
	 */
	public Boolean goPay(String consumer_id, String payAmount, String payType, String order_id, String hotel_id);
	
	

}
