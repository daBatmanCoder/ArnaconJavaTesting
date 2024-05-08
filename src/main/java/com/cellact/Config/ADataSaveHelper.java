package com.cellact.Config;


public interface ADataSaveHelper {

    void setPreference(String key, String value);

    String getPreference(String key, String defaultValue);

    void resetPreferences();

}
