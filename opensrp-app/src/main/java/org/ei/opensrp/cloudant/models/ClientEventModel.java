package org.ei.opensrp.cloudant.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;

import com.cloudant.sync.datastore.BasicDocumentRevision;
import com.cloudant.sync.datastore.ConflictException;
import com.cloudant.sync.datastore.Datastore;
import com.cloudant.sync.datastore.DatastoreManager;
import com.cloudant.sync.datastore.DatastoreNotCreatedException;
import com.cloudant.sync.datastore.DocumentBodyFactory;
import com.cloudant.sync.datastore.DocumentException;
import com.cloudant.sync.datastore.MutableDocumentRevision;
import com.cloudant.sync.notifications.ReplicationCompleted;
import com.cloudant.sync.notifications.ReplicationErrored;
import com.cloudant.sync.query.IndexManager;
import com.cloudant.sync.replication.Replicator;
import com.cloudant.sync.replication.ReplicatorBuilder;
import com.google.common.eventbus.Subscribe;

import org.ei.opensrp.AllConstants;
import org.ei.opensrp.repository.AllSharedPreferences;
import org.ei.opensrp.sync.ClientProcessor;
import org.ei.opensrp.sync.CloudantDataHandler;
import org.ei.opensrp.view.activity.SecuredActivity;
import org.json.JSONObject;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by koros on 3/16/16.
 */
public class ClientEventModel {
    private static final String LOG_TAG = "ClientEventModel";

    private static final String DATASTORE_MANGER_DIR = "data";
    private static final String DATASTORE_NAME = "opensrp_clients_events";

    private Datastore mDatastore;

    private Replicator mPushReplicator;
    private Replicator mPullReplicator;

    private final Context mContext;
    private final Handler mHandler;
    private SecuredActivity mListener;

    private String dbURL;

    private static ClientEventModel instance;

    private CloudantDataHandler mCloudantDataHandler;

    public static ClientEventModel getInstance(Context context){
        if (instance == null){
            instance = new ClientEventModel(context);
        }
        return instance;
    }

