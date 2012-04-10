package org.ei.drishti.activity;

abstract class Lazy<T> {
    private T cachedPayload;

    protected abstract T setupPayload();

    public T getPayload() {
        if (cachedPayload == null) {
            cachedPayload = setupPayload();
        }
        return cachedPayload;
    }
}
