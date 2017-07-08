package com.huan.activemq.主题测试.持久化;

import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MapMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 持久化消息订阅者
 * 
 * <pre>
 *		1、需要在连接上设置clientId，用来识别消费者
 *		2、需要创建 TopicSubscriber 来订阅
 *		3、设置好以上2步后在进行connection的启动
 *		4、一定要先运行一次，等于向消息服务中间件注册这个消费者，然后再运行发布者发送消息，
 *		      这个时候无论消费者是否在线，这个消息消费者都可以消费掉。不在线的话，下次上线即可
 *		      接收到这个消息
 * </pre>
 * 
 * @描述
 * @作者 huan
 * @时间 2017年6月18日 - 下午5:45:56
 */
public class TopicConsumer {
	public static void main(String[] args) throws Exception {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		Connection connection = connectionFactory.createConnection();
		// 设置消费者编号，用于识别消费者
		connection.setClientID("client-02");
		Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("topic-02");
		// 创建持久化订阅者
		TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "name-01");
		connection.start();
		MapMessage message;
		while ((message = (MapMessage) topicSubscriber.receive(8000)) != null) {
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
