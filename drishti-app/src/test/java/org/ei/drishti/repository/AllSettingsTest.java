package org.ei.drishti.repository;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class AllSettingsTest {
    @Mock
    private Repository repository;
    private AllSettings allSettings;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        allSettings = new AllSettings(repository);
    }

    @Test
    public void shouldSaveANMID() throws Exception {
        allSettings.saveANMIdentifier("1234");

        verify(repository).updateSetting("anm", "1234");
    }

    @Test
    public void shouldFetchANMIdentifier() throws Exception {
        when(repository.querySetting("anm","ANM")).thenReturn("1234");

        String actual = allSettings.fetchANMIdentifier();
        verify(repository).querySetting("anm", "ANM");

        assertEquals("1234", actual);
    }
}
