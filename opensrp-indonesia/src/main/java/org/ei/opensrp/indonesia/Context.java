package org.ei.opensrp.indonesia;

import org.ei.opensrp.repository.AllSharedPreferences;
import org.ei.opensrp.service.UserService;

/**
 * Created by Dimas Ciputra on 9/12/15.
 */
public class Context {
    private android.content.Context applicationContext;
    private static Context context = new Context();
    private org.ei.opensrp.Context contextRoot;

    public static Context getInstance() {
        return context;
    }

    public android.content.Context applicationContext() {
        return applicationContext;
    }

    public Context updateApplicationContext(android.content.Context applicationContext) {
        this.applicationContext = applicationContext;
        contextRoot = org.ei.opensrp.Context.getInstance().updateApplicationContext(applicationContext);
        return this;
    }

    public Boolean IsUserLoggedOut() {
        return contextRoot.userService().hasSessionExpired();
    }

    public UserService userService() {
        return contextRoot.userService();
    }

    public AllSharedPreferences allSharedPreferences() {
        return contextRoot.allSharedPreferences();
    }

}
