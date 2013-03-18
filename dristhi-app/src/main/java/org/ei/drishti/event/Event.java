package org.ei.drishti.event;

import org.ei.drishti.domain.FetchStatus;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class Event<CallbackType> {
    public static final Event<FetchStatus> ON_DATA_FETCHED = new Event<FetchStatus>();
    public static final Event<Boolean> ON_DATA_CHANGE = new Event<Boolean>();
    public static final Event<Boolean> ON_LOGOUT = new Event<Boolean>();
    public static final Event<Boolean> SYNC_STARTED = new Event<Boolean>();
    public static final Event<Boolean> SYNC_COMPLETED = new Event<Boolean>();

    List<WeakReference<Listener<CallbackType>>> listeners;

    public Event() {
        listeners = new ArrayList<WeakReference<Listener<CallbackType>>>();
    }

    public void addListener(Listener<CallbackType> listener) {
        listeners.add(new WeakReference<Listener<CallbackType>>(listener));
    }

    public void removeListener(Listener<CallbackType> listener) {
        WeakReference<Listener<CallbackType>> listenerToRemove = null;
        for (WeakReference<Listener<CallbackType>> l : listeners) {
            if (listener.equals(l.get())) {
                listenerToRemove = l;
            }
        }
        listeners.remove(listenerToRemove);
    }

    public void notifyListeners(CallbackType data) {
        for (WeakReference<Listener<CallbackType>> listener : listeners) {
            if (listener.get() != null) {
                listener.get().onEvent(data);
            }
        }
    }
}
