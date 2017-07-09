package com.huan.activemq.延时或定时消息;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.listener.SessionAwareMessageListener;

/**
 * 消息消费者
 * 
 * @描述
 * @作者 huan
 * @时间 2017年7月8日 - 下午11:40:09
 */
public class CustomerMessageProducerListener implements SessionAwareMessageListener<TextMessage> {

	@Override
	public void onMessage(TextMessage message, Session session) throws JMSException {
		String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-hh HH:mm:ss"));
		System.out.println(time + " :接收  " + this.getClass().getName() + " 接收到一条消息:" + message.getText());
	}

}
