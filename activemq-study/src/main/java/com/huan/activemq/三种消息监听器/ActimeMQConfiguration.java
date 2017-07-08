package com.huan.activemq.三种消息监听器;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.SessionAwareMessageListener;

/**
 * ActiveMQ的配置
 * 
 * @描述
 * @作者 huan
 * @时间 2017年6月24日 - 下午3:40:43
 */
@Configuration
public class ActimeMQConfiguration {

	/** 配置jms连接工厂 */
	@Bean
	public ConnectionFactory connectionFactory() {
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
		factory.setBrokerURL("tcp://localhost:61616");
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

	/** 消息发送的目的地 */
	@Bean
	public Destination messageListenerQueue() {
		return new ActiveMQQueue("message-listener-queue");
	}

	/** 消息生产者 */
	@Bean
	public MessageProducer MessageProducer() {
		MessageProducer producer = new MessageProducer();
		producer.setJmsTemplate(createJmsTemplate());
		return producer;
	}

	/** 消息发送的目的地 */
	@Bean
	public Destination springSessionAwareMessageListenerQueue() {
		return new ActiveMQQueue("spring-session-aware-listener-queue");
	}

	/** 消息监听器 */
	@Bean
	public MessageListener customerMessageListener() {
		return new CustomerMessageListener();
	}

	/** 消息监听器 */
	public SessionAwareMessageListener<TextMessage> sprigSessionAwareMessageListener() {
		ConsumerSessionAwareMessageListener messageListener = new ConsumerSessionAwareMessageListener();
		messageListener.setDestination(messageListenerQueue());
		return messageListener;
	}

	/** 消息发送接收模板 */
	@Bean
	public JmsTemplate createJmsTemplate() {
		JmsTemplate jmsTemplate = new JmsTemplate();
		jmsTemplate.setConnectionFactory(pooledConnectionFactory());
		jmsTemplate.setDefaultDestination(springSessionAwareMessageListenerQueue());
		return jmsTemplate;
	}

	/** 消息监听容器 */
	@Bean
	public DefaultMessageListenerContainer defaultMessageListenerContainer() {
		DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
		container.setConnectionFactory(pooledConnectionFactory());
		container.setDestination(messageListenerQueue());
		container.setMessageListener(customerMessageListener());
		return container;
	}

	/** 消息监听容器 */
	@Bean
	public DefaultMessageListenerContainer defaultMessageListenerContainer01() {
		DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
		container.setConnectionFactory(pooledConnectionFactory());
		container.setDestination(springSessionAwareMessageListenerQueue());
		container.setMessageListener(sprigSessionAwareMessageListener());
		return container;
	}
}
