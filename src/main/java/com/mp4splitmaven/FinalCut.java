package com.mp4splitmaven;


import com.mp4splitmaven.helperclass.FfmpegManager;
import com.mp4splitmaven.helperclass.TimeStampManager;
import com.mp4splitmaven.logging.Logging;
import com.mp4splitmaven.logging.Multilogger;

import java.io.File;
import java.util.ArrayList;

public class FinalCut {

    private Logging loggingHandler = Multilogger.getInstance();
    private FfmpegManager ffmpegManager = new FfmpegManager();
    private Settings settings = Settings.getInstance();

    public FinalCut() {}

    public void finalCut() {
        {
            try {

                String[] filesToCut = getNameToCut();
                String ffmpegLocation = Settings.FFMPEG_LOCATION;

                for(String files: filesToCut) {
                    String fileLocation = findFile(getOriginalFileName(files),new File (settings.getInputLocationt()));
                    String startTime = getStartTimeFromName(files);
                    Long clipLength = settings.getClipLength()*1000l;
                    String videoLength = new TimeStampManager().formatTime(clipLength);

                    ffmpegManager.cutVideoExact(ffmpegLocation,fileLocation,startTime,videoLength);
                }

            }catch (Exception e){
                loggingHandler.println(Multilogger.FATAL,"There was a problem trying to cut the video exact", e);
            }
        }
    }

    public String[] getNameToCut(){
        ArrayList<String> names = new ArrayList<String >();
        try {
            File folder = new File(Settings.FINALCUT_LOCATION);
            File[] listOfFiles = folder.listFiles();

            for(File file: listOfFiles){
                names.add(file.getName());
            }
        }catch (Exception e) {
            loggingHandler.println(Multilogger.WARN, "There was a problem trying to find the Files", e);
        }
        return names.toArray(new String[0]);
    }

    public String findFile(String name, File file) throws Exception {
        try {
            File[] list = file.listFiles();
            if (list != null) {
                for (File fil : list) {
                    if (fil.isDirectory()) {
                        String subFolder;
                        if(null != (subFolder = findSubFile(name, fil))){
                            return subFolder;
                        }
                    } else if (name.equalsIgnoreCase(fil.getName())) {

                        return fil.getAbsolutePath();
                    }
                }
            }
            throw new Exception();
        }catch (Exception e){
            loggingHandler.println(Multilogger.FATAL, "Can't find original File", e);
            throw new Exception();
        }
    }

    public String findSubFile(String name, File file) {
        try {
            File[] list = file.listFiles();
            if (list != null) {
                for (File fil : list) {
                    if (fil.isDirectory()) {
                        String subFolder;
                        if(null != (subFolder = findSubFile(name, fil))){
                            return subFolder;
                        }
                    } else if (name.equalsIgnoreCase(fil.getName())) {

                        return fil.getAbsolutePath();
                    }
                }
            }
        }catch (Exception e){
            loggingHandler.println(Multilogger.FATAL, "Can't find original File in Subfolder", e);
        }
        return null;
    }

    public String getStartTimeFromName(String fileName){
        return  (fileName.substring(fileName.length()-12,fileName.length()-4)).replace(".",":");
    }

    public String getOriginalFileName(String filename){
        String name = filename.substring(0,filename.indexOf("_clip_"))+".mp4";
        System.out.println(name);
        return name;
    }
}
