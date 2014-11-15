package javax.cache.core.stepbystep;/*
 * File: MyCacheEntryListener.java
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

import javax.cache.event.CacheEntryCreatedListener;
import javax.cache.event.CacheEntryEvent;
import javax.cache.event.CacheEntryListenerException;
import javax.cache.event.CacheEntryUpdatedListener;

/**
 * A custom cache entry listener.
 *
 * @author Brian Oliver (Oracle Corporation)
 */
public class MyCacheEntryListener implements CacheEntryCreatedListener<String, String>,
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
