package org.ei.opensrp.sync;

import android.content.Context;

import com.cloudant.sync.replication.PullFilter;
import com.cloudant.sync.replication.Replicator;
import com.cloudant.sync.replication.ReplicatorBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by onamacuser on 11/03/2016.
 */

public class PullPushService extends ReplicationService {

    private static final String TAG = PullPushService.class.getCanonicalName();


    public PullPushService(Context context, ReplicationListenerCallback _callback) throws Exception {
        super(context, _callback);
    }

    public void push() throws Exception {

// Create a replicator that replicates changes from the local
// datastore to the remote database.
        Replicator replicator = ReplicatorBuilder.push().to(getURI()).from(dataStore).build();

        replicator.getEventBus().register(listener);
        replicator.start();


    }

    public void pull() throws Exception {

// Create a replicator that replicates changes from the remote
// database to the local datastore.
        Replicator replicator = ReplicatorBuilder.pull().from(getURI()).to(dataStore).build();

        replicator.getEventBus().register(listener);
        replicator.start();
    }

    public void pushpull() throws Exception {

// Create the pull replicator
        Replicator pullReplicator = ReplicatorBuilder.pull().from(getURI()).to(dataStore).build();

// Create the push replicator
        Replicator pushReplicator = ReplicatorBuilder.push().to(getURI()).from(dataStore).build();


// Set the listener and start for both pull and push replications
        pullReplicator.getEventBus().register(listener);
        pullReplicator.start();
        pushReplicator.getEventBus().register(listener);
        pushReplicator.start();

    }

    public void filteredPull(String filterName, Map<String, String> filterParams) throws Exception {
        //SERVER side filter definition sample - filters by type and locationid
        //filter name is e.g myfilter/sampletimestampfilter
//        {
//            "_id": "_design/myfilter",
//                "_rev": "2-df256e589a6f16d635ddf4b575fbb006",
//                "filters": {
//            "samplefilter": "function(doc, req){if (doc.type != req.query.type){return false;}if (doc.locationId != req.query.locationId){return false;}return true;}"
//        }
//        }

        if (filterParams == null) {
            filterParams = new HashMap<String, String>();
        }
        PullFilter filter = new PullFilter(filterName, filterParams);
        Replicator replicator = ReplicatorBuilder.pull()
                .from(getURI())
                .to(dataStore)
                .filter(filter)
                .build();

        replicator.start();

        replicator.getEventBus().register(listener);

        // function(doc) { if(doc.type && doc.type == 'Event' && doc.locationId&& doc.locationId=='korangi') { emit(null, { 'id': doc._id }); }  }
    }

}
