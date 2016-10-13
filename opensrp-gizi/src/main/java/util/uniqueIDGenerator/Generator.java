package util.uniqueIDGenerator;

import org.ei.opensrp.util.Cache;
import java.util.List;

/**
 * Created by Null on 2016-10-13.
 */
public class Generator {
    private UniqueIdRepository uniqueIdRepository;
    private Cache<List<Long>> uIdsCache;
    private AllSettingsINA allSettingsINA;
    private UniqueIdController uniqueIdController;
    private UniqueIdService uniqueIdService;



    public AllSettingsINA allSettingsINA() {
        initRepository();
        if(allSettingsINA == null)
            allSettingsINA = new AllSettingsINA(allSharedPreferences(), settingsRepository());

        return allSettingsINA;
    }
    public Cache<List<Long>> uIdsCache() {
        if (uIdsCache == null)
            uIdsCache = new Cache<>();
        return uIdsCache;
    }
    public UniqueIdRepository uniqueIdRepository() {
        if(uniqueIdRepository==null)
            uniqueIdRepository = new UniqueIdRepository();
        return uniqueIdRepository;
    }
    public UniqueIdController uniqueIdController() {
        if(uniqueIdController == null)
            uniqueIdController = new UniqueIdController(uniqueIdRepository(), allSettingsINA(), uIdsCache());
        return uniqueIdController;
        }
    public UniqueIdService uniqueIdService() {
        if(uniqueIdService == null)
            uniqueIdService = new UniqueIdService(httpAgent(), configuration(), uniqueIdController(), allSettingsINA(), allSharedPreferences());
        return uniqueIdService;
    }

}
