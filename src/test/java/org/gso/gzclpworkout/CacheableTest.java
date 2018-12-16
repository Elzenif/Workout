package org.gso.gzclpworkout;

import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CacheableTest.CacheConfigurations.class)
public class CacheableTest {

    public static class Customer {

        final private String id;
        final private String name;

        public Customer(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

    }

    private final static AtomicInteger cacheableCalled = new AtomicInteger(0);
    private final static AtomicInteger cachePutCalled = new AtomicInteger(0);
    private final static AtomicInteger cacheableCalled2 = new AtomicInteger(0);
    private final static AtomicInteger cachePutCalled2 = new AtomicInteger(0);
    private final static Map<String, Customer> customers = new HashMap<>();

    public static class CustomerCachedService {

        @Cacheable("CustomerCache")
        public Customer cacheable(String v) {
            cacheableCalled.incrementAndGet();
            return new Customer(v, "Cacheable " + v);
        }

        @CachePut("CustomerCache")
        public Customer cachePut(String b) {
            cachePutCalled.incrementAndGet();
            return new Customer(b, "Cache put " + b);
        }

        @NonNull
        @Cacheable("allCustomers")
        public Collection<Customer> findAll() {
            cacheableCalled2.incrementAndGet();
            return customers.values();
        }

        @NotNull
        @CachePut("allCustomers")
        public <S extends Customer> S save(@NotNull S s) {
            cachePutCalled2.incrementAndGet();
            customers.put(s.getId(), s);
            return s;
        }


    }

    @Configuration
    @EnableCaching()
    public static class CacheConfigurations {

        @Bean
        public CustomerCachedService customerCachedService() {
            return new CustomerCachedService();
        }

        @Bean
        public CacheManager cacheManager() {
            return new CaffeineCacheManager("CustomerCache", "allCustomers");
        }

    }

    @Autowired
    public CustomerCachedService cachedService;

    @Test
    public void testCacheable() {
        for(int i = 0; i < 1000; i++) {
            cachedService.cacheable("A");
        }
        Assert.assertEquals(cacheableCalled.get(), 1);
    }

    @Test
    public void testCachePut() {
        for(int i = 0; i < 1000; i++) {
            cachedService.cachePut("B");
        }
        Assert.assertEquals(cachePutCalled.get(), 1000);
    }

    @Test
    public void testBoth() {
        Customer c1 = new Customer("1", "C 1");
        Customer c2 = new Customer("2", "C 2");

        Collection<Customer> collection = cachedService.findAll();
        Assertions.assertThat(collection).isEmpty();

        cachedService.save(c1);
        collection = cachedService.findAll();
        Assertions.assertThat(collection).containsExactly(c1);

        cachedService.save(c2);
        collection = cachedService.findAll();
        Assertions.assertThat(collection).containsExactly(c1, c2);

        Assert.assertEquals(cacheableCalled2.get(), 1);
        Assert.assertEquals(cachePutCalled2.get(), 2);
    }

}

