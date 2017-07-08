package com.huan.activemq.组合队列.方式一;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 实现消息的接收方
 * 
 * @描述
 * @作者 huan
 * @时间 2017年6月18日 - 上午10:23:15
 */
public class Consumer02 {
	public static void main(String[] args) throws Exception {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();/** 创建一个session,用于发送消息 */
		Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue("spring-queue");
		MessageConsumer consumer = session.createConsumer(destination);
		int i = 0;
		while (i < 5) {
			TextMessage message = (TextMessage) consumer.receive(111);
			session.commit();// 需要commit不然会接收到多次
			System.out.println("接收到消息:" + message.getText() + ",额外的属性:" + message.getStringProperty("username"));
			i++;
		}
		session.close();
		connection.close();
	}
}
