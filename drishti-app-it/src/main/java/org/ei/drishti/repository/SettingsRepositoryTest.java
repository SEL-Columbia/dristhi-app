package org.ei.drishti.repository;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import org.ei.drishti.util.Session;

public class SettingsRepositoryTest extends AndroidTestCase {
    private SettingsRepository settingsRepository;

    @Override
    protected void setUp() throws Exception {
        settingsRepository = new SettingsRepository();
        Session session = new Session().setPassword("password").setRepositoryName("drishti.db");
        new Repository(new RenamingDelegatingContext(getContext(), "test_"), session, settingsRepository);
    }

    public void testShouldGetDefaultValueIfNothingHasBeenSet() throws Exception {
        assertEquals("someDefaultValue", settingsRepository.querySetting("NOT-SET", "someDefaultValue"));
    }

    public void testSettingsFetchAndSave() throws Exception {
        settingsRepository.updateSetting("abc", "def");

        assertEquals("def", settingsRepository.querySetting("abc", "someDefaultValue"));
    }

    public void testShouldGiveDefaultValueIfThereHasBeenNoSetValue() throws Exception {
        assertEquals("someDefaultValue", settingsRepository.querySetting("SOMETHING_WHICH_DOES_NOT_EXIST", "someDefaultValue"));
    }

    public void testShouldOverwriteExistingValueWhenUpdating() throws Exception {
        settingsRepository.updateSetting("abc", "def");
        settingsRepository.updateSetting("abc", "ghi");

        assertEquals("ghi", settingsRepository.querySetting("abc", "someDefaultValue"));
    }

}
