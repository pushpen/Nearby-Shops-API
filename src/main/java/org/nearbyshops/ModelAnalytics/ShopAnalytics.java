package org.nearbyshops.ModelAnalytics;

import org.nearbyshops.Model.Shop;
import org.nearbyshops.ModelRoles.User;

import java.sql.Timestamp;

/**
 * Created by sumeet on 8/8/16.
 */
public class ShopAnalytics {

    // Table Name
    public static final String TABLE_NAME = "SHOP_ANALYTICS";

    // column Names
    public static final String END_USER_ID = "END_USER_ID"; // foreign Key
    public static final String SHOP_ID = "SHOP_ID"; // foreign Key
    public static final String DETAIL_VIEW_CLICK_COUNT = "DETAIL_VIEW_CLICK_COUNT";
    public static final String PHONE_CLICK_COUNT = "PHONE_CLICK_COUNT";
    public static final String DATE_TIME_CREATED = "DATE_TIME_CREATED";
    public static final String LAST_UPDATE = "LAST_UPDATE";



    // Create Table Statement
    public static final String createTable = "CREATE TABLE IF NOT EXISTS "
            + ShopAnalytics.TABLE_NAME + "("

            + " " + ShopAnalytics.END_USER_ID + " int not null,"
            + " " + ShopAnalytics.SHOP_ID + " int not null,"
            + " " + ShopAnalytics.DETAIL_VIEW_CLICK_COUNT + " bigint not null default 0,"
            + " " + ShopAnalytics.PHONE_CLICK_COUNT + " bigint not null default 0,"
            + " " + ShopAnalytics.DATE_TIME_CREATED + " timestamp with time zone NOT NULL DEFAULT now(),"
            + " " + ShopAnalytics.LAST_UPDATE + " timestamp with time zone NOT NULL DEFAULT now(),"

            + " FOREIGN KEY(" + ShopAnalytics.END_USER_ID +") REFERENCES " + User.TABLE_NAME + "(" + User.USER_ID + "),"
            + " FOREIGN KEY(" + ShopAnalytics.SHOP_ID +") REFERENCES " + Shop.TABLE_NAME + "(" + Shop.SHOP_ID + "),"
            + " PRIMARY KEY (" + ShopAnalytics.END_USER_ID + ", " + ShopAnalytics.SHOP_ID + ")"
            + ")";


    // instance Variables

    private int endUserID;
    private int shopID;
    private long detailViewClickCount;
    private long phoneClickCount;
    private Timestamp dateTimeCreated;
    private Timestamp lastUpdate;





    // Getter and Setter


    public void setEndUserID(int endUserID) {
        this.endUserID = endUserID;
    }

    public void setShopID(int shopID) {
        this.shopID = shopID;
    }

    public long getDetailViewClickCount() {
        return detailViewClickCount;
    }

    public void setDetailViewClickCount(long detailViewClickCount) {
        this.detailViewClickCount = detailViewClickCount;
    }

    public long getPhoneClickCount() {
        return phoneClickCount;
    }

    public void setPhoneClickCount(long phoneClickCount) {
        this.phoneClickCount = phoneClickCount;
    }

    public Timestamp getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(Timestamp dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }


}
