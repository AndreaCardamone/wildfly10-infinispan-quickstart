package com.hictech.test.wildfly10;

import javax.annotation.Resource;
import javax.ejb.Stateless;

import org.infinispan.AdvancedCache;
import org.infinispan.Cache;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.manager.EmbeddedCacheManager;

@Stateless
public class TestService {

	@Resource(lookup = "java:jboss/infinispan/container/server")
	private EmbeddedCacheManager manager;

	public String test() {
		return testManager();
	}

	private String testManager() {
		try {
			String test = "test 1: " + cls(manager) + "\n";

			Cache<Object, Object> cache = manager.getCache();
			test += "test 3: " + cls(cache) + "\n";

			Configuration configuration = cache.getCacheConfiguration();
			test += "test 3: " + configuration + "\n";

			AdvancedCache<Object, Object> advanced_cache = cache.getAdvancedCache();
			test += "test 4: " + advanced_cache.getTransactionManager() + "\n";

			return test;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String cls(Object o) {
		return o != null ? o.getClass().toString() : "null";
	}

}
