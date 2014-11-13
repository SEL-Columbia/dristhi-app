package org.ei.drishti.view.dialog;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ei.drishti.domain.FPMethod;
import org.ei.drishti.view.contract.FPSmartRegisterClient;
import org.ei.drishti.view.contract.SmartRegisterClient;

public class FPMethodFilter implements FilterOption {
    private final String fpMethodName;
    public final static String ALL_METHODS_SERVICE_OPTION = "All Methods";
    public final static String OTHER_METHODS_SERVICE_OPTION = "Others";

    public FPMethodFilter(String serviceModeOption) {
        this.fpMethodName = serviceModeOption;
    }

    @Override
    public String name() {
        return fpMethodName;
    }

    @Override
    public boolean filter(SmartRegisterClient client) {
        boolean shouldFilter;
        FPSmartRegisterClient fpSmartRegisterClient = (FPSmartRegisterClient) client;
        if (name().equalsIgnoreCase(ALL_METHODS_SERVICE_OPTION)) {
            shouldFilter = doesClientUseFpMethod(fpSmartRegisterClient);
        }
        else if (name().equalsIgnoreCase(OTHER_METHODS_SERVICE_OPTION)){
            shouldFilter = doesClientUseOtherFpMethod(fpSmartRegisterClient);
        }
        else{
            shouldFilter = name().equalsIgnoreCase(fpSmartRegisterClient.fpMethod().displayName());
        }
        return shouldFilter;
    }

    private boolean doesClientUseOtherFpMethod(FPSmartRegisterClient fpClient) {
        String currentMethod = fpClient.fpMethod().displayName();
        return currentMethod.equalsIgnoreCase(FPMethod.LAM.displayName()) ||
                currentMethod.equalsIgnoreCase(FPMethod.TRADITIONAL_METHODS.displayName()) ||
                currentMethod.equalsIgnoreCase(FPMethod.CENTCHROMAN.displayName());
    }

    private boolean doesClientUseFpMethod(FPSmartRegisterClient fpClient) {
        return !fpClient.fpMethod().displayName().equalsIgnoreCase(FPMethod.NONE.displayName());
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
