package com.mp4splitmaven.HelperClass;

import com.mp4splitmaven.LoggingHandler;

import java.io.IOException;

public class FfmpegManager {


    public void cutVideo(String ffmpegLocation, String inputFileLocation,String[] startTime, String[] videoLength) {
        LoggingHandler.println(LoggingHandler.INFO,"Starting to cut Video");

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
            LoggingHandler.println(LoggingHandler.DEBUG,"FFmpeg Comand: [" +cmdComand+ "]");

            execCmd(cmdComand);
        }
    }
    public void cutVideoExact(String ffmpegLocation, String inputFileLocation,String[] startTime, String[] videoLength) {
        LoggingHandler.println(LoggingHandler.INFO,"Starting to cut Video");

        String saveFileLocation = inputFileLocation.substring(0,inputFileLocation.lastIndexOf("."))+"_clip_";

        for(int i = 0; i < startTime.length; i++) {
            String cmdComand = ("\"" + ffmpegLocation + "\"" +
                    " -i " +
                    "\"" + inputFileLocation + "\"" +
                    " -ss " +
                    startTime[i] +
                    " -t " +
                    videoLength[i] +
                    " -async 1 " +
                    "\"" + saveFileLocation +
                    startTime[i].replace(":",".")
                    +".mp4" +"\"");
            LoggingHandler.println(LoggingHandler.DEBUG,"FFmpeg Comand: [" +cmdComand+ "]");

            execCmd(cmdComand);
        }
    }

    public int getVideoLength(String ffprobLocation, String videoLocation) throws Exception {
        LoggingHandler.println(LoggingHandler.INFO,"Determining Video Length");

        int videoLength = 0;
        String strVideoLength = "";
        String cmdComand =  "\"" + ffprobLocation +"\"" +
                " -v error -show_entries format=duration " +
                "\"" + videoLocation + "\"" +
                " -of default=noprint_wrappers=1:nokey=1";
        LoggingHandler.println(LoggingHandler.DEBUG,"Ffprobe command: ["+cmdComand+"]");


        try {
            strVideoLength = execCmdwithOutput(cmdComand);
            videoLength = Integer.parseInt(strVideoLength.substring(0,strVideoLength.indexOf(".")));
        } catch (IOException e) {
            LoggingHandler.println(LoggingHandler.ERROR,"There was a problem while getting the length from ffprobe", e);
            throw new Exception();
        }

        LoggingHandler.println(LoggingHandler.DEBUG,"Video Length ["+ videoLength + "]");
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
            LoggingHandler.println(LoggingHandler.WARN,"There was a problem trying to run an Command",e);
        } catch (IOException e) {
            LoggingHandler.println(LoggingHandler.WARN,"There was a problem trying to run an Command",e);
        }


    }
}
