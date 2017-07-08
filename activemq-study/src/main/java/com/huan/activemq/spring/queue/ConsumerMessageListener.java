package com.huan.activemq.spring.queue;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 消息消费者-不推荐使用这个，推荐使用spring封装后的
 * 
 * @描述
 * @作者 huan
 * @时间 2017年6月19日 - 下午11:20:37
 */
public class ConsumerMessageListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		TextMessage txtMessage = (TextMessage) message;
		try {
			System.out.println("消费者收到一条消息:" + txtMessage.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
