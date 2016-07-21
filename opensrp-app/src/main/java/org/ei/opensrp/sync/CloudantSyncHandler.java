package org.ei.opensrp.sync;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;

import com.cloudant.sync.datastore.Datastore;
import com.cloudant.sync.notifications.ReplicationCompleted;
import com.cloudant.sync.notifications.ReplicationErrored;
import com.cloudant.sync.replication.Replicator;
import com.cloudant.sync.replication.ReplicatorBuilder;
import com.google.common.eventbus.Subscribe;

import org.ei.opensrp.AllConstants;
import org.ei.opensrp.repository.AllSharedPreferences;
import org.ei.opensrp.view.activity.SecuredActivity;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Handles cloundant replication/sync processes.
 * Created by koros on 3/16/16.
 */
public class CloudantSyncHandler {
    private static final String LOG_TAG = "CloudantSyncHandler";


    private Replicator mPushReplicator;
    private Replicator mPullReplicator;

    private final Context mContext;
    private final Handler mHandler;
    private SecuredActivity mListener;

    private String dbURL;

    private static CloudantSyncHandler instance;

    public static CloudantSyncHandler getInstance(Context context){
        if (instance == null){
            instance = new CloudantSyncHandler(context);
        }
        return instance;
    }

    public CloudantSyncHandler(Context context) {
        this.mContext = context;
        // Allow us to switch code called by the ReplicationListener into
        // the main thread so the UI can update safely.
        this.mHandler = new Handler(Looper.getMainLooper());
        try {

            // Retrieve database host from preferences
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.mContext);
            AllSharedPreferences allSharedPreferences = new AllSharedPreferences(preferences);
            String port = AllConstants.CloudantSync.COUCHDB_PORT;
            String databaseName = AllConstants.CloudantSync.COUCH_DATABASE_NAME;
            dbURL = allSharedPreferences.fetchHost("").concat(":").concat(port).concat("/").concat(databaseName);

            this.reloadReplicationSettings();

        }catch (URISyntaxException e) {
            Log.e(LOG_TAG, "Unable to construct remote URI from configuration", e);
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
    public void reloadReplicationSettings() throws URISyntaxException, Exception {
        this.stopAllReplications();

        // Set up the new replicator objects
        URI uri = this.createServerURI();

        CloudantDataHandler mCloudantDataHandler = CloudantDataHandler.getInstance(mContext);
        Datastore mDatastore = mCloudantDataHandler.getDatastore();

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
