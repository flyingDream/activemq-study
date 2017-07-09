package com.huan.activemq.延时或定时消息;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.jms.TextMessage;

import org.apache.activemq.ScheduledMessage;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jms.core.JmsTemplate;

/**
 * 定时消息投递生产者
 * 
 * @描述
 * @作者 huan
 * @时间 2017年7月8日 - 下午11:35:54
 */
public class CronMessageProducer implements InitializingBean, DisposableBean {

	private ExecutorService executorService;

	private JmsTemplate jmsTemplate;

	@Override
	public void destroy() throws Exception {
		executorService.shutdown();
		executorService.awaitTermination(3, TimeUnit.SECONDS);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		executorService = Executors.newSingleThreadExecutor();
		executorService.execute(() -> {
			jmsTemplate.send(session -> {
				TextMessage textMessage = session.createTextMessage("测试cron消息投递");
				// 分 时 天 月 星期几
				textMessage.setStringProperty(ScheduledMessage.AMQ_SCHEDULED_CRON, "46 10 * * *");
				return textMessage;
			});
		});
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
}
