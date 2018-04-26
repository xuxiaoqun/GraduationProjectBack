package com.nefu.springboot.service.impl;

import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.nefu.springboot.service.EmailService;

import freemarker.template.Template;

@Service
public class EmailServiceImpl implements EmailService {
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	private static ThreadLocal<Integer> local = new ThreadLocal<Integer>();
	

	public static ThreadLocal<Integer> getLocal() {
		return local;
	}

	public static void setLocal(ThreadLocal<Integer> local) {
		EmailServiceImpl.local = local;
	}

	@Override
	public String emailValidate(Map<String, Object> model) throws Exception {
		//生成四位的随机数
		Integer identifyingCode = new Random().nextInt(10000)%(10000-1000+1) + 1000;
		local.set(identifyingCode);
		System.err.println(identifyingCode);
		model.put("identifyingCode", identifyingCode.toString());
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate("emailValidate.ftl", "utf-8");
		String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
		return text;
	}

	@Override
	public String emailInformConsumer(Map<String, Object> model) throws Exception {
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate("informConsumer.ftl", "utf-8");
		String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
		return text;
	}

	@Override
	public String emailInformBusiness(Map<String, Object> model) throws Exception {
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate("informBusiness.ftl", "utf-8");
		String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
		return text;
	}

}
