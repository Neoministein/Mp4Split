package com.mp4splitmaven;

import com.mp4splitmaven.logging.LogfileLogger;
import com.mp4splitmaven.logging.Logger;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class LoggingHandler {

    private static String stringToLog = "";

    public final static int FATAL = 0;
    public final static int ERROR = 1;
    public final static int WARN  = 2;
    public final static int INFO  = 3;
    public final static int DEBUG = 4;
    public final static int KEYPRESS = 5;

    private static LoggingHandler instance = new LoggingHandler();
    private static List<Logger> loggers;

    private LoggingHandler() {
        loggers = new ArrayList<>();
        addLogger(new LogfileLogger());
    }
    public static LoggingHandler getInstance() {
        return  instance;
    }


    public static void println(int loggingLevel, String text){
        print(loggingLevel,text+"\n");
    }

    public static void println(int loggingLevel, String text, Exception exception){
        print(loggingLevel,text+getStackTrace(exception)+"\n");
    }

    public static void print(int loggingLevel,String text){
        if(hasNewLine(text)){
            log(loggingLevel);
            resetLoggingText();
        }
    }

    public static void printlnNoIO(int loggingLevel, String text) {printNoIO(loggingLevel,text+"\n");}

    public static void printlnNoIO(int loggingLevel, String text, Exception exception){
        printNoIO(loggingLevel,text+getStackTrace(exception)+"\n");
    }

    public static void printNoIO(int loggingLevel,String text){
        for(Logger logger : loggers){
            if(!logger.isIOLogger()) {
                if (loggingLevel <= logger.getLoglevel()) {
                    logger.print(text);
                }
            }
        }
    }

    private static boolean hasNewLine(String textToPrint) {
        stringToLog += textToPrint;
        if(stringToLog.contains("\n") || stringToLog.contains("\r")){
            return true;
        }else {
            return false;
        }
    }

    private static void resetLoggingText(){
        stringToLog = "";
    }

    private static void log(int loggingLevel){
        String textToPrint = generatePreText(loggingLevel);

        for (Logger logger : loggers){
            if (loggingLevel <= logger.getLoglevel()){
                logger.print(textToPrint);
            }
        }
    }

    private static String generatePreText(int loggingLevel){
        return "["+new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(
                new Timestamp(System.currentTimeMillis()))+"]" +
                debugLevelToString(loggingLevel)+
                stringToLog;
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
            case KEYPRESS  :
                return "[KEYPRESS]";
            default:
                return "[?????]";
        }
    }

    public static void addLogger(Logger logger){
        loggers.add(logger);
        println(LoggingHandler.DEBUG,"new Log location at ["+logger.getLoglocation()+"]");
    }

    public static void clearLogger(){
        loggers.clear();
    }

    private static String getStackTrace(Exception exception){
        String stackTrace = ("\n"+exception.getStackTrace()[0].getClassName()+": "+exception.getMessage());
        for(StackTraceElement stackTraceElement: exception.getStackTrace()) {

            stackTrace +=("\n"+"at "+stackTraceElement.getClassName()+
                    "."+stackTraceElement.getMethodName()+
                    "("+stackTraceElement.getMethodName()+
                    "."+stackTraceElement.getLineNumber()+")");

        }
        return stackTrace;
    }
}
