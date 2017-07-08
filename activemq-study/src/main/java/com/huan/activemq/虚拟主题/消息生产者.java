package com.huan.activemq.虚拟主题;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jms.core.JmsTemplate;

/**
 * 产生持久化主题消息
 * 
 * @描述
 * @作者 huan
 * @时间 2017年7月8日 - 下午2:11:33
 */
public class 消息生产者 implements InitializingBean, DisposableBean {

	private ScheduledExecutorService executorService;

	private JmsTemplate jmsTemplate;

	@Override
	public void destroy() throws Exception {
		executorService.shutdown();
		executorService.awaitTermination(1, TimeUnit.SECONDS);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.scheduleAtFixedRate(() -> {
			String msg = "持久化的主题发送消息:" + String.valueOf(new Random().nextInt(10000000));
			System.out.println(this.getClass().getName() + "发送消息:" + msg);
			jmsTemplate.convertAndSend(msg);
		}, 0, 3, TimeUnit.SECONDS);
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
}
