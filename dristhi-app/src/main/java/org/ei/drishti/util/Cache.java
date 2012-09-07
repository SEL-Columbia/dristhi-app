package org.ei.drishti.util;

import org.ei.drishti.domain.FetchStatus;
import org.ei.drishti.event.Listener;

import java.util.HashMap;
import java.util.Map;

import static org.ei.drishti.domain.FetchStatus.fetched;
import static org.ei.drishti.event.Event.ON_DATA_FETCHED;

public class Cache<T> {
    private Map<String, T> value = new HashMap<String, T>();
    private final Listener<FetchStatus> actionsFetchedListener;

    public Cache() {
        actionsFetchedListener = new Listener<FetchStatus>() {
            @Override
            public void onEvent(FetchStatus data) {
                if (fetched.equals(data)) {
                    value.clear();
                }
            }
        };
        ON_DATA_FETCHED.addListener(actionsFetchedListener);
    }

    public T get(String key, CacheableData<T> cacheableData) {
        if (value.get(key) != null) {
            return value.get(key);
        }
        T fetchedData = cacheableData.fetch();
        value.put(key, fetchedData);
        return fetchedData;
    }
}

