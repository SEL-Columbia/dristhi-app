package org.ei.drishti.event;

public interface Listener<T> {
    void onEvent(T data);
}
