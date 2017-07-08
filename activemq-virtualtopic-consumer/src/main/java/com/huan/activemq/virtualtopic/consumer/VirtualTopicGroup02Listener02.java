package com.huan.activemq.virtualtopic.consumer;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.listener.SessionAwareMessageListener;

/**
 * 虚拟主题第二组监听器02（模拟在节点127.0.0.3上启动）
 * 
 * @描述
 * @作者 huan
 * @时间 2017年7月8日 - 下午2:33:56
 */
public class VirtualTopicGroup02Listener02 implements SessionAwareMessageListener<TextMessage> {

	@Override
	public void onMessage(TextMessage message, Session session) throws JMSException {
		System.out.println(this.getClass().getName() + "接收到消息:" + message.getText());
	}

}
