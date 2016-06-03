package org.ei.opensrp.indonesia.domain;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.ei.opensrp.AllConstants.ANCRegistrationFields.HIGH_RISK_REASON;
import static org.ei.opensrp.AllConstants.ANCRegistrationFields.IS_HIGH_RISK;
import static org.ei.opensrp.indonesia.AllConstantsINA.BOOLEAN_TRUE;
import static org.ei.opensrp.indonesia.AllConstantsINA.SPACE;

/**
 * Created by Dimas Ciputra on 3/3/15.
 */
public class Ibu {
    private String id;
    private String kartuIbuId;
    private String referenceDate;
    private Map<String, String> details;
    private boolean isClosed;
    private String type;

    public Ibu(String id, String kartuIbuId, String referenceDate) {
        this.id = id;
        this.kartuIbuId = kartuIbuId;
        this.referenceDate = referenceDate;
        this.details = new HashMap<String, String>();
        this.isClosed = false;
    }

    public String getId() {
        return id;
    }

    public String getKartuIbuId() {
        return kartuIbuId;
    }

    public String getReferenceDate() {
        return referenceDate;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public String getType() {
        return type;
    }

    public boolean isHighRisk() {
        return BOOLEAN_TRUE.equals(details.get(IS_HIGH_RISK));
    }

    public String highRiskReason() {
        String highRiskReason = details.get(HIGH_RISK_REASON) == null ? "" : details.get(HIGH_RISK_REASON).trim();
        return StringUtils.join(new HashSet<String>(Arrays.asList(highRiskReason.split(SPACE))).toArray(), SPACE);
    }

    public Ibu withDetails(Map<String, String> details) {
        this.details = details;
        return this;
    }

    public Ibu withType(String type) {
        this.type = type;
        return this;
    }

    public Ibu setIsClosed(boolean isClosed) {
        this.isClosed = isClosed;
        return this;
    }

    public String getDetail(String name) {
        return details.get(name);
    }

    public boolean isANC() {
        return "anc".equalsIgnoreCase(this.type);
    }

    public boolean isPNC() {
        return "pnc".equalsIgnoreCase(this.type);
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
