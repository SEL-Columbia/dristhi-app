package org.ei.drishti.event;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class Event<CallbackType, T extends Listener<CallbackType>> {
    List<WeakReference<T>> listeners;

    public Event() {
        listeners = new ArrayList<WeakReference<T>>();
    }

    public void listenFor(T listener) {
        listeners.add(new WeakReference<T>(listener));
    }

    public void notifyListeners(CallbackType blah) {
        for (WeakReference<T> listener : listeners) {
            if (listener.get() != null) {
                listener.get().onEvent(blah);
            }
        }
    }
}
