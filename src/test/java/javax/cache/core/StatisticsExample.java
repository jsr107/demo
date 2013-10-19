package javax.cache.core;

import static javax.cache.expiry.Duration.ONE_HOUR;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.spi.CachingProvider;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import org.junit.Test;

/**
 * Examples on retrieving cache statistics utilizing MXBeans.
 * @author Greg Luck
 * @author Dhrubajyoti Gogoi
 */
public class StatisticsExample {
  
  /**
   * Defining cache statistics parameters as constants.
   */
  private enum CacheStatistics {
      CacheHits, CacheHitPercentage,
      CacheMisses, CacheMissPercentage,
      CacheGets, CachePuts, CacheRemovals, CacheEvictions,
      AverageGetTime, AveragePutTime, AverageRemoveTime
  }

  @Test
  public void accessStatistics() throws MalformedObjectNameException,
      AttributeNotFoundException, MBeanException, ReflectionException,
      InstanceNotFoundException {

	// resolving cache manager.
    CachingProvider cachingProvider = Caching.getCachingProvider();
    CacheManager cacheManager = cachingProvider.getCacheManager();

    // configuring cache.
    MutableConfiguration<String, Integer> config =
        new MutableConfiguration<String, Integer>();
    config.setTypes(String.class, Integer.class)
        .setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(ONE_HOUR))
        .setStatisticsEnabled(true);

    Cache<String, Integer> cache = cacheManager.createCache("simpleCache", config);

    // defining constants.
    long cacheHits = 5;
    long cacheMisses = 3;
    long cacheGets = cacheHits + cacheMisses;
    long cacheRemovals = 3;
    long cachePuts = cacheRemovals + 1;
    // TODO: add eviction logic.
    long cacheEvictions = 0;
    float cacheHitPercentage = (float) cacheHits / cacheGets * 100.0f;
    float cacheMissPercentage = (float) cacheMisses / cacheGets * 100.0f;
    
    // use the cache
    cache.put("valid-key", 1);
    for (int i = 0; i < cacheHits; i++)	{ cache.get("valid-key"); }
    for (int i = 0; i < cacheMisses; i++)	{ cache.get("invalid-key"); }
    for (int i = 0; i < cacheRemovals; i++) { cache.put("key" + i, i); }
    for (int i = 0; i < cacheRemovals; i++) { cache.remove("key" + i); }
    
    ArrayList<MBeanServer> mBeanServers = MBeanServerFactory.findMBeanServer(null);
    ObjectName objectName = new ObjectName("javax.cache:type=CacheStatistics"
        + ",CacheManager=" + (cache.getCacheManager().getURI().toString())
        + ",Cache=" + cache.getName());
    
    // assertions
    assertEquals(cacheHits, mBeanServers.get(0).getAttribute(objectName, CacheStatistics.CacheHits.toString()));
    assertEquals(cacheHitPercentage, mBeanServers.get(0).getAttribute(objectName, CacheStatistics.CacheHitPercentage.toString()));
    assertEquals(cacheMisses, mBeanServers.get(0).getAttribute(objectName, CacheStatistics.CacheMisses.toString()));
    assertEquals(cacheMissPercentage, mBeanServers.get(0).getAttribute(objectName, CacheStatistics.CacheMissPercentage.toString()));
    assertEquals(cacheGets, mBeanServers.get(0).getAttribute(objectName, CacheStatistics.CacheGets.toString()));
    assertEquals(cachePuts, mBeanServers.get(0).getAttribute(objectName, CacheStatistics.CachePuts.toString()));
    assertEquals(cacheRemovals, mBeanServers.get(0).getAttribute(objectName, CacheStatistics.CacheRemovals.toString()));
    assertEquals(cacheEvictions, mBeanServers.get(0).getAttribute(objectName, CacheStatistics.CacheEvictions.toString()));
    assertTrue((float) mBeanServers.get(0).getAttribute(objectName, CacheStatistics.AverageGetTime.toString()) > 0.0f);
    assertTrue((float) mBeanServers.get(0).getAttribute(objectName, CacheStatistics.AveragePutTime.toString()) > 0.0f);
    assertTrue((float) mBeanServers.get(0).getAttribute(objectName, CacheStatistics.AverageRemoveTime.toString()) > 0.0f);
    
    // printing retrieved cache statistics to console.
    for (CacheStatistics cacheStatistic : CacheStatistics.values())	{
    	System.out.println(cacheStatistic + ": " + mBeanServers.get(0)
    			.getAttribute(objectName, cacheStatistic.toString()));
    }
  }
}
