package com.mp4splitmaven;

import com.mp4splitmaven.logging.ConsoleLogger;
import com.mp4splitmaven.logging.LogfileLogger;
import com.mp4splitmaven.logging.ScreenLogger;
import com.mp4splitmaven.screen.ScreenManager;

public class Main {

    LoggingHandler loggingHandler =  LoggingHandler.getInstance();
    Settings settings = Settings.getInstance();

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
                default:
                    main.runWhilePlaying();
                    break;
            }
        }
        main.startScreen();
        main.runWhilePlaying();
    }

    public void runWhilePlaying() {
        LoggingHandler.addLogger(new ConsoleLogger(settings.getDebuglevel()));

        WhilePlaying whilePlaying = new WhilePlaying();
        whilePlaying.startKeyLogger();
    }
    public void runFinalCut() {
        FinalCut finalCut = new FinalCut();

        finalCut.finalCut();
    }
    public void startScreen(){
        LoggingHandler.addLogger(new ScreenLogger( settings.getDebuglevel() ,ScreenManager.getInstace()));
    }
}
