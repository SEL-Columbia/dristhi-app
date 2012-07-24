package org.ei.drishti;

import org.ei.drishti.repository.*;
import org.ei.drishti.service.*;
import org.ei.drishti.util.Session;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class Context {
    private android.content.Context applicationContext;
    private static Context context;

    private Repository repository;
    private EligibleCoupleRepository eligibleCoupleRepository;
    private AlertRepository alertRepository;
    private SettingsRepository settingsRepository;
    private BeneficiaryRepository beneficiaryRepository;
    private TimelineEventRepository timelineEventRepository;

    private AllSettings allSettings;
    private AllAlerts allAlerts;
    private AllEligibleCouples allEligibleCouples;
    private AllBeneficiaries allBeneficiaries;
    private AllTimelineEvents allTimelineEvents;

    private DrishtiService drishtiService;
    private ActionService actionService;
    private UserService userService;
    private ANMService anmService;

    private CommCareService commCareService;
    private Session session;

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

    protected DrishtiService drishtiService() {
        if (drishtiService == null) {
            drishtiService = new DrishtiService(new HTTPAgent(applicationContext), "https://drishti.modilabs.org");
        }
        return drishtiService;
    }

    public ActionService actionService() {
        if (actionService == null) {
            actionService = new ActionService(drishtiService(), allSettings(), allAlerts(), allEligibleCouples(), allBeneficiaries());
        }
        return actionService;
    }

    private Repository initRepository() {
        if (repository == null) {
            repository = new Repository(this.applicationContext, session(), settingsRepository(), alertRepository(),
                    eligibleCoupleRepository(), beneficiaryRepository(), timelineEventRepository());
        }
        return repository;
    }

    public AllEligibleCouples allEligibleCouples() {
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

    public AllBeneficiaries allBeneficiaries() {
        initRepository();
        if (allBeneficiaries == null) {
            allBeneficiaries = new AllBeneficiaries(beneficiaryRepository());
        }
        return allBeneficiaries;
    }

    public AllTimelineEvents allTimelineEvents() {
        initRepository();
        if (allTimelineEvents == null) {
            allTimelineEvents = new AllTimelineEvents(timelineEventRepository());
        }
        return allTimelineEvents;
    }

    private EligibleCoupleRepository eligibleCoupleRepository() {
        if (eligibleCoupleRepository == null) {
            eligibleCoupleRepository = new EligibleCoupleRepository();
        }
        return eligibleCoupleRepository;
    }

    private AlertRepository alertRepository() {
        if (alertRepository == null) {
            alertRepository = new AlertRepository();
        }
        return alertRepository;
    }

    private SettingsRepository settingsRepository() {
        if (settingsRepository == null) {
            settingsRepository = new SettingsRepository();
        }
        return settingsRepository;
    }

    private BeneficiaryRepository beneficiaryRepository() {
        if (beneficiaryRepository == null) {
            beneficiaryRepository = new BeneficiaryRepository(timelineEventRepository());
        }
        return beneficiaryRepository;
    }

    private TimelineEventRepository timelineEventRepository() {
        if (timelineEventRepository == null) {
            timelineEventRepository = new TimelineEventRepository();
        }
        return timelineEventRepository;
    }

    public UserService userService() {
        if (userService == null) {
            Repository repo = initRepository();
            userService = new UserService(commCareService(), repo, allSettings(), session());
        }
        return userService;
    }

    private CommCareService commCareService() {
        if (commCareService == null) {
            commCareService = new CommCareService(new HTTPAgent(applicationContext), "https://www.commcarehq.org", "frhs-who-columbia");
        }
        return commCareService;
    }

    public Session session() {
        if (session == null) {
            session = new Session();
        }
        return session;
    }

    public ANMService anmService() {
        if (anmService == null) {
            anmService = new ANMService(allSettings(), beneficiaryRepository(), eligibleCoupleRepository());
        }
        return anmService;
    }
}
