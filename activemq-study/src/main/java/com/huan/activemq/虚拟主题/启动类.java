package com.huan.activemq.虚拟主题;

import java.util.concurrent.TimeUnit;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 测试虚拟主题
 * 
 * @描述
 * @作者 huan
 * @时间 2017年7月8日 - 下午2:16:47
 */
public class 启动类 {
	public static void main(String[] args) throws InterruptedException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:application-virtualtopic-provider.xml");
		TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
	}
}
