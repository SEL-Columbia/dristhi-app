package org.ei.drishti.view.contract;

import org.ei.drishti.domain.FPMethod;
import org.ei.drishti.util.IntegerUtil;

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
