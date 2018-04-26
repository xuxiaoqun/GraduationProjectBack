package com.nefu.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nefu.springboot.service.ConsumerService;
import com.nefu.springboot.service.OrderService;
import com.nefu.springboot.vo.Account;
import com.nefu.springboot.vo.Credit;
import com.nefu.springboot.vo.Order;

@RestController
public class TakeOrderController {
	
	private static final Logger log = LoggerFactory.getLogger(TakeOrderController.class);
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	ConsumerService consumerService;
	
	
	@RequestMapping("/takeOrder")
	public Boolean takeOrder(Order order){
		log.info("订单信息为：" + order);
		orderService.takeOrder(order);
		return true;
	}
	
	@RequestMapping("/getOrder")
	public Map<String, List<Map<String, Object>>> getOrder(String id){
		log.info("订单用户的id：" + id);
		return orderService.getOrder(Integer.valueOf(id));
	}
	
	@RequestMapping("/getCredit")
	public Map<String, Object> getCredit(String id){
		log.info("用户id：" + id);
		Map<String, Object> data = new HashMap<String, Object>();
		Credit credit = consumerService.getCredit(Integer.valueOf(id));
		if (credit == null) {
			data.put("is_credit", "N");
			return data;
		}
		data.put("credit", credit);
		return data;
	}
	
	@RequestMapping("/saveCredit")
	public Boolean saveCredit(Credit credit){
		log.info("信用信息:" + credit);
		consumerService.saveCredit(credit);
		return true;
	}
	
	@RequestMapping("/updateCredit")
	public Boolean updateCredit(Credit credit){
		log.info("更新的信用信息为:" + credit);
		consumerService.updateCredit(credit);
		return true;
	}
	
	@RequestMapping("/getAccount")
	public Map<String, Object> getAccount(int id){
		log.info("用户id：" + id);
		return consumerService.getAccount(id);
	}

	@RequestMapping("/updateAccount")
	public Boolean updateAccount(Account account){
		log.info("更新的账户信息：" + account);
		consumerService.updateAccountandBill(account);
		return true;
	}
	
	@RequestMapping("/goPay")
	public Boolean  balancePay(String consumer_id, String payAmount, String payType ,String order_id, String hotel_id){
		log.info("用户id:" + consumer_id + ", 支付金额：" + payAmount + ",支付方式:" + payType + ",订单id:" + order_id + ",酒店id:" + hotel_id);
		return orderService.goPay(consumer_id, payAmount, payType, order_id, hotel_id);
	}
}
