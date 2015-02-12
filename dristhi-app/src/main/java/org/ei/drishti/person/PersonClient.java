package org.ei.drishti.person;

/**
 * Created by user on 2/12/15.
 */
public class PersonClient {

    private String caseId;
    private String name;
    private String age;
    private String sex;
    private String address;
    private String resistanceType;
    private String patientType;
    private String riskFactors;
    private String riskFactorsOther;
    private String drugRegimenStart;
    private String drugRegimen;
    private String currentDrugRegimenStart;
    private String bmi;
    private String currentBmi;
    private String resistanceDrugs;
    private String currentResistanceDrugs;

    public PersonClient(String caseId, String name, String age, String sex, String address, String resistanceType, String patientType, String riskFactors, String riskFactorsOther, String drugRegimenStart, String drugRegimen, String currentDrugRegimenStart, String bmi, String currentBmi, String resistanceDrugs, String currentResistanceDrugs) {
        this.caseId = caseId;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.address = address;
        this.resistanceType = resistanceType;
        this.patientType = patientType;
        this.riskFactors = riskFactors;
        this.riskFactorsOther = riskFactorsOther;
        this.drugRegimenStart = drugRegimenStart;
        this.drugRegimen = drugRegimen;
        this.currentDrugRegimenStart = currentDrugRegimenStart;
        this.bmi = bmi;
        this.currentBmi = currentBmi;
        this.resistanceDrugs = resistanceDrugs;
        this.currentResistanceDrugs = currentResistanceDrugs;
    }

    // Getter
    public String getCaseId() {
        return caseId;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    public String getAddress() {
        return address;
    }

    public String getResistanceType() {
        return resistanceType;
    }

    public String getPatientType() {
        return patientType;
    }

    public String getRiskFactors() {
        return riskFactors;
    }

    public String getRiskFactorsOther() {
        return riskFactorsOther;
    }

    public String getDrugRegimenStart() {
        return drugRegimenStart;
    }

    public String getDrugRegimen() {
        return drugRegimen;
    }

    public String getCurrentDrugRegimenStart() {
        return currentDrugRegimenStart;
    }

    public String getBmi() {
        return bmi;
    }

    public String getCurrentBmi() {
        return currentBmi;
    }

    public String getResistanceDrugs() {
        return resistanceDrugs;
    }

    public String getCurrentResistanceDrugs() {
        return currentResistanceDrugs;
    }

    // Setter
    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setResistanceType(String resistanceType) {
        this.resistanceType = resistanceType;
    }

    public void setPatientType(String patientType) {
        this.patientType = patientType;
    }

    public void setRiskFactors(String riskFactors) {
        this.riskFactors = riskFactors;
    }

    public void setRiskFactorsOther(String riskFactorsOther) {
        this.riskFactorsOther = riskFactorsOther;
    }

    public void setDrugRegimenStart(String drugRegimenStart) {
        this.drugRegimenStart = drugRegimenStart;
    }

    public void setDrugRegimen(String drugRegimen) {
        this.drugRegimen = drugRegimen;
    }

    public void setCurrentDrugRegimenStart(String currentDrugRegimenStart) {
        this.currentDrugRegimenStart = currentDrugRegimenStart;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public void setCurrentBmi(String currentBmi) {
        this.currentBmi = currentBmi;
    }

    public void setResistanceDrugs(String resistanceDrugs) {
        this.resistanceDrugs = resistanceDrugs;
    }

    public void setCurrentResistanceDrugs(String currentResistanceDrugs) {
        this.currentResistanceDrugs = currentResistanceDrugs;
    }
}
