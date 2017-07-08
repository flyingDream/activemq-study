package com.huan.activemq.消息转换器;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.huan.activemq.消息转换器.configuration.ActimeMQConfiguration;

/**
 * 测试消息转换器
 * 
 * @描述
 * @作者 huan
 * @时间 2017年6月24日 - 下午6:51:57
 */
public class 启动类 {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ActimeMQConfiguration.class);
		context.start();
	}
}
