package org.ei.drishti.view.controller;

import com.google.gson.Gson;
import org.ei.drishti.service.ANMService;
import org.ei.drishti.util.Cache;
import org.ei.drishti.util.CacheableData;
import org.ei.drishti.view.contract.HomeContext;

public class ANMController {
    private final ANMService anmService;
    private final Cache<String> cache;
    private static final String HOME_CONTEXT = "homeContext";


    public ANMController(ANMService anmService, Cache<String> cache) {
        this.anmService = anmService;
        this.cache = cache;
    }

    public String get() {
        return cache.get(HOME_CONTEXT, new CacheableData<String>() {
            @Override
            public String fetch() {
                return new Gson().toJson(new HomeContext(anmService.fetchDetails()));
            }
        });
    }
}
