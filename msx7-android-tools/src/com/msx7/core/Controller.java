package com.msx7.core;

import java.io.IOException;
import java.util.Properties;

import android.app.Application;

public class Controller extends Application {
    public static final String CONFIG_NAME = "config.ini";
    public static final String MAX_PROCESS = "max_process";
    private static Controller instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        loadConfig();
    }

    public static final Controller getApplication() {
        return instance;
    }

    private void loadConfig() {
        try {
            Properties pro = new Properties();
            pro.load(getAssets().open(CONFIG_NAME));
            pro.getProperty(MAX_PROCESS, "1");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
}
