package com.nefu.springboot.service.impl;

import java.text.ParseException;
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

import com.nefu.springboot.dao.ConsumerDao;
import com.nefu.springboot.dao.OrderDao;
import com.nefu.springboot.mq.MessageProducer;
import com.nefu.springboot.service.ConsumerService;
import com.nefu.springboot.service.OrderService;
import com.nefu.springboot.vo.Account;
import com.nefu.springboot.vo.Bill;
import com.nefu.springboot.vo.Credit;
import com.nefu.springboot.vo.Order;
import com.nefu.springboot.vo.PayOrder;

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
	ConsumerDao consumerDao;

	@Autowired
	MessageProducer producer;

	@Override
	public void takeOrder(Order order) {

		// 1.将订单信息存入数据库中
		order.setOrder_time(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		order.setIs_payment("N");
		order.setOrder_status("待支付");
		orderDao.saveOrder(order);
	}

	@Override
	public Map<String, List<Map<String, Object>>> getOrder(int conusmer_id) {
		List<Map<String, Object>> orderList = orderDao.getOrderByConsumerId(conusmer_id);
		List<Map<String, Object>> unUsed = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> unpaid = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> refund = new ArrayList<Map<String, Object>>();
		// List<Map<String, Object>> outOfDate = new ArrayList<Map<String,
		// Object>>();
		Map<String, List<Map<String, Object>>> orderData = new HashMap<String, List<Map<String, Object>>>();
		
		Map<String, Object> parms = new HashMap<String, Object>();
		try {
			for (Map<String, Object> order : orderList) {
				if ("N".equals(MapUtils.getString(order, "is_payment"))) {
					if ("待支付".equals(MapUtils.getString(order, "order_status"))) {
						String order_time = MapUtils.getString(order, "order_time");
						long time = new Date().getTime() - DateUtils.parseDate(order_time, "yyyy-MM-dd HH:mm:ss").getTime();
						if (time > 30 * 60 * 1000) {
							order.put("order_status", "支付超时");
							parms.put("order_id", order.get("order_id"));
							parms.put("order_status", "支付超时");
							orderDao.updateOrderStatus(parms);
							// outOfDate.add(order);
						} else {
							unpaid.add(order);
						}
					}
					
				} else {
					Date leaveDate = DateUtils.parseDate(MapUtils.getString(order, "leaveDate"), "yyyy-MM-dd");
					if (MapUtils.getString(order, "order_status").equals("待入住")) {
						if (new Date().before(leaveDate)) {
							unUsed.add(order);
						}else{
							order.put("order_status", "已消费");
							parms.put("order_id", order.get("order_id"));
							parms.put("order_status", "已消费");
							orderDao.updateOrderStatus(parms);
						}
					}
					else if ("已退款".equals(MapUtils.getString(order, "order_status"))) {
						refund.add(order);
					}
				}
			}
		} catch (Exception e) {
			log.info("日期转换异常：", e);
		}

		orderData.put("orderList", orderList);
		orderData.put("unUsed", unUsed);
		orderData.put("unpaid", unpaid);
		orderData.put("refund", refund);
		// orderData.put("outOfDate", outOfDate);
		return orderData;
	}

	@Override
	public Map<String, Object> goPay(String consumer_id, String payAmount, String payType, String order_id, String hotel_id) {

		//payStatus 0:余额不够；1:未开通信用；2:信用指数不够支付；3:支付成功
		Map<String, Object> status = new HashMap<String, Object>();
		
		// 1.获得当前的账户余额
		Account account = consumerService.getAccountBalance(Integer.valueOf(consumer_id));
		float balance = account.getBalance();

		// 2.判断当前支付方式所需的金额
		float needAmount = 0;
		if ("balance".equals(payType)) {
			// 支付方式为余额支付时，付全款
			needAmount = Integer.valueOf(payAmount);
			if (balance < needAmount) {
				status.put("payStatus", "0");
			}else {
				status.put("payStatus", "3");
			}
		} else if ("credit".equals(payType)) {
			Credit credit = consumerService.getCredit(Integer.valueOf(consumer_id));
			if (credit == null) {
				status.put("payStatus", "1");
			}else{
				int creditIndex = credit.getCreditIndex();
				// 3.当支付方式为信用支付时，60<=信用指数<70时，付订单金额一半;信用指数>=70,无需付款
				if (creditIndex < 60) {
					status.put("payStatus", "2");
				} else if (creditIndex >= 60 && creditIndex < 70) {
					needAmount = Integer.valueOf(payAmount) / 2f;
					if (balance < needAmount) {
						status.put("payStatus", "0");
					}else {
						status.put("payStatus", "3");
					}
				} else if (creditIndex >= 70) {
					needAmount = 0;
					status.put("payStatus", "3");
				}
			}
			
		}
		
		//支付成功走以下逻辑，否则直接返回
		if ("3".equals(status.get("payStatus"))) {
			// 4.更新账户余额和添加账单信息
			account.setBalance(balance - needAmount);
			account.setChange(-needAmount);
			consumerService.updateAccountandBill(account);

			// 5.将订单is_payment更新为已支付,order_status更新为待确认
			Map<String, Object> parms = new HashMap<String, Object>();
			parms.put("is_payment", "Y");
			parms.put("order_id", order_id);
			parms.put("order_status", "待确认");
			orderDao.updateIsPay(parms);
			orderDao.updateOrderStatus(parms);
			
			//6.将支付订单写入支付表
			Map<String, Object> payParms = new HashMap<String, Object>();
			payParms.put("order_id", order_id);
			payParms.put("payType", payType);
			//实际支付金额
			payParms.put("payAmount", needAmount);
			payParms.put("payType", payType);
			payParms.put("status", "N");
			orderDao.savePayment(payParms);
			
			//7.通知用户订单付款成功，等待商家接单
			sendEmail(Integer.valueOf(consumer_id), 0, 1);

			//8.通知商家接单
			Map<String, Object> business = orderDao.getConsumerByHotelId(Integer.valueOf(hotel_id));
			sendEmail(MapUtils.getIntValue(business, "id"), 1, 4);
		}

		return status;
	}

	@Override
	public Map<String, Object> getPayment(String consumer_id) {
		Map<String, Object> data = new HashMap<String,Object>();
		//所有的支付表里成功支付订单
		List<Map<String, Object>> orders = orderDao.getPayment(Integer.valueOf(consumer_id));
		//商家未确认的订单
		List<Map<String, Object>> noConfirmOrders = new ArrayList<Map<String, Object>>();
		//商家已确认的订单
		List<Map<String, Object>> confirmOrders = new ArrayList<Map<String, Object>>();
		//今日入住订单
		List<Map<String, Object>> todayOrders = new ArrayList<Map<String, Object>>();
		//商家拒绝的订单
		List<Map<String, Object>> refuseOrders = new ArrayList<Map<String, Object>>();
		
		for (Map<String, Object> order : orders) {
			if("N".equals(order.get("status"))){
				noConfirmOrders.add(order);
			}else if("Y".equals(order.get("status"))){
				confirmOrders.add(order);
				if (MapUtils.getString(order, "arrivalDate").equals
						(DateFormatUtils.format(new Date(), "yyyy-MM-dd"))) {
					todayOrders.add(order);
				}
			}else{
				refuseOrders.add(order);
			}
		}
		
		data.put("noConfirmOrders", noConfirmOrders);
		data.put("confirmOrders", confirmOrders);
		data.put("refuseOrders", refuseOrders);
		data.put("todayOrders", todayOrders);
		return data;
	}

	@Override
	public Map<String, Object> isSureOrder(PayOrder payOrder) throws ParseException {
		
		//status状态 1：拒绝成功 ，2：房间数不够，自动拒绝订单 ，3：确认成功
		Map<String, Object> data = new HashMap<String, Object>();
		
		if ("拒绝".equals(payOrder.getOrder_status())) {
			refund(payOrder.getConsumer_id(), payOrder.getPayAmount(), payOrder.getOrder_id(), "R", 2);
			data.put("status", "1");
		}else {
			Date arrivalDate = DateUtils.parseDate(payOrder.getArrivalDate(), "yyyy-MM-dd");
			Date leaveDate = DateUtils.parseDate(payOrder.getLeaveDate(), "yyyy-MM-dd");
			int days = (int) ((leaveDate.getTime() - arrivalDate.getTime()) / (1000*3600*24));
			int produce_amount = payOrder.getProduce_amount();
			for (int i = 0; i < days; i++) {
				payOrder.setArrivalDate(DateFormatUtils.format(DateUtils.addDays(arrivalDate, i), "yyyy-MM-dd"));
				payOrder.setLeaveDate(DateFormatUtils.format(DateUtils.addDays(arrivalDate, i + 1), "yyyy-MM-dd"));
				int amount = MapUtils.getIntValue(orderDao.getRestAmount(payOrder), "restAmount", 0);
				if (produce_amount > amount) {
					//通知用户订单已被拒绝,并完成退款工作，改变订单状态
					data.put("status", "2");
					refund(payOrder.getConsumer_id(), payOrder.getPayAmount(), payOrder.getOrder_id(), "R", 2);
					return data;
				}
			}
			//通知用户商家已确认订单，按时入住,并更新房型剩余房间数
			data.put("status", "3");
			for (int i = 0; i < days; i++) {
				Map<String, Object> parms = new HashMap<String, Object>();
				payOrder.setArrivalDate(DateFormatUtils.format(DateUtils.addDays(arrivalDate, i), "yyyy-MM-dd"));
				payOrder.setLeaveDate(DateFormatUtils.format(DateUtils.addDays(arrivalDate, i + 1), "yyyy-MM-dd"));
				int amount = MapUtils.getIntValue(orderDao.getRestAmount(payOrder), "restAmount", 0);
				parms.put("arrivalDate", payOrder.getArrivalDate());
				parms.put("leaveDate", payOrder.getLeaveDate());
				parms.put("produce_id", payOrder.getProduce_id());
				parms.put("restAmount", amount - produce_amount);
				log.info("更新房型日期表的参数：" + parms);
				orderDao.updateProduceTime(parms);
			}
			
			//改变支付表中的订单状态和订单表中的状态
			updateStatus(payOrder.getOrder_id(), "Y", "待入住");
//			Map<String, Object> parms = new HashMap<String, Object>();
//			parms.put("status", "Y");
//			parms.put("order_id", payOrder.getOrder_id());
//			parms.put("order_status", "待入住");
//			
//			orderDao.updateOrderStatus(parms);
//			orderDao.updatePaystatus(parms);
			
			sendEmail(payOrder.getConsumer_id(), 0, 3);
		}
		
		return data;
	}
	
	/**
	 * 根据支付表完成退款工作
	 * @param order_id
	 */
	public void refund(int consumer_id, float payAmount, int order_id, String status, int flag){
		//当前下单人的账户
		Account account = consumerService.getAccountBalance(consumer_id);
		//更新账户
		account.setBalance(account.getBalance() + payAmount);
		consumerDao.updateAccount(account);
		
		//添加账单信息
		Bill bill = new Bill();
		bill.setAccount_id(account.getAccount_id());
		String time = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		bill.setRecharge(time + ",退款金额：" + payAmount);
		
		consumerDao.saveBill(bill);
		
		//改变支付表中的订单状态和订单表中的状态
		updateStatus(order_id, status, "已退款");
//		Map<String, Object> parms = new HashMap<String, Object>();
//		parms.put("status", status);
//		parms.put("order_id", payOrder.getOrder_id());
//		parms.put("order_status", "已退款");
//		if ("R".equals(status)) {
//			parms.put("reason_refund", "商家拒绝");
//		}else{
//			parms.put("reason_refund", "主动退款");
//		}
//		orderDao.updateOrderStatus(parms);
//		orderDao.updatePaystatus(parms);
		
		//通知下单人商家拒绝订单
		sendEmail(consumer_id, 0, flag);
	}
	
	/**
	 * 
	 * @param consumer_id
	 * @param flag1  数字 0表示给用户发，1表示给商家发 
	 * @param flag2  数字0表示通知用户主动退款成功 ,1表示通知用户订单提交成功,2表示通知用户商家拒绝订单 ,3表示通知用户商家接单 ,4表示通知商家确认订单 ,5表示通知商家用户退款
	 */
	public void sendEmail(int consumer_id, int flag1, int flag2){
		Map<String, Object> consumer = orderDao.getConsumerByConsumerID(Integer.valueOf(consumer_id));
		String consumer_email = MapUtils.getString(consumer, "email");
		String conusmer_name = MapUtils.getString(consumer, "name");
		//msg标识  ；
		//
		String msg = flag1 + "," + consumer_email + "," + conusmer_name + "," + flag2;
		producer.sendMsg(destinationName, msg);
	}

	@Override
	public void updateStatus(int order_id, String status, String order_status) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("status", status);
		parms.put("order_id", order_id);
		parms.put("order_status", order_status);
		if ("R".equals(status)) {
			parms.put("reason_refund", "商家拒绝");
		}
		orderDao.updateOrderStatus(parms);
		orderDao.updatePaystatus(parms);
		
	}

	@Override
	public Map<String, Object> getUsedOrder(String consumer_id) {
		Map<String, Object> data = new HashMap<String, Object>();
		List<Map<String, Object>> noEvaluation = orderDao.getNoEvaluation(Integer.valueOf(consumer_id));
		List<Map<String, Object>> evaluation = orderDao.getEvaluation(Integer.valueOf(consumer_id));
		data.put("noEvaluation", noEvaluation);
		data.put("evaluation", evaluation);
		return data;
	}

	@Override
	public void saveEvaluation(int order_id, int hotel_id, String msg, Float grade) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("order_id", order_id);
		parms.put("msg", msg);
		parms.put("grade", grade);
		parms.put("order_status", "已评价");
		parms.put("eva_date", DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
		//保存评价
		orderDao.saveEvaluation(parms);
		//更改订单 表中的评价
		orderDao.updateOrderStatus(parms);
		//更改酒店中的评分
		orderDao.updateHotelGrade(hotel_id);
	}

	@Override
	public void applyapplyRefund(int order_id) throws Exception {
		// 1.根据订单id获得退款信息
		Map<String, Object> data = orderDao.getRefundInfo(order_id);
		// 2.完成退款和账单记录
		refund(MapUtils.getIntValue(data, "consumer_id"), MapUtils.getIntValue(data, "payAmount"), order_id, "Y", 0);
		// 3.完成相应房型的库存增加
		Date arrivalDate = DateUtils.parseDate(MapUtils.getString(data, "arrivalDate"), "yyyy-MM-dd");
		Date leaveDate = DateUtils.parseDate(MapUtils.getString(data, "leaveDate"), "yyyy-MM-dd");
		int days = (int) ((leaveDate.getTime() - arrivalDate.getTime()) / (1000*3600*24));
		int produce_amount = MapUtils.getIntValue(data, "produce_amount");
		int produce_id = MapUtils.getIntValue(data, "produce_id");
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("produce_id", produce_id);
		parms.put("restAmount", produce_amount);
		for (int i = 0; i < days; i++) {
			parms.put("arrivalDate", DateFormatUtils.format(DateUtils.addDays(arrivalDate, i), "yyyy-MM-dd"));
			parms.put("leaveDate", DateFormatUtils.format(DateUtils.addDays(arrivalDate, i + 1), "yyyy-MM-dd"));
			orderDao.updateProduceTimeAdd(parms);
		}
		//通知商家被退款
		Map<String, Object> business = orderDao.getConsumerByHotelId(MapUtils.getIntValue(data, "hotel_id"));
		sendEmail(MapUtils.getIntValue(business, "id"), 1, 5);
		
	}

}
