package com.huan.activemq.延时或定时消息;

import java.util.concurrent.TimeUnit;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 测试延时和定时消息<br />
 * 需要在xml文件中配置schedulerSupport=true
 * 
 * @描述
 * @作者 huan
 * @时间 2017年7月8日 - 下午11:42:21
 */
public class 测试延时定时消息 {
	public static void main(String[] args) throws InterruptedException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:application-delay-cron.xml");
		TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
	}
}
