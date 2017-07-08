package com.huan.activemq.主题测试.非持久;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 非持久主题消息提供方，接收方必须要先启动，否则会发生提供方之前<br/>
 * 发送的数据接收方获取不到
 * 
 * @描述
 * @作者 huan
 * @时间 2017年6月18日 - 下午4:22:01
 */
public class NonTopicProvider {
	public static void main(String[] args) throws Exception {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
		// 创建出一个主题
		Topic topic = session.createTopic("topic-01");
		MessageProducer producer = session.createProducer(topic);
		for (int i = 0; i < 3; i++) {
			MapMessage mapMessage = session.createMapMessage();
			mapMessage.setString("map_key_" + i, "map_value_" + i);
			mapMessage.setStringProperty("ext_prop", "额外的属性_测试非持久会主题消息.");
			producer.send(mapMessage);
		}
		session.commit();
		session.close();
		connection.close();
		System.out.println("消息发送完毕...");
	}
}
