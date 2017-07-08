package com.huan.activemq.事务.和spring整合.linstener;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.listener.SessionAwareMessageListener;

/**
 * 此类中的方法和UserService类中的方法一一对应
 * 
 * @描述
 * @作者 huan
 * @时间 2017年7月1日 - 下午10:21:02
 */
public class TransactionListener implements SessionAwareMessageListener<TextMessage> {

	@Override
	public void onMessage(TextMessage message, Session session) throws JMSException {
		sendJmpMessageWithDelay(message);
	}

	/**
	 * @param message
	 * @throws JMSException
	 */
	public void sendJmpMessageWithDelay(TextMessage message) throws JMSException {
		System.out.println("接收到一条消息:" + message.getText());
	}
}
