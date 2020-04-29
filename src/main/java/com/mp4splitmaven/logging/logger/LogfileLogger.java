package com.mp4splitmaven.logging.logger;

import com.mp4splitmaven.helperclass.FileManager;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class LogfileLogger implements Logger {

    private static final String BASELOCATION = System.getProperty("user.dir") +"\\logs\\";

    private final int logLevel = Integer.MAX_VALUE;
    private final String logLocation;


    public LogfileLogger(){
        logLocation = createDebugFile();
    }

    @Override
    public void print(String text) {
        FileManager.writeToFile(logLocation,text);
    }

    private String createDebugFile() {

        String logFileLocation = BASELOCATION +
                (new SimpleDateFormat("yyyy.MM.dd - HH.mm.ss").format(
                        new Timestamp(System.currentTimeMillis())) +
                        ".txt").replace(":",".");

        FileManager.createFile(logFileLocation);

        return logFileLocation;

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
        return logLocation;
    }
}
