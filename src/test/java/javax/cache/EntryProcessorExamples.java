package javax.cache;

import org.junit.Test;

import javax.cache.configuration.MutableConfiguration;
import javax.cache.processor.MutableEntry;
import javax.cache.spi.CachingProvider;
import java.io.Serializable;
import javax.cache.processor.EntryProcessor;

/**
 * {@link EntryProcessor} examples.
 *
 * @author Brian Oliver
 */
public class EntryProcessorExamples {

  /**
   * Demonstrates incrementing a value in a {@link Cache} using
   * an {@link EntryProcessor}.
   */
  @Test
  public void incrementValue() {

    CachingProvider provider = Caching.getCachingProvider();
    CacheManager manager = provider.getCacheManager();

    MutableConfiguration<String, Integer> configuration =
      new MutableConfiguration<String, Integer>()
        .setTypes(String.class, Integer.class);

    manager.createCache("example", configuration);
    Cache<String, Integer> cache = manager.getCache("example", String.class,
        Integer.class);

    String key = "counter";
    cache.put(key, 1);

    int previous = cache.invoke(key, new IncrementProcessor<String>());

    assert previous == 1;
    assert cache.get(key) == 2;
  }

  /**
   * An {@link EntryProcessor} that increments an {@link Integer}.
   *
   * @param <K> the type of keys
   */
  public static class IncrementProcessor<K>
      implements EntryProcessor<K, Integer, Integer>, Serializable {

    /**
     * The serialVersionUID required for {@link java.io.Serializable}.
     */
    public static final long serialVersionUID = 201306211238L;

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer process(MutableEntry<K, Integer> entry, Object... arguments) {
      if (entry.exists()) {
        Integer current = entry.getValue();
        entry.setValue(current + 1);
        return current;
      } else {
        entry.setValue(0);
        return -1;
      }
    }
  }
}
