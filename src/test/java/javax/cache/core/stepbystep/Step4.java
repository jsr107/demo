package javax.cache.core.stepbystep;/*
 * File: Step4.java
 *
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * The contents of this file are subject to the terms and conditions of 
 * the Common Development and Distribution License 1.0 (the "License").
 *
 * You may not use this file except in compliance with the License.
 *
 * You can obtain a copy of the License by consulting the LICENSE.txt file
 * distributed with this file, or by consulting https://oss.oracle.com/licenses/CDDL
 *
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file LICENSE.txt.
 *
 * MODIFICATIONS:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 */

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.CacheEntryListenerConfiguration;
import javax.cache.configuration.FactoryBuilder;
import javax.cache.configuration.MutableCacheEntryListenerConfiguration;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.processor.EntryProcessor;
import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.MutableEntry;
import javax.cache.spi.CachingProvider;

/**
 * JCache Step 4: Observing Cache Entry Events
 *
 * @author Brian Oliver (Oracle Corporation)
 */
public class Step4 {
    public static void main(String[] args) {
        // acquire the default caching provider
        CachingProvider provider = Caching.getCachingProvider();

        // acquire the default cache manager
        CacheManager manager = provider.getCacheManager();

        // define a configuration for a cache from String to String
        MutableConfiguration<String, String> configuration =
                new MutableConfiguration().setStoreByValue(true).setTypes(String.class,
                        String.class);

        // create a cache
        Cache<String, String> cache = manager.createCache("greetings", configuration);

        // define a cache entry listener configuration
        CacheEntryListenerConfiguration<String, String> listenerConfiguration =
                new MutableCacheEntryListenerConfiguration<String,
                        String>(FactoryBuilder.factoryOf(MyCacheEntryListener.class),
                        null,
                        false,
                        true);

        cache.registerCacheEntryListener(listenerConfiguration);

        // put some values
        cache.put("AU", "gudday mate");
        cache.put("US", "hello");
        cache.put("FR", "bonjour");

        // convert the entries to upper case
        cache.invoke("AU", new EntryProcessor<String, String, String>() {
            @Override
            public String process(MutableEntry<String, String> entry,
                                  Object... arguments) throws EntryProcessorException {
                if (entry.exists()) {
                    entry.setValue(entry.getValue().toUpperCase());
                }

                return null;
            }
        });

        // get a value
        System.out.println("Greeting for today: " + cache.get("AU"));
    }
}
