package org.ei.telemedicine.view;

public interface BackgroundAction<T> {
    T actionToDoInBackgroundThread();

    void postExecuteInUIThread(T result);
}
