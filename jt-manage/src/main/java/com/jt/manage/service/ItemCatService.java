package com.jt.manage.service;

import java.util.List;

import com.jt.manage.vo.EasyUITree;

public interface ItemCatService {

	List<EasyUITree> finditemCalAll(long parentId);

	List<EasyUITree> findTreeCache(Long parentId);

}
