package com.huan.activemq.三种消息监听器;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.listener.SessionAwareMessageListener;

/**
 * Spring为我们提供的消息监听接口<br/>
 * 可以通过第二个参数，进行接收到一个消息之后的答复
 * 
 * @描述
 * @作者 huan
 * @时间 2017年6月24日 - 下午3:36:48
 */
public class ConsumerSessionAwareMessageListener implements SessionAwareMessageListener<TextMessage> {

	/** 获取消息后需要答复的地址 */
	private Destination destination;

	@Override
	public void onMessage(TextMessage message, Session session) throws JMSException {
		System.out.println(this.getClass().getName() + "接收到一条消息:" + message.getText());
		MessageProducer producer = session.createProducer(destination);
		TextMessage txtMessage = session.createTextMessage(this.getClass().getName() + " 返回的信息");
		producer.send(txtMessage);
	}

	public Destination getDestination() {
		return destination;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}

}
