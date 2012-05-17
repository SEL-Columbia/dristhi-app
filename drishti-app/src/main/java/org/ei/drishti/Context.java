package org.ei.drishti;

import org.ei.drishti.controller.AlertController;
import org.ei.drishti.controller.EligibleCoupleController;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.repository.*;
import org.ei.drishti.service.*;
import org.ei.drishti.view.adapter.ListAdapter;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class Context {
    private android.content.Context applicationContext;
    private static Context context;

    private Repository repository;
    private EligibleCoupleRepository eligibleCoupleRepository;
    private AlertRepository alertRepository;
    private SettingsRepository settingsRepository;

    private AllSettings allSettings;
    private AllAlerts allAlerts;
    private AllEligibleCouples allEligibleCouples;

    private DrishtiService drishtiService;
    private ActionService actionService;
    private UserService userService;
    private LoginService loginService;
    private CommCareService commCareService;

    protected Context() {
    }

    public static Context getInstance() {
        if (context == null) {
            context = new Context();
        }
        return context;
    }

    public static Context setInstance(Context context) {
        Context.context = context;
        return context;
    }

    public Context updateApplicationContext(android.content.Context applicationContext) {
        this.applicationContext = applicationContext;
        return this;
    }

    public AlertController alertController(ListAdapter<Alert> adapter) {
        return new AlertController(adapter, allAlerts());
    }

    public EligibleCoupleController eligibleCoupleController(ListAdapter<EligibleCouple> adapter) {
        return new EligibleCoupleController(adapter, allEligibleCouples());
    }

    protected DrishtiService drishtiService() {
        if (drishtiService == null) {
            drishtiService = new DrishtiService(new HTTPAgent(), "http://drishti.modilabs.org");
        }
        return drishtiService;
    }

    public ActionService actionService() {
        if (actionService == null) {
            actionService = new ActionService(drishtiService(), allSettings(), allAlerts(), allEligibleCouples());
        }
        return actionService;
    }

    public UserService userService() {
        if (userService == null) {
            userService = new UserService(allSettings(), allAlerts(), allEligibleCouples());
        }
        return userService;
    }

    private void initRepository() {
        if (repository == null) {
            repository = new Repository(this.applicationContext, "drishti.db", settingsRepository(), alertRepository(), eligibleCoupleRepository());
        }
    }

    protected AllEligibleCouples allEligibleCouples() {
        initRepository();
        if (allEligibleCouples == null) {
            allEligibleCouples = new AllEligibleCouples(eligibleCoupleRepository());
        }
        return allEligibleCouples;
    }

    public AllAlerts allAlerts() {
        initRepository();
        if (allAlerts == null) {
            allAlerts = new AllAlerts(alertRepository());
        }
        return allAlerts;
    }

    protected AllSettings allSettings() {
        initRepository();
        if (allSettings == null) {
            allSettings = new AllSettings(getDefaultSharedPreferences(this.applicationContext), settingsRepository());
        }
        return allSettings;
    }

    protected EligibleCoupleRepository eligibleCoupleRepository() {
        if (eligibleCoupleRepository == null) {
            eligibleCoupleRepository = new EligibleCoupleRepository();
        }
        return eligibleCoupleRepository;
    }

    protected AlertRepository alertRepository() {
        if (alertRepository == null) {
            alertRepository = new AlertRepository();
        }
        return alertRepository;
    }

    protected SettingsRepository settingsRepository() {
        if (settingsRepository == null) {
            settingsRepository = new SettingsRepository();
        }
        return settingsRepository;
    }

    public LoginService loginService() {
        if (loginService == null) {
            loginService = new LoginService(commCareService());
        }
        return loginService;
    }

    private CommCareService commCareService() {
        if (commCareService == null) {
            commCareService = new CommCareService(new HTTPAgent(), "https://www.commcarehq.org", "frhs-who-columbia");
        }
        return commCareService;
    }
}
