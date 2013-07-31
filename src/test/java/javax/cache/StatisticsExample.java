package javax.cache;

import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.spi.CachingProvider;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import java.lang.management.ManagementFactory;
import java.util.Set;

import static javax.cache.expiry.Duration.ONE_HOUR;

public class StatisticsExample {

  public void accessStatistics() throws MalformedObjectNameException,
      AttributeNotFoundException, MBeanException, ReflectionException,
      InstanceNotFoundException {

    CachingProvider cachingProvider = Caching.getCachingProvider();
    CacheManager cacheManager = cachingProvider.getCacheManager();

    MutableConfiguration<String, Integer> config =
        new MutableConfiguration<String, Integer>();
    config.setStoreByValue(false)
        .setTypes(String.class, Integer.class)
        .setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(ONE_HOUR))
        .setStatisticsEnabled(true);

    cacheManager.getOrCreateCache("simpleCache", config);
    Cache<String, Integer> cache = cacheManager.getCache("simpleCache",
        String.class, Integer.class);

    Set<ObjectName> registeredObjectNames = null;
    MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

    ObjectName objectName = new ObjectName("javax.cache:type=CacheStatistics"
        + ",CacheManager=" + (cache.getCacheManager().getURI().toString())
        + ",Cache=" + cache.getName());
    System.out.println(mBeanServer.getAttribute(objectName,
        "CacheHitPercentage"));
  }
}
