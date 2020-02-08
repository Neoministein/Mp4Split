package com.mp4splitmaven.HelperClass;

import com.mp4splitmaven.LoggingHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    public static String[] readFile (String location) {
        try {
            FileReader fileReader = new FileReader(System.getProperty("user.dir")+"\\"+location);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            List<String> lines = new ArrayList<String>();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }

            bufferedReader.close();

            return lines.toArray(new String[lines.size()]);
        }catch (IOException ex) {
            LoggingHandler.println(LoggingHandler.ERROR,"There was a problem trying to read the file at ["+ location +"]");
            return null;
        }
    }

    public static String readLineToSting(String location,int line) {
        try {
            String l = readFile(location)[line];
            String value = l.substring(l.indexOf(":")+1);
            LoggingHandler.println(LoggingHandler.DEBUG,"Line ["+ line +"] contains ["+ value +"]");
            return value;
        }catch (Exception ex) {
            LoggingHandler.println(LoggingHandler.WARN,"There was a wrong value found on line ["+ line +"] in file ["+ location+"]");
            return "";
        }
    }

    public static int readLineToInt(String location,int line) {
        String l = readFile(location)[line];
        try {
            int value = Integer.parseInt(l.substring(l.indexOf(":")+1));
            LoggingHandler.println(LoggingHandler.DEBUG,"Line ["+ line +"] contains ["+ value+"]");
            return value;
        }catch (Exception ex) {
            LoggingHandler.println(LoggingHandler.WARN,"There was a wrong value found on line ["+ line +"] in file ["+ location+"]");
            return 0;
        }
    }

    public static boolean readLineToBoolan(String location,int line) {
        String l = readFile(location)[line];
        try {
            boolean value = Boolean.parseBoolean(l.replaceAll(" ","").substring((l.replaceAll(" ","").indexOf(":")+1)));
            LoggingHandler.println(LoggingHandler.DEBUG,"Line ["+ line +"] contains ["+ value +"]");
            return value;
        }catch (Exception ex) {
            LoggingHandler.println(LoggingHandler.WARN,"There was a wrong value found on line ["+ line +"] in file ["+ location+"]");
            return false;
        }
    }
}
