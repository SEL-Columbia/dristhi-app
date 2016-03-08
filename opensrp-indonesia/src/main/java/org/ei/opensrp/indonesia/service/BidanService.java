package org.ei.opensrp.indonesia.service;

import org.ei.opensrp.indonesia.domain.Bidan;
import org.ei.opensrp.indonesia.repository.AllKartuIbus;
import org.ei.opensrp.indonesia.repository.AllKohort;
import org.ei.opensrp.repository.AllSharedPreferences;

/**
 * Created by Dimas Ciputra on 3/4/15.
 */
public class BidanService {
    private AllSharedPreferences allSharedPreferences;
    private AllKartuIbus allKartuIbus;
    private AllKohort allKohort;

    public BidanService(AllSharedPreferences allSharedPreferences, AllKartuIbus allKartuIbus, AllKohort allKohort) {
        this.allSharedPreferences = allSharedPreferences;
        this.allKartuIbus = allKartuIbus;
        this.allKohort = allKohort;
    }

    public Bidan fetchDetails() {
        return new Bidan(allSharedPreferences.fetchRegisteredANM(), allKartuIbus.count(), allKohort.ancCount(), allKohort.pncCount(), allKohort.anakCount(), allKartuIbus.kbCount());
    }
}