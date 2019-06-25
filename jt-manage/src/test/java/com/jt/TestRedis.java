package com.jt;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.Transaction;

public class TestRedis {

	// 1.操作String类型
	@Test
	public void testString() {
		Jedis jedis = new Jedis("178.10.253.149", 6379);
		jedis.set("a", "A");
		System.out.println(jedis.get("a"));
	}

	// 2 操作list类型
	@Test
	public void testList() {
		Jedis jedis = new Jedis("178.10.253.149", 6379);
		jedis.lpush("list", "1", "2", "3");
		for (int i = 0; i < 3; i++) {
			String value = jedis.lpop("list");
			System.out.println(value);
		}
	}

	// redis事务控制
	@Test
	public void testTx() {
		Jedis jedis = new Jedis("178.10.253.149", 6379);
		// 开启事务
		Transaction transaction = jedis.multi();
		try {
			transaction.set("bb", "bb");
			// 事务提交
			transaction.exec();
		} catch (Exception e) {
			e.printStackTrace();
			// 事务回滚
			transaction.discard();
			System.out.println("回滚");
		}
	}

	// redis分片操作
	@Test
	public void testShards() {
		String host = "178.10.253.149";
		List<JedisShardInfo> shards = new ArrayList<>();
		shards.add(new JedisShardInfo(host, 6379));
		shards.add(new JedisShardInfo(host, 6380));
		shards.add(new JedisShardInfo(host, 6381));
		ShardedJedis shardedJedis = new ShardedJedis(shards);
		shardedJedis.set("a", "AA");
		System.out.println("获取数据"+shardedJedis.get("a"));
	}
	
	// 实现哨兵的操作
	/**
	 * masterName : 获取主机的变量名称
	 */
	@Test
	public void testSentinel() {
		Set<String> sentinels = new HashSet<>();
		//sentinels.add(new HostAndPort(jost,port).toString()); 跟下面一样
		sentinels.add("178.10.253.149:26379");
		JedisSentinelPool pool = new JedisSentinelPool("mymaster", sentinels);
		//获取redis连接
		Jedis jedis = pool.getResource();
		jedis.set("aa", "redis哨兵配置");
		System.out.println(jedis.get("aa"));
		jedis.close();//关闭链接
	}
	
	//实现redis集群操作
	@Test
	public void testCluster() {
		String host = "178.10.253.149";
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort(host, 7000));
		nodes.add(new HostAndPort(host, 7001));
		nodes.add(new HostAndPort(host, 7002));
		nodes.add(new HostAndPort(host, 7003));
		nodes.add(new HostAndPort(host, 7004));
		nodes.add(new HostAndPort(host, 7005));
		nodes.add(new HostAndPort(host, 7006));
		nodes.add(new HostAndPort(host, 7007));
		nodes.add(new HostAndPort(host, 7008));
		JedisCluster cluster = new JedisCluster(nodes);
		cluster.set("cluster", "redis集群");
		System.out.println(cluster.get("cluster"));
	}
	
	
	
	
}
