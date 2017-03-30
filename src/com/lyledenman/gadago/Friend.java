package com.lyledenman.gadago;

import java.util.Set;

/**
 * Created by wally on 3/28/17.
 */
public class Friend {
    String name;
    Location location;
    Set<Integer> preferences;

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    Friend(String name, Location location, Set<Integer> preferences) {
        this.name = name;
        this.location = location;
        this.preferences = preferences;
    }

    public Set<Integer> getMatchingPreferences() {
        return preferences;
    }

}