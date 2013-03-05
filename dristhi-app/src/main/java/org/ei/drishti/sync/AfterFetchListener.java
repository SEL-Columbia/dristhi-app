package org.ei.drishti.sync;

import org.ei.drishti.domain.FetchStatus;

public interface AfterFetchListener {
    void afterFetch(FetchStatus fetchStatus);
}
