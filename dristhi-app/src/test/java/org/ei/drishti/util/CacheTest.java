package org.ei.drishti.util;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.event.Event;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.ei.drishti.domain.FetchStatus.fetched;
import static org.ei.drishti.domain.FetchStatus.fetchedFailed;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class CacheTest {
    @Mock
    private CacheableData<String> cacheableData;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void shouldGetAndCacheValueOnlyWhenItDoesNotExist() throws Exception {
        Cache<String> cache = new Cache<String>();
        when(cacheableData.fetch()).thenReturn("value");

        assertEquals("value", cache.get("key", cacheableData));

        verify(cacheableData).fetch();

        assertEquals("value", cache.get("key", cacheableData));

        verify(cacheableData, times(1)).fetch();
    }

    @Test
    public void shouldClearCacheWhenActionsAreUpdated() throws Exception {
        Cache<String> cache = new Cache<String>();
        when(cacheableData.fetch()).thenReturn("value");

        cache.get("key", cacheableData);
        Event.ON_DATA_FETCHED.notifyListeners(fetched);

        assertEquals("value", cache.get("key", cacheableData));
        verify(cacheableData, times(2)).fetch();

        Event.ON_DATA_FETCHED.notifyListeners(fetchedFailed);
        assertEquals("value", cache.get("key", cacheableData));
        verify(cacheableData, times(2)).fetch();
    }

}
