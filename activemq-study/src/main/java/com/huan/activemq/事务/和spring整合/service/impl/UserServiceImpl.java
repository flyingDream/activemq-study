package com.huan.activemq.事务.和spring整合.service.impl;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.huan.activemq.事务.和spring整合.dao.UserDao;
import com.huan.activemq.事务.和spring整合.entity.User;
import com.huan.activemq.事务.和spring整合.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private JmsTemplate jmsTemplate;

	@Override
	public void testTransaction() {
		User user = new User();
		user.setAge("20");
		user.setName("测试");
		user.setId(String.valueOf(new Random().nextInt(100000)));
		userDao.insertUser(user);
		int i = 1 / 0;
		System.out.println(i);
		userDao.deleteUser(user.getId());
	}

	@Override
	public void sendJmpMessageWithDelay() {
		jmsTemplate.send(session -> {
			return session.createTextMessage("这是一个消息.");
		});
		System.out.println("线程休息3秒...");
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendJmsMessageWithDelayAndTransaction() {
		jmsTemplate.send(session -> {
			return session.createTextMessage("这是一个消息.");
		});
		System.out.println("线程休息3秒...");
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
