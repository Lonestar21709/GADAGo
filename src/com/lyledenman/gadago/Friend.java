package com.lyledenman.gadago;

/**
 * Created by wally on 3/28/17.
 */
public class Friend {
    String name;
    Location location;
    int[] preferences;

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    Friend(String name, Location location, int[] preferences) {
        this.name = name;
        this.location = location;
        this.preferences = preferences;
    }

    public int[] getMatchingPreferences() {
        int[] prefs = {0, 2, 4};
        return prefs;
    }

}