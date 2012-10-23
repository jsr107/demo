package javax.cache;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

/**
 * @author Greg Luck
 */
public class CacheTest {



    @Test
    public void simpleAPI() {
        String cacheName = "sampleCache";
        CacheManager cacheManager = Caching.getCacheManager();
        Cache<String, Integer> cache = cacheManager.getCache(cacheName);
        cache = cacheManager.<String, Integer>createCacheBuilder(cacheName).setStoreByValue(false).build();

        String key = "key";
        Integer value1 = 1;
        cache.put(key, value1);
        Integer value2 = cache.get(key);
        assertEquals(value1, value2);

        cache.remove("key");
        assertNull(cache.get("key"));


    }

}