    public ClientEventModel(Context context) {
        this.mContext = context;
        // Allow us to switch code called by the ReplicationListener into
        // the main thread so the UI can update safely.
        this.mHandler = new Handler(Looper.getMainLooper());
        try {
            // Set up our datastore within its own folder in the applications data directory.
            File path = this.mContext.getApplicationContext().getDir(DATASTORE_MANGER_DIR, Context.MODE_PRIVATE);
            DatastoreManager manager = new DatastoreManager(path.getAbsolutePath());
            this.mDatastore = manager.openDatastore(DATASTORE_NAME);
            IndexManager indexManager = new IndexManager(mDatastore);
            List<Object> indexFields = new ArrayList<>();
            indexFields.add("version");
            indexManager.ensureIndexed(indexFields, "eventdocindex");

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.mContext);
            AllSharedPreferences allSharedPreferences = new AllSharedPreferences(preferences);
            String port = AllConstants.CloudantSync.COUCHDB_PORT;
            String databaseName = AllConstants.CloudantSync.COUCH_DATABASE_NAME;
            dbURL = allSharedPreferences.fetchHost("").concat(":").concat(port).concat("/").concat(databaseName);


            this.reloadReplicationSettings();
            this.mCloudantDataHandler = CloudantDataHandler.getInstance(mContext.getApplicationContext());
            Log.d(LOG_TAG, "Set up database at " + path.getAbsolutePath());

        }catch (URISyntaxException e) {
            Log.e(LOG_TAG, "Unable to construct remote URI from configuration", e);
        }catch (DatastoreNotCreatedException dnce) {
            Log.e(LOG_TAG, "Unable to open Datastore", dnce);
        }catch (Exception e) {
            Log.e(LOG_TAG, "Exception While setting up datastore", e);
        }

    }

    //
    // GETTERS AND SETTERS
    //

    /**
     * Sets the listener for replication callbacks as a weak reference.
     * @param listener {@link SecuredActivity} to receive callbacks.
     */
    public void setReplicationListener(SecuredActivity listener) {
        this.mListener = listener;
    }

    //
    // DOCUMENT CRUD
    //
    /**
     * Creates a Client, assigning an ID.
     * @param client Client to create
     * @return new revision of the document
     */
    public Client createClientDocument(Client client) {
        MutableDocumentRevision rev = new MutableDocumentRevision();
        rev.body = DocumentBodyFactory.create(client.asMap());
        try {
            //save the model only once if it already exist ignore, or merge the document
            Client c = mCloudantDataHandler.getClientDocumentByBaseEntityId(client.getBaseEntityId());

            if (c == null){
                BasicDocumentRevision created = this.mDatastore.createDocumentFromRevision(rev);
                return Client.fromRevision(created);
            }
            else{
                //TODO: merge/update the client document
                return c;
            }

        } catch (Exception e) {
            Log.e(LOG_TAG, e.toString(), e);
        }

        return null;
    }

    /**
     * Creates a Event, assigning an ID.
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
     * @param client client to delete
     * @throws ConflictException if the client passed in has a rev which doesn't
     *      match the current rev in the datastore.
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
        for(BasicDocumentRevision rev : all) {
            Client client = Client.fromRevision(rev);
            if (client != null) {
                clients.add(client);
            }
        }

        return clients;
    }

    //
    // MANAGE REPLICATIONS
    //
    /**
     * <p>Stops running replications.</p>
     *
     * <p>The stop() methods stops the replications asynchronously, see the
     * replicator docs for more information.</p>
     */
    public void stopAllReplications() {
        if (this.mPullReplicator != null) {
            this.mPullReplicator.stop();
        }
        if (this.mPushReplicator != null) {
            this.mPushReplicator.stop();
        }
    }

    /**
     * <p>Starts the configured push replication.</p>
     */
    public void startPushReplication() {
        if (this.mPushReplicator != null) {
            this.mPushReplicator.start();
        } else {
            throw new RuntimeException("Push replication not set up correctly");
        }
    }

    /**
     * <p>Starts the configured pull replication.</p>
     */
    public void startPullReplication() {
        if (this.mPullReplicator != null) {
            this.mPullReplicator.start();
        } else {
            throw new RuntimeException("Push replication not set up correctly");
        }
    }

    /**
     * <p>Stops running replications and reloads the replication settings from
     * the app's preferences.</p>
     */
    public void reloadReplicationSettings() throws URISyntaxException {
        this.stopAllReplications();

        // Set up the new replicator objects
        URI uri = this.createServerURI();

        mPullReplicator = ReplicatorBuilder.pull().to(mDatastore).from(uri).build();
        mPushReplicator = ReplicatorBuilder.push().from(mDatastore).to(uri).build();

        mPushReplicator.getEventBus().register(this);
        mPullReplicator.getEventBus().register(this);

        Log.d(LOG_TAG, "Set up replicators for URI:" + uri.toString());
    }

    /**
     * <p>Returns the URI for the remote database, based on the app's
     * configuration.</p>
     * @return the remote database's URI
     * @throws URISyntaxException if the settings give an invalid URI
     */
    private URI createServerURI() throws URISyntaxException {
        // We recommend always using HTTPS to talk to Cloudant.
        return new URI(dbURL);
    }

    //
    // REPLICATIONLISTENER IMPLEMENTATION
    //
    /**
     * Calls the SecuredActivity's replicationComplete method on the main thread,
     * as the complete() callback will probably come from a replicator worker
     * thread.
     */
    @Subscribe
    public void complete(ReplicationCompleted rc) {
        // Call the logic to break down CE into case models
        try{
            ClientProcessor.getInstance(mContext.getApplicationContext()).processClient();
        }catch (Exception e){
            Log.e(LOG_TAG, e.toString(), e);
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mListener != null) {
                    mListener.replicationComplete();
                }
            }
        });
    }

    /**
     * Calls the SecuredActivity's replicationComplete method on the main thread,
     * as the error() callback will probably come from a replicator worker
     * thread.
     */
    @Subscribe
    public void error(ReplicationErrored re) {
        Log.e(LOG_TAG, "Replication error:", re.errorInfo.getException());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mListener != null) {
                    mListener.replicationError();
                }
            }
        });
    }
}
