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

    public String asJSONString(String password) {
        return new Gson().toJson(new ANMLocationJSONString(district, phcIdentifier, subCenter, password));
    }

    private class ANMLocationJSONString {
        private String district;
        private String phc;
        private String subCenter;
        private String password;

        private ANMLocationJSONString(String district, String phc, String subCenter, String password) {
            this.district = district;
            this.phc= phc;
            this.subCenter = subCenter;
            this.password = password;
        }
    }
}
