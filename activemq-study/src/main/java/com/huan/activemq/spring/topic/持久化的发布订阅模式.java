package com.huan.activemq.spring.topic;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 持久化模式下的发布订阅模式
 * 
 * @描述
 * @作者 huan
 * @时间 2017年6月22日 - 下午10:46:44
 */
public class 持久化的发布订阅模式 {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ActiveMqConfiguration.class);
		context.start();
	}
}
