package com.mp4splitmaven.logging;

import com.mp4splitmaven.helperclass.FileManager;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class LogfileLogger implements Logger {

    private int logLevel = Integer.MAX_VALUE;
    private String logLocation;


    public LogfileLogger(){
        logLocation = createDebugFile();
    }

    @Override
    public void print(String text) {
        FileManager.writeToFile(logLocation,text);
    }

    private String createDebugFile() {

        String logFileLocation = System.getProperty("user.dir") +"\\logs\\"+
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
