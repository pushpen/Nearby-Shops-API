package org.nearbyshops.ModelAnalytics;

import org.nearbyshops.Model.Item;
import org.nearbyshops.ModelRoles.User;

import java.sql.Timestamp;

/**
 * Created by sumeet on 8/8/16.
 */
public class ItemAnalytics {

    // Table Name
    public static final String TABLE_NAME = "ITEM_ANALYTICS";

    // column Names
    public static final String END_USER_ID = "END_USER_ID"; // foreign Key
    public static final String ITEM_ID = "ITEM_ID"; // foreign Key
    public static final String DETAIL_VIEW_CLICK_COUNT = "DETAIL_VIEW_CLICK_COUNT";
    public static final String DATE_TIME_CREATED = "DATE_TIME_CREATED";
    public static final String LAST_UPDATE = "LAST_UPDATE";




    // Create Table Statement
    public static final String createTable = "CREATE TABLE IF NOT EXISTS "
            + ItemAnalytics.TABLE_NAME + "("

            + " " + ItemAnalytics.END_USER_ID + " int not null,"
            + " " + ItemAnalytics.ITEM_ID + " int not null,"
            + " " + ItemAnalytics.DETAIL_VIEW_CLICK_COUNT + " bigint not null default 0,"
            + " " + ItemAnalytics.DATE_TIME_CREATED + " timestamp with time zone NOT NULL DEFAULT now(),"
            + " " + ItemAnalytics.LAST_UPDATE + " timestamp with time zone NOT NULL DEFAULT now(),"

            
            + " FOREIGN KEY(" + ItemAnalytics.END_USER_ID +") REFERENCES " + User.TABLE_NAME + "(" + User.USER_ID + ") ON DELETE SET NULL,"
            + " FOREIGN KEY(" + ItemAnalytics.ITEM_ID +") REFERENCES " + Item.TABLE_NAME + "(" + Item.ITEM_ID + ") ON DELETE SET NULL,"
            + " PRIMARY KEY (" + ItemAnalytics.END_USER_ID + ", " + ItemAnalytics.ITEM_ID + ")"
            + ")";





    // instance Variables

    private int endUserID;
    private int itemID;
    private long detailViewClickCount;
    private int detailViewClickCountIncrement;

    private Timestamp dateTimeCreated;
    private Timestamp lastUpdate;






    // Getter and Setter


    public int getDetailViewClickCountIncrement() {
        return detailViewClickCountIncrement;
    }

    public void setDetailViewClickCountIncrement(int detailViewClickCountIncrement) {
        this.detailViewClickCountIncrement = detailViewClickCountIncrement;
    }

    public int getEndUserID() {
        return endUserID;
    }

    public void setEndUserID(int endUserID) {
        this.endUserID = endUserID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public long getDetailViewClickCount() {
        return detailViewClickCount;
    }

    public void setDetailViewClickCount(long detailViewClickCount) {
        this.detailViewClickCount = detailViewClickCount;
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
