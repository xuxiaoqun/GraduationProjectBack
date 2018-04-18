package com.nefu.springboot.mq;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.nefu.springboot.email.EmailUtil;
import com.nefu.springboot.service.ConsumerService;
import com.nefu.springboot.service.impl.EmailServiceImpl;
import com.nefu.springboot.vo.Consumer;

/**
 * 消息消费者（Active mq）
 * 
 * @author xzc
 * @since
 */
@Service
public class MessageConsumer {
	
	@Autowired
	EmailUtil emailUtil;
	
	@Autowired
	EmailServiceImpl emailServiceImpl;
	
	@Autowired
	ConsumerService consumerService;

	/**
	 * 监听邮箱队列收到的消息
	 * @param text
	 */
	@JmsListener(destination = "email.queue")
	public void receiveMsg(String email) {
		System.out.println("<<<<<<============ 邮箱是： " + email);
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("email", email);
			String emailText = emailServiceImpl.emailValidate(model);
			emailUtil.sendEmail(email, "邮箱验证", emailText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//将验证码以及对于的邮箱写入数据库
		int identifyingCode = emailServiceImpl.getLocal().get();
		Consumer consumer = new Consumer();
		consumer.setEmail(email);
		consumer.setIdentifyingCode(identifyingCode+"");
		
		//如果该邮箱不存在，则增加该用户信息
		if(consumerService.getConsumerByEmail(email) == null){
			consumerService.addConsumer(consumer);
		}else{
			//如果该邮箱存在，则更新该用户信息
			consumerService.updateConsumer(consumer);
		}
		
		
	}

}
