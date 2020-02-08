package com.mp4splitmaven;

import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class LoggingHandler {


    private static String stringToLog = "";
    private static int debugLevel = 10;
    private static String logLocation;

    public final static int FATAL = 0;
    public final static int ERROR = 1;
    public final static int WARN  = 2;
    public final static int INFO  = 3;
    public final static int DEBUG = 4;
    public final static int KEYPRESS = 5;

    private LoggingHandler() {
        logLocation = createDebugFile();

        println(LoggingHandler.INFO,"new Logfile at ["+logLocation+"]");
    }

    public void setDebugLevel() {
        debugLevel = Settings.getInstance().getDebuglevel();
    }
    private static LoggingHandler instance = new LoggingHandler();
    public static LoggingHandler getInstance() {
        return  instance;
    }


    public static void println(int loggingLevel, String text){
        print(loggingLevel,text+"\n");
    }

    public static void println(int loggingLevel, String text, Exception exeption){
        text += ("\n"+exeption.getStackTrace()[0].getClassName()+": "+exeption.getMessage());
        for(StackTraceElement stackTrace: exeption.getStackTrace()) {

            text +=("\n"+"at "+stackTrace.getClassName()+
                    "."+stackTrace.getMethodName()+
                    "("+stackTrace.getMethodName()+
                    "."+stackTrace.getLineNumber()+")");

        }
        print(loggingLevel,text+"\n");
    }

    public static void print(int loggingLevel,String text){
        printNoLog(loggingLevel,debugLevelToString(loggingLevel)+text);
        checkPrintToLog(loggingLevel,text);
    }

    public static void printNoLog(int loggingLevel,String text) {
        if (loggingLevel <= debugLevel){
            System.out.print(text);
        }
    }

    private String createDebugFile() {

        String logFileLocation = System.getProperty("user.dir") +"\\logs\\"+
                (new SimpleDateFormat("yyyy.MM.dd - HH.mm.ss").format(
                        new Timestamp(System.currentTimeMillis())) +
                ".txt").replace(":",".");
        try {
            new File(logFileLocation).createNewFile();

        } catch (IOException e) {
            printNoLog(LoggingHandler.FATAL,"There is a problem creating a Logfile: " + e.getMessage());
        }
        
        return logFileLocation;

    }
    private static String debugLevelToString(int debugLevel){
        switch (debugLevel){
            case FATAL:
                return "[FATAL]";
            case ERROR:
                return "[ERROR]";
            case WARN:
                return "[WARN]";
            case INFO:
                return "[INFO]";
            case DEBUG:
                return "[DEBUG]";
            default:
                return "[?????]";
        }
    }

    private static void checkPrintToLog(int loggingLevel, String textToPrint) {
        stringToLog += textToPrint;
        if(stringToLog.contains("\n") || stringToLog.contains("\r")){
            stringToLog = debugLevelToString(loggingLevel) + stringToLog;
            printToLog();

            stringToLog = "";
        }
    }
    private static void printToLog() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(logLocation,true));
            writer.write("["+new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(
                            new Timestamp(System.currentTimeMillis()))+"]" + stringToLog);
            writer.close();

        } catch (IOException e) {
            printNoLog(LoggingHandler.WARN,"There was a problem while writing to the Log");
        }
    }




}
