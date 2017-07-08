package com.huan.activemq.队列测试_01;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 实现消息的发送
 * 
 * @描述
 * @作者 huan
 * @时间 2017年6月18日 - 上午9:56:47
 */
public class 队列消息发送方 {
	public static void main(String[] args) throws Exception {
		/** 消息队列的连接工厂,JMS使用连接工厂创建连接 */
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61616");
		/** 从工厂中创建出一个连接 */
		Connection connection = connectionFactory.createConnection();
		/** 启动链接 */
		connection.start();
		/** 创建一个session,用于发送消息 */
		Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
		/** 消息的目的地;消息发送给谁 */
		Destination destination = session.createQueue("provider-01");
		/** 消息发送着 */
		MessageProducer producer = session.createProducer(destination);
		for (int i = 0; i < 5; i++) {
			/** 创建出一个消息 */
			TextMessage message = session.createTextMessage("这是一个文本消息." + i);
			message.setStringProperty("username", "fuhuan");
			System.out.println("发送一条消息:" + message.getText());
			/** 发送消息 */
			producer.send(message);
		}
		// 提交session
		session.commit();
		// 关闭session
		session.close();
		// 关闭连接
		connection.close();

		System.out.println("发送成功...");
	}
}
