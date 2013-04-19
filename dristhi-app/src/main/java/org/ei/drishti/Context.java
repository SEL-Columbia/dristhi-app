package org.ei.drishti;

import org.ei.drishti.repository.*;
import org.ei.drishti.service.*;
import org.ei.drishti.util.Cache;
import org.ei.drishti.util.Session;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static org.ei.drishti.AllConstants.DRISHTI_BASE_URL;

public class Context {
    private android.content.Context applicationContext;
    private static Context context;

    private Repository repository;
    private EligibleCoupleRepository eligibleCoupleRepository;
    private AlertRepository alertRepository;
    private SettingsRepository settingsRepository;
    private ChildRepository childRepository;
    private MotherRepository motherRepository;
    private TimelineEventRepository timelineEventRepository;
    private ReportRepository reportRepository;
    private FormDataRepository formDataRepository;

    private AllSettings allSettings;
    private AllAlerts allAlerts;
    private AllEligibleCouples allEligibleCouples;
    private AllBeneficiaries allBeneficiaries;
    private AllTimelineEvents allTimelineEvents;
    private AllReports allReports;

    private DrishtiService drishtiService;
    private ActionService actionService;
    private FormSubmissionService formSubmissionService;
    private FormSubmissionSyncService formSubmissionSyncService;
    private ZiggyService ziggyService;
    private UserService userService;
    private AlertService alertService;
    private EligibleCoupleService eligibleCoupleService;
    private MotherService motherService;
    private ChildService childService;
    private ANMService anmService;
    private BeneficiaryService beneficiaryService;

    private NavigationService navigationService;
    private CommCareHQService commCareService;
    private CommCareClientService commCareClientService;

    private Session session;
    private Cache<String> listCache;

    private HTTPAgent httpAgent;
    private ZiggyFileLoader ziggyFileLoader;

    protected Context() {
    }

    public android.content.Context applicationContext() {
        return applicationContext;
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

    public BeneficiaryService beneficiaryService() {
        if (beneficiaryService == null) {
            beneficiaryService = new BeneficiaryService(allEligibleCouples(), allBeneficiaries());
        }
        return beneficiaryService;
    }

    public Context updateApplicationContext(android.content.Context applicationContext) {
        this.applicationContext = applicationContext;
        return this;
    }

    protected DrishtiService drishtiService() {
        if (drishtiService == null) {
            drishtiService = new DrishtiService(new HTTPAgent(applicationContext), DRISHTI_BASE_URL);
        }
        return drishtiService;
    }

    public ActionService actionService() {
        if (actionService == null) {
            actionService = new ActionService(drishtiService(), allSettings(), allReports());
        }
        return actionService;
    }

    public FormSubmissionService formSubmissionService() {
        if (formSubmissionService == null) {
            formSubmissionService = new FormSubmissionService(ziggyService(), formDataRepository(), allSettings());
        }
        return formSubmissionService;
    }

    private ZiggyService ziggyService() {
        initRepository();
        if (ziggyService == null) {
            ziggyService = new ZiggyService(ziggyFileLoader(), formDataRepository());
        }
        return ziggyService;
    }

    public ZiggyFileLoader ziggyFileLoader() {
        if (ziggyFileLoader == null) {
            ziggyFileLoader = new ZiggyFileLoader("js/form", "www/form", applicationContext().getAssets());
        }
        return ziggyFileLoader;
    }

    public FormSubmissionSyncService formSubmissionSyncService() {
        if (formSubmissionSyncService == null) {
            formSubmissionSyncService = new FormSubmissionSyncService(formSubmissionService(), httpAgent(), formDataRepository(), allSettings());
        }
        return formSubmissionSyncService;
    }

    private HTTPAgent httpAgent() {
        if (httpAgent == null) {
            httpAgent = new HTTPAgent(applicationContext);
        }
        return httpAgent;
    }

    private Repository initRepository() {
        if (repository == null) {
            repository = new Repository(this.applicationContext, session(), settingsRepository(), alertRepository(),
                    eligibleCoupleRepository(), childRepository(), timelineEventRepository(), motherRepository(), reportRepository(),
                    formDataRepository());
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
            allBeneficiaries = new AllBeneficiaries(motherRepository(), childRepository());
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

    public AllReports allReports() {
        initRepository();
        if (allReports == null) {
            allReports = new AllReports(reportRepository());
        }
        return allReports;
    }

    private EligibleCoupleRepository eligibleCoupleRepository() {
        if (eligibleCoupleRepository == null) {
            eligibleCoupleRepository = new EligibleCoupleRepository(motherRepository(), timelineEventRepository(), alertRepository());
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

    private ChildRepository childRepository() {
        if (childRepository == null) {
            childRepository = new ChildRepository(timelineEventRepository(), alertRepository());
        }
        return childRepository;
    }

    private MotherRepository motherRepository() {
        if (motherRepository == null) {
            motherRepository = new MotherRepository(childRepository(), timelineEventRepository(), alertRepository());
        }
        return motherRepository;
    }

    private TimelineEventRepository timelineEventRepository() {
        if (timelineEventRepository == null) {
            timelineEventRepository = new TimelineEventRepository();
        }
        return timelineEventRepository;
    }

    private ReportRepository reportRepository() {
        if (reportRepository == null) {
            reportRepository = new ReportRepository();
        }
        return reportRepository;
    }

    public FormDataRepository formDataRepository() {
        if (formDataRepository == null) {
            formDataRepository = new FormDataRepository();
        }
        return formDataRepository;
    }

    public UserService userService() {
        if (userService == null) {
            Repository repo = initRepository();
            userService = new UserService(commCareService(), repo, allSettings(), session());
        }
        return userService;
    }

    public AlertService alertService() {
        if (alertService == null) {
            alertService = new AlertService(alertRepository(), allBeneficiaries(), allEligibleCouples());
        }
        return alertService;
    }

    public EligibleCoupleService eligibleCoupleService() {
        if (eligibleCoupleService == null) {
            eligibleCoupleService = new EligibleCoupleService(eligibleCoupleRepository());
        }
        return eligibleCoupleService;
    }

    public MotherService motherService() {
        if (motherService == null) {
            motherService = new MotherService(motherRepository(), allTimelineEvents(), eligibleCoupleRepository());
        }
        return motherService;
    }

    public ChildService childService() {
        if (childService == null) {
            childService = new ChildService(motherRepository(), childRepository(), allTimelineEvents());
        }
        return childService;
    }

    private CommCareHQService commCareService() {
        if (commCareService == null) {
            commCareService = new CommCareHQService(httpAgent(), "https://india.commcarehq.org", "dristhi");
        }
        return commCareService;
    }

    public CommCareClientService commCareClientService() {
        if (commCareClientService == null) {
            commCareClientService = new CommCareClientService(allSettings(), navigationService());
        }
        return commCareClientService;
    }

    public Session session() {
        if (session == null) {
            session = new Session();
        }
        return session;
    }

    public ANMService anmService() {
        if (anmService == null) {
            anmService = new ANMService(allSettings(), allBeneficiaries(), allEligibleCouples());
        }
        return anmService;
    }

    public NavigationService navigationService() {
        if (navigationService == null) {
            navigationService = new NavigationService();
        }
        return navigationService;
    }

    public Cache<String> listCache() {
        if (listCache == null) {
            listCache = new Cache<String>();
        }
        return listCache;
    }

    public Boolean IsUserLoggedOut() {
        return userService().hasSessionExpired();
    }
}
