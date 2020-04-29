package com.mp4splitmaven.logging.logger;

public interface Logger {

    void print(String text);

    int getLoglevel();

    boolean isIOLogger();

    String getLoglocation();
}
