package org.ei.opensrp.indonesia;

import org.ei.opensrp.indonesia.repository.AnakRepository;
import org.ei.opensrp.indonesia.repository.BidanRepository;
import org.ei.opensrp.indonesia.repository.IbuRepository;
import org.ei.opensrp.indonesia.repository.KartuIbuRepository;
import org.ei.opensrp.indonesia.repository.UniqueIdRepository;
import org.ei.opensrp.repository.FormDataRepository;
import org.ei.opensrp.repository.Repository;

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

    public Context getContext() {
        return this;
    }

    public static Context getInstance() {
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
        return new Repository(applicationContext(), session(), super.settingsRepository(),
                super.alertRepository(), super.timelineEventRepository(), super.formDataRepository(),
                super.serviceProvidedRepository(), super.formsVersionRepository(), kartuIbuRepository(), ibuRepository(),
                anakRepository(), bidanRepository(), uniqueIdRepository());
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


}
