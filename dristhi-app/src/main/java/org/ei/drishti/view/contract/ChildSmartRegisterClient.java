package org.ei.drishti.view.contract;


import org.ei.drishti.domain.ChildServiceType;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.ei.drishti.util.DateUtil.formatDate;

public interface ChildSmartRegisterClient extends SmartRegisterClient {

    public static class ChildSickStatus {
        public static ChildSickStatus noDiseaseStatus = new ChildSickStatus(null, null, null);

        private String diseases;
        private String otherDiseases;
        private String date;

        public ChildSickStatus(String diseases, String otherDiseases, String date) {
            this.diseases = diseases;
            this.otherDiseases = otherDiseases;
            this.date = date;
        }

        public String diseases() {
            return diseases + (isBlank(otherDiseases) ? "" : (", " + otherDiseases));
        }

        public String date() {
            return formatDate(date);
        }
    }

    public String gender();

    public String weight();

    public String thayiCardNumber();

    public String motherEcNumber();

    public String dateOfBirth();

    public String fatherName();

    public String motherName();

    public String locationStatus();

    public List<ServiceProvidedDTO> serviceProvided();

    public ServiceProvidedDTO lastServiceProvided();

    public ServiceProvidedDTO illnessVisitServiceProvided();

    public ChildSickStatus sickStatus();

    public boolean isBcgDone();

    public boolean isOpvDone();

    public boolean isHepBDone();

    public boolean isPentavDone();

    public String bcgDoneDate();

    public String opvDoneDate();

    public String hepBDoneDate();

    public String pentavDoneDate();

    public boolean isMeaslesDone();

    public boolean isOpvBoosterDone();

    public boolean isDptBoosterDone();

    public boolean isVitaminADone();

    public String measlesDoneDate();

    public String opvBoosterDoneDate();

    public String dptBoosterDoneDate();

    public String vitaminADoneDate();

    public List<AlertDTO> alerts();

    public AlertDTO getAlert(ChildServiceType measles);

}
