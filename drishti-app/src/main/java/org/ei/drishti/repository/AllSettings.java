package org.ei.drishti.repository;

public class AllSettings {
    private Repository repository;

    public AllSettings(Repository repository) {
        this.repository = repository;
    }

    public void saveANMIdentifier(String value) {
        repository.updateSetting("anm", value);
    }

    public String fetchANMIdentifier(){
        return repository.querySetting("anm", "ANM");
    }
}
