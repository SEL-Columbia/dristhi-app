package org.ei.telemedicine.sync;

import org.ei.telemedicine.domain.FetchStatus;

public interface AfterFetchListener {
    void afterFetch(FetchStatus fetchStatus);
}
