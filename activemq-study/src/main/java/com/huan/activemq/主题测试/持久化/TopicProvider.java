package com.huan.activemq.主题测试.持久化;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 持久化的消息发布者
 * 
 * @描述
 * @作者 huan
 * @时间 2017年6月18日 - 下午5:45:38
 */
public class TopicProvider {
	public static void main(String[] args) throws Exception {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		Connection connection = connectionFactory.createConnection();
		Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
		// 创建出一个主题
		Topic topic = session.createTopic("topic-02");
		MessageProducer producer = session.createProducer(topic);
		// 设置持久化模式
		producer.setDeliveryMode(DeliveryMode.PERSISTENT);
		// 先设置持久化模型，在启动连接
		connection.start();

		for (int i = 0; i < 3; i++) {
			MapMessage mapMessage = session.createMapMessage();
			mapMessage.setString("map_key_" + i, "持久化的消息" + i);
			/**
			 * <pre>
			 * param1:需要发送的消息
			 * param2:消息存在的模式，持久or非持久
			 * param3:消息的优先级，默认是4
			 * param4:消息的过期时间，0表示永不过期
			 * </pre>
			 */
			producer.send(mapMessage, DeliveryMode.PERSISTENT, 4, 0);
			// producer.send(mapMessage);
		}
		session.commit();
		session.close();
		connection.close();
		System.out.println("消息发送完毕...");
	}
}
