package com.nefu.springboot.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nefu.springboot.dao.OrderDao;
import com.nefu.springboot.vo.Order;

@Component
public class MyTask {
	
	private static final Logger log = LoggerFactory.getLogger(MyTask.class);
	
	@Autowired
	OrderDao orderDao;

	/**
	 * 每天凌晨一点，更新订单表中所有数据的状态
	 * @throws Exception 
	 */
//	@Scheduled(cron = "0 0 1 * * ?")
    public void updateOrderStatus() throws Exception{
		log.info("开始更新时间：" + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		
		//所有订单
		List<Order> orderList = orderDao.getAllOrder();	
		
		Map<String, Object> parms = new HashMap<String, Object>();
		for(Order order : orderList){
			if ("N".equals(order.getIs_payment())) {
				parms.put("order_id", order.getOrder_id());
				long time = new Date().getTime() - DateUtils.parseDate(order.getOrder_time(), "yyyy-MM-dd HH:mm:ss").getTime();
				if (time > 30 * 60 * 1000) {
					parms.put("order_status", "支付超时");
				}else{
					parms.put("order_status", "待支付");
				}
				orderDao.updateOrderStatus(parms);
			}else{
				parms.put("order_id", order.getOrder_id());
				Map<String, Object> pay = orderDao.getPaymentByOrderId(order.getOrder_id());
				//商家确认订单，且用户没有申请退款
				if("Y".equals(pay.get("status")) && "N".equals(pay.get("is_refund"))){
					Date leaveDate = DateUtils.parseDate(order.getLeaveDate(), "yyyy-MM-dd");
					if (new Date().before(leaveDate)) {
						parms.put("order_status", "待入住");
					}else{
						parms.put("order_status", "已完成");
					}
					
				}else if ( "Y".equals(pay.get("is_refund"))) {
					parms.put("order_status", "已退款");
				}
			}
		}
		
		log.info("更新结束时间：" + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
      
      
    }
}
