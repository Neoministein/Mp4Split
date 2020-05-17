package com.mp4splitmaven.helperclass;

import com.neoutil.logging.Multilogger;
import com.neoutil.logging.Logging;

import java.io.IOException;

public class FfmpegManager {

    private Logging loggingHandler = Multilogger.getInstance();

    public void cutVideo(String ffmpegLocation, String inputFileLocation,String[] startTime, String[] videoLength) {
        loggingHandler.println(Multilogger.INFO,"Starting to cut Video");

        String saveFileLocation = inputFileLocation.substring(0,inputFileLocation.lastIndexOf("."))+"_clip_";

        for(int i = 0; i < startTime.length; i++) {
                String cmdComand = ("\"" + ffmpegLocation + "\"" +
                    " -i " +
                    "\"" + inputFileLocation + "\"" +
                    " -ss " +
                    startTime[i] +
                    " -t " +
                    videoLength[i] +
                    " -vcodec copy -acodec copy " +
                    "\"" + saveFileLocation +
                    startTime[i].replace(":",".")
                    +".mp4" +"\"");
            loggingHandler.println(Multilogger.DEBUG,"FFmpeg Comand: [" +cmdComand+ "]");

            execCmd(cmdComand);
        }
    }
    public void cutVideoExact(String ffmpegLocation, String inputFileLocation,String startTime, String videoLength) {
        loggingHandler.println(Multilogger.INFO,"Starting to cut Video");

        String saveFileLocation = inputFileLocation.substring(0,inputFileLocation.lastIndexOf("."))+"_clip_";

        String cmdComand = ("\"" + ffmpegLocation + "\"" +
                " -i " +
                "\"" + inputFileLocation + "\"" +
                " -ss " +
                startTime +
                " -t " +
                videoLength +
                " -async 1 " +
                "\"" + saveFileLocation +
                startTime.replace(":",".")
                +".mp4" +"\"");
        loggingHandler.println(Multilogger.DEBUG,"FFmpeg Comand: [" +cmdComand+ "]");

        execCmd(cmdComand);
    }

    public int getVideoLength(String ffprobLocation, String videoLocation) throws Exception {
        loggingHandler.println(Multilogger.INFO,"Determining Video Length");

        int videoLength = 0;
        String strVideoLength = "";
        String cmdComand =  "\"" + ffprobLocation +"\"" +
                " -v error -show_entries format=duration " +
                "\"" + videoLocation + "\"" +
                " -of default=noprint_wrappers=1:nokey=1";
        loggingHandler.println(Multilogger.DEBUG,"Ffprobe command: ["+cmdComand+"]");


        try {
            strVideoLength = execCmdwithOutput(cmdComand);
            videoLength = Integer.parseInt(strVideoLength.substring(0,strVideoLength.indexOf(".")));
        } catch (IOException e) {
            loggingHandler.println(Multilogger.ERROR,"There was a problem while getting the length from ffprobe", e);
            throw new Exception();
        }

        loggingHandler.println(Multilogger.DEBUG,"Video Length ["+ videoLength + "]");
        return videoLength;

    }
    public String execCmdwithOutput(String cmd) throws IOException {
        java.util.Scanner s = new java.util.Scanner(Runtime.getRuntime().exec(cmd).getInputStream()).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    private void execCmd(String cmdComand){
        try {
        Process proc = Runtime.getRuntime().exec(cmdComand);

        // Any error message?
        Thread errorGobbler
                = new Thread(new StreamGobbler(proc.getErrorStream(), System.err));

        // Any output?
        Thread outputGobbler
                = new Thread(new StreamGobbler(proc.getInputStream(), System.out));

        errorGobbler.start();
        outputGobbler.start();

        // Any error?

            int exitVal = proc.waitFor();

            errorGobbler.join();   // Handle condition where the
            outputGobbler.join();  // process ends before the threads finish
        } catch (InterruptedException e) {
            loggingHandler.println(Multilogger.WARN,"There was a problem trying to run an Command",e);
        } catch (IOException e) {
            loggingHandler.println(Multilogger.WARN,"There was a problem trying to run an Command",e);
        }


    }
}
