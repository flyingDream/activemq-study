package com.huan.activemq.内嵌broker;

import org.apache.activemq.broker.BrokerService;

/**
 * 嵌入式的broker
 * 
 * @描述
 * @作者 huan
 * @时间 2017年6月18日 - 下午8:36:02
 */
public class InnerBroker {
	public static void main(String[] args) throws Exception {
		BrokerService brokerService = new BrokerService();
		brokerService.setUseJmx(true);
		brokerService.addConnector("tcp://localhost:61616");
		brokerService.start();
	}
}
