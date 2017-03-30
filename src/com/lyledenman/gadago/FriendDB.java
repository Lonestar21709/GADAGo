package com.lyledenman.gadago;

import java.util.HashSet;
import java.util.Set;

public class FriendDB {
    private Set<Friend> friends = new HashSet<>();

    FriendDB() {
    }

    FriendDB(boolean init) {
        Location ginaLoc = new Location("Gina Hall", 30.526394, -97.684899);
        int[] ginaPrefs = {0, 2, 5};
        Friend gina = new Friend("Gina Hall", ginaLoc, ginaPrefs);
        friends.add(gina);

        Location michaelLoc = new Location("Michael Foster", 30.374624, -97.738907);
        int[] michaelPrefs = {0, 1, 3, 6};
        Friend michael = new Friend("Michael Foster", michaelLoc, michaelPrefs);
        friends.add(michael);

        Location cesarLoc = new Location("Gina Hall", 30.305916, -97.721623);
        int[] cesarPrefs = {1, 4, 5, 7, 9};
        Friend cesar = new Friend("Cesar Garza", cesarLoc, cesarPrefs);
        friends.add(cesar);

        Location donaldLoc = new Location("Donald Trump", 38.897685, -77.036541);
        int[] donaldPrefs = {};
        Friend donald = new Friend("Donald Trump", cesarLoc, cesarPrefs);
        friends.add(donald);
    }

    public void addFriend(Friend f) {
        friends.add(f);
    }

    public Friend searchByName(String name) {

        String[] nameInParts = name.split(" ");

        for (Friend f : friends) {
            if (f.getName().matches(name)) {
                return f;
            }
        }

        return null;
    }

    public Set<Friend> getAllFriends() {
        Set<Friend> allFriends = new HashSet<>();

        for (Friend f : friends) {
            allFriends.add(f);
        }

        if (allFriends.size() > 0) {
            return allFriends;
        }

        return null;
    }
}
