package org.ei.drishti.service;

import com.google.gson.Gson;
import org.ei.drishti.domain.FetchStatus;
import org.ei.drishti.domain.Response;
import org.ei.drishti.dto.Action;
import org.ei.drishti.repository.*;
import org.ei.drishti.util.Log;

import java.util.List;

import static java.lang.String.format;
import static org.ei.drishti.domain.FetchStatus.fetchedFailed;
import static org.ei.drishti.domain.FetchStatus.nothingFetched;

public class ActionService {
    private final DrishtiService drishtiService;
    private final AllSettings allSettings;
    private final AllEligibleCouples allEligibleCouples;
    private final AllBeneficiaries allBeneficiaries;
    private final AllReports allReports;
    private AlertService alertService;
    private ChildService childService;

    public ActionService(DrishtiService drishtiService, AllSettings allSettings, AllEligibleCouples allEligibleCouples,
                         AllBeneficiaries allBeneficiaries, AllReports allReports, AlertService alertService, ChildService childService) {
        this.drishtiService = drishtiService;
        this.allSettings = allSettings;
        this.allEligibleCouples = allEligibleCouples;
        this.allBeneficiaries = allBeneficiaries;
        this.allReports = allReports;
        this.alertService = alertService;
        this.childService = childService;
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
                    alertService.handleAction(action);
                }
            });

        } else if (actionToUse.target().equals("child")) {
            runAction(actionToUse, new ActionHandler() {
                @Override
                public void run(Action action) {
                    childService.handleAction(action);
                }
            });

        } else if (actionToUse.target().equals("mother")) {
            runAction(actionToUse, new ActionHandler() {
                @Override
                public void run(Action action) {
                    allBeneficiaries.handleMotherAction(action);
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
                    allEligibleCouples.handleAction(action);
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
