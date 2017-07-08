package com.huan.activemq.事务.和spring整合.service;

import org.springframework.transaction.annotation.Transactional;

public interface UserService {

	@Transactional
	public void testTransaction();

	/**
	 * 不加事务注解，发送一条jms消息，并在此方法中停留3秒，看监听器试立即收到消息，还是3秒后收到消息
	 */
	public void sendJmpMessageWithDelay();

	/**
	 * 存在延时，并且在spring的事务中
	 */
	@Transactional
	public void sendJmsMessageWithDelayAndTransaction();
}
