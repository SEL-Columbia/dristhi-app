package org.ei.telemedicine.view.contract;

import org.ei.telemedicine.domain.FPMethod;
import org.ei.telemedicine.util.IntegerUtil;

import java.util.Comparator;

public interface BaseFPSmartRegisterClient extends SmartRegisterClient {

    public FPMethod fpMethod();

    public String familyPlanningMethodChangeDate();

    public String numberOfOCPDelivered();

    public String numberOfCondomsSupplied();

    public String numberOfCentchromanPillsDelivered();

    public String iudPerson();

    public String iudPlace();

}
