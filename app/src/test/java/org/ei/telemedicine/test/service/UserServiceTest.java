package org.ei.telemedicine.test.service;

import android.test.AndroidTestCase;

import org.ei.telemedicine.DristhiConfiguration;
import org.ei.telemedicine.repository.AllAlerts;
import org.ei.telemedicine.repository.AllEligibleCouples;
import org.ei.telemedicine.repository.AllSettings;
import org.ei.telemedicine.repository.AllSharedPreferences;
import org.ei.telemedicine.repository.Repository;
import org.ei.telemedicine.service.HTTPAgent;
import org.ei.telemedicine.service.UserService;
import org.ei.telemedicine.sync.SaveANMLocationTask;
import org.ei.telemedicine.util.Session;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private Repository repository;
    @Mock
    private AllSettings allSettings;
    @Mock
    private AllSharedPreferences allSharedPreferences;
    @Mock
    private AllAlerts allAlerts;
    @Mock
    private AllEligibleCouples allEligibleCouples;
    @Mock
    private Session session;
    @Mock
    private HTTPAgent httpAgent;
    @Mock
    private DristhiConfiguration configuration;
    @Mock
    private SaveANMLocationTask saveANMLocationTask;

    private UserService userService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        userService = new UserService(repository, allSettings, allSharedPreferences, httpAgent, session, configuration, saveANMLocationTask);
    }

    @Test
    public void testShouldUseHttpAgentToDoRemoteLoginCheck() {
        when(configuration.dristhiBaseURL()).thenReturn("http://dristhi_base_url");

        userService.isValidRemoteLogin("userX", "password Y");

        verify(httpAgent).urlCanBeAccessWithGivenCredentials("http://dristhi_base_url/anm-villages?anm-id=userX", "userX", "password Y");
    }

   /* @Test
    public void testShouldSaveANMLocationWhenRemoteLoginIsSuccessful() {
        userService.remoteLogin("userX", "password Y", "anm location");

        verify(saveANMLocationTask).save("anm location");
    }*/

    @Test
    public void testShouldConsiderALocalLoginValidWhenUsernameMatchesRegisteredUserAndPasswordMatchesTheOneInDB() {
        when(allSharedPreferences.fetchRegisteredANM()).thenReturn("ANM X");
        when(repository.canUseThisPassword("password Z")).thenReturn(true);

        assertTrue(userService.isValidLocalLogin("ANM X", "password Z"));

        verify(allSharedPreferences).fetchRegisteredANM();
        verify(repository).canUseThisPassword("password Z");
    }

    @Test
    public void testShouldConsiderALocalLoginInvalidWhenRegisteredUserDoesNotMatch() {
        when(allSharedPreferences.fetchRegisteredANM()).thenReturn("ANM X");

        assertFalse(userService.isValidLocalLogin("SOME OTHER ANM", "password"));

        verify(allSharedPreferences).fetchRegisteredANM();
        verifyZeroInteractions(repository);
    }

    @Test
    public void testShouldConsiderALocalLoginInvalidWhenRegisteredUserMatchesButNotThePassword() {
        when(allSharedPreferences.fetchRegisteredANM()).thenReturn("ANM X");
        when(repository.canUseThisPassword("password Z")).thenReturn(false);

        assertFalse(userService.isValidLocalLogin("ANM X", "password Z"));

        verify(allSharedPreferences).fetchRegisteredANM();
        verify(repository).canUseThisPassword("password Z");
    }

    @Test
    public void testShouldDeleteDataAndSettingsWhenLogoutHappens() throws Exception {
        userService.logout();

        verify(repository).deleteRepository();
        verify(allSettings).savePreviousFetchIndex("0");
        verify(allSettings).registerANM("", "");
    }

}
