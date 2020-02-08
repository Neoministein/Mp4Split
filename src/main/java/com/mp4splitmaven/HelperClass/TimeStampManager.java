package com.mp4splitmaven.HelperClass;

import com.mp4splitmaven.LoggingHandler;
import com.mp4splitmaven.Settings;

import java.util.ArrayList;
import java.util.Date;

public class TimeStampManager {


    private long startStamp;
    private long endStamp;
    private ArrayList<Long> cutStartTimeStamp = new ArrayList<>();
    private ArrayList<Long> cutStopTimeStamp = new ArrayList<>();
    private ArrayList<Long> timeStampToCut = new ArrayList<>();
    private ArrayList<Long> clipLengthToCut = new ArrayList<>();

    public long clipLength;

    public TimeStampManager(){
        this.clipLength = Settings.getInstance().getClipLength()*1000;
    }
    public String[] getCutStartTimeStamp() {
        return formatTime(cutStartTimeStamp.toArray(new Long[0]));
    }

    public String[] getCutStopTimeStamp() {
        return formatTime(cutStopTimeStamp.toArray(new Long[0]));
    }

    public String[] getclipLengthToCut() {
        return formatTime(clipLengthToCut.toArray(new Long[0]));
    }


    public void addTimeStampToCut() {
        timeStampToCut.add(new Date().getTime());
        LoggingHandler.println(LoggingHandler.DEBUG,"new Timestamp ["+new Date().getTime()+"]");
    }
    public void clipHasEnded(){
        endStamp = new Date().getTime();
    }

    public void prepareTimeStamp(int videoLength) {

        startStamp = endStamp - (videoLength*1000);

        LoggingHandler.println(LoggingHandler.DEBUG,"StartStamp [" +startStamp+"]");
        LoggingHandler.println(LoggingHandler.DEBUG,"EndStamp [" + endStamp+"]");


        for(long timetoCut : timeStampToCut.toArray(new Long[0])) {
            if(startStamp < timetoCut) {
                if (startStamp+clipLength < timetoCut) {
                    cutStartTimeStamp.add(timetoCut - clipLength - startStamp);
                    LoggingHandler.println(LoggingHandler.DEBUG,"Starting Time [" + (timetoCut - clipLength - startStamp)+"]");
                } else {
                    cutStartTimeStamp.add(startStamp-startStamp);
                    LoggingHandler.println(LoggingHandler.DEBUG,"Starting Time [" + (startStamp-startStamp)+"]");
                }

                if (timetoCut + clipLength < endStamp) {
                    cutStopTimeStamp.add(timetoCut- startStamp);
                    LoggingHandler.println(LoggingHandler.DEBUG,"Stopping Time [" + (timetoCut - startStamp)+"]");
                } else {
                    cutStopTimeStamp.add(endStamp - startStamp);
                    LoggingHandler.println(LoggingHandler.DEBUG,"Stoping Time [" + (endStamp - startStamp)+ "]");
                }


            }else {
                LoggingHandler.println(LoggingHandler.DEBUG,"Cliptimestamp is befor Startingpoint");
            }
        }

        for(int i = 0; i < cutStartTimeStamp.size(); i++) {
            clipLengthToCut.add(cutStopTimeStamp.get(i)- cutStartTimeStamp.get(i));
            LoggingHandler.println(LoggingHandler.DEBUG,"Clip number ["+i+"] length is [" + clipLengthToCut.get(i)+"]");
        }

    }

    public String[] formatTime(Long[] miliSecond) {

        String[] timeFormat = new String[miliSecond.length];

        int index = 0;
        //for(long value : miliSecond) {
        for(int i = 0; i < miliSecond.length; i++) {
            long secs = miliSecond[i] / 1000;

            timeFormat[i] = String.format("%02d:%02d:%02d", secs / 3600, (secs % 3600) / 60, secs % 60);
            LoggingHandler.println(LoggingHandler.DEBUG,"Timestamp ["+ miliSecond[i] +"] to Format 00:00:00 ["+ (timeFormat[i]) +"]");
        }

        return timeFormat;
    }
}
