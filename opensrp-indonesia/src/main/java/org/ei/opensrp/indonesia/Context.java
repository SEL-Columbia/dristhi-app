package org.ei.opensrp.indonesia;

import org.ei.opensrp.DristhiConfiguration;
import org.ei.opensrp.indonesia.repository.AllKartuIbus;
import org.ei.opensrp.indonesia.repository.AllKohort;
import org.ei.opensrp.indonesia.repository.AllSettingsINA;
import org.ei.opensrp.indonesia.repository.AnakRepository;
import org.ei.opensrp.indonesia.repository.BidanRepository;
import org.ei.opensrp.indonesia.repository.IbuRepository;
import org.ei.opensrp.indonesia.repository.KartuIbuRepository;
import org.ei.opensrp.indonesia.repository.UniqueIdRepository;
import org.ei.opensrp.indonesia.service.BidanService;
import org.ei.opensrp.indonesia.service.KartuAnakService;
import org.ei.opensrp.indonesia.service.KartuIbuService;
import org.ei.opensrp.indonesia.service.UniqueIdService;
import org.ei.opensrp.indonesia.view.contract.BidanHomeContext;
import org.ei.opensrp.indonesia.view.contract.KBClients;
import org.ei.opensrp.indonesia.view.contract.KIANCClients;
import org.ei.opensrp.indonesia.view.contract.KIPNCClients;
import org.ei.opensrp.indonesia.view.contract.KartuIbuClients;
import org.ei.opensrp.indonesia.view.controller.AnakRegisterController;
import org.ei.opensrp.indonesia.view.controller.BidanController;
import org.ei.opensrp.indonesia.view.controller.KIANCRegisterController;
import org.ei.opensrp.indonesia.view.controller.KartuIbuRegisterController;
import org.ei.opensrp.indonesia.view.controller.UniqueIdController;
import org.ei.opensrp.repository.FormDataRepository;
import org.ei.opensrp.repository.Repository;
import org.ei.opensrp.util.Cache;

import java.util.List;

/**
 * Created by Dimas Ciputra on 9/12/15.
 */
public class Context extends org.ei.opensrp.Context{
    private static Context context = new Context();
    private KartuIbuRepository kartuIbuRepository;
    private IbuRepository ibuRepository;
    private AnakRepository anakRepository;
    private UniqueIdRepository uniqueIdRepository;
    private BidanRepository bidanRepository;
    private FormDataRepository formDataRepository;

    private AllKartuIbus allKartuIbus;
    private AllKohort allKohort;

    private Cache<KartuIbuClients>kartuIbuClientsCache;
    private Cache<KIANCClients>kartuIbuANCClientsCache;
    private Cache<KIPNCClients>kartuIbuPNCClientsCache;
    private Cache<KBClients>kbClientsCache;
    private Cache<BidanHomeContext>bidanHomeContextCache;
    private Cache<List<Long>> uIdsCache;

    private BidanService bidanService;
    private UniqueIdService uniqueIdService;

    private BidanController bidanController;
    private UniqueIdController uniqueIdController;
    private KartuIbuRegisterController kartuIbuRegisterController;
    private KIANCRegisterController kartuIbuANCRegisterController;
    private AnakRegisterController anakRegisterController;

    private AllSettingsINA allSettingsINA;

    private KartuIbuService kartuIbuService;
    private KartuAnakService kartuAnakService;

    public Context getContext() {
        return this;
    }

    public static Context getInstance() {
        if (context == null){
            context = new Context();
        }
        return context;
    }

    public Context setApplicationContextChild(android.content.Context applicationContext) {
        super.setApplicationContext(applicationContext);
        return this;
    }

    public KartuIbuRepository kartuIbuRepository() {
        if(kartuIbuRepository==null) {
            kartuIbuRepository = new KartuIbuRepository();
        }
        return kartuIbuRepository;
    }

    public IbuRepository ibuRepository() {
        if(ibuRepository==null) {
            ibuRepository = new IbuRepository();
        }
        return ibuRepository;
    }

    public AnakRepository anakRepository() {
        if(anakRepository==null) {
            anakRepository = new AnakRepository();
        }
        return anakRepository;
    }

    public BidanRepository bidanRepository() {
        if(bidanRepository==null) {
            bidanRepository = new BidanRepository();
        }
        return bidanRepository;
    }

    public UniqueIdRepository uniqueIdRepository() {
        if(uniqueIdRepository==null) {
            uniqueIdRepository = new UniqueIdRepository();
        }
        return uniqueIdRepository;
    }

    @Override
    protected Repository initRepository() {
        super.initRepository();
        if(getRepository() == null) {
            setRepository(new Repository(applicationContext(), session(), super.settingsRepository(),
                    super.alertRepository(), super.timelineEventRepository(), super.formDataRepository(),
                    super.serviceProvidedRepository(), super.formsVersionRepository(), kartuIbuRepository(), ibuRepository(),
                    anakRepository(), bidanRepository(),super.imageRepository(), uniqueIdRepository()));
        }
        return getRepository();
    }

