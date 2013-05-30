package javax.cache;

import javax.cache.expiry.Duration;
import javax.cache.expiry.ExpiryPolicy;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static javax.cache.Cache.Entry;

/**
 * Expiry examples
 */
public class Expiry {

  public class ShoppingCartExpiryPolicy implements ExpiryPolicy<String,
      ShoppingCart> {

    @Override
    public Duration getExpiryForCreatedEntry(Entry<? extends String, ? extends
        ShoppingCart> entry) {
      return new Duration(TimeUnit.MINUTES, 20);
    }

    @Override
    public Duration getExpiryForAccessedEntry(Entry<? extends String, ? extends
        ShoppingCart> entry) {
      //we don't change the expiry time for accesses
      return null;
    }

    @Override
    public Duration getExpiryForModifiedEntry(Entry<? extends String, ? extends
        ShoppingCart> entry) {
      ShoppingCart c = entry.getValue();

      //when a shopping cart is closed, we can expire it immediately,
      //otherwise we give shopping cart another 20 minutes. To cause this
      //to take immediate effect the cart must be put back in the cache.
      return c.isClosed() ? Duration.ZERO : new Duration(TimeUnit.HOURS, 1);
    }
  }

  public class ShoppingCart {

    boolean closed;
    List items;

    public boolean isClosed() {
      return closed;
    }
  }


}
