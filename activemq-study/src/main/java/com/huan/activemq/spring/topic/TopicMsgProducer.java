package com.huan.activemq.spring.topic;

import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jms.core.JmsTemplate;

/**
 * 发布订阅模式消息生产者
 * 
 * @描述
 * @作者 huan
 * @时间 2017年6月22日 - 下午10:38:15
 */
public class TopicMsgProducer implements InitializingBean, DisposableBean {
	private ScheduledExecutorService executor;

	private JmsTemplate jmsTemplate;

	public void sendMsg() {
		executor.scheduleAtFixedRate(() -> {
			System.out.println("ok...");
			jmsTemplate.send(session -> {
				return session.createTextMessage("发布订阅模式发送的数据:" + new Random().nextInt(10000));
			});
		}, 0, 3, TimeUnit.SECONDS);
	}

	@Override
	public void destroy() throws Exception {
		executor.shutdown();
		executor.awaitTermination(3, TimeUnit.SECONDS);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		executor = new ScheduledThreadPoolExecutor(1);
		this.sendMsg();
	}

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

}
