package org.ei.drishti.view.dialog;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ei.drishti.view.contract.FPSmartRegisterClient;
import org.ei.drishti.view.contract.SmartRegisterClient;

public class FPMethodFilter implements FilterOption {
    private final String fpMethodName;
    private String allMethodsIdentifier;

    public FPMethodFilter(String fpMethodName) {

        this.fpMethodName = fpMethodName;
        this.allMethodsIdentifier = "All Methods";
    }

    @Override
    public String name() {
        return fpMethodName;
    }

    @Override
    public boolean filter(SmartRegisterClient client) {
        FPSmartRegisterClient fpSmartRegisterClient = (FPSmartRegisterClient) client;
        return name().equalsIgnoreCase(allMethodsIdentifier) || name().equalsIgnoreCase(fpSmartRegisterClient.fpMethod().displayName());
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
