package org.nearbyshops.ModelRoles;

import org.nearbyshops.ModelSettings.ServiceConfigurationLocal;

import java.sql.Timestamp;

/**
 * Created by sumeet on 29/5/16.
 */
public class UserMarkets {


    // This table keeps information about associations between local user account and market aggregator account
    // A market can be listed on multiple market aggregators therefore a single local user account can be associated
    // with multiple market aggregator account.
    // Therefore we need a special table to keep the information about this association



    // Table Name for User
    public static final String TABLE_NAME = "USER_MARKETS_TABLE";

    // Column names
    public static final String LOCAL_USER_ID = "LOCAL_USER_ID";
    public static final String GLOBAL_USER_ID = "GLOBAL_USER_ID";
    public static final String MARKET_AGGREGATOR_URL = "MARKET_AGGREGATOR_URL";
    public static final String DATE_TIME_CREATED = "DATE_TIME_CREATED";



    // Create Table CurrentServiceConfiguration Provider
    public static final String createTable =

            "CREATE TABLE IF NOT EXISTS "
                    + UserMarkets.TABLE_NAME + "("
                    + " " + UserMarkets.LOCAL_USER_ID + " int not null,"
                    + " " + UserMarkets.GLOBAL_USER_ID + " int not null,"
                    + " " + UserMarkets.MARKET_AGGREGATOR_URL + " text not null,"
                    + " " + UserMarkets.DATE_TIME_CREATED + " timestamp with time zone NOT NULL default now(),"
                    + " FOREIGN KEY(" + UserMarkets.LOCAL_USER_ID +") REFERENCES " + User.TABLE_NAME + "(" + User.USER_ID + ") ON DELETE CASCADE ,"
                    + " UNIQUE (" + UserMarkets.LOCAL_USER_ID + "," + UserMarkets.GLOBAL_USER_ID + "," + UserMarkets.MARKET_AGGREGATOR_URL + "),"
                    + " UNIQUE (" + UserMarkets.GLOBAL_USER_ID + "," + UserMarkets.MARKET_AGGREGATOR_URL + "),"
                    + " UNIQUE (" + UserMarkets.LOCAL_USER_ID + "," + UserMarkets.MARKET_AGGREGATOR_URL + ")"
                    + ")";



    // Instance Variables
    private int userIDLocal;
    private int userIDGlobal;
    private String marketAggregatorURL;
    private Timestamp dateTimeCreated;




    // Getters and Setters

    public int getUserIDLocal() {
        return userIDLocal;
    }

    public void setUserIDLocal(int userIDLocal) {
        this.userIDLocal = userIDLocal;
    }

    public int getUserIDGlobal() {
        return userIDGlobal;
    }

    public void setUserIDGlobal(int userIDGlobal) {
        this.userIDGlobal = userIDGlobal;
    }

    public String getMarketAggregatorURL() {
        return marketAggregatorURL;
    }

    public void setMarketAggregatorURL(String marketAggregatorURL) {
        this.marketAggregatorURL = marketAggregatorURL;
    }

    public Timestamp getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(Timestamp dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }
}
