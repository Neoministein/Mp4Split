package com.mp4splitmaven.logging;

import com.mp4splitmaven.screen.ScreenManager;

public class ScreenLogger implements Logger {

    int logLevel;
    ScreenManager screenManager;

    public ScreenLogger(int logLevel,ScreenManager screenManager){
        this.logLevel = logLevel;
        this.screenManager = screenManager;
    }

    @Override
    public void print(String text) {
        screenManager.displayToLog(text);
    }

    @Override
    public int getLoglevel() {
        return logLevel;
    }

    @Override
    public boolean isIOLogger() {
        return true;
    }

    @Override
    public String getLoglocation() {
        return "Application screen";
    }
}
