package org.ei.drishti.util;

import android.app.Activity;
import org.ei.drishti.service.NavigationService;

public class FakeNavigationService extends NavigationService {
    private boolean isAtHome;

    @Override
    public void goHome(Activity activity) {
        this.isAtHome = true;
    }

    public boolean isAtHome() {
        return isAtHome;
    }
}
