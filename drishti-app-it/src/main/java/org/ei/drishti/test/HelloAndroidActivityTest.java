package org.ei.drishti.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.ListView;
import org.ei.drishti.R;
import org.ei.drishti.activity.DrishtiMainActivity;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.domain.AlertAction;
import org.ei.drishti.domain.Response;
import org.ei.drishti.service.DrishtiService;

import java.util.*;

public class HelloAndroidActivityTest extends ActivityInstrumentationTestCase2<DrishtiMainActivity> {

    public HelloAndroidActivityTest() {
        super(DrishtiMainActivity.class);
    }

    @UiThreadTest
    public void testActivity() throws Exception {
        DrishtiMainActivity activity = getActivity();
        String suffix = String.valueOf(new Date().getTime());
        activity.setDrishtiService(fakeDrishtiService(suffix));

        final Button button = (Button) activity.findViewById(R.id.button);
        button.performClick();

        ListView listView = (ListView) activity.findViewById(R.id.listView);
        assertEquals(2, listView.getCount());

        Alert firstAlert = ((Alert) listView.getItemAtPosition(0));
        Alert secondAlert = ((Alert) listView.getItemAtPosition(1));

        assertEquals("Theresa 1 " + suffix, firstAlert.beneficiaryName());
        assertEquals("Theresa 2 " + suffix, secondAlert.beneficiaryName());
    }

    private DrishtiService fakeDrishtiService(final String suffix) {
        return new DrishtiService(null, null) {
            @Override
            public Response<List<AlertAction>> fetchNewAlertActions(String anmIdentifier, String previouslyFetchedIndex) {
                AlertAction deleteXAction = new AlertAction("Case X", "deleteAll", new HashMap<String, String>(), "123456");
                AlertAction deleteYAction = new AlertAction("Case Y", "deleteAll", new HashMap<String, String>(), "123456");
                AlertAction firstAction = new AlertAction("Case X", "create", dataForCreateAction("due", "Theresa 1 " + suffix, "ANC 1", "Thaayi " + suffix, "2012-04-09"), "123456");
                AlertAction secondAction = new AlertAction("Case Y", "create", dataForCreateAction("due", "Theresa 2 " + suffix, "ANC 1", "Thaayi " + suffix, "2012-04-09"), "123456");

                return new Response<List<AlertAction>>(Response.ResponseStatus.success, Arrays.asList(deleteXAction, deleteYAction, firstAction, secondAction));
            }

            private Map<String, String> dataForCreateAction(String lateness, String motherName, String visitCode, String thaayiCardNumber, String dueDate) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("latenessStatus", lateness);
                map.put("motherName", motherName);
                map.put("visitCode", visitCode);
                map.put("thaayiCardNumber", thaayiCardNumber);
                map.put("dueDate", dueDate);
                return map;
            }
        };
    }
}

