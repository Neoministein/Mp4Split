package com.mp4splitmaven.logging.logger;

import com.mp4splitmaven.screen.ScreenManager;
import com.neoutil.logging.logger.Logger;

public class ScreenLogger implements Logger {

    private int logLevel;
    private ScreenManager screenManager;

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
