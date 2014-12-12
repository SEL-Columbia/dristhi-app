package org.ei.drishti.view.contract;


import org.ei.drishti.domain.FPMethod;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public interface ECSmartRegisterClient extends SmartRegisterClient, ECSmartRegisterBaseClient {

    public String numberOfPregnancies();

    public String parity();

    public String numberOfLivingChildren();

    public String numberOfStillbirths();

    public String numberOfAbortions();

    public String familyPlanningMethodChangeDate();

    public String numberOfOCPDelivered();

    public String numberOfCondomsSupplied();

    public String numberOfCentchromanPillsDelivered();

    public String iudPerson();

    public String iudPlace();

    public FPMethod fpMethod();

    public List<ECChildClient> children();

    public Map<String, String> status();
}
