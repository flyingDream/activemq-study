package com.huan.activemq.三种消息监听器;

import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jms.core.JmsTemplate;

/**
 * 消息发送者
 */
public class MessageProducer implements InitializingBean, DisposableBean {

	private JmsTemplate jmsTemplate;

	private ScheduledExecutorService executor;

	@Override
	public void destroy() throws Exception {
		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.SECONDS);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		executor = new ScheduledThreadPoolExecutor(1);
		executor.scheduleAtFixedRate(() -> {
			jmsTemplate.send(session -> {
				System.out.println("发送消息..");
				return session.createTextMessage(this.getClass().getName() + "发送消息:" + new Random().nextInt(1000));
			});
		}, 0, 2, TimeUnit.SECONDS);
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
}
