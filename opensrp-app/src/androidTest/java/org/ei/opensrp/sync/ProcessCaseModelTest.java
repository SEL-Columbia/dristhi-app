package org.ei.opensrp.sync;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by keyman on 05/10/16.
 */
public class ProcessCaseModelTest extends BaseClientProcessorTest {

    public void testProcessCloseCaseModelWhenNullOrEmpty() {
        try {

            String baseEntityId = getBaseEntityId();
            JSONObject client = createClient(baseEntityId);

            Boolean processed = ClientProcessor.getInstance(getContext()).closeCase(client, null);
            assertFalse("Case Model should not be processed", processed);

            processed = ClientProcessor.getInstance(getContext()).closeCase(client, new JSONArray());
            assertFalse("Case Model should not be processed", processed);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public void testProcessCloseCaseModelWhenClientBaseEntityIdIsNull() {
        try {

            JSONObject client = createEmptyJsonObject();
            updateJsonObject(client, "Test", "test");

            Boolean processed = ClientProcessor.getInstance(getContext()).closeCase(client, createCases("case1"));
            assertNull("Case Model should not be processed, Exception thrown", processed);


        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public void testProcessCloseCaseModel() {
        try {

            String baseEntityId = getBaseEntityId();
            JSONObject client = createClient(baseEntityId);

            Boolean processed = ClientProcessor.getInstance(getContext()).closeCase(client, createCases("case1"));
            assertTrue("Case Model should not be processed", processed);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }


    public void testProcessCaseModelWhenNullOrEmpty() {
        try {

            String baseEntityId = getBaseEntityId();
            JSONObject client = createClient(baseEntityId);

            JSONObject event = createEvent(baseEntityId, false);

            Boolean processed = ClientProcessor.getInstance(getContext()).processCaseModel(event, client, null);
            assertFalse("Case Model should not be processed", processed);

            processed = ClientProcessor.getInstance(getContext()).processCaseModel(event, client, new JSONArray());
            assertFalse("Case Model should not be processed", processed);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }


}
