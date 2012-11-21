package javax.cache;

import org.jsr107.ri.SimpleCacheConfigurationBuilder;
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
        
        SimpleCacheConfigurationBuilder<String, Integer> builder = new SimpleCacheConfigurationBuilder<String, Integer>();
        builder.setStoreByValue(false);
        
        Cache<String, Integer> cache = cacheManager.configureCache(cacheName, builder.build());

        String key = "key";
        Integer value1 = 1;
        cache.put(key, value1);
        Integer value2 = cache.get(key);
        assertEquals(value1, value2);

        cache.remove("key");
        assertNull(cache.get("key"));


    }

}
