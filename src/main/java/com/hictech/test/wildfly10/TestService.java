/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hictech.test.wildfly10;

import javax.annotation.Resource;
import javax.ejb.Stateless;

import org.infinispan.AdvancedCache;
import org.infinispan.Cache;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.manager.EmbeddedCacheManager;


@Stateless
public class TestService {
		
	@Resource(lookup="java:jboss/infinispan/container/server")
	private EmbeddedCacheManager manager; 

    public String test() {
        return testManager();
    }
    
    private String testManager() {
    	try {
    		String test = "test 1: " + cls(manager) + "\n";
    		
    		Cache<Object,Object> cache = manager.getCache();
    		test += "test 3: " + cls(cache) + "\n";

    		Configuration configuration = cache.getCacheConfiguration();
    		test += "test 3: " + configuration + "\n";
    		
    		AdvancedCache<Object, Object> advanced_cache = cache.getAdvancedCache();
			test += "test 4: " + advanced_cache.getTransactionManager() + "\n";
    	
    		return test;
    	}
    	catch( Exception e ) {
    		throw new RuntimeException(e);
    	}
	}
    
    private String cls(Object o) {
		return o != null? o.getClass().toString() : "null";
    }

}
