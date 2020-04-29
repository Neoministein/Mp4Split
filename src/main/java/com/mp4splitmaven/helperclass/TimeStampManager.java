package com.mp4splitmaven.helperclass;

import com.mp4splitmaven.logging.Multilogger;
import com.mp4splitmaven.Settings;
import com.mp4splitmaven.logging.Logging;

import java.util.ArrayList;
import java.util.Date;

public class TimeStampManager {

    private Logging loggingHandler = Multilogger.getInstance();

    private long startStamp;
    private long endStamp;
    private ArrayList<Long> cutStartTimeStamp = new ArrayList<>();
    private ArrayList<Long> cutStopTimeStamp = new ArrayList<>();
    private ArrayList<Long> timeStampToCut = new ArrayList<>();
    private ArrayList<Long> clipLengthToCut = new ArrayList<>();

    private long clipLength;

    public TimeStampManager(){
        this.clipLength = Settings.getInstance().getClipLength()*1000;
    }

    public void addNewTimeStamp() {
        timeStampToCut.add(new Date().getTime());
        loggingHandler.println(Multilogger.DEBUG,"new Timestamp ["+new Date().getTime()+"]");
    }

    public void addNewTimeStampMock(Long timeStamp) {
        timeStampToCut.add(timeStamp);
        loggingHandler.println(Multilogger.DEBUG,"new Timestamp ["+timeStamp+"]");
    }

    public void clipHasEnded(){
        endStamp = new Date().getTime();
    }

    public void prepareTimeStamp(int videoLengthInSeconds) {

        startStamp = endStamp - (videoLengthInSeconds*1000);

        loggingHandler.println(Multilogger.DEBUG,"StartStamp [" +startStamp+"]");
        loggingHandler.println(Multilogger.DEBUG,"EndStamp [" + endStamp+"]");


        for(long timetoCut : timeStampToCut.toArray(new Long[0])) {
            if(startStamp < timetoCut) {
                if (startStamp+clipLength < timetoCut) {
                    cutStartTimeStamp.add(timetoCut - clipLength - startStamp);
                    loggingHandler.println(Multilogger.DEBUG,"Starting Time [" + (timetoCut - clipLength - startStamp)+"]");
                } else {
                    cutStartTimeStamp.add(startStamp-startStamp);
                    loggingHandler.println(Multilogger.DEBUG,"Starting Time [" + (startStamp-startStamp)+"]");
                }

                cutStopTimeStamp.add(timetoCut- startStamp);
                loggingHandler.println(Multilogger.DEBUG,"Stopping Time [" + (timetoCut - startStamp)+"]");


            }else {
                loggingHandler.println(Multilogger.DEBUG,"Cliptimestamp is befor Startingpoint");
            }
        }

        for(int i = 0; i < cutStartTimeStamp.size(); i++) {
            clipLengthToCut.add(cutStopTimeStamp.get(i) - cutStartTimeStamp.get(i));
            loggingHandler.println(Multilogger.DEBUG,"Clip number ["+i+"] length is [" + clipLengthToCut.get(i)+"]");
        }
    }

    public String formatTime(Long miliSeconds){
        if(0 <= miliSeconds) {
            long secs = miliSeconds / 1000;

            String newFormat = String.format("%02d:%02d:%02d", secs / 3600, (secs % 3600) / 60, secs % 60);
            loggingHandler.println(Multilogger.DEBUG,"Timestamp ["+ miliSeconds +"] to Format 00:00:00 ["+ (newFormat) +"]");
            return newFormat;
        }
        loggingHandler.println(Multilogger.WARN,"FormatTime encountered a negative value");
        return "00:00:00";
    }

    public String[] formatTime(Long[] miliSecond) {
        String[] timeFormat = new String[miliSecond.length];

        for(int i = 0; i < miliSecond.length; i++) {
            timeFormat[i] = formatTime(miliSecond[i]);
        }

        return timeFormat;
    }
    public int getAmountOfStamps(){
        return timeStampToCut.size();
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
}
