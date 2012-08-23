package org.ei.drishti.view.contract;

import com.google.gson.Gson;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.domain.CommCareForm;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static org.ei.drishti.domain.CommCareForm.ANC_SERVICES;
import static org.ei.drishti.dto.AlertPriority.normal;
import static org.ei.drishti.dto.AlertPriority.urgent;

@RunWith(RobolectricTestRunner.class)
public class ProfileTodoTest {
    @Test
    public void shouldProvideProfileTodoDataBasedOnVisitCode() throws Exception {
        assertData("ANC Visit #1", ANC_SERVICES, new ProfileTodo(new Alert("Case X", "Theresa", "bherya", "ANC 1", "Thaayi 1", urgent, "2012-02-02", "2012-02-11")));
        assertData("ANC Visit #2", ANC_SERVICES, new ProfileTodo(new Alert("Case X", "Theresa", "bherya", "ANC 2", "Thaayi 1", normal, "2012-02-02", "2012-02-11")));
        assertData("ANC Visit #3", ANC_SERVICES, new ProfileTodo(new Alert("Case X", "Theresa", "bherya", "ANC 3", "Thaayi 1", normal, "2012-02-02", "2012-02-11")));
        assertData("ANC Visit #4", ANC_SERVICES, new ProfileTodo(new Alert("Case X", "Theresa", "bherya", "ANC 4", "Thaayi 1", urgent, "2012-02-02", "2012-02-11")));
    }

    @Test
    public void shouldProvideSensibleDefaultsIfAVisitCodeIsNotFound() throws Exception {
        assertData("UNKNOWN_VISIT_CODE", null, new ProfileTodo(new Alert("Case X", "Theresa", "bherya", "UNKNOWN_VISIT_CODE", "Thaayi 1", urgent, "2012-02-02", "2012-02-11")));
    }

    private void assertData(String message, CommCareForm formToOpen, ProfileTodo todo) {
        String formToOpenMessage = "";
        if (formToOpen != null) {
            formToOpenMessage = ",\"formToOpen\":\"" + formToOpen + "\"";
        }
        assertEquals("{\"message\":\"" + message + "\"" + formToOpenMessage + "}", new Gson().toJson(todo));
    }
}
