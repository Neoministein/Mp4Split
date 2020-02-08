package com.mp4splitmaven.HelperClass;

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
        try {
            int c;
            while ((c = is.read()) != -1)
                os.print((char) c);
        } catch (IOException e) {
            LoggingHandler.println(LoggingHandler.WARN,"There was a problem trying to manage the OutputStream", e);
        }
    }

}
