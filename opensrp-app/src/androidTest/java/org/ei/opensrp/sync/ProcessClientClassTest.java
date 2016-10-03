package org.ei.opensrp.sync;

import org.json.JSONObject;


/**
 * Created by keyman on 30/09/16.
 */
public class ProcessClientClassTest extends BaseClientProcessorTest {



    public void testProcessClientClassWhenClassIsNullOrEmpty() {
        try {

            JSONObject client = createClient();
            JSONObject event = createEvent(true);

            Boolean processed = ClientProcessor.getInstance(getContext()).processClientClass(null, event, client);
            assertFalse("Client should not be processed", processed);

            processed = ClientProcessor.getInstance(getContext()).processClientClass(createEmptyJsonObject(), event, client);
            assertFalse("Client should not be processed", processed);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public void testProcessClientClassWhenEventIsNullOrEmpty() {
        try {

            JSONObject client = createClient();
            JSONObject classsification = createClassification();

            Boolean processed = ClientProcessor.getInstance(getContext()).processClientClass(classsification, null, client);
            assertFalse("Client should not be processed", processed);

            processed = ClientProcessor.getInstance(getContext()).processClientClass(classsification, createEmptyJsonObject(), client);
            assertFalse("Client should not be processed", processed);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public void testProcessClientClassWhenClientIsNullOrEmpty() {
        try {

            JSONObject classsification = createClassification();
            JSONObject event = createEvent(true);

            Boolean processed = ClientProcessor.getInstance(getContext()).processClientClass(classsification, event, null);
            assertFalse("Client should not be processed", processed);

            processed = ClientProcessor.getInstance(getContext()).processClientClass(classsification, event, createEmptyJsonObject());
            assertFalse("Client should not be processed", processed);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public void testProcessClientClassWhenClassHasNoRules() {
        try {

            JSONObject classsification = createClassification();
            JSONObject client = createClient();
            JSONObject event = createEvent(true);

            Boolean processed = ClientProcessor.getInstance(getContext()).processClientClass(classsification, event, client);
            assertNull("Client should not be processed, Exception thrown", processed);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
