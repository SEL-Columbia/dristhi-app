package org.ei.opensrp.service.intentservices;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import org.ei.opensrp.sync.CloudantSyncHandler;


/**
 * Created by onamacuser on 18/03/2016.
 */
public class ReplicationIntentService extends IntentService {
    private static final String TAG = ReplicationIntentService.class.getCanonicalName();

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public ReplicationIntentService(String name) {
        super(name);
    }

    public ReplicationIntentService() {

        super("ReplicationIntentService");

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            CloudantSyncHandler cloudantSyncHandler = CloudantSyncHandler.getInstance(getApplicationContext());
            cloudantSyncHandler.startPushReplication();
            cloudantSyncHandler.startPullReplication();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

    }
}
