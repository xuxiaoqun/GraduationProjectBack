package com.nefu.springboot.dao;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

@CacheConfig(cacheNames = "baseCache")
public interface UserDao {

	@Cacheable
	public List<Map<String, Object>> get();
	
	public void insert(Map<String, Object> parm);
}
