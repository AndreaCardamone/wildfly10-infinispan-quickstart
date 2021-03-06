package com.hictech.test.wildfly10;

import static org.infinispan.configuration.cache.CacheMode.LOCAL;
import static org.infinispan.configuration.cache.CacheMode.REPL_SYNC;
import static org.infinispan.transaction.TransactionMode.TRANSACTIONAL;
import static org.infinispan.util.concurrent.IsolationLevel.SERIALIZABLE;

import javax.annotation.Resource;
import javax.ejb.Stateless;

import org.infinispan.AdvancedCache;
import org.infinispan.Cache;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.remoting.transport.Transport;
import org.infinispan.transaction.LockingMode;
import org.infinispan.transaction.lookup.GenericTransactionManagerLookup;

@Stateless
public class TestService {

	@Resource(lookup = "java:jboss/infinispan/container/server")
	private EmbeddedCacheManager manager;

	public String test() {
		String test;
		try {
			manager.defineConfiguration("default", conf());
			
			test  = "cache manager class: " + cls(manager) + "\n";

			Cache<Object, Object> cache = manager.getCache();
			test += "cache default class: " + cls(cache) + "\n";

			AdvancedCache<Object, Object> advanced_cache = cache.getAdvancedCache();
			test += "tx manager class:    " + advanced_cache.getTransactionManager() + "\n";

			return test;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private Configuration conf() {
		Transport transport = manager.getGlobalComponentRegistry().getGlobalConfiguration().transport().transport();
		
		return new ConfigurationBuilder()
			.clustering()
				.cacheMode(transport == null? LOCAL : REPL_SYNC)
				
			.transaction()
				.transactionMode(TRANSACTIONAL)
				.transactionManagerLookup(new GenericTransactionManagerLookup())
				.autoCommit(false)
			
			.lockingMode(LockingMode.PESSIMISTIC)
				.locking()
				.isolationLevel(SERIALIZABLE)
				
			.persistence()
				.passivation(false)
				.addSingleFileStore()
				.purgeOnStartup(true)
		.build();
	}
	
	private String cls(Object o) {
		return o != null? o.getClass().toString() : "null";
	}

}
