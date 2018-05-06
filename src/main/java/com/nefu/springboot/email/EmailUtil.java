package com.nefu.springboot.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {
	
	private static final Logger log = LoggerFactory.getLogger(EmailUtil.class);

	@Autowired
	private JavaMailSender mailSender = new JavaMailSenderImpl();

	@Value("${spring.mail.username}")
	private String fromEmail;

	/**
	 * 
	 * @function 模板邮件
	 * @param sendEmail 收件人的email
	 * @param subject 邮件的主题
	 * @param text 邮件内容
	 * @throws MessagingException
	 */
	public void sendEmail(String sendEmail, String subject, String text) throws MessagingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setFrom(fromEmail);
		helper.setTo(sendEmail);
		helper.setSubject(subject);
		helper.setText(text, true);
		log.info("收件人的email：" + sendEmail);
		mailSender.send(mimeMessage);
	}
}
