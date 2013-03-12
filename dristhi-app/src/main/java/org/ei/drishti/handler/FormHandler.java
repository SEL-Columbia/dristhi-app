package org.ei.drishti.handler;

public interface FormHandler {
    public boolean appliesTo(String formType);

    void process(String instanceId, String data);
}
