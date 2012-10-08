package org.ei.drishti.util;

import android.app.Activity;
import android.app.Instrumentation;
import com.jayway.android.robotium.solo.Solo;
import org.ei.drishti.view.activity.LoginActivity;

import static org.ei.drishti.util.Wait.*;

public class DrishtiSolo extends Solo {
    public DrishtiSolo(Instrumentation instrumentation, Activity activity) {
        super(instrumentation, activity);
        waitForProgressBarToGoAway(activity);
    }

    public DrishtiSolo assertCanLogin(FakeNavigationService navigationService, String userName, String password) {
        enterText(0, userName);
        enterText(1, password);
        clickOnButton(0);
        waitForLoginActivityToFinish(navigationService);
        return this;
    }

    public DrishtiSolo assertCannotLogin(String userName, String password) {
        enterText(0, userName);
        enterText(1, password);
        clickOnButton(0);
        waitForActivity(LoginActivity.class.getSimpleName());
        waitForFilteringToFinish();
        return this;
    }

    public void logout() {
        sendKey(MENU);
        clickOnText("Logout");
        waitForActivity(LoginActivity.class.getSimpleName());
    }
}
