package org.ei.drishti.domain;

import java.util.Map;

public class ServiceProvided {
    private final String name;
    private final String date;
    private final Map<String, String> data;

    public ServiceProvided(String name, String date, Map<String, String> data) {
        this.name = name;
        this.date = date;
        this.data = data;
    }

    public String name() {
        return name;
    }

    public String date() {
        return date;
    }

    public Map<String, String> data() {
        return data;
    }
}
