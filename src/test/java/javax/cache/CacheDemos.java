package javax.cache;

import org.junit.Test;

import javax.cache.expiry.Accessed;
import javax.cache.expiry.Duration;
import javax.cache.expiry.ExpiryPolicy;
import javax.cache.spi.CachingProvider;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
        .setExpiryPolicyFactory(Factories.of(new Accessed<String, Integer>(new Duration(TimeUnit.HOURS, 1))))
        .setStatisticsEnabled(true);

    Cache<String, Integer> cache = cacheManager.configureCache(cacheName, config);

    String key = "key";
    Integer value1 = 1;
    cache.put(key, value1);
    Integer value2 = cache.get(key);
    assertEquals(value1, value2);

    cache.remove("key");
    assertNull(cache.get("key"));
  }


}
