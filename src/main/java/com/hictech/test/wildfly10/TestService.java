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
		String test;
		try {
			test  = "cache manager class: " + cls(manager) + "\n";

			Cache<Object, Object> cache = manager.getCache();
			test += "cache default class: " + cls(cache) + "\n";

			AdvancedCache<Object, Object> advanced_cache = cache.getAdvancedCache();
			test += "tx manager class:   " + advanced_cache.getTransactionManager() + "\n";

			Configuration configuration = cache.getCacheConfiguration();
			test += "cache confifugration:\n" + configuration + "\n";

			return test;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String cls(Object o) {
		return o != null? o.getClass().toString() : "null";
	}

}
