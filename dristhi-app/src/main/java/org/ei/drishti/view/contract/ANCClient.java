package org.ei.drishti.view.contract;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.ISODateTimeFormat;

import java.util.List;

public class ANCClient {
    private final String entityId;
    private final String ec_number;
    private final String village;
    private final String name;
    private final String thayi;
    private final String age;
    private String husband_name;
    private final String photo_path;
    private final String edd;
    private final String lmp;
    private final boolean isHighPriority;
    private final boolean isHighRisk;
    private final String locationStatus;
    private final String caste;
    private final List<AlertDTO> alerts;
    private final List<ServiceProvidedDTO> services_provided;

    public ANCClient(String entityId, String ecNumber, String village, String name, String thayi, String age, String husband_name,
                     String edd, String lmp, boolean isHighPriority, boolean isHighRisk, boolean outOfArea, String caste, String photo_path,
                     List<AlertDTO> alerts, List<ServiceProvidedDTO> services_provided) {
        this.entityId = entityId;
        this.ec_number = ecNumber;
        this.village = village;
        this.name = name;
        this.thayi = thayi;
        this.age = age;
        this.husband_name = husband_name;
        this.photo_path = photo_path;
        this.edd = LocalDateTime.parse(edd, DateTimeFormat.forPattern("EEE, dd MMM yyyy HH:mm:ss ZZZ")).toString(ISODateTimeFormat.dateTime());
        this.lmp = lmp;
        this.isHighPriority = isHighPriority;
        this.isHighRisk = isHighRisk;
        this.locationStatus = outOfArea ? "out_of_area" : "in_area";
        this.caste = caste;
        this.alerts = alerts;
        this.services_provided = services_provided;
    }

    public String wifeName() {
        return name;
    }
}
