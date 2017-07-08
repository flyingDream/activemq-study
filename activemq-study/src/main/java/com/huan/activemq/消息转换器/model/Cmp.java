package com.huan.activemq.消息转换器.model;

import java.io.Serializable;

/**
 * 实体类
 * 
 * @描述
 * @作者 huan
 * @时间 2017年6月24日 - 下午6:27:27
 */
public class Cmp implements Serializable {

	private static final long serialVersionUID = -4168759801391917604L;
	private String name;
	private int version;
	private Type type;

	public Cmp(String name, int version, Type type) {
		this.name = name;
		this.version = version;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public static enum Type {
		TOPIC, QUEUE
	}

	@Override
	public String toString() {
		return "Cmp [name=" + name + ", version=" + version + ", type=" + type + "]";
	}

}
