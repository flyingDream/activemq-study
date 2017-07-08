package com.huan.activemq.三种消息监听器;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * JMS规范中原始的消息监听器
 * 
 * @描述
 * @作者 huan
 * @时间 2017年6月24日 - 下午3:21:42
 */
public class CustomerMessageListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		TextMessage txtMessage = (TextMessage) message;
		try {
			System.out.println(this.getClass().getName() + "接收到消息:" + txtMessage.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
