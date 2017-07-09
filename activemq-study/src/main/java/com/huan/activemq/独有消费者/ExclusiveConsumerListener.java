package com.huan.activemq.独有消费者;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.listener.SessionAwareMessageListener;

/**
 * 测试独有消费者
 * 
 * @描述
 * @作者 huan
 * @时间 2017年7月9日 - 下午3:22:23
 */
public class ExclusiveConsumerListener implements SessionAwareMessageListener<TextMessage> {
	@Override
	public void onMessage(TextMessage message, Session session) throws JMSException {
		String msg = message.getText();
		if (msg.startsWith("2")) {
			try {
				System.out.println(Thread.currentThread().getName() + " 开始沉睡5秒.");
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread().getName() + " : " + this.getClass().getName() + " :开始消费消息:" + msg);
	}
}
