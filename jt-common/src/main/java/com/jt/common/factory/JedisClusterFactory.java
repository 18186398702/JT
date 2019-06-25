package com.jt.common.factory;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Value;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

// spring工厂模式
public class JedisClusterFactory implements FactoryBean<JedisCluster>{
	
	@Value("${redis.nodes}")  //根据配置文件properties动态获取
	private String redisNodes;
	
	public Set<HostAndPort> getNodes(){
		Set<HostAndPort> nodes = new HashSet<>();
		String[] strNodes = redisNodes.split(",");
		//  IP:端口, IP:端口, IP:端口,
		for (String node : strNodes) {
			String[] params = node.split(":");
			String host = params[0];
			int port = Integer.parseInt(params[1]);
			HostAndPort hostAndPort = new HostAndPort(host, port);
			nodes.add(hostAndPort);
		}
		return nodes;
	}
	
	@Override
	public JedisCluster getObject() throws Exception {
		Set<HostAndPort> nodes = getNodes();
		return new JedisCluster(nodes);
	}

	@Override
	public Class<?> getObjectType() {
		
		return JedisCluster.class;
	}

	@Override
	public boolean isSingleton() {
		
		return true;
	}
	
	
}
