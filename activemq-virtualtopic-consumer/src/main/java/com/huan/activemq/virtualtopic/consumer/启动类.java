package com.huan.activemq.virtualtopic.consumer;

import java.util.concurrent.TimeUnit;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 测试需主题的监听，负载均衡测试
 * 
 * @描述
 * @作者 huan
 * @时间 2017年7月8日 - 下午2:35:18
 */
public class 启动类 {
	public static void main(String[] args) throws InterruptedException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:application-virtualtopic-consumer.xml");
		TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
	}
}
