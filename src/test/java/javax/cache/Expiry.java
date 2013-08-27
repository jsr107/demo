package javax.cache;

import javax.cache.expiry.Duration;
import javax.cache.expiry.ExpiryPolicy;
import java.util.List;

import static javax.cache.expiry.Duration.TWENTY_MINUTES;
import static javax.cache.expiry.Duration.ZERO;

/**
 * Expiry examples
 */
public class Expiry {



  public class ShoppingCartExpiryPolicy<String> implements ExpiryPolicy<String> {

    private final Cache<String, ShoppingCart> cache;

    /**
     * Create a shopping cart expiry policy
     * @param cache the cache that holds the shopping carts
     */
    public ShoppingCartExpiryPolicy(Cache<String, ShoppingCart> cache) {
      this.cache = cache;
    }

    @Override
    public Duration getExpiryForCreatedEntry(String key) {
      return TWENTY_MINUTES;
    }

    @Override
    public Duration getExpiryForAccessedEntry(String key) {
      //we don't change the expiry time for accesses
      return null;
    }

    @Override
    public Duration getExpiryForModifiedEntry(String key) {
      Expiry.ShoppingCart cart = cache.get(key);


      //when a shopping cart is closed, we can expire it immediately,
      //otherwise we give shopping cart another 20 minutes. To cause this
      //to take immediate effect the cart must be put back in the cache.
      return cart.isClosed() ? ZERO : TWENTY_MINUTES;
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
