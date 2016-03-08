package org.ei.opensrp.indonesia.view.controller;

import org.ei.opensrp.indonesia.repository.AllKartuIbus;
import org.ei.opensrp.util.Cache;
import org.ei.opensrp.util.CacheableData;
import org.ei.opensrp.view.contract.Village;
import org.ei.opensrp.view.contract.Villages;

import java.util.List;

/**
 * Created by Dimas Ciputra on 6/11/15.
 */
public class BidanVillageController {

    private static final String VILLAGE_LIST = "VILLAGE_LIST";
    private Cache<Villages> villagesCache;
    private AllKartuIbus allKartuIbus;

    public BidanVillageController(Cache<Villages> villagesCache, AllKartuIbus allKartuIbus) {
        this.villagesCache = villagesCache;
        this.allKartuIbus = allKartuIbus;
    }

    public Villages getVillagesIndonesia() {
        return villagesCache.get(VILLAGE_LIST, new CacheableData<Villages>() {
            @Override
            public Villages fetch() {
                Villages villagesList = new Villages();
                List<String> villages = allKartuIbus.villages();
                for (String village : villages) {
                    if(village != null){
                        villagesList.add(new Village(village.replace("+", " ")));
                    }
                }
                return villagesList;
            }
        });
    }
}
