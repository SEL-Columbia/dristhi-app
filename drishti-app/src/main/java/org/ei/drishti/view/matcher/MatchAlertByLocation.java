package org.ei.drishti.view.matcher;

import android.content.Context;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.domain.FetchStatus;
import org.ei.drishti.event.Event;
import org.ei.drishti.event.Listener;
import org.ei.drishti.view.DialogAction;
import org.ei.drishti.view.widget.TextCanvas;

import java.util.ArrayList;
import java.util.List;

import static org.ei.drishti.Context.getInstance;

public class MatchAlertByLocation extends DialogMatcher<DisplayableString, Alert> {
    public static final DisplayableString DEFAULT_VALUE = new DisplayableString("All");
    private final Listener<FetchStatus> onDataFetchedFromServer;

    public MatchAlertByLocation(Context context, final DialogAction<DisplayableString> dialog) {
        super(dialog, DEFAULT_VALUE, TextCanvas.getInstance(context));

        onDataFetchedFromServer = new Listener<FetchStatus>() {
            public void onEvent(FetchStatus status) {
                if (!FetchStatus.fetched.equals(status)) return;

                dialog.changeOptions(getUniqueLocations());
            }
        };

        dialog.changeOptions(getUniqueLocations());
        Event.ON_DATA_FETCHED.addListener(onDataFetchedFromServer);
    }

    public boolean matches(Alert alert) {
        return alert.location() == null || DEFAULT_VALUE.equals(currentValue()) || alert.location().equals(currentValue().displayValue());
    }

    private List<DisplayableString> getUniqueLocations() {
        List<DisplayableString> displayables = new ArrayList<DisplayableString>();
        displayables.add(DEFAULT_VALUE);
        for (String location : getInstance().allAlerts().uniqueLocations()) {
            displayables.add(new DisplayableString(location));
        }
        return displayables;
    }

}
