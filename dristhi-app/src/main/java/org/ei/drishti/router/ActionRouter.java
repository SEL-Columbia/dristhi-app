package org.ei.drishti.router;

import org.ei.drishti.domain.AlertActionRoute;
import org.ei.drishti.domain.ChildActionRoute;
import org.ei.drishti.domain.ECActionRoute;
import org.ei.drishti.dto.Action;

import static org.ei.drishti.util.Log.logWarn;

public class ActionRouter {
    public void directAlertAction(Action action) {
        AlertActionRoute[] alertActionRoutes = AlertActionRoute.values();
        for (AlertActionRoute alertActionRoute : alertActionRoutes) {
            if (alertActionRoute.identifier().equals(action.type())) {
                alertActionRoute.direct(action);
                return;
            }
        }
        logWarn("Unknown type in Alert action: " + action);
    }

    public void directChildAction(Action action) {
        ChildActionRoute[] childActionRoutes = ChildActionRoute.values();
        for (ChildActionRoute childActionRoute : childActionRoutes) {
            if (childActionRoute.identifier().equals(action.type())) {
                childActionRoute.direct(action);
                return;
            }
        }
        logWarn("Unknown type in Child action: " + action);
    }

    public void directECAction(Action action) {
        ECActionRoute[] ecActionRoutes = ECActionRoute.values();
        for (ECActionRoute ecActionRoute : ecActionRoutes) {
            if (ecActionRoute.identifier().equals(action.type())) {
                ecActionRoute.direct(action);
                return;
            }
        }
        logWarn("Unknown type in EC action: " + action);
    }
}
