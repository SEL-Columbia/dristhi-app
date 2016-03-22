package org.ei.opensrp.sync;

import android.content.Context;
import android.util.Log;

import com.cloudant.sync.datastore.Datastore;
import com.cloudant.sync.datastore.DatastoreManager;
import com.cloudant.sync.query.IndexManager;
import com.google.common.eventbus.EventBus;

import org.ei.opensrp.AllConstants;
import org.ei.opensrp.R;

import java.io.File;
import java.net.URI;
import java.util.concurrent.CountDownLatch;


/**
 * Created by onamacuser on 11/03/2016.
 */

public class ReplicationService {
    protected final Context mContext;

    protected Datastore dataStore;
    private static final String TAG = ReplicationService.class.getCanonicalName();
    protected DatastoreManager manager;
    protected String dbURL = "", dataStoreName = "";
    protected EventBus replicationEventBus;
    protected CountDownLatch countDownLatch;
    protected ReplicationListener listener;
    protected IndexManager indexManager;

    public ReplicationService(Context context, ReplicationListenerCallback callback) throws Exception {

        this.mContext = context;

        dataStoreName = mContext.getString(R.string.datastore_name);
        // Set up our tasks datastore within its own folder in the applications
        // data directory.
        File path = this.mContext.getApplicationContext().getDir(
                AllConstants.DATASTORE_MANAGER_DIR,
                Context.MODE_PRIVATE
        );
        manager = new DatastoreManager(path.getAbsolutePath());

        countDownLatch = new CountDownLatch(1);
        listener = new ReplicationListener(countDownLatch, this.mContext,callback);
        replicationEventBus = manager.getEventBus();
        manager.getEventBus().register(listener);

        dataStore = manager.openDatastore(dataStoreName);
        indexManager = new IndexManager(dataStore);

        Log.d(TAG, "Set up database at " + path.getAbsolutePath());
    }


    protected URI getURI() throws Exception {
        dbURL = mContext.getString(R.string.couchdb_server_url).concat(":").concat(mContext.getString(R.string.couchdb_server_port)).concat("/").concat(mContext.getString(R.string.couchdb_dbname));
        URI uri = new URI(dbURL);
        return uri;
    }


}
