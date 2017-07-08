package com.huan.activemq.队列测试_01;

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
public class 队列消息接收方 {
	public static void main(String[] args) throws Exception {
		/** 消息队列的连接工厂,JMS使用连接工厂创建连接 */
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61616");
		/** 从工厂中创建出一个连接 */
		Connection connection = connectionFactory.createConnection();
		/** 启动链接 */
		connection.start();/** 创建一个session,用于发送消息 */
		Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
		/** 消息的目的地;消息发送给谁 */
		Destination destination = session.createQueue("provider-01");
		/** 消息接收者 */
		MessageConsumer consumer = session.createConsumer(destination);
		// 进行消息的接收
		int i = 0;
		while (i < 5) {
			/** 同步消费，次方法会阻塞 */
			TextMessage message = (TextMessage) consumer.receive(111);
			session.commit();// 需要commit不然会接收到多次
			System.out.println("接收到消息:" + message.getText() + ",额外的属性:" + message.getStringProperty("username"));
			i++;
		}
		session.close();
		connection.close();
	}
}
