package com.mp4splitmaven.logging.logger;

public class ConsoleLogger implements Logger {

    private final int loglevel;

    public ConsoleLogger(int loglevel){
        this.loglevel = loglevel;
    }

    @Override
    public void print(String text) {
        System.out.print(text);
    }

    @Override
    public int getLoglevel() {
        return loglevel;
    }

    @Override
    public boolean isIOLogger() {
        return false;
    }

    @Override
    public String getLoglocation() {
        return "Console";
    }
}
