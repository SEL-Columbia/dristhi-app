package org.ei.drishti.view.contract;

import org.ei.drishti.domain.ANM;

public class HomeContext {
    private String anmName;
    private long ancCount;
    private long pncCount;
    private long childCount;
    private long eligibleCoupleCount;

    public HomeContext(ANM anm) {
        this.anmName = anm.name();
        this.ancCount = anm.ancCount();
        this.pncCount = anm.pncCount();
        this.childCount = anm.childCount();
        this.eligibleCoupleCount = anm.ecCount();
    }
}
