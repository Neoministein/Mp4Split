package com.mp4splitmaven;

import com.mp4splitmaven.logging.Multilogger;
import com.mp4splitmaven.logging.logger.ConsoleLogger;
import com.mp4splitmaven.logging.logger.ScreenLogger;
import com.mp4splitmaven.screen.ScreenManager;

public class Main {

    private Multilogger multilogger =  Multilogger.getInstance();
    private Settings settings = Settings.getInstance();
    private ScreenManager screenManager = ScreenManager.getInstace();

    public static void main(String args[]) {
        Main main = new Main();
        if(args.length != 0) {
            System.out.println(args[0]);
            switch (args[0]) {
                case "0":
                    main.runWhilePlaying();
                    break;
                case "1":
                    main.runFinalCut();
                    break;
                case "2":
                    main.startScreen();
                    main.runWhilePlaying();
                    break;
                case "3":
                    main.startScreen();
                    main.runFinalCut();
                    break;
            }
        } else {
            main.startScreen();
            main.runWhilePlaying();
        }
    }

    public void runWhilePlaying() {
        multilogger.addLogger(new ConsoleLogger(settings.getDebuglevel()));

        WhilePlaying whilePlaying = new WhilePlaying();
        whilePlaying.startKeyLogger();
    }
    public void runFinalCut() {
        multilogger.addLogger(new ConsoleLogger(settings.getDebuglevel()));
        FinalCut finalCut = new FinalCut();

        finalCut.finalCut();
    }
    public void startScreen(){
        screenManager.setScreenVisible(true);
        multilogger.addLogger(new ScreenLogger( settings.getDebuglevel() ,screenManager));
    }
}
