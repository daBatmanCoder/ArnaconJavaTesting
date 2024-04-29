package com.cellact.Config;

public interface ALogger {
    void debug(String value);

    void error(String error, Exception e);
}
