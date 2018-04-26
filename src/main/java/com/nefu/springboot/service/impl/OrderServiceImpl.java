package com.nefu.springboot.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nefu.springboot.dao.OrderDao;
import com.nefu.springboot.mq.MessageProducer;
import com.nefu.springboot.service.ConsumerService;
import com.nefu.springboot.service.OrderService;
import com.nefu.springboot.vo.Account;
import com.nefu.springboot.vo.Credit;
import com.nefu.springboot.vo.Order;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	private static final String destinationName = "email.queue.inform";

	private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	OrderDao orderDao;

	@Autowired
	ConsumerService consumerService;

	@Autowired
	MessageProducer producer;

	@Override
	public void takeOrder(Order order) {

		// 1.将订单信息存入数据库中
		order.setOrder_time(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		order.setIs_payment("N");
		orderDao.saveOrder(order);
	}

	@Override
	public Map<String, List<Map<String, Object>>> getOrder(int conusmer_id) {
		List<Map<String, Object>> orderList = orderDao.getOrderByConsumerId(conusmer_id);
		List<Map<String, Object>> unUsed = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> unpaid = new ArrayList<Map<String, Object>>();
		// List<Map<String, Object>> outOfDate = new ArrayList<Map<String,
		// Object>>();
		Map<String, List<Map<String, Object>>> orderData = new HashMap<String, List<Map<String, Object>>>();
		try {
			for (Map<String, Object> order : orderList) {
				if ("N".equals(MapUtils.getString(order, "is_payment"))) {
					String order_time = MapUtils.getString(order, "order_time");
					long time = new Date().getTime() - DateUtils.parseDate(order_time, "yyyy-MM-dd HH:mm:ss").getTime();
					if (time > 30 * 60 * 1000) {
						order.put("is_payment", "支付超时");
						// outOfDate.add(order);
					} else {
						unpaid.add(order);
					}
				} else {
					Date leaveDate = DateUtils.parseDate(MapUtils.getString(order, "leaveDate"), "yyyy-MM-dd");
					if (new Date().before(leaveDate)) {
						unUsed.add(order);
					}
				}
			}
		} catch (Exception e) {
			log.info("日期转换异常：", e);
		}

		orderData.put("orderList", orderList);
		orderData.put("unUsed", unUsed);
		orderData.put("unpaid", unpaid);
		// orderData.put("outOfDate", outOfDate);
		return orderData;
	}

	@Override
	public Boolean goPay(String consumer_id, String payAmount, String payType, String order_id, String hotel_id) {

		// 1.获得当前的账户余额
		Account account = consumerService.getAccountBalance(Integer.valueOf(consumer_id));
		float balance = account.getBalance();

		// 2.判断当前支付方式所需的金额
		float needAmount = 0;
		if ("balance".equals(payType)) {
			// 支付方式为余额支付时，付全款
			needAmount = Integer.valueOf(payAmount);
			if (balance < needAmount) {
				return false;
			}
		} else if ("credit".equals(payType)) {
			Credit credit = consumerService.getCredit(Integer.valueOf(consumer_id));
			int creditIndex = credit.getCreditIndex();
			// 3.当支付方式为信用支付时，60<=信用指数<70时，付订单金额一半;信用指数>=70,无需付款
			if (creditIndex < 60) {
				return false;
			} else if (creditIndex >= 60 && creditIndex < 70) {
				needAmount = Integer.valueOf(payAmount) / 2f;
			} else if (creditIndex >= 70) {
				needAmount = 0;
			}
		}

		// 4.更新账户余额和添加账单信息
		account.setBalance(balance - needAmount);
		account.setChange(-needAmount);
		consumerService.updateAccountandBill(account);

		// 5.将订单状态更新为已支付
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("is_payment", "Y");
		parms.put("order_id", order_id);
		orderDao.updateIsPay(parms);

		// 6通知用户订单付款成功，等待商家接单
		Map<String, Object> consumer = orderDao.getConsumerByConsumerID(Integer.valueOf(consumer_id));
		String consumer_email = MapUtils.getString(consumer, "email");
		String conusmer_name = MapUtils.getString(consumer, "name");
		String msg = 0 + "," + consumer_email + "," + conusmer_name;
		producer.sendMsg(destinationName, msg);

		
		Map<String, Object> business = orderDao.getConsumerByHotelId(Integer.valueOf(hotel_id));
		String business_email = MapUtils.getString(business, "email");
		String business_name = MapUtils.getString(business, "name");
		String msg1 = 1 + "," + business_email + "," + business_name;
		producer.sendMsg(destinationName, msg1);
		return true;
	}

}
