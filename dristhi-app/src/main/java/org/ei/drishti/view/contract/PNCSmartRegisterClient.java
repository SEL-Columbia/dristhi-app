package org.ei.drishti.view.contract;


import org.ei.drishti.domain.FPMethod;
import org.joda.time.LocalDate;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

public interface PNCSmartRegisterClient extends BaseFPSmartRegisterClient {

    Comparator<SmartRegisterClient> DATE_OF_DELIVERY_COMPARATOR = new Comparator<SmartRegisterClient>() {
        @Override
        public int compare(SmartRegisterClient lhs, SmartRegisterClient rhs) {
            return ((PNCSmartRegisterClient) lhs).deliveryDateISO8601().compareTo(((PNCSmartRegisterClient) rhs).deliveryDateISO8601());
        }
    };
    public String thayiNumber();
    public String deliveryDate();
    public String deliveryShortDate();
    public LocalDate deliveryDateISO8601();
    public String deliveryPlace();
    public String deliveryType();
    public String deliveryComplications();
    public String womanDOB();
    public List<ChildClient> children();
}

