package org.ei.drishti.view.contract;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class EC {
    private String caseId;
    private String wifeName;
    private String husbandName;
    private String ecNumber;
    private String villageName;
    private String thayiCardNumber;
    private boolean isHighPriority;
    private boolean hasTodos;

    public EC(String caseId, String wifeName, String husbandName, String villageName, String ecNumber, String thayiCardNumber, boolean isHighPriority, boolean hasTodos) {
        this.caseId = caseId;
        this.wifeName = wifeName;
        this.husbandName = husbandName;
        this.villageName = villageName;
        this.ecNumber = ecNumber;
        this.thayiCardNumber = thayiCardNumber;
        this.isHighPriority = isHighPriority;
        this.hasTodos = hasTodos;
    }

    public String wifeName() {
        return wifeName;
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
