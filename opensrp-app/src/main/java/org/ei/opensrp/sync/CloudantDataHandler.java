package org.ei.opensrp.sync;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.util.Log;

import com.cloudant.sync.datastore.BasicDocumentRevision;
import com.cloudant.sync.datastore.ConflictException;
import com.cloudant.sync.datastore.Datastore;
import com.cloudant.sync.datastore.DatastoreManager;
import com.cloudant.sync.datastore.DocumentBody;
import com.cloudant.sync.datastore.DocumentBodyFactory;
import com.cloudant.sync.datastore.DocumentException;
import com.cloudant.sync.datastore.DocumentRevision;
import com.cloudant.sync.datastore.MutableDocumentRevision;
import com.cloudant.sync.query.IndexManager;
import com.cloudant.sync.query.QueryResult;

import org.ei.opensrp.AllConstants;
import org.ei.opensrp.R;
import org.ei.opensrp.clientandeventmodel.processor.ClientsProcessor;
import org.ei.opensrp.clientandeventmodel.processor.EventsProcessor;
import org.ei.opensrp.cloudant.models.Client;
import org.ei.opensrp.cloudant.models.Event;
import org.ei.opensrp.repository.AllSharedPreferences;
import org.ei.opensrp.repository.ClientRepository;
import org.ei.opensrp.repository.EventRepository;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Handles Cloudant data access methods
 * Created by onamacuser on 11/03/2016.
 */

public class CloudantDataHandler {
    private static final String TAG = CloudantDataHandler.class.getCanonicalName();

    private static CloudantDataHandler instance;

    private static final String baseEntityIdJSONKey = "baseEntityId";
    List<String> fields = Arrays.asList("obs", "eventDate", "eventType", "formSubmissionId", "provider", baseEntityIdJSONKey, "type", "entityType", "version");

    private static final String DATASTORE_MANGER_DIR = "data";
    private static final String DATASTORE_NAME = "opensrp_clients_events";

    private final Context mContext;
    private final Datastore mDatastore;
    private final IndexManager mIndexManager;

    public CloudantDataHandler(Context context) throws Exception {
        this.mContext = context;


        // Set up our datastore within its own folder in the applications data directory.
        File path = this.mContext.getApplicationContext().getDir(DATASTORE_MANGER_DIR, Context.MODE_PRIVATE);
        DatastoreManager manager = new DatastoreManager(path.getAbsolutePath());
        this.mDatastore = manager.openDatastore(DATASTORE_NAME);
        this.mIndexManager = new IndexManager(mDatastore);
        List<Object> indexFields = new ArrayList<>();
        indexFields.add("version");
        this.mIndexManager.ensureIndexed(indexFields, "eventdocindex");

        Log.d(TAG, "Set up database at " + path.getAbsolutePath());

        instance = this;

    }

    private CountDownLatch latch = null;

    public static CloudantDataHandler getInstance(Context context) throws Exception {
        if (instance == null) {
            instance = new CloudantDataHandler(context);
        }
        return instance;
    }

