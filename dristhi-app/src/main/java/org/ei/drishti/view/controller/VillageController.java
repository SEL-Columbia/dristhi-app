package org.ei.drishti.view.controller;

import com.google.gson.Gson;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.util.Cache;
import org.ei.drishti.util.CacheableData;
import org.ei.drishti.view.contract.Village;

import java.util.ArrayList;
import java.util.List;

public class VillageController {
    private static final String VILLAGE_LIST = "VILLAGE_LIST";
    private AllEligibleCouples allEligibleCouples;
    private final Cache<String> cache;

    public VillageController(AllEligibleCouples allEligibleCouples, Cache<String> cache) {
        this.allEligibleCouples = allEligibleCouples;
        this.cache = cache;
    }

    public String villages() {
        return cache.get(VILLAGE_LIST, new CacheableData<String>() {
            @Override
            public String fetch() {
                List<Village> villagesList = new ArrayList<Village>();
                List<String> villages = allEligibleCouples.villages();
                for (String village : villages) {
                    villagesList.add(new Village(village));
                }
                return new Gson().toJson(villagesList);
            }
        });
    }
}
