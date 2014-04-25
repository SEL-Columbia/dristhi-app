package org.ei.drishti.view;

import org.ei.drishti.Context;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.repository.Repository;
import org.ei.drishti.view.controller.ANMController;
import org.mockito.Mock;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;

import java.util.ArrayList;
import java.util.List;

import static org.ei.drishti.util.EasyMap.mapOf;

@Implements(Context.class)
public class ShadowContext {

    @Mock Repository repository;
    @Mock ANMController anmController;
    @Mock public AllEligibleCouples allEligibleCouples;
    @Mock AllBeneficiaries allBeneficiaries;

    @RealObject Context context = Context.getInstance();
    private int clientCount = 0;

    @Implementation public Boolean IsUserLoggedOut() {
        return false;
    }

    @Implementation private Repository initRepository() {
        return repository;
    }

    @Implementation public ANMController anmController() {
        return anmController;
    }

    @Implementation public AllEligibleCouples allEligibleCouples() {
       return new AllEligibleCouples(null, null, null);
    }

    public List<EligibleCouple> couples(int clientCount) {
        List<EligibleCouple> couple = new ArrayList<EligibleCouple>();
        for (int i = 0; i < clientCount; i++) {
            couple.add(new EligibleCouple("CASE " + i, "Wife 1" + i, "Husband 1" + i, "EC 1" + i, "Village 1" + i, "SC 1" + i, mapOf("isHighPriority", "yes")));
        }
        System.out.println("creating couples : " + couple.size());
        return couple;
    }

    @Implementation public AllBeneficiaries allBeneficiaries() {
        return allBeneficiaries;
    }

    public void setECClientsCount(int clientCount) {
        System.out.println("setting count for Faking data clients : " + clientCount);
        this.clientCount = clientCount;
    }
}
