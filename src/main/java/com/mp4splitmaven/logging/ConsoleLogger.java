package com.mp4splitmaven.logging;

public class ConsoleLogger implements Logger {

    int loglevel;

    public ConsoleLogger(int loglevel){
        this.loglevel = loglevel;
    }

    @Override
    public void print(String text) {
        System.out.print(text);
    }

    @Override
    public int getLoglevel() {
        return 0;
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
