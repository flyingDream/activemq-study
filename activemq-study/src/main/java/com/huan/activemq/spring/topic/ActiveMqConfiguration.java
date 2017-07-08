package com.huan.activemq.spring.topic;

import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;

/**
 * ActiveMq的配置
 * 
 * @描述
 * @作者 huan
 * @时间 2017年6月21日 - 下午10:40:53
 */
@Configuration
public class ActiveMqConfiguration {
	/** 配置jms连接工厂 */
	@Bean
	public ConnectionFactory connectionFactory() {
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
		factory.setBrokerURL("tcp://localhost:61616");
		factory.setUseAsyncSend(true); // 不进行异步发送
		// factory.setClientID("factory-client-01"); // 当工厂中存在多个链接时，clientId不可重复
		return factory;
	}

	/** 带缓存效果的连接工厂 */
	@Bean
	public PooledConnectionFactory pooledConnectionFactory() {
		PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
		pooledConnectionFactory.setConnectionFactory(connectionFactory());
		pooledConnectionFactory.setMaxConnections(100);
		return pooledConnectionFactory;
	}

	/** 发布订阅模式的消息的目的地 */
	@Bean
	public Destination topicDestination() {
		return new ActiveMQTopic("spring-topic");
	}

	/** 配置JmsTemplate */
	@Bean
	public JmsTemplate jmsTemplate() {
		JmsTemplate jmsTemplate = new JmsTemplate(pooledConnectionFactory());
		jmsTemplate.setDefaultDestination(topicDestination());
		// 发布订阅模式
		jmsTemplate.setPubSubDomain(true);
		// 接收消息的超时时间
		// jmsTemplate.setReceiveTimeout(10000);
		/** deliveryMode, priority, timeToLive 的开关要生效，必须配置为true，默认false */
		jmsTemplate.setExplicitQosEnabled(true);
		jmsTemplate.setDeliveryMode(DeliveryMode.PERSISTENT);
		return jmsTemplate;
	}

	/** 消息监听器1 */
	@Bean
	public MessageListener consumerListener() {
		return new ConsumerListener();
	}

	/** 消息监听器2 */
	@Bean
	public MessageListener consumerListener2() {
		return new ConsumerListener2();
	}

	/** 生产者 */
	@Bean
	public TopicMsgProducer topicMsgProducer() {
		TopicMsgProducer topicMsgProducer = new TopicMsgProducer();
		topicMsgProducer.setJmsTemplate(jmsTemplate());
		return topicMsgProducer;
	}

	/** 消费者-01 */
	@Bean
	public MessageListenerContainer defaultMessageListenerContainer() {
		DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
		container.setConnectionFactory(pooledConnectionFactory());
		container.setDestination(topicDestination());
		container.setPubSubDomain(true);
		container.setSubscriptionDurable(true);
		// <!---这里是设置接收客户端的ID，在持久化时，但这个客户端不在线时，消息就存在数据库里，直到被这个ID的客户端消费掉-->
		container.setClientId("spring-topic-client-01");
		container.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
		container.setMessageListener(consumerListener());
		return container;
	}

	/** 消费者-02 */
	@Bean
	public MessageListenerContainer defaultMessageListenerContainer2() {
		DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
		container.setConnectionFactory(pooledConnectionFactory());
		container.setDestination(topicDestination());
		container.setPubSubDomain(true);
		container.setSubscriptionDurable(true);
		// <!---这里是设置接收客户端的ID，在持久化时，但这个客户端不在线时，消息就存在数据库里，直到被这个ID的客户端消费掉-->
		container.setClientId("spring-topic-client-02");
		container.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
		container.setMessageListener(consumerListener2());
		return container;
	}

}
