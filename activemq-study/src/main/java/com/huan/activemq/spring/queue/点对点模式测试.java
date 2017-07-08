package com.huan.activemq.spring.queue;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 点对点模式测试
 * 
 * @描述
 * @作者 huan
 * @时间 2017年6月19日 - 下午11:26:59
 */
public class 点对点模式测试 {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext-jms.xml");
		context.start();
	}
}
