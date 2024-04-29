package com.cellact.sdktesting;

import com.cellact.Config.ALogger;

public class Logger implements ALogger{

    public void debug(String value) {
        System.out.println(value);
    }

    public void error(String error, Exception e) {
        System.out.println(error);
        e.printStackTrace();
    }
}
