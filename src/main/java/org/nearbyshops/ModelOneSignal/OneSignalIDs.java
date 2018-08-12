package org.nearbyshops.ModelOneSignal;

import org.nearbyshops.ModelRoles.User;

public class OneSignalIDs {

    // Table Name for User
    public static final String TABLE_NAME = "ONE_SIGNAL_IDS";

    // Column names
    public static final String USER_ID = "USER_ID"; // foreign key unique
    public static final String PLAYER_ID = "PLAYER_ID"; //


    // Create Table CurrentServiceConfiguration Provider
    public static final String createTable =
            "CREATE TABLE IF NOT EXISTS "
                    + OneSignalIDs.TABLE_NAME + "("
                    + " " + OneSignalIDs.USER_ID + " int UNIQUE NOT NULL,"
                    + " " + OneSignalIDs.PLAYER_ID + " text,"
                    + " FOREIGN KEY(" + OneSignalIDs.USER_ID +") REFERENCES " + User.TABLE_NAME + "(" + User.USER_ID + ") ON DELETE CASCADE "
                    + ")";



    // instance ids

    private int userID;
    private int playerID;



    // getter and setters


    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }
}
