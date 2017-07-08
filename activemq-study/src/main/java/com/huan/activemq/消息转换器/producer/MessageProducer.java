package com.huan.activemq.消息转换器.producer;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jms.core.JmsTemplate;

import com.huan.activemq.消息转换器.model.Cmp;
import com.huan.activemq.消息转换器.model.Cmp.Type;

/**
 * 消息生产者
 * 
 * @描述
 * @作者 huan
 * @时间 2017年6月24日 - 下午6:41:13
 */
public class MessageProducer implements InitializingBean, DisposableBean {

	private ScheduledExecutorService executor;

	private JmsTemplate jmsTemplate;

	@Override
	public void destroy() throws Exception {
		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.SECONDS);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		executor = new ScheduledThreadPoolExecutor(1);
		executor.scheduleAtFixedRate(() -> {
			try {
				Cmp cmp = new Cmp("cmp报文", 1, Type.QUEUE);
				jmsTemplate.convertAndSend(cmp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, 0, 2, TimeUnit.SECONDS);
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

}
