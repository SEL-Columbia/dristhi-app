package org.ei.opensrp.sync;

import android.content.Context;
import android.util.Log;

import com.cloudant.sync.datastore.BasicDocumentRevision;
import com.cloudant.sync.datastore.DocumentBody;
import com.cloudant.sync.datastore.DocumentRevision;
import com.cloudant.sync.query.IndexManager;
import com.cloudant.sync.query.QueryResult;

import org.ei.opensrp.R;
import org.ei.opensrp.clientandeventmodel.processor.ClientsProcessor;
import org.ei.opensrp.clientandeventmodel.processor.EventsProcessor;
import org.ei.opensrp.cloudant.models.Client;
import org.ei.opensrp.repository.ClientRepository;
import org.ei.opensrp.repository.EventRepository;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by onamacuser on 11/03/2016.
 */

public class CloudantDataHandler extends ReplicationService {
    private static final String TAG = CloudantDataHandler.class.getCanonicalName();
    private static CloudantDataHandler instance;

    public CloudantDataHandler(Context context) throws Exception {
        this(context, null);
        instance = this;
    }

    public CloudantDataHandler(Context context, ReplicationListenerCallback _callback) throws Exception {
        super(context, _callback);
        instance = this;
    }

    private CountDownLatch latch = null;

    public static CloudantDataHandler getInstance(Context context) throws Exception {
        if (instance == null){
            instance = new CloudantDataHandler(context);
        }
        return instance;
    }

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

    public void syncEventsToSqlite() throws Exception{
        Map<String, Object> query = new HashMap<String, Object>();
        query.put("type", "Event");
        QueryResult result = indexManager.find(query);

        for (DocumentRevision rev : result) {
            EventRepository EventRepo = new EventRepository(mContext, "events", new String[]{"father_name", "voided", "providerId"});
            Log.v("Tag json", rev.getBody().toString());
            try {
                EventsProcessor eventsProcessor = new EventsProcessor(new JSONObject(mContext.getResources().getString(R.string.event_fields_extractor)), new JSONObject(rev.getBody().toString()));
                EventRepo.insertValues(EventRepo.createValuesFor(eventsProcessor.createEventObject()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void syncClientsToSqlite() throws Exception{
        Map<String, Object> query = new HashMap<String, Object>();
        query.put("type", "Client");
        int querysize = indexManager.find(query).size();
        Log.v("TAG TAG", "" + querysize);
        QueryResult result = indexManager.find(query);

        for (DocumentRevision rev : result) {
            ClientRepository clientRepo = new ClientRepository(mContext, new String[]{"firstName"});
            Log.v("Tag json", rev.getBody().toString());
            try {
                ClientsProcessor clientsProcessor = new ClientsProcessor(new JSONObject(mContext.getResources().getString(R.string.client_fields_extractor)), new JSONObject(rev.getBody().toString()));
                clientRepo.insertValues(clientRepo.createValuesFor(clientsProcessor.createClientObject()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public Client getClientByBaseEntityId(String baseEntityId){
        Map<String, Object> query = new HashMap<String, Object>();
        query.put("type", "Client");
        query.put("base_entity_id", baseEntityId);
        int querysize = indexManager.find(query).size();
        Log.v("TAG TAG", "" + querysize);
        QueryResult result = indexManager.find(query);

        Iterator<DocumentRevision> it = result.iterator();
        if (it.hasNext()){
            DocumentRevision rev = it.next();
            Client client = Client.fromRevision((BasicDocumentRevision)rev);
            return client;
        }
        return null;
    }

}
