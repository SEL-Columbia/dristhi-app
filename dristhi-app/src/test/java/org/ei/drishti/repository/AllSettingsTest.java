package org.ei.drishti.repository;

import android.content.SharedPreferences;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.ei.drishti.AllConstants.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class AllSettingsTest {
    @Mock
    private SettingsRepository settingsRepository;
    @Mock
    private SharedPreferences preferences;

    private AllSettings allSettings;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        allSettings = new AllSettings(preferences, settingsRepository);
    }

    @Test
    public void shouldFetchANMIdentifierFromPreferences() throws Exception {
        when(preferences.getString("anmIdentifier", "")).thenReturn("1234");

        String actual = allSettings.fetchRegisteredANM();

        verify(preferences).getString("anmIdentifier", "");
        assertEquals("1234", actual);
    }

    @Test
    public void shouldFetchANMPassword() throws Exception {
        when(settingsRepository.querySetting("anmPassword", "")).thenReturn("actual password");

        String actual = allSettings.fetchANMPassword();

        verify(settingsRepository).querySetting("anmPassword", "");
        assertEquals("actual password", actual);
    }

    @Test
    public void shouldTrimANMIdentifier() throws Exception {
        when(preferences.getString("anmIdentifier", "")).thenReturn("  1234 ");

        String actual = allSettings.fetchRegisteredANM();

        verify(preferences).getString("anmIdentifier", "");
        assertEquals("1234", actual);
    }

    @Test
    public void shouldSavePreviousFetchIndex() throws Exception {
        allSettings.savePreviousFetchIndex("1234");

        verify(settingsRepository).updateSetting("previousFetchIndex", "1234");
    }

    @Test
    public void shouldFetchPreviousFetchIndex() throws Exception {
        when(settingsRepository.querySetting("previousFetchIndex", "0")).thenReturn("1234");

        String actual = allSettings.fetchPreviousFetchIndex();

        verify(settingsRepository).querySetting("previousFetchIndex", "0");
        assertEquals("1234", actual);
    }

    @Test
    public void shouldSavePreviousFormSyncIndex() throws Exception {
        allSettings.savePreviousFormSyncIndex("1234");

        verify(settingsRepository).updateSetting("previousFormSyncIndex", "1234");
    }

    @Test
    public void shouldFetchPreviousFormSyncIndex() throws Exception {
        when(settingsRepository.querySetting("previousFormSyncIndex", "0")).thenReturn("1234");

        String actual = allSettings.fetchPreviousFormSyncIndex();

        verify(settingsRepository).querySetting("previousFormSyncIndex", "0");
        assertEquals("1234", actual);
    }

    @Test
    public void shouldSaveAppliedVillageFilter() throws Exception {
        allSettings.saveAppliedVillageFilter("munjanahalli");

        verify(settingsRepository).updateSetting("appliedVillageFilter", "munjanahalli");
    }

    @Test
    public void shouldGetAppliedVillageFilter() throws Exception {
        allSettings.appliedVillageFilter("All");

        verify(settingsRepository).querySetting("appliedVillageFilter", "All");
    }

    @Test
    public void shouldSaveCommCareKeyID() throws Exception {
        allSettings.saveCommCareKeyID("key code");

        verify(settingsRepository).updateSetting("commCareKeyID", "key code");
    }

    @Test
    public void shouldSaveCommCarePublicKey() throws Exception {
        byte[] publicKey = {1, 2};
        allSettings.saveCommCarePublicKey(publicKey);

        verify(settingsRepository).updateBLOB("commCarePublicKey", publicKey);
    }

    @Test
    public void shouldFetchCommCareKeyID() throws Exception {
        when(settingsRepository.querySetting("commCareKeyID", null)).thenReturn("key id");

        assertEquals("key id", allSettings.fetchCommCareKeyID());
    }

    @Test
    public void shouldFetchCommCarePublicKey() throws Exception {
        byte[] expectedPublicKey = {1, 2};
        when(settingsRepository.queryBLOB("commCarePublicKey")).thenReturn(expectedPublicKey);

        assertEquals(expectedPublicKey, allSettings.fetchCommCarePublicKey());
    }

    @Test
    public void shouldFetchLanguagePreference() throws Exception {
        when(preferences.getString(LANGUAGE_PREFERENCE_KEY, DEFAULT_LOCALE)).thenReturn(ENGLISH_LANGUAGE);

        assertEquals("English", allSettings.fetchLanguagePreference());
    }

    @Test
    public void shouldFetchIsSyncInProgress() throws Exception {
        when(preferences.getBoolean(IS_SYNC_IN_PROGRESS_PREFERENCE_KEY, false)).thenReturn(true);

        assertTrue(allSettings.fetchIsSyncInProgress());
    }

    @Test
    public void shouldSaveIsSyncInProgress() throws Exception {
        SharedPreferences.Editor editor = mock(SharedPreferences.Editor.class);
        when(preferences.edit()).thenReturn(editor);
        when(editor.putBoolean(IS_SYNC_IN_PROGRESS_PREFERENCE_KEY, true)).thenReturn(editor);

        allSettings.saveIsSyncInProgress(true);

        verify(editor).putBoolean(IS_SYNC_IN_PROGRESS_PREFERENCE_KEY, true);
        verify(editor).commit();
    }
}
