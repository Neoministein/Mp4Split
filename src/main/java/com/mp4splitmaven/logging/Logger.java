package com.mp4splitmaven.logging;

public interface Logger {

    void print(String text);

    int getLoglevel();

    boolean isIOLogger();

    String getLoglocation();
}
