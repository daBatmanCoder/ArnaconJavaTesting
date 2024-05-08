package com.cellact.sdktesting;

import java.util.Properties;

import com.cellact.Config.ADataSaveHelper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SharedPreferencesHelper implements ADataSaveHelper {

    private Properties properties;
    private static final String PROP_FILE = "web2data.properties";

    public SharedPreferencesHelper() {
        properties = new Properties();
        try {
            FileInputStream in = new FileInputStream(PROP_FILE);
            properties.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setPreference(String key, String value) {
        properties.setProperty(key, value.toString());
        try {
            FileOutputStream out = new FileOutputStream(PROP_FILE);
            properties.store(out, null);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getPreference(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    @Override
    public void resetPreferences() {
        properties.clear();
        try {
            FileOutputStream out = new FileOutputStream(PROP_FILE);
            properties.store(out, null);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}