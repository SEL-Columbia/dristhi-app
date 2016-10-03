package org.ei.opensrp.sync;

import android.test.AndroidTestCase;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by keyman on 03/10/16.
 */
public class BaseClientProcessorTest extends AndroidTestCase {

    protected static final String baseEntityIdJSONKey = "baseEntityId";

    protected static final String detailsUpdated = "detailsUpdated";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        System.setProperty("dexmaker.dexcache", getContext().getCacheDir().getPath());
    }

    protected JSONObject createEmptyJsonObject() {
        try {
            return new JSONObject("{}");

        } catch (JSONException e) {
            return null;
        }
    }

    protected JSONObject createJsonObject(String key, Object value) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(key, value);
            return jsonObject;
        } catch (JSONException e) {
            return null;
        }
    }

    protected JSONObject updateJsonObject(JSONObject jsonObject, String key, Object value) {
        try {
            if (jsonObject == null) {
                jsonObject = new JSONObject();
            }
            jsonObject.put(key, value);
            return jsonObject;
        } catch (JSONException e) {
            return null;
        }
    }

    protected String getBaseEntityId() {
        return String.valueOf(DateTime.now().getMillis());
    }

    protected JSONObject createClient() {
        return createJsonObject(baseEntityIdJSONKey, getBaseEntityId());
    }

    protected JSONObject createEvent(boolean details) {
        JSONObject event = createJsonObject(baseEntityIdJSONKey, getBaseEntityId());
        if(details) {
            updateJsonObject(event, detailsUpdated, true);
        }
        return event;
    }

    protected JSONObject createClassification(){
        JSONObject comment = createJsonObject("comment", "Test Comment");
        JSONObject rule = createJsonObject("rule", "{}");
        JSONArray rules = new JSONArray();
        rules.put(comment);
        rules.put(rule);

        JSONObject classification = createJsonObject("case_classification_rules", rules);
        return classification;
    }

}