    public void getClients() throws Exception {


        Map<String, Object> query = new HashMap<String, Object>();
        query.put("type", "Client");

        QueryResult result = this.mIndexManager.find(query);

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

    public org.ei.opensrp.clientandeventmodel.Event getEvents() throws Exception {


        Map<String, Object> query = new HashMap<String, Object>();
        query.put("type", "Event");

        QueryResult result = this.mIndexManager.find(query);

        Iterator<DocumentRevision> it = result.iterator();
        if (it.hasNext()) {
            DocumentRevision rev = it.next();
            org.ei.opensrp.clientandeventmodel.Event event = Event.fromRevision((BasicDocumentRevision) rev);

            return event;
        }
        return null;
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

        QueryResult result = this.mIndexManager.find(query);
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

    public List<JSONObject> getUpdatedEventsDocs() throws Exception {

        List<JSONObject> events = new ArrayList<JSONObject>();

        Map<String, Object> query = new HashMap<String, Object>();

        List<Map<String, String>> sortDocument = new ArrayList<Map<String, String>>();

        Map<String, String> sortByVersion = new HashMap<String, String>();
        sortByVersion.put("version", "asc");
        sortDocument.add(sortByVersion);

        Map<String, Object> filterByVersion = new HashMap<String, Object>();
        filterByVersion.put("$gt", 0);

        query.put("version", filterByVersion);
        query.put("type", "Event");

        QueryResult result = this.mIndexManager.find(query, 0, 0, fields, sortDocument);

        if (result != null) {
            for (DocumentRevision rev : result) {
                DocumentBody doc = rev.getBody();
                String docstr = doc.toString();
                JSONObject object = new JSONObject(docstr);
                events.add(object);
            }
        }

        return events;
    }

    public void syncEventsToSqlite() throws Exception {
        Map<String, Object> query = new HashMap<String, Object>();
        query.put("type", "Event");
        QueryResult result = this.mIndexManager.find(query);

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

    public void syncClientsToSqlite() throws Exception {
        Map<String, Object> query = new HashMap<String, Object>();
        query.put("type", "Client");
        int querysize = this.mIndexManager.find(query).size();
        Log.v("TAG TAG", "" + querysize);
        QueryResult result = this.mIndexManager.find(query);

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

    public JSONObject getClientByBaseEntityId(String baseEntityId) throws Exception {
        try {
            Client client = getClientDocumentByBaseEntityId(baseEntityId);

            if (client != null) {
                SQLiteDatabase db = loadDatabase();
                Cursor cursor = db.rawQuery("select json from revs r inner join docs d on r.doc_id=d.doc_id where d.docid='" + client.getDocumentRevision().getId() + "' and json not like '{}' order by updated_at desc", null);
                if (cursor != null && cursor.moveToFirst()) {
                    byte[] json = (cursor.getBlob(0));
                    String jsonEventStr = new String(json, "UTF-8");
                    JSONObject jsonObectClient = new JSONObject(jsonEventStr);
                    return jsonObectClient;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Client getClientDocumentByBaseEntityId(String baseEntityId) throws Exception {
        Map<String, Object> query = new HashMap<String, Object>();
        query.put("type", "Client");
        query.put(baseEntityIdJSONKey, baseEntityId);
        Iterator<DocumentRevision> iterator = this.mIndexManager.find(query).iterator();

        if (iterator != null && iterator.hasNext()) {
            DocumentRevision rev = iterator.next();
            Client c = Client.fromRevision((BasicDocumentRevision) rev);
            return c;
        }

        return null;
    }

    public List<JSONObject> getUpdatedEvents() throws Exception {

        SharedPreferences preferences  = PreferenceManager.getDefaultSharedPreferences(mContext);
        AllSharedPreferences allSharedPreferences = new AllSharedPreferences(preferences);
        long lastSyncTimeStamp = allSharedPreferences.fetchLastSyncDate(0);
        Date lastSyncDate = new Date(lastSyncTimeStamp);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String lastSyncString = df.format(lastSyncDate);

        List<JSONObject> events = new ArrayList<JSONObject>();
        SQLiteDatabase db = loadDatabase();
        Cursor cursor = db.rawQuery("select json, updated_at from revs where updated_at is not null and updated_at > \"" + lastSyncString + "\" and json not like '{}' and json like '%\"type\":\"Event\"%' order by updated_at asc ", null);


        while (cursor.moveToNext()) {
            byte[] json = (cursor.getBlob(0));

            try {
                lastSyncDate = df.parse(cursor.getString(1));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String jsonEventStr = new String(json, "UTF-8");
            JSONObject jsonObectEvent = new JSONObject(jsonEventStr);
            events.add(jsonObectEvent);
        }

        //TODO Use couch db filters
        Collections.sort(events, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject lhs, JSONObject rhs) {
                try {
                    if (lhs.getLong("version") > rhs.getLong("version")) {
                        return 1;
                    }else if(lhs.getLong("version") < rhs.getLong("version")){
                        return -1;
                    }
                    return 0;
                }catch (JSONException e){
                    return 0;
                }
            }
        });

        allSharedPreferences.saveLastSyncDate(lastSyncDate.getTime());
        return events;
    }

    //load cloudant db
    public SQLiteDatabase loadDatabase() {
        SQLiteDatabase db = null;
        try {
            String dataStoreName = mContext.getString(R.string.datastore_name);
            // data directory.
            File path = mContext.getDir(
                    AllConstants.DATASTORE_MANAGER_DIR,
                    android.content.Context.MODE_PRIVATE
            );
            String dbpath = path.getAbsolutePath().concat(File.separator).concat(dataStoreName).concat(File.separator).concat("db.sync");
            db = SQLiteDatabase.openDatabase(dbpath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
            // ((HelloCloudantApplication) this.getApplication()).setCloudantDB(db);


        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return db;
    }

    /**
     * Updates a Client document within the datastore.
     *
     * @param client client to update
     * @return the updated revision of the Client
     * @throws ConflictException if the client passed in has a rev which doesn't
     *                           match the current rev in the datastore.
     */
    public Client updateDocument(Client client) throws ConflictException {
        MutableDocumentRevision rev = client.getDocumentRevision().mutableCopy();
        rev.body = DocumentBodyFactory.create(client.asMap());
        try {
            BasicDocumentRevision updated = this.mDatastore.updateDocumentFromRevision(rev);
            return Client.fromRevision(updated);
        } catch (DocumentException de) {
            return null;
        }
    }

    //
    // DOCUMENT CRUD
    //

    /**
     * Creates a Client, assigning an ID.
     *
     * @param client Client to create
     * @return new revision of the document
     */
    public Client createClientDocument(Client client) {
        MutableDocumentRevision rev = new MutableDocumentRevision();
        rev.body = DocumentBodyFactory.create(client.asMap());
        try {
            //save the model only once if it already exist ignore, or merge the document
            Client c = getClientDocumentByBaseEntityId(client.getBaseEntityId());

            if (c == null) {
                BasicDocumentRevision created = this.mDatastore.createDocumentFromRevision(rev);
                return Client.fromRevision(created);
            } else {
                //TODO: merge/update the client document
                return c;
            }

        } catch (Exception e) {
            Log.e(TAG, e.toString(), e);
        }

        return null;
    }

    /**
     * Creates a Event, assigning an ID.
     *
     * @param event Client to create
     * @return new revision of the document
     */
    public Event createEventDocument(Event event) {
        MutableDocumentRevision rev = new MutableDocumentRevision();
        rev.body = DocumentBodyFactory.create(event.asMap());
        try {
            BasicDocumentRevision created = this.mDatastore.createDocumentFromRevision(rev);
            return Event.fromRevision(created);
        } catch (DocumentException de) {
            return null;
        }
    }

    /**
     * Deletes a Client document within the datastore.
     *
     * @param client client to delete
     * @throws ConflictException if the client passed in has a rev which doesn't
     *                           match the current rev in the datastore.
     */
    public void deleteDocument(Client client) throws ConflictException {
        this.mDatastore.deleteDocumentFromRevision(client.getDocumentRevision());
    }

    /**
     * <p>Returns all {@code Client} documents in the datastore.</p>
     */
    public List<Client> allClients() {
        int nDocs = this.mDatastore.getDocumentCount();
        List<BasicDocumentRevision> all = this.mDatastore.getAllDocuments(0, nDocs, true);
        List<Client> clients = new ArrayList<Client>();
        // Filter all documents down to those of type client.
        for (BasicDocumentRevision rev : all) {
            Client client = Client.fromRevision(rev);
            if (client != null) {
                clients.add(client);
            }
        }

        return clients;
    }

    public Datastore getDatastore() {
        return mDatastore;
    }
}
