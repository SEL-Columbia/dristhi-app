package org.ei.telemedicine.view.contract;

import com.google.gson.Gson;

import org.ei.telemedicine.Context;

import java.util.List;

public class ANMLocation {
    private String district;
    private String phcName;
    private String phcIdentifier;
    private String subCenter;
    private List<String> villages;

    public String asJSONString(String password, String wifeMINAge, String wifeMAXAge, String husbandMINAge, String husbandMAXAge, String countryCode) {
        return new Gson().toJson(new ANMLocationJSONString(district, phcName, subCenter, password, wifeMINAge, wifeMAXAge, husbandMINAge, husbandMAXAge, countryCode, System.currentTimeMillis()));
    }

    private class ANMLocationJSONString {
        private String district;
        private String phc;
        private String subCenter;
        private String password;
        private String wifeMINAge;
        private String wifeMAXAge;
        private String husbandMINAge;
        private String husbandMAXAge;
        private String countryCode;
        private long regNo;

        private ANMLocationJSONString(String district, String phc, String subCenter, String password, String wifeMINAge, String wifeMAXAge, String husbandMINAge, String husbandMAXAge, String countryCode, long regNo) {
            this.district = district;
            this.phc = phc;
            this.subCenter = subCenter;
            this.password = password;
            this.wifeMINAge = wifeMINAge;
            this.wifeMAXAge = wifeMAXAge;
            this.husbandMINAge = husbandMINAge;
            this.husbandMAXAge = husbandMAXAge;
            this.countryCode = countryCode;
            this.regNo = regNo;
        }
    }
}
