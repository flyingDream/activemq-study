package com.huan.activemq.spring.queue;

import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * 点对点模式的队列消息生产者
 * 
 * @描述
 * @作者 huan
 * @时间 2017年6月19日 - 下午11:09:28
 */
@Component("queueMsgProducer")
public class QueueMsgProducer implements InitializingBean, DisposableBean {

	private ScheduledExecutorService executor;

	@Autowired
	private JmsTemplate jmsTemplate;

	public void sendMsg() {
		executor.scheduleAtFixedRate(() -> {
			jmsTemplate.send(session -> {
				return session.createTextMessage("队列模式发送的数据:" + new Random().nextInt(10000));
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

}
