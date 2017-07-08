package com.huan.activemq.spring.topic;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 消息消费者
 * 
 * @描述
 * @作者 huan
 * @时间 2017年6月22日 - 下午10:37:26
 */
public class ConsumerListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		TextMessage txt = (TextMessage) message;
		try {
			System.out.println(this.getClass().getName() + "收到一条消息:" + txt.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
