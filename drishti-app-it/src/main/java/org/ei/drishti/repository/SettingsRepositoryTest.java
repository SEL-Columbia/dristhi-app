package org.ei.drishti.repository;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

public class SettingsRepositoryTest extends AndroidTestCase {
    private SettingsRepository settingsRepository;

    @Override
    protected void setUp() throws Exception {
        settingsRepository = new SettingsRepository(new RenamingDelegatingContext(getContext(), "test_"));
    }

    public void testSettingsFetchAndSave() throws Exception {
        settingsRepository.updateSetting("abc", "def");

        assertEquals("def", settingsRepository.querySetting("abc", "someDefaultValue"));
    }

    public void testShouldGiveDefaultValueIfThereHasBeenNoSetValue() throws Exception {
        assertEquals("someDefaultValue", settingsRepository.querySetting("abc", "someDefaultValue"));
    }

    public void testShouldOverwriteExistingValueWhenUpdating() throws Exception {
        settingsRepository.updateSetting("abc", "def");
        settingsRepository.updateSetting("abc", "ghi");

        assertEquals("ghi", settingsRepository.querySetting("abc", "someDefaultValue"));
    }

}
