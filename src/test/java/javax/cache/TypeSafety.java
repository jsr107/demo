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
public class TypeSafety {

  public void runtimeTypeEnforcement() {
    CachingProvider cachingProvider = Caching.getCachingProvider();
    CacheManager cacheManager = cachingProvider.getCacheManager();

    MutableConfiguration<String, Integer> config = new
        MutableConfiguration<String, Integer>();
    config.setTypes(String.class, Integer.class);
    cacheManager.configureCache("simpleCache", config);
    Cache<String, Integer> simpleCache = cacheManager.getCache("simpleCache",
        String.class, Integer.class);

    simpleCache.put("key1", 3);
    Integer value2 = simpleCache.get("key1");
  }


}
