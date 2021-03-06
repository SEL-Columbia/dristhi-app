package org.ei.drishti.view.dialog;

import org.ei.drishti.Context;
import org.ei.drishti.R;
import org.ei.drishti.view.contract.ServiceProvidedDTO;
import org.joda.time.LocalDate;

public class PNCVisitClause implements FilterClause<ServiceProvidedDTO> {
    private LocalDate visitEndDate;
    private String PNC_IDENTIFIER = "PNC";
//    private String PNC_IDENTIFIER = Context.getInstance().getStringResource(R.string.str_pnc_clause);

    public PNCVisitClause (LocalDate visitEndDate) {
        this.visitEndDate = visitEndDate;
    }

    @Override
    public boolean filter(ServiceProvidedDTO service) {
        return PNC_IDENTIFIER.equalsIgnoreCase(service.name()) && service.localDate().isBefore(visitEndDate);
    }
}
