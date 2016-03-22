package org.ei.opensrp.sync;

import com.cloudant.sync.replication.ErrorInfo;

/**
 * A {@code ReplicationListener} Fired by the replication listener when it receives events from the push/pull replicators
 * replication has finished.
 */

public interface ReplicationListenerCallback {
    void replicationCompleted(int documentsReplicated, int batchesReplicated);

    void replicationFailed(ErrorInfo error);
}
