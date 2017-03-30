package com.lyledenman.gadago;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Lyle on 2/19/2017.
 */

public class CreateJSON {

    private List<String> itemList = new ArrayList<>();

    public void createJSON() throws FileNotFoundException {

        // Get File
        String fileName = "C:\\Users\\Lyle\\Documents\\JavaCode\\CrackingCodingInterview\\Chapter01\\GADA_Go\\src\\YelpRestaurantCategories.json";

        scanToArray(fileName, itemList);
    }

    // Return the itemList;
    public List getItemList() {
        return itemList;
    }


    //Take in a file name and Array and populate the array with each line of the file
    //Pre: empty ArrayList<String>
    //Post: filled ArrayList<String>
    public static void scanToArray(String fileName, List<String> itemsList)
            throws FileNotFoundException {
        Scanner scan = new Scanner(new File(fileName));
        scan.useDelimiter("\n");

        while (scan.hasNext()) {
            String line = scan.next();
            itemsList.add(line);
        }
        scan.close();
    }

}
