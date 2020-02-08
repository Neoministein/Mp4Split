package com.mp4splitmaven;

import com.mp4splitmaven.HelperClass.FfmpegManager;
import com.mp4splitmaven.HelperClass.InputFileManager;
import com.mp4splitmaven.HelperClass.TimeStampManager;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.logging.Level;
import java.util.logging.Logger;

public class WhilePlaying implements NativeKeyListener {
    int lastKeyCode = 0;
    int clipPressOne = NativeKeyEvent.VC_ALT;
    int clipPressTwo = NativeKeyEvent.VC_0;

    int stopPressOne = NativeKeyEvent.VC_ALT;
    int stopPressTwo = NativeKeyEvent.VC_9;

    TimeStampManager timeStampManager = null;
    Settings settings = Settings.getInstance();

    public WhilePlaying() {

        this.clipPressOne = settings.getClipPressOne();
        this.clipPressTwo = settings.getClipPressTwo();

        this.stopPressOne = settings.getStopPressOne();
        this.stopPressTwo = settings.getStopPressTwo();

    }

    public void startKeyLogger() {

        turnOffLog();

        try {
            GlobalScreen.registerNativeHook();
            LoggingHandler.println(LoggingHandler.INFO,"Activated  Keylogger");
        }
        catch (NativeHookException ex) {
            LoggingHandler.println(LoggingHandler.FATAL,"There was a problem starting the Keylogger. \n", ex);


            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new WhilePlaying());
    }
    public void stopKeyLoger() {
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException ex) {
            LoggingHandler.println(LoggingHandler.FATAL,"There was a problem stopping the Keylogger. \n", ex);
        }
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        LoggingHandler.printNoLog(LoggingHandler.KEYPRESS,"Key Pressed [" + NativeKeyEvent.getKeyText(e.getKeyCode())+"]\n");

        if(checkForDoubleKeyCode(lastKeyCode,e.getKeyCode(),clipPressOne,clipPressTwo)) {
            doTimeStamp();
        }

        if(checkForDoubleKeyCode(lastKeyCode,e.getKeyCode(),stopPressOne,stopPressTwo)) {
            cutVideo();
        }

        lastKeyCode = e.getKeyCode();
    }


    public void nativeKeyReleased(NativeKeyEvent e) {}
    public void nativeKeyTyped(NativeKeyEvent e) {}

    private void cutVideo() {
        LoggingHandler.println(LoggingHandler.INFO,"Video stop macro");
        if (timeStampManager != null) {
            try {
                LoggingHandler.println(LoggingHandler.DEBUG, "video needs to be cut");
                timeStampManager.clipHasEnded();


                for(int i = 0; i < settings.getTrysToCut(); i++) {
                    try {
                        waitFor(settings.getSecondsUntilCut());
                        String inputFileLocation = InputFileManager.lastFileModified(settings.getInputLocationt());

                        FfmpegManager f = new FfmpegManager();
                        timeStampManager.prepareTimeStamp(f.getVideoLength(settings.getFfprobeLocation(), inputFileLocation));
                        f.cutVideo(settings.getFfmpegLocation(), inputFileLocation, timeStampManager.getCutStartTimeStamp(), timeStampManager.getclipLengthToCut());
                        timeStampManager = null;
                        break;
                    }catch (Exception e){
                        LoggingHandler.println(LoggingHandler.WARN, "Video could not be cut: ", e);
                        LoggingHandler.println(LoggingHandler.DEBUG, "Trying again "+i+"/"+settings.getTrysToCut());
                    }
                }
                throw new Exception();
            }catch (Exception e) {
                LoggingHandler.println(LoggingHandler.FATAL, "Video could not be cut: ",e);

                timeStampManager = null;
            }
        }else{
            LoggingHandler.println(LoggingHandler.DEBUG, "Video doesn't need to be cut");
        }
    }

    private void doTimeStamp() {
        if (timeStampManager == null) {
            timeStampManager = new TimeStampManager();
        }
        timeStampManager.addTimeStampToCut();
        LoggingHandler.println(LoggingHandler.INFO,"Timestamp has been set");
    }

    private void turnOffLog() {
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        logger.setUseParentHandlers(false);
        LoggingHandler.println(LoggingHandler.DEBUG,"Turned off Keylog log");
    }


    private boolean checkForDoubleKeyCode(int lastKeyCode,int currentKeyCode,int wantedLastKeyCode, int wantedCurrentKeyCode) {
            return (lastKeyCode == wantedLastKeyCode && currentKeyCode == wantedCurrentKeyCode);
    }

    private void waitFor(int seconds) throws Exception {
        LoggingHandler.println(LoggingHandler.DEBUG,"Waiting for [" + seconds + "] seconds");
        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {
            LoggingHandler.println(LoggingHandler.ERROR,"The was a problem while trying to wait: ", e);
            throw new Exception();
        }
    }
}
