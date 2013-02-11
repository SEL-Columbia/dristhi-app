package org.ei.drishti.util;

import android.app.Activity;
import android.content.Context;
import org.ei.drishti.service.CommCareClientService;

public class FakeCommCareClientService extends CommCareClientService {
    private FakeNavigationService navigationService;

    public FakeCommCareClientService(FakeNavigationService navigationService) {
        super(null, navigationService);
        this.navigationService = navigationService;
    }

    @Override
    public void tryCommCareLogin(Context applicationContext) {
    }

    @Override
    public void establishConnection(Activity activity) {
        navigationService.goHome(activity);
    }
}
