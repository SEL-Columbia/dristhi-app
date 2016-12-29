package util.uniqueIdGenerator;


import org.ei.opensrp.repository.AllSettings;
import org.ei.opensrp.repository.Repository;
import org.ei.opensrp.repository.SettingsRepository;

/**
 * Created by Null on 2016-12-21.
 */
public class Context extends org.ei.opensrp.Context{
    public AllSettings allSettings(){
        return super.allSettings();
    }

    public Repository initRepository(){
        return super.initRepository();
    }

    public SettingsRepository settingsRepository(){
        return super.settingsRepository();
    }


}
