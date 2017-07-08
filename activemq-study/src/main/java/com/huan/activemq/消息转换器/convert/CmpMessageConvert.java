package com.huan.activemq.消息转换器.convert;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

/**
 * cmp消息转换器
 * 
 * @描述
 * @作者 huan
 * @时间 2017年6月24日 - 下午6:35:19
 */
public class CmpMessageConvert implements MessageConverter {

	@Override
	public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
		System.out.println(object);
		return session.createObjectMessage((Serializable) object);

	}

	@Override
	public Object fromMessage(Message message) throws JMSException, MessageConversionException {
		ObjectMessage objectMessage = (ObjectMessage) message;
		return objectMessage.getObject();
	}

}
