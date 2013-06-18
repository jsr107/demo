package javax.cache;

import org.junit.Test;

import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.spi.CachingProvider;

import static javax.cache.configuration.FactoryBuilder.factoryOf;
import static javax.cache.expiry.Duration.ONE_HOUR;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;


/**
 * @author Greg Luck
 */
public class CacheDemos {


  @Test
  public void simpleAPI() {
    CachingProvider cachingProvider = Caching.getCachingProvider();
    CacheManager cacheManager = cachingProvider.getCacheManager();

    MutableConfiguration<String, Integer> config = new MutableConfiguration<String, Integer>();
    config.setStoreByValue(false)
        .setTypes(String.class, Integer.class)
        .setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(ONE_HOUR))
        .setStatisticsEnabled(true);

    cacheManager.configureCache("simpleCache", config);
    Cache<String, Integer> cache = cacheManager.getCache("simpleCache",
        String.class, Integer.class);
    String key = "key";
    Integer value1 = 1;
    cache.put("key", value1);
    Integer value2 = cache.get(key);
    assertEquals(value1, value2);

    cache.remove("key");
    assertNull(cache.get("key"));
  }

  @Test
  public void simpleAPINoGenerics() {
    String cacheName = "sampleCache";
    CachingProvider cachingProvider = Caching.getCachingProvider();
    CacheManager cacheManager = cachingProvider.getCacheManager();

    MutableConfiguration config = new MutableConfiguration<String, Integer>()
        .setTypes(String.class, Integer.class);
    config.setStoreByValue(false)
        .setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(ONE_HOUR))
    .setStatisticsEnabled(true);

    Cache cache = cacheManager.configureCache(cacheName, config);
    String key = "key";
    Integer value1 = 1;
    cache.put("key", value1);
    //wrong
    cache.put(value1, "key1");
    Integer value2 = (Integer) cache.get(key);
    assertEquals(value1, value2);

    cache.remove("key");
    assertNull(cache.get("key"));
  }


}
