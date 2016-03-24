package org.ei.telemedicine.doctor;

/**
 * Created by naveen on 6/2/15.
 */
public class PocDrugData {

    private String direction;
    private String frequncy;
    private String drugName;
    private String dosage;
    private String drugNoofDays;
    private String drugQty;
    private String drugStopByDate;
    private boolean isDrugDuplicate;

    public boolean isDrugDuplicate() {
        return isDrugDuplicate;
    }

    public void setIsDrugDuplicate(boolean isDrugDuplicate) {
        this.isDrugDuplicate = isDrugDuplicate;
    }

    public String getDrugNoofDays() {
        return drugNoofDays;
    }

    public void setDrugNoofDays(String drugNoofDays) {
        this.drugNoofDays = drugNoofDays;
    }

    public String getDrugQty() {
        return drugQty;
    }

    public void setDrugQty(String drugQty) {
        this.drugQty = drugQty;
    }

    public String getDrugStopByDate() {
        return drugStopByDate;
    }

    public void setDrugStopByDate(String drugStopByDate) {
        this.drugStopByDate = drugStopByDate;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getFrequncy() {
        return frequncy;
    }

    public void setFrequncy(String frequncy) {
        this.frequncy = frequncy;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }
}