    @Override
    public FormDataRepository formDataRepository() {
        if(formDataRepository == null) {
            formDataRepository = super.formDataRepository();
            formDataRepository.addTableColumnMap(KartuIbuRepository.KI_TABLE_NAME, KartuIbuRepository.KI_TABLE_COLUMNS);
            formDataRepository.addTableColumnMap(IbuRepository.IBU_TABLE_NAME, IbuRepository.IBU_TABLE_COLUMNS);
            formDataRepository.addTableColumnMap(AnakRepository.ANAK_TABLE_NAME, AnakRepository.ANAK_TABLE_COLUMNS);
            formDataRepository.addTableColumnMap(BidanRepository.BIDAN_TABLE_NAME, BidanRepository.BIDAN_TABLE_COLUMNS);
        }
        return formDataRepository;
    }

    public AllKartuIbus allKartuIbus() {
        initRepository();
        if (allKartuIbus == null) {
            allKartuIbus = new AllKartuIbus(kartuIbuRepository(), alertRepository(),
                    timelineEventRepository(), configuration());
        }
        return allKartuIbus;
    }

    public AllKohort allKohort() {
        if(allKohort == null) {
            allKohort = new AllKohort(ibuRepository(), anakRepository(), alertRepository(), timelineEventRepository(), configuration());
        }
        return allKohort;
    }

    public Cache<KartuIbuClients> kiClientsCache() {
        if (kartuIbuClientsCache == null) {
            kartuIbuClientsCache = new Cache<KartuIbuClients>();
        }
        return kartuIbuClientsCache;
    }

    public Cache<KIANCClients> kartuIbuANCClientsCache() {
        if (kartuIbuANCClientsCache == null) {
            kartuIbuANCClientsCache = new Cache<>();
        }
        return kartuIbuANCClientsCache;
    }

    public Cache<KBClients> kbClientsCache() {
        if (kbClientsCache == null) {
            kbClientsCache = new Cache<>();
        }
        return kbClientsCache;
    }

    public Cache<KIPNCClients> kartuIbuPNCClientsCache() {
        if (kartuIbuPNCClientsCache == null) {
            kartuIbuPNCClientsCache = new Cache<>();
        }
        return kartuIbuPNCClientsCache;
    }

    public Cache<List<Long>> uIdsCache() {
        if (uIdsCache == null) {
            uIdsCache = new Cache<>();
            }
        return uIdsCache;
    }

    public BidanController bidanController() {
        if (bidanController == null) {
            bidanController = new BidanController(bidanService(), listCache(), bidanHomeContextCache());
        }
        return bidanController;
    }

    public UniqueIdController uniqueIdController() {
        if(uniqueIdController == null) {
            uniqueIdController = new UniqueIdController(uniqueIdRepository(), allSettingsINA(), uIdsCache());
        }
        return uniqueIdController;
    }

    public BidanService bidanService() {
        if (bidanService == null) {
            bidanService = new BidanService(allSharedPreferences(), allKartuIbus(), allKohort());
        }
        return bidanService;
    }

    public Cache<BidanHomeContext> bidanHomeContextCache() {
        if (bidanHomeContextCache == null) {
            bidanHomeContextCache = new Cache<>();
        }
        return bidanHomeContextCache;
    }

    public AllSettingsINA allSettingsINA() {
        initRepository();
        if(allSettingsINA == null) {
            allSettingsINA = new AllSettingsINA(allSharedPreferences(), settingsRepository());
        }
        return allSettingsINA;
    }

    public UniqueIdService uniqueIdService() {
        if(uniqueIdService == null) {
            uniqueIdService = new UniqueIdService(httpAgent(), configuration(), uniqueIdController(), allSettingsINA(), allSharedPreferences());
        }
        return uniqueIdService;
    }

    public KartuIbuService kartuIbuService() {
        if(kartuIbuService == null) {
            kartuIbuService = new KartuIbuService(allKartuIbus(), allTimelineEvents(), allKohort(), uniqueIdController());
        }
        return kartuIbuService;
    }


    public KartuAnakService kartuAnakService() {
        if(kartuAnakService == null) {
            kartuAnakService = new KartuAnakService( uniqueIdController());
        }
        return kartuAnakService;
    }

    public KartuIbuRegisterController kartuIbuRegisterController() {
        if (kartuIbuRegisterController == null) {
            kartuIbuRegisterController = new KartuIbuRegisterController(allKartuIbus(), listCache(), serviceProvidedService(), alertService(), kiClientsCache(), allKohort());
        }
        return kartuIbuRegisterController;
    }

    public KIANCRegisterController kartuIbuANCRegisterController() {
        if (kartuIbuANCRegisterController == null) {
            kartuIbuANCRegisterController = new KIANCRegisterController(allKohort(), alertService(), serviceProvidedService(), listCache(), kartuIbuANCClientsCache(), villagesCache());
        }
        return kartuIbuANCRegisterController;
    }

    public AnakRegisterController anakRegisterController() {
        if (anakRegisterController == null) {
            anakRegisterController = new AnakRegisterController(allKohort(), alertService(), serviceProvidedService(), listCache(),smartRegisterClientsCache(), villagesCache());
        }
        return anakRegisterController;
    }

    @Override
    public DristhiConfiguration configuration() {
        if (configuration == null) {
            configuration = new BidanConfiguration(getInstance().applicationContext().getAssets());
        }
        return configuration;
    }
}
