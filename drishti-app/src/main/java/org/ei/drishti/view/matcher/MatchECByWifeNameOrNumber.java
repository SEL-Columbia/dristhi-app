package org.ei.drishti.view.matcher;

import android.widget.EditText;
import org.ei.drishti.domain.EligibleCouple;

public class MatchECByWifeNameOrNumber extends TextFieldMatcher<EligibleCouple> {
    public MatchECByWifeNameOrNumber(EditText searchBox) {
        super(searchBox);
    }

    public boolean matches(EligibleCouple eligibleCouple) {
        String currentValue = currentValue().displayValue().toLowerCase();
        return (eligibleCouple.ecNumber().toLowerCase().contains(currentValue) || eligibleCouple.wifeName().toLowerCase().contains(currentValue));
    }
}
