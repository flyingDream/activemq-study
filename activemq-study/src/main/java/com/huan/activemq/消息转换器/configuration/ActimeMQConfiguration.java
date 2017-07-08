package com.huan.activemq.消息转换器.configuration;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.support.converter.MessageConverter;

import com.huan.activemq.消息转换器.convert.CmpMessageConvert;
import com.huan.activemq.消息转换器.linstener.CmpSessionAwareListener;
import com.huan.activemq.消息转换器.producer.MessageProducer;

/**
 * ActiveMQ的配置
 * 
 * @描述
 * @作者 huan
 * @时间 2017年6月24日 - 下午6:46:19
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

	/** 消息转换器 */
	public MessageConverter messageConverter() {
		return new CmpMessageConvert();
	}

	/** 消息监听器 */
	@Bean
	public CmpSessionAwareListener cmpSessionAwareListener() {
		CmpSessionAwareListener listener = new CmpSessionAwareListener();
		listener.setMessageConverter(messageConverter());
		return listener;
	}

	/** 消息发送接收模板 */
	@Bean
	public JmsTemplate createJmsTemplate() {
		JmsTemplate jmsTemplate = new JmsTemplate();
		jmsTemplate.setConnectionFactory(pooledConnectionFactory());
		jmsTemplate.setDefaultDestination(messageListenerQueue());
		jmsTemplate.setMessageConverter(messageConverter());
		return jmsTemplate;
	}

	/** 消息监听容器 */
	@Bean
	public DefaultMessageListenerContainer defaultMessageListenerContainer() {
		DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
		container.setConnectionFactory(pooledConnectionFactory());
		container.setDestination(messageListenerQueue());
		container.setMessageListener(cmpSessionAwareListener());
		return container;
	}

}
