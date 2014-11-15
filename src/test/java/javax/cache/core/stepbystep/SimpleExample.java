package javax.cache.core.stepbystep;/*
 * File: SimpleExample.java
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
import javax.cache.event.CacheEntryCreatedListener;
import javax.cache.event.CacheEntryEvent;
import javax.cache.event.CacheEntryListenerException;
import javax.cache.event.CacheEntryUpdatedListener;
import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.MutableEntry;
import javax.cache.spi.CachingProvider;

/**
 * A simple JCache example
 */
public class SimpleExample {

    public static void main(String[] args) {
        CachingProvider provider = Caching.getCachingProvider();

        CacheManager manager = provider.getCacheManager();

        MutableConfiguration<String, String> configuration =
                new MutableConfiguration().setStoreByValue(false).setTypes(String.class,
                        String.class);

        Cache<String, String> cache = manager.createCache("my-cache", configuration);

        CacheEntryListenerConfiguration<String, String> listenerConfiguration =
                new MutableCacheEntryListenerConfiguration<String,
                        String>(FactoryBuilder.factoryOf(MyCacheEntryListener.class),
                        null,
                        false,
                        true);

        cache.registerCacheEntryListener(listenerConfiguration);

        cache.put("message", "hello");
        cache.put("message", "g'day");
        cache.put("message", "bonjour");

        String result = cache.invoke("message", new AbstractEntryProcessor<String, String, String>() {
            @Override
            public String process(MutableEntry<String, String> entry,
                                  Object... arguments) throws EntryProcessorException {
                return entry.exists() ? entry.getValue().toUpperCase() : null;
            }
        });

        System.out.println("EntryProcessor result:" + result);

        // cache.clear();

        System.out.println(cache.get("message"));
    }


    /**
     * A simple cache listener example
     */
    public static class MyCacheEntryListener implements CacheEntryCreatedListener<String, String>,
            CacheEntryUpdatedListener<String, String> {
        @Override
        public void onCreated(Iterable<CacheEntryEvent<? extends String, ? extends String>> cacheEntryEvents)
                throws CacheEntryListenerException {
            for (CacheEntryEvent<? extends String, ? extends String> entryEvent : cacheEntryEvents) {
                System.out.println("Created: " + entryEvent.getKey() + " with value: " + entryEvent.getValue());
            }
        }


        @Override
        public void onUpdated(Iterable<CacheEntryEvent<? extends String, ? extends String>> cacheEntryEvents)
                throws CacheEntryListenerException {
            for (CacheEntryEvent<? extends String, ? extends String> entryEvent : cacheEntryEvents) {
                System.out.println("Updated: " + entryEvent.getKey() + " with value: " + entryEvent.getValue());
            }
        }
    }
}
