package org.ei.drishti.repository;

import android.content.SharedPreferences;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
    public void shouldTrimANMIdentifier() throws Exception {
        when(preferences.getString("anmIdentifier", "")).thenReturn("  1234 ");

        String actual = allSettings.fetchRegisteredANM();
        verify(preferences).getString("anmIdentifier", "");

        assertEquals("1234", actual);
    }

    @Test
    public void shouldRegisterANMIdentifier() {
        SharedPreferences.Editor editor = mock(SharedPreferences.Editor.class);
        when(preferences.edit()).thenReturn(editor);
        when(editor.putString("anmIdentifier", "ANM X")).thenReturn(editor);

        allSettings.registerANM("ANM X");

        verify(editor).putString("anmIdentifier", "ANM X");
        verify(editor).apply();
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
}
