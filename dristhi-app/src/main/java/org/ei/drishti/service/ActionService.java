package org.ei.drishti.service;

import com.google.gson.Gson;
import org.ei.drishti.domain.*;
import org.ei.drishti.dto.Action;
import org.ei.drishti.repository.*;
import org.ei.drishti.router.ActionRouter;
import org.ei.drishti.util.Log;

import java.util.List;

import static java.lang.String.format;
import static org.ei.drishti.domain.FetchStatus.fetchedFailed;
import static org.ei.drishti.domain.FetchStatus.nothingFetched;

public class ActionService {
    private final ActionRouter actionRouter;
    private DrishtiService drishtiService;
    private AllSettings allSettings;
    private AllReports allReports;
    private EligibleCoupleService eligibleCoupleService;
    private MotherService motherService;

    public ActionService(DrishtiService drishtiService, AllSettings allSettings, AllReports allReports,
                         EligibleCoupleService eligibleCoupleService, MotherService motherService) {
        this.drishtiService = drishtiService;
        this.allSettings = allSettings;
        this.allReports = allReports;
        this.eligibleCoupleService = eligibleCoupleService;
        this.motherService = motherService;
        this.actionRouter = new ActionRouter();
    }

    public ActionService(DrishtiService drishtiService, AllSettings allSettings, AllReports allReports,
                         EligibleCoupleService eligibleCoupleService, MotherService motherService, ActionRouter actionRouter) {
        this.drishtiService = drishtiService;
        this.allSettings = allSettings;
        this.allReports = allReports;
        this.eligibleCoupleService = eligibleCoupleService;
        this.motherService = motherService;
        this.actionRouter = actionRouter;
    }

    public FetchStatus fetchNewActions() {
        String previousFetchIndex = allSettings.fetchPreviousFetchIndex();
        Response<List<Action>> response = drishtiService.fetchNewActions(allSettings.fetchRegisteredANM(), previousFetchIndex);

        if (response.isFailure()) {
            return fetchedFailed;
        }

        if (response.payload().isEmpty()) {
            return nothingFetched;
        }

        handleActions(response);
        return FetchStatus.fetched;
    }

    private void handleActions(Response<List<Action>> response) {
        for (Action actionToUse : response.payload()) {
            try {
                handleAction(actionToUse);
            } catch (Exception e) {
                Log.logError(format("Failed while handling action with target: " + actionToUse.target()));
            }
        }
    }

    private void handleAction(Action actionToUse) {
        if (actionToUse.target().equals("alert")) {
            runAction(actionToUse, new ActionHandler() {
                @Override
                public void run(Action action) {
                    actionRouter.directAlertAction(action);
                }
            });

        } else if (actionToUse.target().equals("child")) {
            runAction(actionToUse, new ActionHandler() {
                @Override
                public void run(Action action) {
                    actionRouter.directChildAction(action);
                }
            });

        } else if (actionToUse.target().equals("mother")) {
            runAction(actionToUse, new ActionHandler() {
                @Override
                public void run(Action action) {
                    motherService.handleAction(action);
                }
            });

        } else if (actionToUse.target().equals("report")) {
            runAction(actionToUse, new ActionHandler() {
                @Override
                public void run(Action action) {
                    allReports.handleAction(action);
                }
            });

        } else if (actionToUse.target().equals("eligibleCouple")) {
            runAction(actionToUse, new ActionHandler() {
                @Override
                public void run(Action action) {
                    eligibleCoupleService.handleAction(action);
                }
            });

        } else {
            Log.logWarn("Unknown action " + actionToUse.target());
        }
        allSettings.savePreviousFetchIndex(actionToUse.index());
    }

    private void runAction(Action action, ActionHandler actionHandler) {
        try {
            actionHandler.run(action);
        } catch (Exception e) {
            throw new RuntimeException("Failed to run action: " + new Gson().toJson(action), e);
        }
    }

    private abstract class ActionHandler {
        public void run(Action action) {
        }
    }
}
