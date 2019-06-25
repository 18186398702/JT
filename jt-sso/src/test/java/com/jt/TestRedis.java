package com.jt;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import redis.clients.jedis.JedisCluster;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedis {

	@Autowired
	private JedisCluster jedisCluster;
	
	@Test
	public void test() {
		System.out.println(jedisCluster.get("26eaf25a1303e81aaab667393d4293f3"));
	}
}
