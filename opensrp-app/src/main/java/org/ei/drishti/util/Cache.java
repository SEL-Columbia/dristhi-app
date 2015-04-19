package org.ei.drishti.util;

import org.ei.drishti.domain.FetchStatus;
import org.ei.drishti.event.CapturedPhotoInformation;
import org.ei.drishti.event.Listener;

import java.util.HashMap;
import java.util.Map;

import static java.text.MessageFormat.format;
import static org.ei.drishti.domain.FetchStatus.fetched;
import static org.ei.drishti.event.Event.*;
import static org.ei.drishti.util.Log.logWarn;

public class Cache<T> {
    private Map<String, T> value = new HashMap<String, T>();
    private final Listener<String> formSubmittedListener;
    private final Listener<FetchStatus> actionsFetchedListener;
    private final Listener<CapturedPhotoInformation> photoCapturedListener;
    private final Listener<String> actionHandledListener;

    public Cache() {
        actionsFetchedListener = new Listener<FetchStatus>() {
            @Override
            public void onEvent(FetchStatus data) {
                if (fetched.equals(data)) {
                    logWarn("List cache invalidated as new data was fetched from server.");
                    value.clear();
                }
            }
        };
        formSubmittedListener = new Listener<String>() {
            @Override
            public void onEvent(String reason) {
                logWarn(format("List cache invalidated: {0}.", reason));
                value.clear();
            }
        };
        photoCapturedListener = new Listener<CapturedPhotoInformation>() {
            @Override
            public void onEvent(CapturedPhotoInformation data) {
                value.clear();
            }
        };
        actionHandledListener = new Listener<String>() {
            @Override
            public void onEvent(String data) {
                logWarn(format("List cache invalidated as Action handled: {0}", data));
                value.clear();
            }
        };
        ON_DATA_FETCHED.addListener(actionsFetchedListener);
        FORM_SUBMITTED.addListener(formSubmittedListener);
        ON_PHOTO_CAPTURED.addListener(photoCapturedListener);
        ACTION_HANDLED.addListener(actionHandledListener);
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

