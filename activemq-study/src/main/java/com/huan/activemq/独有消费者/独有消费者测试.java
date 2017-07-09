package com.huan.activemq.独有消费者;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

/**
 * 测试独有消费者
 * 
 * @描述
 * @作者 huan
 * @时间 2017年7月9日 - 下午3:26:56
 */
public class 独有消费者测试 {
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:application-exclusive-consumer.xml");
		JmsTemplate jmsTemplate = ctx.getBean(JmsTemplate.class);
		String msg1 = "1-这是第一条消息";
		String msg2 = "2-这是第二条消息";
		String msg3 = "3-这是第三条消息";
		jmsTemplate.convertAndSend(msg1);
		jmsTemplate.convertAndSend(msg2);
		jmsTemplate.convertAndSend(msg3);
	}
}
