package com.nefu.springboot.mq;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static final Logger log = LoggerFactory.getLogger(MessageConsumer.class);
	
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
			log.info("发送消息异常:",e);
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
	
	@JmsListener(destination = "email.queue.inform")
	public void receiveMsgInform(String msg){
		log.info("通知邮件接收到的信息：" + msg);
		String flag = msg.split(",")[0];
		String email = msg.split(",")[1];
		String name = msg.split(",")[2];
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("email", email);
			model.put("name", name);
			String emailText = "";
			if(flag.equals("0")){
				emailText = emailServiceImpl.emailInformConsumer(model);
			}else{
				emailText = emailServiceImpl.emailInformBusiness(model);
			}
			
			emailUtil.sendEmail(email, "邮箱通知", emailText);
		} catch (Exception e) {
			log.info("发送消息异常:",e);
		}
	}

}
