package com.nefu.springboot.dao;

import java.util.List;
import java.util.Map;

import com.nefu.springboot.vo.Order;

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
	
}
