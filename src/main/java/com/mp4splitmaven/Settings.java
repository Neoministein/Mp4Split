package com.mp4splitmaven;

import com.mp4splitmaven.HelperClass.FileManager;

public class Settings {

    private String settingsLocation = "settings.txt";
    private String ffmpegLocation = System.getProperty("user.dir")+"\\ffmpeg.exe";
    private String ffprobeLocation = System.getProperty("user.dir")+"\\ffprobe.exe";

    private int debuglevel;

    private int clipLength;
    private int secondsUntilCut;
    private int trysToCut;
    private String inputLocation;

    private int clipPressOne;
    private int clipPressTwo;

    private int stopPressOne;
    private int stopPressTwo;

    private static Settings instance = new Settings();
    public static Settings getInstance() {
        return  instance;
    }

    private Settings() {
        loadSettings();
    }


    public void loadSettings() {
        System.out.println("Load settings");
        debuglevel = FileManager.readLineToInt(settingsLocation,3);
        clipLength = FileManager.readLineToInt(settingsLocation,4);
        secondsUntilCut = FileManager.readLineToInt(settingsLocation,5);
        trysToCut = FileManager.readLineToInt(settingsLocation,6);
        inputLocation = FileManager.readLineToSting(settingsLocation,7);
        clipPressOne = FileManager.readLineToInt(settingsLocation,8);
        clipPressTwo = FileManager.readLineToInt(settingsLocation,9);
        stopPressOne = FileManager.readLineToInt(settingsLocation,10);
        stopPressTwo = FileManager.readLineToInt(settingsLocation,11);
    }

    public int getDebuglevel() {
        return debuglevel;
    }
    public int getClipLength() {
        return clipLength;
    }
    public int getSecondsUntilCut() {
        return secondsUntilCut;
    }
    public int getTrysToCut() {
        return trysToCut;
    }
    public String getFfmpegLocation() {
        return ffmpegLocation;
    }
    public String getFfprobeLocation() {return  ffprobeLocation;}
    public String getInputLocationt() {
        return inputLocation;
    }
    public int getClipPressOne() {
        return clipPressOne;
    }
    public int getClipPressTwo() {
        return clipPressTwo;
    }
    public int getStopPressOne() {
        return stopPressOne;
    }
    public int getStopPressTwo(){
        return stopPressTwo;
    }

}
