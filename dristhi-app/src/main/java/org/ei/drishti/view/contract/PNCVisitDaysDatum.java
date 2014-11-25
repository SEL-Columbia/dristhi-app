package org.ei.drishti.view.contract;

public class PNCVisitDaysDatum {
    Integer day;
    PNCVisitType visitType;

    public PNCVisitDaysDatum(Integer day, PNCVisitType visitType) {
        this.day = day;
        this.visitType = visitType;
    }

    public Integer getDay() {
        return day;
    }

    public PNCVisitType getVisitType() {
        return visitType;
    }
}
