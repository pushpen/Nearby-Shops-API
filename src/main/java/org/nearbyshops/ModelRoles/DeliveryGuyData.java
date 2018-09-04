package org.nearbyshops.ModelRoles;

import org.nearbyshops.Model.Shop;

/**
 * Created by sumeet on 28/8/17.
 */



public class DeliveryGuyData {

    // Table Name for User
    public static final String TABLE_NAME = "DELIVERY_GUY_DATA";

    // Column names
    public static final String DATA_ID = "DATA_ID";
    public static final String STAFF_USER_ID = "STAFF_USER_ID";
    public static final String LAT_CURRENT = "LAT_CURRENT";
    public static final String LON_CURRENT = "LON_CURRENT";

    // indicates whether an employee of shop or an independent delivery guy
    public static final String IS_EMPLOYED_BY_SHOP = "IS_EMPLOYED_BY_SHOP";
    public static final String SHOP_ID = "SHOP_ID";
    public static final String CURRENT_BALANCE = "CURRENT_BALANCE";








    // Create Table CurrentServiceConfiguration Provider
    public static final String createTablePostgres =

            "CREATE TABLE IF NOT EXISTS "
                    + DeliveryGuyData.TABLE_NAME + "("
                    + " " + DeliveryGuyData.DATA_ID + " SERIAL PRIMARY KEY,"
                    + " " + DeliveryGuyData.STAFF_USER_ID + " int UNIQUE NOT NULL ,"
                    + " " + DeliveryGuyData.LAT_CURRENT + " float not null default 0,"
                    + " " + DeliveryGuyData.LON_CURRENT + " float not null default 0,"
                    + " " + DeliveryGuyData.IS_EMPLOYED_BY_SHOP + " boolean NOT NULL default 'f',"
                    + " " + DeliveryGuyData.SHOP_ID + " INT NULL,"
                    + " " + DeliveryGuyData.CURRENT_BALANCE + " INT NOT NULL default '0',"


                    + " FOREIGN KEY(" + DeliveryGuyData.SHOP_ID +") REFERENCES " + Shop.TABLE_NAME + "(" + Shop.SHOP_ID + ") ON DELETE CASCADE,"
                    + " FOREIGN KEY(" + DeliveryGuyData.STAFF_USER_ID +") REFERENCES " + User.TABLE_NAME + "(" + User.USER_ID + ") ON DELETE CASCADE "
                    + ")";






    // instance variables
    private int dataID;
    private int staffUserID;
    private double latCurrent;
    private double lonCurrent;
    private boolean isEmployedByShop;
    private int shopID;
    private double currentBalance;




    // getter and setters


    public int getDataID() {
        return dataID;
    }

    public void setDataID(int dataID) {
        this.dataID = dataID;
    }

    public int getStaffUserID() {
        return staffUserID;
    }

    public void setStaffUserID(int staffUserID) {
        this.staffUserID = staffUserID;
    }

    public double getLatCurrent() {
        return latCurrent;
    }

    public void setLatCurrent(double latCurrent) {
        this.latCurrent = latCurrent;
    }

    public double getLonCurrent() {
        return lonCurrent;
    }

    public void setLonCurrent(double lonCurrent) {
        this.lonCurrent = lonCurrent;
    }

    public boolean isEmployedByShop() {
        return isEmployedByShop;
    }

    public void setEmployedByShop(boolean employedByShop) {
        isEmployedByShop = employedByShop;
    }

    public int getShopID() {
        return shopID;
    }

    public void setShopID(int shopID) {
        this.shopID = shopID;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }
}
