package com.mp4splitmaven.helperclass;

import com.mp4splitmaven.LoggingHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

class StreamGobbler implements Runnable {
    private final InputStream is;
    private final PrintStream os;

    StreamGobbler(InputStream is, PrintStream os) {
        this.is = is;
        this.os = os;
    }

    public void run() {
        LoggingHandler.println(LoggingHandler.DEBUG,"Console output:");
        try {
            int c;
            while ((c = is.read()) != -1)
                LoggingHandler.printNoIO(LoggingHandler.DEBUG, (char) c + "");
        } catch (IOException e) {
            LoggingHandler.println(LoggingHandler.WARN,"There was a problem trying to manage the OutputStream", e);
        }
    }

}
