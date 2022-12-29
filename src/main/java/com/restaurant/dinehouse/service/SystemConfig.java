package com.restaurant.dinehouse.service;

import com.google.gson.Gson;
import com.restaurant.dinehouse.model.SystemProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Calendar;
import java.util.Map;
import java.util.Properties;

@Component
@Slf4j
public class SystemConfig {

    private Properties properties;
    private Map<String, String> SYSTEM_CREDENTIALS;

    @Autowired
    public SystemConfig() {
        try {
            loadProperties();
            loadSystemCredentials();
        } catch (Exception ex) {
            log.info("failed to load system properties");
        }
    }

    private void loadProperties() throws IOException {
        InputStream inputStream = null;
        try {
            properties = new Properties();
            String propFileName = "application.properties";
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
        } catch (Exception ex) {
            log.error("failed to load application properties {}" + ex.getMessage());
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    public Properties getProperties() {
        return properties;
    }

    private void loadSystemCredentials() throws Exception {
        StringBuffer jsonStr = new StringBuffer();
        try {
            log.info("System Resource loading ::" + Calendar.getInstance().getTime());
            BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/system-credentials.json")));

            String oneLine;
            while ((oneLine = reader.readLine()) != null) {
                jsonStr.append(oneLine);
            }
            log.info("System Resource :: {}" + jsonStr);
        } catch (IOException e) {
            log.info(e.getMessage());
            throw e;
        }
        SystemProperties systemProperties = new Gson().fromJson(jsonStr.toString(), SystemProperties.class);
        SYSTEM_CREDENTIALS = systemProperties.getSystemCredentials();
        log.info("System credentials & service codes are loaded :: {}", Calendar.getInstance().getTime());
    }

    public String getEncryptedPassword(String username) {
        if (SYSTEM_CREDENTIALS.containsKey(username)) {
            return SYSTEM_CREDENTIALS.get(username);
        }
        throw new RuntimeException("No credentials found!");
    }
}
