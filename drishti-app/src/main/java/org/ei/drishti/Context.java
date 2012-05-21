package org.ei.drishti;

import org.ei.drishti.controller.AlertController;
import org.ei.drishti.controller.EligibleCoupleController;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.repository.*;
import org.ei.drishti.service.*;
import org.ei.drishti.view.adapter.ListAdapter;

import java.util.Date;

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
    private UserService loginService;
    private CommCareService commCareService;

    private String password;
    private long sessionExpiryTime = 0;

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

    private Repository initRepository() {
        if (repository == null) {
            repository = new Repository(this.applicationContext, repositoryName(), settingsRepository(), alertRepository(), eligibleCoupleRepository());
        }
        return repository;
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

    public AllSettings allSettings() {
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

    public UserService userService() {
        if (loginService == null) {
            Repository repo = initRepository();
            loginService = new UserService(commCareService(), repo, allSettings());
        }
        return loginService;
    }

    private CommCareService commCareService() {
        if (commCareService == null) {
            commCareService = new CommCareService(new HTTPAgent(), "https://www.commcarehq.org", "frhs-who-columbia");
        }
        return commCareService;
    }

    public Context setPassword(String password) {
        this.password = password;
        return this;
    }

    public String password() {
        return password;
    }

    protected String repositoryName() {
        return "drishti.db";
    }

    public Context startSession(long numberOfMillisecondsAfterNowThatThisSessionEnds) {
        setSessionExpiryTimeTo(new Date().getTime() + numberOfMillisecondsAfterNowThatThisSessionEnds);
        return this;
    }

    public boolean sessionHasExpired() {
        if (password() == null) {
            return true;
        }

        long now = new Date().getTime();
        return now > sessionExpiryTime;
    }

    public void expireSession() {
        setSessionExpiryTimeTo(new Date().getTime() - 1);
    }

    public long sessionLengthInMilliseconds() {
        return 30 * 60 * 1000;
    }

    private void setSessionExpiryTimeTo(long expiryTimeInMillisecondsSinceEpoch) {
        this.sessionExpiryTime = expiryTimeInMillisecondsSinceEpoch;
    }
}
