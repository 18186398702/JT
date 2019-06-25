package com.jt.manage.service;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.po.ItemCat;
import com.jt.common.service.RedisService;
import com.jt.manage.mapper.ItemCatMapper;
import com.jt.manage.vo.EasyUITree;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ShardedJedis;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private ItemCatMapper itemCatMapper;
	@Autowired
	private JedisCluster jedisCluster ;
	//private RedisService redisService;
	//private ShardedJedis jedis;
	//private Jedis jedis;
	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * 商品分类目录展示
	 */
	@Override
	public List<EasyUITree> finditemCalAll(long parentId) {
		ItemCat itemCat = new ItemCat();
		itemCat.setParentId(parentId);
		List<ItemCat> itemCatList = itemCatMapper.select(itemCat);
		// 将ItemCat集合转成前端EasyUI需要的可展示的集合
		List<EasyUITree> treeList = new ArrayList<>();
		for (ItemCat itemCatTemp : itemCatList) {
			EasyUITree easyUITree = new EasyUITree();
			easyUITree.setId(itemCatTemp.getId());
			easyUITree.setText(itemCatTemp.getName());
			// 是父级 closed 是子级 open
			easyUITree.setState(itemCatTemp.getIsParent() ? "closed" : "open");
			treeList.add(easyUITree);
		}
		return treeList;
	}

	/**
	 * redis缓存展示商品分类 1.用户查询，先查redis 2.如果redis没有数据。则查数据库 3.如果redis有数据，则查询缓存数据
	 * 
	 */
	@SuppressWarnings("unchecked") //压制警告
	@Override
	public List<EasyUITree> findTreeCache(Long parentId) {
		String key = "ITEM_CAT_" + parentId;
		String json = jedisCluster.get(key);
		List<EasyUITree> treeList = new ArrayList<>();
		try {
			if (StringUtils.isEmpty(json)) {
				treeList = finditemCalAll(parentId);
				System.out.println("查询数据库");
				String listJSON = objectMapper.writeValueAsString(treeList);
				jedisCluster.set(key, listJSON);
			}else {
				treeList = objectMapper.readValue(json, treeList.getClass());
				System.out.println("用户查询缓存");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return treeList;
	}

}
