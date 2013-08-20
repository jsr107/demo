package javax.cache;

import org.junit.Test;

import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;

/**
 * @author Greg Luck
 */
public class TypeSafety {

  @Test
  public void runtimeTypeEnforcement() {
    CachingProvider cachingProvider = Caching.getCachingProvider();
    CacheManager cacheManager = cachingProvider.getCacheManager();

    MutableConfiguration<String, Integer> config = new
        MutableConfiguration<String, Integer>();
    config.setTypes(String.class, Integer.class);
    cacheManager.createCache("simpleCache5", config);
    Cache<String, Integer> simpleCache = cacheManager.getCache("simpleCache5",
        String.class, Integer.class);

    simpleCache.put("key1", 3);
    Integer value2 = simpleCache.get("key1");

    //Shows how to get around runtime+generics safety
    Cache secondReferenceToCache = simpleCache;
    secondReferenceToCache.put(123, "String");

  }


}
