package com.mp4splitmaven.HelperClass;

import com.mp4splitmaven.LoggingHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;

public class InputFileManager {

    public static String lastFileModified(String dir) throws Exception {

        LoggingHandler.println(LoggingHandler.INFO, "Looking for newest File directory [" + dir + "]");
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
                LoggingHandler.println(LoggingHandler.DEBUG, "Found directory/file [" + mostRecent.getAbsolutePath() + "]");
            } while (mostRecent.isDirectory());
            LoggingHandler.println(LoggingHandler.INFO, "Newest file [" + mostRecent.getAbsolutePath() + "]");

            if (nameContainDVR(mostRecent.getName())) {
                LoggingHandler.println(LoggingHandler.DEBUG, "Deleting DVR From Filename");

                if(renameFile(mostRecent.getAbsolutePath(), removeDVR(mostRecent.getName()))){
                    return (mostRecent.getAbsolutePath().substring(0, mostRecent.getAbsolutePath().lastIndexOf("\\")) + "\\" + removeDVR(mostRecent.getName()));
                }
            }
        }catch (Exception e) {
            LoggingHandler.println(LoggingHandler.FATAL,"There was a Problem trying to find the newest file: ", e);
            throw new Exception();
        }


        return mostRecent.getAbsolutePath();
    }

    private static boolean renameFile(String fileLocation, String newFileName) {
        Path source = Paths.get(fileLocation);
        try {
            Files.move(source,source.resolveSibling(newFileName));
            return true;
        } catch (IOException e) {
            LoggingHandler.println(LoggingHandler.ERROR,"There was a problem trying to rename file ["
                    +fileLocation +"] to ["+ newFileName+ "]: "+ e);
            return false;
        }
    }
    private static boolean nameContainDVR(String name) {
        return name.contains(".DVR");
    }
    private static String removeDVR(String name) {
        return name.replace(".DVR","");
    }

}
