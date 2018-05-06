package com.nefu.springboot.dao;

import java.util.List;
import java.util.Map;

import com.nefu.springboot.vo.Order;
import com.nefu.springboot.vo.PayOrder;

/**
 * 对订单的相关操作
 * @author   xzc
 * @since
 */
public interface OrderDao {
	
	public void saveOrder(Order order);
	
	public Map<String, Object> getConsumerByHotelId(int hotel_id);
	
	public Map<String, Object> getConsumerByConsumerID(int consumer_id);
	
	public List<Map<String, Object>> getOrderByConsumerId(int consumer_id);
	
	/**
	 * 更新订单的付款状态
	 * @param is_payment
	 */
	public void updateIsPay(Map<String, Object> parms);
	
	/**
	 * 更新订单的状态
	 * @param order_id
	 */
	public void updateOrderStatus(Map<String, Object> parms);
	
	/**
	 * 保存支付成功的订单到支付表
	 * @param parms
	 */
	public void savePayment(Map<String, Object> parms);
	
	/**
	 * 获取支付表根据商家的id
	 */
	public List<Map<String, Object>> getPayment(int consumer_id);
	
	/**
	 * 获取所有订单
	 * @return
	 */
	public List<Order> getAllOrder();
	
	/**
	 * 获取订单id获取支付成功的payment
	 * @return 
	 */
	public Map<String, Object> getPaymentByOrderId(int order_id);
	
	/**
	 * 根据订单id查找评价信息
	 * @param order_id
	 * @return
	 */
	public Map<String, Object> getevaluationByOrderId(int order_id);
	/**
	 * 获取当前房型的剩余房间数
	 * @param payOrder
	 * @return
	 */
	public Map<String, Object> getRestAmount(PayOrder payOrder);
	
	/**
	 * 更新支付表中的订单状态
	 */
	public void updatePaystatus(Map<String, Object> parms);
	
	/**
	 * 更新当前房型的指定日期的剩余房间数
	 * @param parms
	 */
	public void updateProduceTime(Map<String, Object> parms);
	
	/**
	 * 更新当前房型的指定日期的剩余房间数
	 * @param parms
	 */
	public void updateProduceTimeAdd(Map<String, Object> parms);
	
	/**
	 * 获取当前用户的所有未评价的订单
	 * @param consumer_id
	 * @return
	 */
	public List<Map<String, Object>> getNoEvaluation(int consumer_id);
	
	/**
	 * 获取当前用户的所有已评价的订单
	 * @param consumer_id
	 * @return
	 */
	public List<Map<String, Object>> getEvaluation(int consumer_id);
	
	/**
	 * 保存对应订单的评价
	 * @param parms
	 */
	public void saveEvaluation(Map<String, Object> parms);
	
	/**
	 * 根据订单id完成退款工作（只要待入住的订单才能申请退款操作）
	 * @param order_id
	 */
	public Map<String, Object> getRefundInfo(int order_id);	
	
	/**
	 * 每次用户评价完，相应的更新酒店评分
	 * @param hotel_id
	 */
	public void updateHotelGrade(int hotel_id);
}
