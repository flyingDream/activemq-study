package com.huan.activemq.事务.和spring整合.configuration;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.transaction.PlatformTransactionManager;

import com.huan.activemq.事务.和spring整合.linstener.TransactionListener;

/**
 * ActiveMq的配置
 * 
 * @描述
 * @作者 huan
 * @时间 2017年7月1日 - 下午7:47:18
 */
@Configuration
public class ActivemqConfiguration {

	@Bean
	public ConnectionFactory connectionFactory() {
		return new ActiveMQConnectionFactory("tcp://localhost:61616");
	}

	@Bean
	public PooledConnectionFactory pooledConnectionFactory() {
		PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
		pooledConnectionFactory.setConnectionFactory(connectionFactory());
		pooledConnectionFactory.setMaxConnections(30);
		return pooledConnectionFactory;
	}

	@Bean
	public Destination messageListenerQueue() {
		return new ActiveMQQueue("message-listener-queue");
	}

	/** 消息监听器 */
	@Bean
	public TransactionListener transactionListener() {
		return new TransactionListener();
	}

	/** 消息发送接收模板 */
	@Bean
	public JmsTemplate createJmsTemplate() {
		JmsTemplate jmsTemplate = new JmsTemplate();
		jmsTemplate.setConnectionFactory(pooledConnectionFactory());
		jmsTemplate.setDefaultDestination(messageListenerQueue());
		return jmsTemplate;
	}

	/** 消息监听容器 */
	@Bean
	public DefaultMessageListenerContainer defaultMessageListenerContainer(PlatformTransactionManager transactionManager) {
		DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
		container.setConnectionFactory(pooledConnectionFactory());
		container.setDestination(messageListenerQueue());
		container.setMessageListener(transactionListener());
		container.setTransactionManager(transactionManager);
		return container;
	}
}
