package com.huan.activemq.组合队列.方式一;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 消息生产者
 * 
 * @描述
 * @作者 huan
 * @时间 2017年7月2日 - 下午6:06:12
 */
public class Provider {
	public static void main(String[] args) throws Exception {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue("provider-01,spring-queue");
		MessageProducer producer = session.createProducer(destination);
		for (int i = 0; i < 5; i++) {
			TextMessage message = session.createTextMessage("这是一个文本消息." + i);
			message.setStringProperty("username", "fuhuan");
			System.out.println("发送一条消息:" + message.getText());
			producer.send(message);
		}
		session.commit();
		session.close();
		connection.close();

		System.out.println("发送成功...");
	}
}
