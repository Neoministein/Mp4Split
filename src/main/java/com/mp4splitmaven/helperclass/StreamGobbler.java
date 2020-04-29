package com.mp4splitmaven.helperclass;

import com.mp4splitmaven.logging.Multilogger;
import com.mp4splitmaven.logging.Logging;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

class StreamGobbler implements Runnable {
    private Logging loggingHandler = Multilogger.getInstance();

    private final InputStream is;
    private final PrintStream os;

    StreamGobbler(InputStream is, PrintStream os) {
        this.is = is;
        this.os = os;
    }

    public void run() {
        loggingHandler.println(Multilogger.DEBUG,"Console output:");
        try {
            int c;
            while ((c = is.read()) != -1)
                loggingHandler.printNoIO(Multilogger.DEBUG, (char) c + "");
        } catch (IOException e) {
            loggingHandler.println(Multilogger.WARN,"There was a problem trying to manage the OutputStream", e);
        }
    }

}
