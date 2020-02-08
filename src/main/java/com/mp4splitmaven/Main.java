package com.mp4splitmaven;

public class Main {

    LoggingHandler loggingHandler =  LoggingHandler.getInstance();
    Settings settings = Settings.getInstance();

    public static void main(String args[]) {
        if(args.length != 0) {
            Main main = new Main();
            System.out.println(args[0]);
            switch (args[0]) {
                case "0":
                    main.runWhilePlaying();
                    break;
                case "1":
                    main.runFinalCut();
                    break;
                default:
                    main.runWhilePlaying();
                    break;
            }
        }
    }

    public void runWhilePlaying() {

        loggingHandler.setDebugLevel();


        WhilePlaying whilePlaying = new WhilePlaying();
        whilePlaying.startKeyLogger();

    }
    public void runFinalCut() {
        FinalCut finalCut = new FinalCut();

        finalCut.finalCut();
    }
}
