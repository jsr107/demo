package javax.cache.core;

import org.junit.Test;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
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
        MutableConfiguration<>();
    config.setTypes(String.class, Integer.class);

    Cache<String, Integer> simpleCache =  cacheManager.createCache("simpleCache5", config);

    simpleCache.put("key1", 3);
    Integer value2 = simpleCache.get("key1");

    //Shows how to get around runtime+generics safety
    Cache secondReferenceToCache = simpleCache;
    secondReferenceToCache.put(123, "String");

  }


}
