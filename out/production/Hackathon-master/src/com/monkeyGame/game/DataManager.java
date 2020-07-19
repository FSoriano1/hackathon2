package com.monkeyGame.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DataManager {

    public DataManager(){}

    public void writeToFile(String path, String input){
        try {
            FileWriter writer = new FileWriter(path, true);
            writer.write("\r\n");
            writer.write(input);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String readFile(String path, int lineNum){
        int lineNumber=0;
        String data ="";
        try {
            FileReader reader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;
            //this is a horrible way to do this but im too lazy to figure better ways
            while ((line = bufferedReader.readLine()) != null) {
                lineNumber++;
                if(lineNumber==lineNum) {
                    data = line;
                }
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
    /*
    public static void main(String args[]){
        System.out.println(readFile("resources\\test.txt", 2));
        writeToFile("resources\\test.txt", "poop shart butt");
    }
     */
}
