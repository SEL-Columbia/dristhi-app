package org.ei.telemedicine.doctor;

/**
 * Created by naveen on 6/1/15.
 */
public class PocDiagnosis {
    public String icd10_chapter;
    public String icd10_code;
    public String icd10_name;

    public String getIcd10_chapter() {
        return icd10_chapter;
    }

    public void setIcd10_chapter(String icd10_chapter) {
        this.icd10_chapter = icd10_chapter;
    }

    public String getIcd10_code() {
        return icd10_code;
    }

    public void setIcd10_code(String icd10_code) {
        this.icd10_code = icd10_code;
    }

    public String  isIcd10_name() {
        return icd10_name;
    }

    public void setIcd10_name(String icd10_name) {
        this.icd10_name = icd10_name;
    }

    public String getIcd10_name() {
        return icd10_name;
    }
}
