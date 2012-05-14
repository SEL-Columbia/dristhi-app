package org.ei.drishti.event;

import org.ei.drishti.domain.FetchStatus;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class Event<CallbackType> {
    public static Event<FetchStatus> ON_DATA_FETCHED = new Event<FetchStatus>();
    List<WeakReference<Listener<CallbackType>>> listeners;

    public Event() {
        listeners = new ArrayList<WeakReference<Listener<CallbackType>>>();
    }

    public void addListener(Listener<CallbackType> listener) {
        listeners.add(new WeakReference<Listener<CallbackType>>(listener));
    }

    public void notifyListeners(CallbackType data) {
        for (WeakReference<Listener<CallbackType>> listener : listeners) {
            if (listener.get() != null) {
                listener.get().onEvent(data);
            }
        }
    }
}
