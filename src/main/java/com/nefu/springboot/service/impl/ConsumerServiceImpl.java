package com.nefu.springboot.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nefu.springboot.dao.ConsumerDao;
import com.nefu.springboot.service.ConsumerService;
import com.nefu.springboot.vo.Account;
import com.nefu.springboot.vo.Bill;
import com.nefu.springboot.vo.Consumer;
import com.nefu.springboot.vo.Credit;

@Service
public class ConsumerServiceImpl implements ConsumerService {

	@Autowired
	ConsumerDao consumerDao;
	
	@Override
	public void addConsumer(Consumer consumer) {
		//将对应的信息写入数据库
		consumerDao.addConsumer(consumer);
		
		//默认注册时添加个人账户，金额100
		Account account = new Account();
		account.setBalance(100);
		account.setConsumer_id(consumer.getId());
		consumerDao.saveAccount(account);
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

	@Override
	public Credit getCredit(int id) {
		return consumerDao.getCredit(id);
	}

	@Override
	public void saveCredit(Credit credit) {
		credit.setCreditIndex(60);
		consumerDao.saveCredit(credit);
	}

	@Override
	public void updateCredit(Credit credit) {
		consumerDao.updateCredit(credit);
	}

	@Override
	public Map<String, Object> getAccount(int consumer_id) {
		
		Map<String, Object> data = new HashMap<String, Object>();
		
		Account account = consumerDao.getAccount(consumer_id);
		//所有账单信息
		List<Bill> allBill = consumerDao.getBill(account.getAccount_id());
		//充值记录
		List<Bill> rechargeBill = new ArrayList<Bill>();
		//消费记录
		List<Bill> consumeBill = new ArrayList<Bill>();
		
		for(Bill bill : allBill){
			if (bill.getRecharge() != null) {
				rechargeBill.add(bill);
			}else if(bill.getConsume() != null){
				consumeBill.add(bill);
			}
		}
		data.put("account", account);
		data.put("rechargeBill", rechargeBill);
		data.put("consumeBill", consumeBill);
		return data;
	}

	@Override
	public void updateAccountandBill(Account account) {
		
		//更新账户信息
		consumerDao.updateAccount(account);
		
		//添加账单信息
		Bill bill = new Bill();
		bill.setAccount_id(account.getAccount_id());
		String time = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		if (account.getChange() > 0) {
			bill.setRecharge(time + ",充值金额：" + account.getChange());
		}else{
			bill.setConsume(time + ",消费金额：" + account.getChange());
		}
		consumerDao.saveBill(bill);
		
	}

	@Override
	public Account getAccountBalance(int consumer_id) {
		return consumerDao.getAccount(consumer_id);
	}

	

}
