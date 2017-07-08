package com.huan.activemq.三种消息监听器;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 测试多种消息监听
 * 
 * @描述
 * @作者 huan
 * @时间 2017年6月24日 - 下午5:25:37
 */
public class 启动 {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ActimeMQConfiguration.class);
		context.start();
	}
}
