package org.ei.opensrp.indonesia.view.controller;

import android.support.annotation.Nullable;

import org.ei.opensrp.indonesia.repository.AllSettingsINA;
import org.ei.opensrp.indonesia.repository.UniqueIdRepository;
import org.ei.opensrp.util.Cache;
import org.ei.opensrp.util.CacheableData;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Dimas on 9/8/2015.
 */
public class UniqueIdController {

    private AllSettingsINA allSettings;
    private UniqueIdRepository uniqueIdRepository;
    private Cache<List<Integer>> cache;

    public UniqueIdController(UniqueIdRepository uniqueIdRepository, AllSettingsINA allSettings, Cache<List<Integer>> cache) {
        this.uniqueIdRepository = uniqueIdRepository;
        this.allSettings = allSettings;
        this.cache = cache;
    }

    public String getUniqueId() {
        List<Integer> uids = getAllUniqueId();
        return processLatestUniqueId(uids);
    }

    public String getUniqueIdJson() {
        String uniqueId = getUniqueId();
        if(uniqueId == null || uniqueId.isEmpty()) {
            return null;
        }
        JSONObject obj = new JSONObject();
        try {
            obj.put("unique_id", uniqueId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj.toString();
    }

    public void updateCurrentUniqueId(String id) {
        if(Integer.parseInt(allSettings.fetchCurrentId()) < Integer.parseInt(id)) {
            allSettings.saveCurrentId(id);
        }
    }

    public List<Integer> getAllUniqueId() {
        return cache.get("UNIQUE_ID_LIST", new CacheableData<List<Integer>>() {
            @Override
            public List<Integer> fetch() {
                return uniqueIdRepository.getAllUniqueId();
            }
        });
    }

    public void saveUniqueId(String uniqueId) {
        uniqueIdRepository.saveUniqueId(uniqueId);
    }

    public boolean needToRefillUniqueId() {
        List<Integer> uids = getAllUniqueId();
        int currentId = Integer.parseInt(allSettings.fetchCurrentId())/10;
        return (uids.get(uids.size()-1)/10) - currentId <= uids.size()/4;
    }

    // Class for testing

    public boolean needToRefillUniqueIdTest() {
        List<Integer> uids = uniqueIdRepository.getAllUniqueId();
        int currentId = Integer.parseInt(allSettings.fetchCurrentId())/10;
        return (uids.get(uids.size()-1)/10) - currentId <= uids.size()/4;
    }

    public String getUniqueIdTest() {
        List<Integer> uids = uniqueIdRepository.getAllUniqueId();
        return processLatestUniqueId(uids);
    }

    @Nullable
    private String processLatestUniqueId(List<Integer> uids) {
        int currentId = Integer.parseInt(allSettings.fetchCurrentId());
        if(uids == null || uids.isEmpty() || currentId > uids.get(uids.size()-1)) {
            return null;
        }
        int index = Arrays.binarySearch(uids.toArray(), currentId);
        if(index<-1) index = -1;
        return index >= uids.size()-1 ? null : "" + uids.get(index+1);
    }

}
