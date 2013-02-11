package org.ei.drishti.util;

import android.app.Activity;
import android.content.Context;
import org.ei.drishti.service.CommCareClientService;

public class FakeCommCareClientService extends CommCareClientService {
    public FakeCommCareClientService() {
        super(null, null);
    }

    @Override
    public void tryCommCareLogin(Context applicationContext) {
    }

    @Override
    public void establishConnection(Activity activity) {
    }
}
