package com.huan.activemq.消息转换器.linstener;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.jms.support.converter.MessageConverter;

import com.huan.activemq.消息转换器.model.Cmp;

/**
 * 消息监听器
 * 
 * @描述
 * @作者 huan
 * @时间 2017年6月24日 - 下午6:32:17
 */
public class CmpSessionAwareListener implements SessionAwareMessageListener<ObjectMessage> {

	/**
	 * 消息转换器
	 */
	private MessageConverter messageConverter;

	@Override
	public void onMessage(ObjectMessage message, Session session) throws JMSException {
		if (null != message) {
			Cmp cmp = (Cmp) messageConverter.fromMessage(message);
			System.out.println("接收到一条消息:" + cmp.toString());
		}

	}

	public void setMessageConverter(MessageConverter messageConverter) {
		this.messageConverter = messageConverter;
	}

}
