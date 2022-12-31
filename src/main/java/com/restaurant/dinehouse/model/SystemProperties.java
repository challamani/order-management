package com.restaurant.dinehouse.model;

import java.util.HashMap;

public class SystemProperties {

    private HashMap<String,String> serviceCodes;
    private HashMap<String,User> systemCredentials;

    public HashMap<String, String> getServiceCodes() {
        return serviceCodes;
    }

    public void setServiceCodes(HashMap<String, String> serviceCodes) {
        this.serviceCodes = serviceCodes;
    }

    public HashMap<String, User> getSystemCredentials() {
        return systemCredentials;
    }

    public void setSystemCredentials(HashMap<String, User> systemCredentials) {
        this.systemCredentials = systemCredentials;
    }
}
