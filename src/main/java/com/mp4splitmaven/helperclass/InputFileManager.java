package com.mp4splitmaven.helperclass;

import com.mp4splitmaven.logging.Multilogger;
import com.mp4splitmaven.logging.Logging;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;

public class InputFileManager {

    private Logging loggingHandler = Multilogger.getInstance();

    public String lastFileModified(String dir) throws Exception {

        loggingHandler.println(Multilogger.INFO, "Looking for newest File directory [" + dir + "]");
        File mostRecent = new File(dir);
        try {
            do {
                Path parentFolder = Paths.get(mostRecent.getPath());

                Optional<File> mostRecentFileOrFolder =
                        Arrays
                                .stream(parentFolder.toFile().listFiles())
                                .max(
                                        (f1, f2) -> Long.compare(f1.lastModified(),
                                                f2.lastModified()));

                if (mostRecentFileOrFolder.isPresent()) {
                    mostRecent = mostRecentFileOrFolder.get();
                }
                loggingHandler.println(Multilogger.DEBUG, "Found directory/file [" + mostRecent.getAbsolutePath() + "]");
            } while (mostRecent.isDirectory());
            loggingHandler.println(Multilogger.INFO, "Newest file [" + mostRecent.getAbsolutePath() + "]");

            if (nameContainDVR(mostRecent.getName())) {
                loggingHandler.println(Multilogger.DEBUG, "Deleting DVR From Filename");

                if(renameFile(mostRecent.getAbsolutePath(), removeDVR(mostRecent.getName()))){
                    return (mostRecent.getAbsolutePath().substring(0, mostRecent.getAbsolutePath().lastIndexOf("\\")) + "\\" + removeDVR(mostRecent.getName()));
                }
            }
        }catch (Exception e) {
            loggingHandler.println(Multilogger.FATAL,"There was a Problem trying to find the newest file: ", e);
            throw new Exception();
        }


        return mostRecent.getAbsolutePath();
    }

    private boolean renameFile(String fileLocation, String newFileName) {
        Path source = Paths.get(fileLocation);
        try {
            Files.move(source,source.resolveSibling(newFileName));
            return true;
        } catch (IOException e) {
            loggingHandler.println(Multilogger.ERROR,"There was a problem trying to rename file ["
                    +fileLocation +"] to ["+ newFileName+ "]: "+ e);
            return false;
        }
    }
    private boolean nameContainDVR(String name) {
        return name.contains(".DVR");
    }
    private  String removeDVR(String name) {
        return name.replace(".DVR","");
    }

}
