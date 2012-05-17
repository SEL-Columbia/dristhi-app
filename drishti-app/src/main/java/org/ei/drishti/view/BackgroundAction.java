package org.ei.drishti.view;

public interface BackgroundAction<T> {
    T actionToDoInBackgroundThread();

    void postExecuteInUIThread(T result);
}
