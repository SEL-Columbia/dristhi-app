package org.ei.opensrp.indonesia.view.controller;

import android.content.Context;

import org.ei.opensrp.indonesia.repository.AllSettingsINA;
import org.ei.opensrp.indonesia.repository.UniqueIdRepository;
import org.ei.opensrp.util.Cache;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import edu.emory.mathcs.backport.java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by Dimas on 9/9/2015.
 */
public class UniqueIdControllerTest {
    @Mock
    private AllSettingsINA allSettings;
    @Mock
    private UniqueIdRepository uniqueIdRepository;
    @Mock
    private Cache<List<Integer>> cache;
    @Mock
    Context context;
    private UniqueIdController controller;

    List<Integer> uids;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        controller = new UniqueIdController(uniqueIdRepository, allSettings, cache);
        uids = Arrays.asList(new Integer[]{
                10019,
                10026,
                10035,
                10042,
                10057,
                10061,
                10074,
                10088,
                10090,
                10100,
                10116,
                10128,
                10137,
                10144,
                10159,
                10163,
                10171,
                10185,
                10192,
                10205});
    }

    @Test
    public void shouldGetLatestUniqueId() {
        String currentId = "10044";
        when(allSettings.fetchCurrentId()).thenReturn(currentId);
        when(uniqueIdRepository.getAllUniqueId()).thenReturn(uids);

        String latestUniqueId = controller.getUniqueIdTest();

        verify(allSettings).fetchCurrentId();
        verify(uniqueIdRepository).getAllUniqueId();

        assertEquals(latestUniqueId, uids.get(0)+"");
    }

    @Test
    public void shouldReturnFirstValueWhenTheCurrentIsLowerThanArray() {
        String currentId = "10";
        when(allSettings.fetchCurrentId()).thenReturn(currentId);
        when(uniqueIdRepository.getAllUniqueId()).thenReturn(uids);

        String latestUniqueId = controller.getUniqueIdTest();

        verify(allSettings).fetchCurrentId();
        verify(uniqueIdRepository).getAllUniqueId();

        assertEquals(latestUniqueId, "10019");
    }

    @Test
    public void shouldReturnNullWhenTheCurrentIsHigherThanArray() {
        String currentId = "10205";
        when(allSettings.fetchCurrentId()).thenReturn(currentId);
        when(uniqueIdRepository.getAllUniqueId()).thenReturn(uids);

        String latestUniqueId = controller.getUniqueIdTest();

        verify(allSettings).fetchCurrentId();
        verify(uniqueIdRepository).getAllUniqueId();

        assertNull(latestUniqueId);
    }

    @Test
    public void shouldReturnNullUniqueId() {
        when(allSettings.fetchCurrentId()).thenReturn("0");
        when(uniqueIdRepository.getAllUniqueId()).thenReturn(null);
        String latestUniqueId = controller.getUniqueId();
        verify(allSettings).fetchCurrentId();
        assertNull(latestUniqueId);
    }

    @Test
    public void shouldNeedRefill() throws Exception{
        when(uniqueIdRepository.getAllUniqueId()).thenReturn(uids);
        when(allSettings.fetchCurrentId()).thenReturn("10163");
        boolean needToRefill = controller.needToRefillUniqueIdTest();

        assertTrue(needToRefill);
    }

    @Test
    public void shouldNotNeedRefill() {
        when(uniqueIdRepository.getAllUniqueId()).thenReturn(uids);
        when(allSettings.fetchCurrentId()).thenReturn("10144");
        boolean needToRefill = controller.needToRefillUniqueIdTest();

        assertFalse(needToRefill);
    }

}
