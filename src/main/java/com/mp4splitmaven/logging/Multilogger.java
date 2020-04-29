package com.mp4splitmaven.logging;

import com.mp4splitmaven.logging.logger.LogfileLogger;
import com.mp4splitmaven.logging.logger.Logger;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Multilogger implements Logging {

    public static final int KEYPRESS = 5;

    private String stringToLog = "";

    private static Multilogger instance = new Multilogger();
    private List<Logger> loggers;

    private Multilogger() {
        loggers = new ArrayList<>();
        addLogger(new LogfileLogger());
    }
    public static Multilogger getInstance() {
        return  instance;
    }


    public void println(int loggingLevel, String text){
        print(loggingLevel,text+"\n");
    }

    public void println(int loggingLevel, String text, Exception exception){
        print(loggingLevel,text+getStackTrace(exception)+"\n");
    }

    public void print(int loggingLevel,String text){
        if(hasNewLine(text)){
            log(loggingLevel);
            resetLoggingText();
        }
    }

    public void printlnNoIO(int loggingLevel, String text) {printNoIO(loggingLevel,text+"\n");}

    public void printlnNoIO(int loggingLevel, String text, Exception exception){
        printNoIO(loggingLevel,text+getStackTrace(exception)+"\n");
    }

    public void printNoIO(int loggingLevel,String text){
        for(Logger logger : loggers){
            if(!logger.isIOLogger()) {
                if (loggingLevel <= logger.getLoglevel()) {
                    logger.print(text);
                }
            }
        }
    }
    static String debugLevelToString(int debugLevel){
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

    private boolean hasNewLine(String textToPrint) {
        stringToLog += textToPrint;
        if(stringToLog.contains("\n") || stringToLog.contains("\r")){
            return true;
        }else {
            return false;
        }
    }

    private void resetLoggingText(){
        stringToLog = "";
    }

    private void log(int loggingLevel){
        String textToPrint = generatePreText(loggingLevel);

        for (Logger logger : loggers){
            if (loggingLevel <= logger.getLoglevel()){
                logger.print(textToPrint);
            }
        }
    }

    private String generatePreText(int loggingLevel){
        return "["+new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(
                new Timestamp(System.currentTimeMillis()))+"]" +
                debugLevelToString(loggingLevel)+
                stringToLog;
    }

    public void addLogger(Logger logger){
        loggers.add(logger);
        println(Multilogger.DEBUG,"new Log location at ["+logger.getLoglocation()+"]");
    }

    public void clearLogger(){
        loggers.clear();
    }

    private String getStackTrace(Exception exception){
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
