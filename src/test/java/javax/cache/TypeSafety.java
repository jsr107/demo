package javax.cache;

import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;

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
    cacheManager.createCache("simpleCache", config);
    Cache<String, Integer> simpleCache = cacheManager.getCache("simpleCache",
        String.class, Integer.class);

    simpleCache.put("key1", 3);
    Integer value2 = simpleCache.get("key1");
  }


}
