package com.mp4splitmaven;

import com.mp4splitmaven.HelperClass.FileManager;

public class Settings {

    private static final String SETTINGS_LOCATION = "settings.txt";
    public static final String FFMPEG_LOCATION = System.getProperty("user.dir")+"\\ffmpeg.exe";
    public static final String FFPROBE_LOCATION = System.getProperty("user.dir")+"\\ffprobe.exe";

    public static final String LOG_LOCATION = System.getProperty("user.dir")+"\\logs";
    public static final String FINALCUT_LOCATION = System.getProperty("user.dir")+"\\finalCut";

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
        debuglevel = FileManager.readLineToInt(SETTINGS_LOCATION,3);
        clipLength = FileManager.readLineToInt(SETTINGS_LOCATION,4);
        secondsUntilCut = FileManager.readLineToInt(SETTINGS_LOCATION,5);
        trysToCut = FileManager.readLineToInt(SETTINGS_LOCATION,6);
        inputLocation = FileManager.readLineToSting(SETTINGS_LOCATION,7);
        clipPressOne = FileManager.readLineToInt(SETTINGS_LOCATION,8);
        clipPressTwo = FileManager.readLineToInt(SETTINGS_LOCATION,9);
        stopPressOne = FileManager.readLineToInt(SETTINGS_LOCATION,10);
        stopPressTwo = FileManager.readLineToInt(SETTINGS_LOCATION,11);
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
