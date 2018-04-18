package com.nefu.springboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nefu.springboot.dao.ConsumerDao;
import com.nefu.springboot.service.ConsumerService;
import com.nefu.springboot.vo.Consumer;

@Service
public class ConsumerServiceImpl implements ConsumerService {

	@Autowired
	ConsumerDao consumerDao;
	
	@Override
	public void addConsumer(Consumer consumer) {
		consumerDao.addConsumer(consumer);
	}

	@Override
	public void updateConsumer(Consumer consumer) {
		consumerDao.updateConsumer(consumer);
	}

	@Override
	public Consumer getConsumerByEmail(String email) {
		return consumerDao.getConsumer(email);
	}

	@Override
	public Boolean isRegister(String email) {
		Consumer consumer = getConsumerByEmail(email);
		if(consumer != null ){
			if (consumer.getPassword() != null) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Boolean isLogin(Consumer consumer) {
		Consumer oldConsumer = consumerDao.getConsumer(consumer.getEmail());
		if (oldConsumer != null) {
			if(oldConsumer.getPassword().equals(consumer.getPassword())){
				return true;
			}
		}
		return false;
	}

}
