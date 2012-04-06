package org.ei.drishti.domain;

public class Response<T> {
    private ResponseStatus status;
    private T payload;

    public Response(ResponseStatus status, T payload) {
        this.status = status;
        this.payload = payload;
    }

    public T payload() {
        return payload;
    }

    public ResponseStatus status() {
        return status;
    }

    public static enum ResponseStatus {
        failure, success
    }
}
