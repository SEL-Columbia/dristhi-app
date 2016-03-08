package org.ei.opensrp.indonesia.view.controller;

import com.google.gson.Gson;

import org.ei.opensrp.indonesia.service.BidanService;
import org.ei.opensrp.indonesia.view.contract.BidanHomeContext;
import org.ei.opensrp.util.Cache;
import org.ei.opensrp.util.CacheableData;

/**
 * Created by Dimas Ciputra on 3/4/15.
 */
public class BidanController {
    private final BidanService bidanService;
    private final Cache<String> cache;
    private final Cache<BidanHomeContext> nativeCache;
    private static final String BIDAN_HOME_CONTEXT = "bidanHomeContext";
    private static final String NATIVE_BIDAN_HOME_CONTEXT = "bidanNativeHomeContext";


    public BidanController(BidanService bidanService, Cache<String> cache, Cache<BidanHomeContext> homeContextCache) {
        this.bidanService = bidanService;
        this.cache = cache;
        this.nativeCache = homeContextCache;
    }

    public String get() {
        return cache.get(BIDAN_HOME_CONTEXT, new CacheableData<String>() {
            @Override
            public String fetch() {
                return new Gson().toJson(new BidanHomeContext(bidanService.fetchDetails()));
            }
        });
    }

    public BidanHomeContext getBidanHomeContext() {
        return nativeCache.get(NATIVE_BIDAN_HOME_CONTEXT, new CacheableData<BidanHomeContext>() {
            @Override
            public BidanHomeContext fetch() {
                return new BidanHomeContext(bidanService.fetchDetails());
            }
        });
    }
}
