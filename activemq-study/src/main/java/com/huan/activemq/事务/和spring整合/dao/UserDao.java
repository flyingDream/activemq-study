package com.huan.activemq.事务.和spring整合.dao;

import com.huan.activemq.事务.和spring整合.entity.User;

/**
 * 用户dao
 * 
 * @描述
 * @作者 huan
 * @时间 2017年7月1日 - 下午8:55:51
 */
public interface UserDao {

	int insertUser(User user);

	int deleteUser(String id);
}
