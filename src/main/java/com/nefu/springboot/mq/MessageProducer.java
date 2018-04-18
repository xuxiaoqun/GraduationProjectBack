package com.nefu.springboot.mq;

import javax.jms.Destination;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * 消息生产者（Active mq）
 * @author   xzc
 * @since
 */
@Service
public class MessageProducer {
	
	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;
	
	/**
	 * 发送消息到指定的目标队列
	 * @param destinationName
	 * @param message
	 */
	public void sendMsg(String destinationName, String message) {
        Destination destination = new ActiveMQQueue(destinationName);
        jmsMessagingTemplate.convertAndSend(destination, message);
    }
}
