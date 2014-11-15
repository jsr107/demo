package javax.cache.core;

import org.junit.Test;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.spi.CachingProvider;

import static javax.cache.expiry.Duration.ONE_HOUR;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;


/**
 *
 * @author Greg Luck
 */
public class CacheDemos {


    @Test
    public void simpleCache() {

        Cache<Integer, String> simpleCache = Caching.getCache("simpleCache", Integer.class, String.class);
        simpleCache.put(2, "value");
        String value = simpleCache.get(2);
    }


  @Test
  public void simpleAPITypeEnforcement() {

    //resolve a cache manager
    CachingProvider cachingProvider = Caching.getCachingProvider();
    CacheManager cacheManager = cachingProvider.getCacheManager();

    //configure the cache
    MutableConfiguration<String, Integer> config = new MutableConfiguration<>();
    config.setStoreByValue(true)
        .setTypes(String.class, Integer.class)
        .setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(ONE_HOUR))
        .setStatisticsEnabled(true);

    //create the cache
    cacheManager.createCache("simpleCache", config);

    //... and then later to get the cache
    Cache<String, Integer> cache = Caching.getCache("simpleCache", String.class, Integer.class);

    //use the cache
    String key = "key";
    Integer value1 = 1;
    cache.put("key", value1);
    Integer value2 = cache.get(key);
    assertEquals(value1, value2);

    cache.remove("key");
    assertNull(cache.get("key"));
  }


  @Test
  public void simpleAPITypeEnforcementUsingCaching() {

    //resolve a cache manager
    CachingProvider cachingProvider = Caching.getCachingProvider();
    CacheManager cacheManager = cachingProvider.getCacheManager();

    Number one = new Integer(1);

    //configure the cache
    MutableConfiguration<String, Integer> config = new MutableConfiguration<>();
    config.setTypes(String.class, Integer.class)
        .setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(ONE_HOUR))
        .setStatisticsEnabled(true);

    //create the cache
    cacheManager.createCache("simpleCache2", config);

    //... and then later to get the cache
    Cache<String, Integer> cache = Caching.getCache("simpleCache2",
        String.class, Integer.class);

    //use the cache
    String key = "key";
    Integer value1 = 1;
    cache.put("key", value1);
    Integer value2 = cache.get(key);
    assertEquals(value1, value2);
    cache.remove("key");
    assertNull(cache.get("key"));
  }

  @Test
  public void simpleAPIWithGenericsAndNoTypeEnforcement() {

    //resolve a cache manager
    CachingProvider cachingProvider = Caching.getCachingProvider();
    CacheManager cacheManager = cachingProvider.getCacheManager();

    //configure the cache
    String cacheName = "sampleCache3";
    MutableConfiguration config = new MutableConfiguration<String, Integer>();
    config.setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(ONE_HOUR))
        .setStatisticsEnabled(true);

    //create the cache
    cacheManager.createCache(cacheName, config);

    //... and then later to get the cache
    Cache<String, Integer> cache = cacheManager.getCache(cacheName);

    //use the cache
    String key = "key";
    Integer value1 = 1;
    cache.put("key", value1);

    //The following line gives a compile error
    //cache.put(value1, "key1");
    Integer value2 = (Integer) cache.get(key);

    cache.remove(key);
    assertNull(cache.get(key));
  }


  @Test
  public void simpleAPINoGenericsAndNoTypeEnforcement() {

    //resolve a cache manager
    CachingProvider cachingProvider = Caching.getCachingProvider();
    CacheManager cacheManager = cachingProvider.getCacheManager();

    //configure the cache
    String cacheName = "sampleCache";
    MutableConfiguration config = new MutableConfiguration();
    config.setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(ONE_HOUR))
    .setStatisticsEnabled(true);

    //create the cache
    cacheManager.createCache(cacheName, config);

    //... and then later to get the cache
    Cache cache = cacheManager.getCache(cacheName);

    //use the cache
    String key = "key";
    Integer value1 = 1;
    cache.put(key, value1);
    //wrong
    cache.put(value1, key);
    Integer value2 = (Integer) cache.get(key);
    assertEquals(value1, value2);

    cache.remove(key);
    assertNull(cache.get(key));
  }


    /**
     * Shows the consequences of using Object, Object where you want no enforcement
     */
    @Test
    public void simpleAPITypeEnforcementObject() {

        //resolve a cache manager
        CachingProvider cachingProvider = Caching.getCachingProvider();
        CacheManager cacheManager = cachingProvider.getCacheManager();

        //configure the cache
        MutableConfiguration<Object, Object> config = new MutableConfiguration<>();
        config.setTypes(Object.class, Object.class)
                .setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(ONE_HOUR))
                .setStatisticsEnabled(true);

        //create the cache
        cacheManager.createCache("simpleCache4", config);

        //... and then later to get the cache
        Cache<Object, Object> cache = Caching.getCache("simpleCache4",
                Object.class, Object.class);

        //use the cache
        String key = "key";
        Integer value1 = 1;
        cache.put("key", value1);
        Object value2 = cache.get(key);
        assertEquals(value1, value2);

        cache.remove("key");
        assertNull(cache.get("key"));
    }


}
