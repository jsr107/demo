package javax.cache;

import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.integration.CompletionListenerFuture;
import javax.cache.spi.CachingProvider;
import java.util.HashSet;
import java.util.concurrent.ExecutionException;

import static javax.cache.expiry.Duration.ONE_HOUR;

/**
 * Examples for completino listeners
 * @author Greg Luck
 */
public class CompletionListenerExamples {


  public void completionListenerExample() {

    CachingProvider cachingProvider = Caching.getCachingProvider();
    CacheManager cacheManager = cachingProvider.getCacheManager();

    MutableConfiguration<String, Integer> config = new MutableConfiguration<String, Integer>();
    config.setStoreByValue(false)
        .setTypes(String.class, Integer.class)
        .setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(ONE_HOUR))
        .setStatisticsEnabled(true);

    cacheManager.getOrCreateCache("simpleCache", config);
    Cache<String, Integer> cache = cacheManager.getCache("simpleCache",
        String.class, Integer.class);

    HashSet<String> keys = new HashSet<>();
    keys.add("23432lkj");
    keys.add("4fsdldkj");


    //create a completion future to use to wait for loadAll
    CompletionListenerFuture future = new CompletionListenerFuture();

    //load the values for the set of keys, replacing those that may already
    //exist in the cache
    cache.loadAll(keys, true, future);

    //wait for the cache to load the keys
    try {
      future.get();
    } catch (InterruptedException e) {
      //future interrupted
      e.printStackTrace();
    } catch (ExecutionException e) {
      //throwable was what was sent to onException(Exception e)
      Throwable throwable = e.getCause();
    }


  }


}
