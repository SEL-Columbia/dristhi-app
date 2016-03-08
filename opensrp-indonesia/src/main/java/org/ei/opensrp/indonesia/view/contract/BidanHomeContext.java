package org.ei.opensrp.indonesia.view.contract;

import org.ei.opensrp.indonesia.domain.Bidan;

/**
 * Created by Dimas Ciputra on 3/4/15.
 */
public class BidanHomeContext {

    private long kartuIbuCount;
    private long kartuIbuANCCount;
    private long kartuIbuPNCCount;
    private long anakCount;
    private long kbCount;

    public BidanHomeContext(Bidan bidan) {
        this.kartuIbuCount = bidan.getKartuIbuCount();
        this.kartuIbuANCCount = bidan.getKartuIbuAncCount();
        this.kartuIbuPNCCount = bidan.getKartuIbuPNCCount();
        this.anakCount = bidan.getAnakCount();
        this.kbCount = bidan.getKbCount();
    }

    public long getKartuIbuCount() { return kartuIbuCount; }

    public long getKartuIbuANCCount() { return kartuIbuANCCount; }

    public long getKartuIbuPNCCount() { return kartuIbuPNCCount; }

    public long getAnakCount() { return anakCount; }

    public long getKBCount() { return kbCount; }
}
