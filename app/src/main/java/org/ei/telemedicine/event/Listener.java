package org.ei.telemedicine.event;

public interface Listener<T> {
    void onEvent(T data);
}
