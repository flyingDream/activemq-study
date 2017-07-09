package com.huan.activemq.延时或定时消息;

import java.util.concurrent.TimeUnit;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ScheduledMessage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 删除延时消息
 * 
 * @描述
 * @作者 huan
 * @时间 2017年7月9日 - 上午11:56:03
 */
public class 清理无效的延时消息 {
	public static void main(String[] args) throws JMSException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:application-delay-cron.xml");
		long start = System.currentTimeMillis();
		long end = start + TimeUnit.HOURS.toMillis(24);// 删除：当前时间后24小时范围的延时消息
		ConnectionFactory connectionFactory = ctx.getBean(ConnectionFactory.class);
		Connection conn = connectionFactory.createConnection();
		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination management = session.createTopic(ScheduledMessage.AMQ_SCHEDULER_MANAGEMENT_DESTINATION);
		MessageProducer producer = session.createProducer(management);
		Message request = session.createMessage();
		request.setStringProperty(ScheduledMessage.AMQ_SCHEDULER_ACTION, ScheduledMessage.AMQ_SCHEDULER_ACTION_REMOVEALL);
		// 注释掉下方2个属性将会清除所有的延时消息
		request.setStringProperty(ScheduledMessage.AMQ_SCHEDULER_ACTION_START_TIME, Long.toString(start));
		request.setStringProperty(ScheduledMessage.AMQ_SCHEDULER_ACTION_END_TIME, Long.toString(end));
		producer.send(request);
		System.out.println("删除成功..........");
	}
}
