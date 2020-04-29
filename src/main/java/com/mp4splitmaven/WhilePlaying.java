package com.mp4splitmaven;

import com.mp4splitmaven.helperclass.FfmpegManager;
import com.mp4splitmaven.helperclass.InputFileManager;
import com.mp4splitmaven.helperclass.TimeStampManager;
import com.mp4splitmaven.logging.Logging;
import com.mp4splitmaven.logging.Multilogger;
import com.mp4splitmaven.screen.ScreenManager;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.logging.Level;
import java.util.logging.Logger;

public class WhilePlaying implements NativeKeyListener {
    private int lastKeyCode = 0;
    private int clipPressOne = NativeKeyEvent.VC_ALT;
    private int clipPressTwo = NativeKeyEvent.VC_0;

    private int stopPressOne = NativeKeyEvent.VC_ALT;
    private int stopPressTwo = NativeKeyEvent.VC_9;

    private ScreenManager screenManager = ScreenManager.getInstace();
    private Logging loggingHandler = Multilogger.getInstance();

    private TimeStampManager timeStampManager = null;
    private Settings settings = Settings.getInstance();

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
            loggingHandler.println(Multilogger.INFO,"Activated Macro detection");
        }
        catch (NativeHookException ex) {
            loggingHandler.println(Multilogger.FATAL,"There was a problem starting the Keylogger. \n", ex);


            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new WhilePlaying());
    }
    public void stopKeyLogger() {
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException ex) {
            loggingHandler.println(Multilogger.FATAL,"There was a problem stopping the Keylogger. \n", ex);
        }
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        loggingHandler.printlnNoIO(Multilogger.KEYPRESS,"Key Pressed [" + NativeKeyEvent.getKeyText(e.getKeyCode())+"]\n");

        if(checkForDoubleKeyCode(lastKeyCode,e.getKeyCode(),clipPressOne,clipPressTwo)) {
            createNewTimeStamp();
        }

        if(checkForDoubleKeyCode(lastKeyCode,e.getKeyCode(),stopPressOne,stopPressTwo)) {
            cutVideo();
        }

        lastKeyCode = e.getKeyCode();
    }


    public void nativeKeyReleased(NativeKeyEvent e) {}
    public void nativeKeyTyped(NativeKeyEvent e) {}

    private void cutVideo() {
        loggingHandler.println(Multilogger.INFO,"Video stop macro");
        if (timeStampManager != null) {
            try {
                loggingHandler.println(Multilogger.DEBUG, "video needs to be cut");
                screenManager.isCurrentlyCutting(true);

                timeStampManager.clipHasEnded();

                if (hasCutWithoutError()) {
                    loggingHandler.println(Multilogger.INFO,"Video was cut successfully");
                }else {
                    throw new Exception();
                }

            }catch (Exception e) {
                loggingHandler.println(Multilogger.FATAL, "Video could not be cut: ",e);
                timeStampManager = null;
            }
        }else{
            loggingHandler.println(Multilogger.DEBUG, "Video doesn't need to be cut");
        }
        screenManager.isCurrentlyCutting(false);
        loggingHandler.printlnNoIO(Multilogger.INFO,"----------");
    }

    private boolean hasCutWithoutError(){
        for(int i = 0; i < settings.getTrysToCut(); i++) {
            try {
                waitFor(settings.getSecondsUntilCut());
                String inputFileLocation = new InputFileManager().lastFileModified(settings.getInputLocationt());

                FfmpegManager f = new FfmpegManager();
                timeStampManager.prepareTimeStamp(f.getVideoLength(Settings.FFPROBE_LOCATION, inputFileLocation));
                f.cutVideo(Settings.FFMPEG_LOCATION, inputFileLocation, timeStampManager.getCutStartTimeStamp(), timeStampManager.getclipLengthToCut());
                timeStampManager = null;
                return true;
            }catch (Exception e){
                loggingHandler.println(Multilogger.WARN, "Video could not be cut: ", e);
                loggingHandler.println(Multilogger.DEBUG, "Trying again "+i+"/"+settings.getTrysToCut());
            }
        }
        return false;
    }

    private void createNewTimeStamp() {
        if (timeStampManager == null) {
            timeStampManager = new TimeStampManager();
        }
        timeStampManager.addNewTimeStamp();
        screenManager.displayNumberOfTimestamp(timeStampManager.getAmountOfStamps());
        loggingHandler.println(Multilogger.INFO,"Timestamp has been set");
    }

    private void turnOffLog() {
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        logger.setUseParentHandlers(false);
        loggingHandler.println(Multilogger.DEBUG,"Turned off Keylog log");
    }


    private boolean checkForDoubleKeyCode(int lastKeyCode,int currentKeyCode,int wantedLastKeyCode, int wantedCurrentKeyCode) {
            return (lastKeyCode == wantedLastKeyCode && currentKeyCode == wantedCurrentKeyCode);
    }

    private void waitFor(int seconds) throws Exception {
        loggingHandler.println(Multilogger.DEBUG,"Waiting for [" + seconds + "] seconds");
        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {
            loggingHandler.println(Multilogger.ERROR,"The was a problem while trying to wait: ", e);
            throw new Exception();
        }
    }
}
