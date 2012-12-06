package javax.cache;

import org.junit.Test;

//import java.util.concurrent.TimeUnit;
//
//import static junit.framework.Assert.assertEquals;
//import static junit.framework.Assert.assertNull;

/**
 * @author Greg Luck
 */
public class CacheTest {

    @Test
    public void simpleAPI() {
//TODO: we'll fix the following when Issue #83 is resolved
//        String cacheName = "sampleCache";
//        CacheManager cacheManager = Caching.getCacheManager();
//
//        SimpleCacheConfigurationBuilder<String, Integer> builder = new SimpleCacheConfigurationBuilder<String, Integer>();
//        builder.setStoreByValue(false)
//               .setStatisticsEnabled(true)
//               .setCacheEntryExpiryPolicy(new CacheEntryExpiryPolicy.Accessed<String, Integer>(new CacheConfiguration.Duration(TimeUnit.HOURS, 1)));
//        CacheConfiguration<String, Integer> cacheConfiguration = builder.build();
//        
//        Cache<String, Integer> cache = cacheManager.configureCache(cacheName, cacheConfiguration);
//
//        String key = "key";
//        Integer value1 = 1;
//        cache.put(key, value1);
//        Integer value2 = cache.get(key);
//        assertEquals(value1, value2);
//
//        cache.remove("key");
//        assertNull(cache.get("key"));
    }

}
