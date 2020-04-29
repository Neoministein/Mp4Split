package com.mp4splitmaven.helperclass;

import com.mp4splitmaven.logging.Multilogger;
import com.mp4splitmaven.logging.Logging;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private static Logging loggingHandler = Multilogger.getInstance();

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
            loggingHandler.println(Multilogger.ERROR,"There was a problem trying to read the file at ["+ location +"]");
            return null;
        }
    }

    public static String readLineToSting(String location,int line) {
        try {
            String l = readFile(location)[line];
            String value = l.substring(l.indexOf(":")+1);
            loggingHandler.println(Multilogger.DEBUG,"Line ["+ line +"] contains ["+ value +"]");
            return value;
        }catch (Exception ex) {
            loggingHandler.println(Multilogger.WARN,"There was a wrong value found on line ["+ line +"] in file ["+ location+"]");
            return "";
        }
    }

    public static int readLineToInt(String location,int line) {
        String l = readFile(location)[line];
        try {
            int value = Integer.parseInt(l.substring(l.indexOf(":")+1));
            loggingHandler.println(Multilogger.DEBUG,"Line ["+ line +"] contains ["+ value+"]");
            return value;
        }catch (Exception ex) {
            loggingHandler.println(Multilogger.WARN,"There was a wrong value found on line ["+ line +"] in file ["+ location+"]");
            return 0;
        }
    }

    public static boolean readLineToBoolan(String location,int line) {
        String l = readFile(location)[line];
        try {
            boolean value = Boolean.parseBoolean(l.replaceAll(" ","").substring((l.replaceAll(" ","").indexOf(":")+1)));
            loggingHandler.println(Multilogger.DEBUG,"Line ["+ line +"] contains ["+ value +"]");
            return value;
        }catch (Exception ex) {
            loggingHandler.println(Multilogger.WARN,"There was a wrong value found on line ["+ line +"] in file ["+ location+"]");
            return false;
        }
    }

    public static void writeToFile(String location, String fileContent) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(location,true));
            writer.write(fileContent);
            writer.close();
        }catch (IOException ex) {
            loggingHandler.printlnNoIO(Multilogger.ERROR,"There was problem trying to write toa a file at ["+location+"]",ex);
        }
    }
    public static void createFile(String location){
        try {
            new File(location).createNewFile();

        } catch (IOException ex) {
            loggingHandler.printlnNoIO(Multilogger.FATAL,"There is a problem creating a the File at Path ["+location+"]",ex);
        }
    }

}
