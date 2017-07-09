package com.huan.activemq.延时或定时消息;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.jms.TextMessage;

import org.apache.activemq.ScheduledMessage;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jms.core.JmsTemplate;

/**
 * 延时消息投递生产者
 * 
 * @描述
 * @作者 huan
 * @时间 2017年7月8日 - 下午11:10:31
 */
public class DelayMessageProducer implements InitializingBean, DisposableBean {

	private ScheduledExecutorService executorService;

	private JmsTemplate jmsTemplate;

	@Override
	public void destroy() throws Exception {
		executorService.shutdown();
		executorService.awaitTermination(3, TimeUnit.SECONDS);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.scheduleAtFixedRate(() -> {
			jmsTemplate.send(session -> {
				String msg = this.getClass().getName() + " 发送一条消息" + new Random().nextInt(100000);
				System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + msg + " ");
				TextMessage textMessage = session.createTextMessage(msg);
				// 延时投递消息的时间（延时5秒）
				textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 5000);
				// 重复投递的时间间隔
				textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD, 1000);
				// 重复投递的次数
				textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, 2);
				return textMessage;
			});
		}, 0, 2, TimeUnit.SECONDS);
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

}
