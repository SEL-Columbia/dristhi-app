package org.ei.drishti.test;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import org.ei.drishti.repository.Repository;

public class RepositoryTest extends AndroidTestCase {
    private Repository repository;

    @Override
    protected void setUp() throws Exception {
        repository = new Repository(new RenamingDelegatingContext(getContext(), "test_"));
    }

    public void testSettingsFetchAndSave() throws Exception {
        repository.updateSetting("abc", "def");

        assertEquals("def", repository.querySetting("abc", "someDefaultValue"));
    }

    public void testShouldGiveDefaultValueIfThereHasBeenNoSetValue() throws Exception {
        assertEquals("someDefaultValue", repository.querySetting("abc", "someDefaultValue"));
    }

    public void testShouldOverwriteExistingValueWhenUpdating() throws Exception {
        repository.updateSetting("abc", "def");
        repository.updateSetting("abc", "ghi");

        assertEquals("ghi", repository.querySetting("abc", "someDefaultValue"));
    }
}
