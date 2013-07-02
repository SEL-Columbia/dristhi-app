package org.ei.drishti.view.controller;

import com.google.gson.Gson;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.view.contract.Village;

import java.util.ArrayList;
import java.util.List;

public class VillageController {
    private AllEligibleCouples allEligibleCouples;

    public VillageController(AllEligibleCouples allEligibleCouples) {
        this.allEligibleCouples = allEligibleCouples;
    }

    public String villages() {
        List<Village> villagesList = new ArrayList<Village>();
        List<String> villages = allEligibleCouples.villages();
        for (String village : villages) {
            villagesList.add(new Village(village));
        }

        return new Gson().toJson(villagesList);
    }
}
