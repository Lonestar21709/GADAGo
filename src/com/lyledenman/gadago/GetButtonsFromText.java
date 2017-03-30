package com.lyledenman.gadago;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GetButtonsFromText {

    private List<String> itemList = new ArrayList<>();
    private String buttonStyle = "Button_Green";

    public void getButtonsFromText() {

        // Get File
        // Convert File to Array
        // Read from Array and make a button for each item
        String fileName = "yelp/yelp-genre-list.txt";
        scanToArray(fileName, itemList);
    }

    // Return the itemList;
    public List getItemList() {
        return itemList;
    }


    //Take in a file name and Array and populate the array with each line of the file
    //Pre: empty ArrayList<String>
    //Post: filled ArrayList<String>
    public static void scanToArray(String fileName, List<String> itemsList) {

        try {
            Scanner scan = new Scanner(new File(fileName));
            scan.useDelimiter("\n");

            while (scan.hasNext()) {
                String line = scan.next();
                itemsList.add(line);
            }
            scan.close();
        } catch(FileNotFoundException e) {
            System.err.println("File Not Found");
        }


    }
}