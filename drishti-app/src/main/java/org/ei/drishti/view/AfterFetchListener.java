package org.ei.drishti.view;

import org.ei.drishti.domain.FetchStatus;

public interface AfterFetchListener {
    void afterFetch(FetchStatus fetchStatus);
}
