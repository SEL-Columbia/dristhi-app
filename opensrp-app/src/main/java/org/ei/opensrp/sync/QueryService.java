package org.ei.opensrp.sync;

import android.content.Context;
import android.util.Log;

import com.cloudant.sync.datastore.DocumentBody;
import com.cloudant.sync.datastore.DocumentRevision;
import com.cloudant.sync.query.QueryResult;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by onamacuser on 11/03/2016.
 */

public class QueryService extends ReplicationService {
    private static final String TAG = QueryService.class.getCanonicalName();

    public QueryService(Context context, ReplicationListenerCallback _callback) throws Exception {

        super(context, _callback);
    }

    private CountDownLatch latch = null;


    public void getClients() throws Exception {


        Map<String, Object> query = new HashMap<String, Object>();
        query.put("type", "Client");

        QueryResult result = indexManager.find(query);

        for (DocumentRevision rev : result) {
            DocumentBody doc = rev.getBody();
            Map<String, Object> map = doc.asMap();
            if (map.containsKey("type") && map.get("type").equals("Alert")) {
                Log.d("ID", String.valueOf(map.get("type")));
                Log.d("TYPE", String.valueOf(map.get("entityId")));
            }

            // The returned revision object contains all fields for
            // the object. You cannot project certain fields in the
            // current implementation.
        }
    }

    public void getUpdatedDocs() throws Exception {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

        Date lastSyncDate = df.parse("09-03-2016 12:00:00");
// query: { "timestamp": { "$gt": 12 } }
        Map<String, Object> query = new HashMap<String, Object>();
        Map<String, Object> gttimestamp = new HashMap<String, Object>();
        long timestamp = lastSyncDate.getTime();

        gttimestamp.put("$gt", "2016-03-16 09:37:46");
        query.put("providerId", "demotest");

        QueryResult result = indexManager.find(query);
//        int size = result.size();
//        Log.d("TAG", "" + size);

        for (DocumentRevision rev : result) {
            DocumentBody doc = rev.getBody();
            Map<String, Object> map = doc.asMap();
            if (map.containsKey("providerId") && map.get("providerId").toString().equalsIgnoreCase("demoprovideridtimestamp")) {
                Log.d("TIMESTAMP", String.valueOf(map.get("timestamp")));
            }
        }

    }
}
