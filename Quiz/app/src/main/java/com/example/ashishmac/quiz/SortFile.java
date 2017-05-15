/*
Author : Ashish Mohapatra (axm160031) and Akash Deo (apd160330)
Assignment : 5

This java file processes the HighScore.txt File and Sort the file acccording to Highest score with a limit of 5 Players.
*/
package com.example.ashishmac.quiz;

import android.os.Environment;
import java.io.*;
import java.util.*;

/**
 * Created by AshishMAC on 4/7/17.
 */
//Author : Ashish Mohapatra (axm160031)
public class SortFile {
    public static void main() throws Exception {
        String path  = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Quiz"; //Path to the folder where the file is stored
        File file =new File(path+"/HighScore.txt");
        File lastFile =new File(path+"/LastFile.txt");
        File file2 =new File(path+"/fileSorted.txt");
        int count=0;
        if(file2.exists())
        {
            file2.delete();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "Cp1252"), 100);
        Map<String, String> map=new TreeMap<String, String>().descendingMap();
        String line="";
        while((line=reader.readLine())!=null){
            map.put(getField(line),line);
        }
        reader.close();

        FileOutputStream fos = null;
        OutputStreamWriter myOutWriter = null;
        try
        {
            fos = new FileOutputStream(file2,true); //create a new fileOutputStream
            myOutWriter = new OutputStreamWriter(fos); //Open Outputstream Writer to write into text file
        }
        catch (FileNotFoundException e) {e.printStackTrace();}
        try
            {
                    for (String val : map.values()) {
                        if(count<5) {
                        myOutWriter.write(val); //Append all the data as a single line to the text file.
                        myOutWriter.write("\n"); //Append tab after each word
                            count++;
                    }
                }
            }
            catch (IOException e) {e.printStackTrace();}
        myOutWriter.close(); //Close the file
        fos.close();
        file.renameTo(lastFile);
        file2.renameTo(file);
    }

    private static String getField(String line) {
        return line.split("\t")[1];//extract value you want to sort on
    }
}
