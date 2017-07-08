package com.huan.activemq.主题测试.非持久;

import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MapMessage;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 非持久化主题消息的订阅方
 * 
 * @描述
 * @作者 huan
 * @时间 2017年6月18日 - 下午5:07:12
 */
public class NonTopicConsumer {
	public static void main(String[] args) throws Exception {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("topic-01");
		MessageConsumer consumer = session.createConsumer(topic);
		MapMessage message;
		while ((message = (MapMessage) consumer.receive(8000)) != null) {
			System.out.println("ext_prop:" + message.getStringProperty("ext_prop"));
			Enumeration names = message.getMapNames();
			while (names.hasMoreElements()) {
				String key = (String) names.nextElement();
				System.out.println("key:" + key + " value:" + message.getObject(key));
			}
		}
		session.commit();
		session.close();
		connection.close();
	}
}
