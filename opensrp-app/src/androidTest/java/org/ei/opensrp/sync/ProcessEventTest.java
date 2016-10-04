package org.ei.opensrp.sync;

import org.json.JSONArray;
import org.json.JSONObject;
import org.mockito.Mockito;


/**
 * Created by keyman on 30/09/16.
 */
public class ProcessEventTest extends BaseClientProcessorTest {


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        System.setProperty("dexmaker.dexcache", getContext().getCacheDir().getPath());
    }


    public void testProcessNullEvents() {
        try {
            Boolean processed = ClientProcessor.getInstance(getContext()).processEvent(null, createEmptyJsonObject());
            assertNull("Event should not be processed, Exception thrown", processed);
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    public void testProcessEventWhenClientClassificationIsNull() {
        try {
            String baseEntityId = getBaseEntityId();

            JSONObject client = createClient(baseEntityId);

            CloudantDataHandler cloudantDataHandler = Mockito.mock(CloudantDataHandler.class);
            Mockito.when(cloudantDataHandler.getClientByBaseEntityId(baseEntityId)).thenReturn(client);

            ClientProcessor clientProcessor = ClientProcessor.getInstance(getContext());
            clientProcessor.setCloudantDataHandler(cloudantDataHandler);

            Boolean processed = clientProcessor.processEvent(createJsonObject(baseEntityIdJSONKey, baseEntityId), null);
            assertNull("Event should not be processed, Exception thrown", processed);

            Mockito.verify(cloudantDataHandler).getClientByBaseEntityId(baseEntityId);
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    public void testProcessEventWhenClientIsNull() {
        try {

            String baseEntityId = getBaseEntityId();

            CloudantDataHandler cloudantDataHandler = Mockito.mock(CloudantDataHandler.class);
            Mockito.when(cloudantDataHandler.getClientByBaseEntityId(baseEntityId)).thenReturn(null);

            ClientProcessor clientProcessor = ClientProcessor.getInstance(getContext());
            clientProcessor.setCloudantDataHandler(cloudantDataHandler);

            Boolean processed = clientProcessor.processEvent(createJsonObject(baseEntityIdJSONKey, baseEntityId), null);
            assertFalse("Event should not be processed", processed);

            Mockito.verify(cloudantDataHandler).getClientByBaseEntityId(baseEntityId);
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    public void testProcessEventWhenClientIsEmpty() {
        try {

            String baseEntityId = getBaseEntityId();

            CloudantDataHandler cloudantDataHandler = Mockito.mock(CloudantDataHandler.class);
            Mockito.when(cloudantDataHandler.getClientByBaseEntityId(baseEntityId)).thenReturn(createEmptyJsonObject());

            ClientProcessor clientProcessor = ClientProcessor.getInstance(getContext());
            clientProcessor.setCloudantDataHandler(cloudantDataHandler);

            Boolean processed = clientProcessor.processEvent(createJsonObject(baseEntityIdJSONKey, baseEntityId), null);
            assertFalse("Event should not be processed", processed);

            Mockito.verify(cloudantDataHandler).getClientByBaseEntityId(baseEntityId);
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    public void testProcessEventWhenClassificationHasNoRules() {
        try {

            String baseEntityId = getBaseEntityId();

            JSONObject client = createClient(baseEntityId);

            CloudantDataHandler cloudantDataHandler = Mockito.mock(CloudantDataHandler.class);
            Mockito.when(cloudantDataHandler.getClientByBaseEntityId(baseEntityId)).thenReturn(client);

            ClientProcessor clientProcessor = ClientProcessor.getInstance(getContext());
            clientProcessor.setCloudantDataHandler(cloudantDataHandler);

            Boolean processed = clientProcessor.processEvent(createJsonObject(baseEntityIdJSONKey, baseEntityId), createJsonObject("case_classification_rules", new JSONArray()));
            assertFalse("Event should not be processed", processed);

            Mockito.verify(cloudantDataHandler).getClientByBaseEntityId(baseEntityId);
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    public void testProcessEvent() {
        try {

            String baseEntityId = getBaseEntityId();

            JSONObject client = createClient(baseEntityId);
            JSONObject event = createEvent(baseEntityId, true);
            JSONObject classification = createClassification();

            CloudantDataHandler cloudantDataHandler = Mockito.mock(CloudantDataHandler.class);
            Mockito.when(cloudantDataHandler.getClientByBaseEntityId(baseEntityId)).thenReturn(client);

            ClientProcessor clientProcessor = ClientProcessor.getInstance(getContext());
            clientProcessor.setCloudantDataHandler(cloudantDataHandler);

            ClientProcessor spy = Mockito.spy(clientProcessor);
            Mockito.when(spy.processClientClass(Mockito.any(JSONObject.class), Mockito.any(JSONObject.class), Mockito.any(JSONObject.class))).thenReturn(true);

            Boolean processed = spy.processEvent(event, classification);
            assertTrue("Event should be processed", processed);

            Mockito.verify(cloudantDataHandler).getClientByBaseEntityId(baseEntityId);
            Mockito.verify(spy, Mockito.times(2)).processClientClass(Mockito.any(JSONObject.class), Mockito.any(JSONObject.class), Mockito.any(JSONObject.class));
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }

    }

    public void testProcessEventAndDetailsUpdated() {
        try {

            String baseEntityId = getBaseEntityId();

            JSONObject client = createClient(baseEntityId);

            JSONObject event = createEvent(baseEntityId, false);
            updateJsonObject(event, "eventDate", baseEntityId);

            JSONObject classification = createClassification();

            CloudantDataHandler cloudantDataHandler = Mockito.mock(CloudantDataHandler.class);
            Mockito.when(cloudantDataHandler.getClientByBaseEntityId(baseEntityId)).thenReturn(client);

            ClientProcessor clientProcessor = ClientProcessor.getInstance(getContext());
            clientProcessor.setCloudantDataHandler(cloudantDataHandler);

            ClientProcessor spy = Mockito.spy(clientProcessor);
            Mockito.when(spy.processClientClass(Mockito.any(JSONObject.class), Mockito.any(JSONObject.class), Mockito.any(JSONObject.class))).thenReturn(true);

            Mockito.doNothing().when(spy).saveClientDetails(Mockito.anyString(), Mockito.anyMap(), Mockito.anyLong());

            Boolean processed = spy.processEvent(event, classification);
            assertTrue("Event should be processed", processed);

            assertTrue("Event should have detailsUpdated field", event.has(detailsUpdated));

            assertTrue("Event detailsUpdated field shoud be true", event.getBoolean(detailsUpdated));

            Mockito.verify(cloudantDataHandler).getClientByBaseEntityId(baseEntityId);
            Mockito.verify(spy, Mockito.times(2)).processClientClass(Mockito.any(JSONObject.class), Mockito.any(JSONObject.class), Mockito.any(JSONObject.class));
            Mockito.verify(spy, Mockito.times(4)).saveClientDetails(Mockito.anyString(), Mockito.anyMap(), Mockito.anyLong());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }

    }

}
