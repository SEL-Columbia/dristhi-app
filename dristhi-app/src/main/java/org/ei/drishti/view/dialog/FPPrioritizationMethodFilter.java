package org.ei.drishti.view.dialog;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ei.drishti.Context;
import org.ei.drishti.R;
import org.ei.drishti.view.contract.FPPrioritizationServiceModes;
import org.ei.drishti.view.contract.FPSmartRegisterClient;
import org.ei.drishti.view.contract.SmartRegisterClient;

public class FPPrioritizationMethodFilter implements FilterOption {
    private final String filter;
    private String allECIdentifier;

    public FPPrioritizationMethodFilter(String filter) {

        this.filter = filter;
        this.allECIdentifier = Context.getInstance().getStringResource(R.string.fp_prioritization_all_ec_service_mode);
    }

    @Override
    public String name() {
        return filter;
    }

    @Override
    public boolean filter(SmartRegisterClient client) {
        FPSmartRegisterClient fpSmartRegisterClient = (FPSmartRegisterClient) client;
        return name().equalsIgnoreCase(allECIdentifier) || applyStringLiteralFiler(fpSmartRegisterClient);
    }

    private boolean applyStringLiteralFiler(FPSmartRegisterClient fpSmartRegisterClient) {
        FPPrioritizationServiceModes serviceMode = FPPrioritizationServiceModes.valueOfIdentifier(filter);
        switch (serviceMode) {
            case ALL_EC: return true;
            case HIGH_PRIORITY: return fpSmartRegisterClient.isHighPriority();
            case TWO_CHILDREN: return getIntFromString(fpSmartRegisterClient.numberOfLivingChildren()) == 2;
            case ONE_CHILDREN: return getIntFromString(fpSmartRegisterClient.numberOfLivingChildren()) == 1;
            default: return false;
        }
    }

    private int getIntFromString (String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return 0;
        }
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
