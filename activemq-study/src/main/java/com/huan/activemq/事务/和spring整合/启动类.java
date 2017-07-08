package com.huan.activemq.事务.和spring整合;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.huan.activemq.事务.和spring整合.configuration.ActivemqConfiguration;
import com.huan.activemq.事务.和spring整合.configuration.DatasourceConfiguration;
import com.huan.activemq.事务.和spring整合.service.UserService;

/**
 * 测试activemq的事务管理
 * 
 * @描述
 * @作者 huan
 * @时间 2017年6月24日 - 下午7:26:22
 */
public class 启动类 {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DatasourceConfiguration.class, ActivemqConfiguration.class);
		context.start();
		UserService userService = context.getBean(UserService.class);
		// userService.sendJmpMessageWithDelay();
		userService.sendJmsMessageWithDelayAndTransaction();
	}

	/**
	 * @param context
	 */
	public static void 测试普通的事务是否生效(AnnotationConfigApplicationContext context) {
		UserService userService = context.getBean(UserService.class);
		userService.testTransaction();
	}
}
