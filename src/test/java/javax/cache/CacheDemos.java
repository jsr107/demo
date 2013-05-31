package javax.cache;

import org.junit.Test;

import javax.cache.configuration.FactoryBuilder;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.Accessed;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;
import java.util.concurrent.TimeUnit;

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
    String cacheName = "sampleCache";
    CachingProvider cachingProvider = Caching.getCachingProvider();
    CacheManager cacheManager = cachingProvider.getCacheManager();

    MutableConfiguration<String, Integer> config = new MutableConfiguration<String, Integer>();
    config.setStoreByValue(false)
        .setTypes(String.class, Integer.class)
        .setExpiryPolicyFactory(factoryOf(new Accessed<String, Integer>(ONE_HOUR)))
        .setStatisticsEnabled(true);

    Cache<String, Integer> cache = cacheManager.configureCache(cacheName, config);
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
        .setExpiryPolicyFactory(factoryOf(new Accessed<String,
            Integer>(ONE_HOUR))).setStatisticsEnabled(true);

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
