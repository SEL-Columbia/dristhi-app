package org.ei.drishti.view.contract;


import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.ei.drishti.util.DateUtil.formatDate;

public interface ChildSmartRegisterClient extends SmartRegisterClient {

    public static class SickStatus {
        public static SickStatus noDiseaseStatus = new SickStatus(null, null, null);

        private String diseases;
        private String otherDiseases;
        private String date;

        public SickStatus(String diseases, String otherDiseases, String date) {
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

    public String thayiCardNumber();

    public String motherEcNumber();

    public String dateOfBirth();

    public String fatherName();

    public String motherName();

    public String locationStatus();

    public List<ServiceProvidedDTO> serviceProvided();

    public ServiceProvidedDTO lastServiceProvided();

    public ServiceProvidedDTO illnessVisitService();

    public SickStatus sickStatus();
}
