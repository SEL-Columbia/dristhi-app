package org.ei.telemedicine.sync;

import org.ei.telemedicine.domain.FetchStatus;

import static org.ei.telemedicine.event.Event.ON_DATA_FETCHED;

public class SyncAfterFetchListener implements AfterFetchListener {
    public void afterFetch(FetchStatus status) {
        ON_DATA_FETCHED.notifyListeners(status);
    }
}
