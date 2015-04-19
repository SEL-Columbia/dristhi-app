package org.ei.drishti.service;

import org.ei.drishti.repository.*;
import org.ei.drishti.sync.SaveUserInfoTask;
import org.robolectric.RobolectricTestRunner;
import org.ei.drishti.DristhiConfiguration;
import org.ei.drishti.sync.SaveANMLocationTask;
import org.ei.drishti.util.Session;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.ei.drishti.AllConstants.ENGLISH_LOCALE;
import static org.ei.drishti.AllConstants.KANNADA_LOCALE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
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
    @Mock
    private SaveUserInfoTask saveUserInfoTask;

    private UserService userService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        userService = new UserService(repository, allSettings, allSharedPreferences, httpAgent, session, configuration, saveANMLocationTask, saveUserInfoTask);
    }

    @Test
    public void shouldUseHttpAgentToDoRemoteLoginCheck() {
        when(configuration.dristhiBaseURL()).thenReturn("http://dristhi_base_url");

        userService.isValidRemoteLogin("userX", "password Y");

        verify(httpAgent).urlCanBeAccessWithGivenCredentials("http://dristhi_base_url/security/authenticate", "userX", "password Y");
    }

    @Test
    public void shouldGetANMLocation() {
        when(configuration.dristhiBaseURL()).thenReturn("http://opensrp_base_url");
        userService.getLocationInformation();
        verify(httpAgent).fetch("http://opensrp_base_url/location/location-tree");
    }

    @Test
    public void shouldSaveUserInformationRemoteLoginIsSuccessful() {
        userService.remoteLogin("userX", "password Y", "user info");
        verify(saveUserInfoTask).save("user info");
    }

    @Test
    public void shouldSaveANMLocation() {
        userService.saveAnmLocation("anm location");
        verify(saveANMLocationTask).save("anm location");
    }

    @Test
    public void shouldConsiderALocalLoginValidWhenUsernameMatchesRegisteredUserAndPasswordMatchesTheOneInDB() {
        when(allSharedPreferences.fetchRegisteredANM()).thenReturn("ANM X");
        when(repository.canUseThisPassword("password Z")).thenReturn(true);

        assertTrue(userService.isValidLocalLogin("ANM X", "password Z"));

        verify(allSharedPreferences).fetchRegisteredANM();
        verify(repository).canUseThisPassword("password Z");
    }

    @Test
    public void shouldConsiderALocalLoginInvalidWhenRegisteredUserDoesNotMatch() {
        when(allSharedPreferences.fetchRegisteredANM()).thenReturn("ANM X");

        assertFalse(userService.isValidLocalLogin("SOME OTHER ANM", "password"));

        verify(allSharedPreferences).fetchRegisteredANM();
        verifyZeroInteractions(repository);
    }

    @Test
    public void shouldConsiderALocalLoginInvalidWhenRegisteredUserMatchesButNotThePassword() {
        when(allSharedPreferences.fetchRegisteredANM()).thenReturn("ANM X");
        when(repository.canUseThisPassword("password Z")).thenReturn(false);

        assertFalse(userService.isValidLocalLogin("ANM X", "password Z"));

        verify(allSharedPreferences).fetchRegisteredANM();
        verify(repository).canUseThisPassword("password Z");
    }

    @Test
    public void shouldRegisterANewUser() {
        userService.remoteLogin("user X", "password Y", "");

        verify(allSettings).registerANM("user X", "password Y");
        verify(session).setPassword("password Y");
    }

    @Test
    public void shouldDeleteDataAndSettingsWhenLogoutHappens() throws Exception {
        userService.logout();

        verify(repository).deleteRepository();
        verify(allSettings).savePreviousFetchIndex("0");
        verify(allSettings).registerANM("", "");
    }

    @Test
    public void shouldSwitchLanguageToKannada() throws Exception {
        when(allSharedPreferences.fetchLanguagePreference()).thenReturn(ENGLISH_LOCALE);

        userService.switchLanguagePreference();

        verify(allSharedPreferences).saveLanguagePreference(KANNADA_LOCALE);
    }

    @Test
    public void shouldSwitchLanguageToEnglish() throws Exception {
        when(allSharedPreferences.fetchLanguagePreference()).thenReturn(KANNADA_LOCALE);

        userService.switchLanguagePreference();

        verify(allSharedPreferences).saveLanguagePreference(ENGLISH_LOCALE);
    }
}
