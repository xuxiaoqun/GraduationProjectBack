package com.nefu.springboot.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nefu.springboot.mq.MessageProducer;
import com.nefu.springboot.service.ConsumerService;
import com.nefu.springboot.vo.Consumer;

@Controller
public class FirstWebController {
	
	private static final String destinationName = "email.queue";

	
	@Autowired
	MessageProducer producer;
	
	@Autowired
	ConsumerService consumerService;


	@RequestMapping("/sendEmail")
	@ResponseBody
	public Boolean sendEmail(String email) throws Exception {	
		//判断当前邮箱是否注册过
		if(consumerService.isRegister(email)){
			return false;
		}
		//将消息放进邮箱队列里
		producer.sendMsg(destinationName, email);
		return true;
	}

	@RequestMapping("/register")
	@ResponseBody
	public Boolean register(Consumer consumer){
		//判断当前验证码是否正确
		if(consumerService.getConsumerByEmail(consumer.getEmail()).getIdentifyingCode()
				.equals(consumer.getIdentifyingCode())){
			//将用户名设置为邮箱
			consumer.setName(consumer.getName());
			consumerService.updateConsumer(consumer);
			return true;
		}
		return false;
	};
	
	@RequestMapping("/login")
	@ResponseBody
	public Consumer login(Consumer consumer,HttpServletRequest request){
		if (consumerService.isLogin(consumer)) {
			request.getSession().setAttribute("consumer", consumer);
			return consumerService.getConsumerByEmail(consumer.getEmail());
		}
		return null;
	};
	
	@RequestMapping("/updateConsumer")
	@ResponseBody
	public Boolean updateConsumer(Consumer consumer){
		consumerService.updateConsumer(consumer);
		return true;
	}
	
}
