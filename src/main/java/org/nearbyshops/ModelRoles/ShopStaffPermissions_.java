package org.nearbyshops.ModelRoles;
/**
 * Created by sumeet on 28/8/17.
 */


public class ShopStaffPermissions_ {

    // Table Name for User
    public static final String TABLE_NAME = "STAFF_PERMISSIONS";

    // Column names
    public static final String PERMISSION_ID = "PERMISSION_ID";
    public static final String STAFF_ID = "STAFF_ID";
    public static final String DESIGNATION = "DESIGNATION";
    public static final String LAT_CURRENT = "LAT_CURRENT";
    public static final String LON_CURRENT = "LON_CURRENT";

    // permissions General
    public static final String ADD_REMOVE_ITEMS_FROM_SHOP = "ADD_REMOVE_ITEMS_FROM_SHOP";
    public static final String UPDATE_STOCK = "UPDATE_STOCK";

    // permissions : Home Delivery
    public static final String CANCEL_ORDERS = "CANCEL_ORDERS";
    public static final String CONFIRM_ORDERS = "CONFIRM_ORDERS";
    public static final String SET_ORDERS_PACKED = "SET_ORDERS_PACKED";
    public static final String HANDOVER_TO_DELIVERY = "HANDOVER_TO_DELIVERY";
    public static final String MARK_ORDERS_DELIVERED = "MARK_ORDERS_DELIVERED";
    public static final String ACCEPT_PAYMENTS_FROM_DELIVERY = "ACCEPT_PAYMENTS_FROM_DELIVERY";
    public static final String ACCEPT_RETURNS = "ACCEPT_RETURNS";



    // Create Table CurrentServiceConfiguration Provider
    public static final String createTablePostgres =

            "CREATE TABLE IF NOT EXISTS "
                    + ShopStaffPermissions_.TABLE_NAME + "("
                    + " " + ShopStaffPermissions_.PERMISSION_ID + " SERIAL PRIMARY KEY,"
                    + " " + ShopStaffPermissions_.STAFF_ID + " int UNIQUE NOT NULL ,"
                    + " " + ShopStaffPermissions_.DESIGNATION + " text,"
                    + " " + ShopStaffPermissions_.LAT_CURRENT + " float not null default 0,"
                    + " " + ShopStaffPermissions_.LON_CURRENT + " float not null default 0,"
                    + " " + ShopStaffPermissions_.ADD_REMOVE_ITEMS_FROM_SHOP + " boolean NOT NULL default 'f',"
                    + " " + ShopStaffPermissions_.UPDATE_STOCK + " boolean NOT NULL default 'f',"

                    + " " + ShopStaffPermissions_.CANCEL_ORDERS + " boolean NOT NULL default 'f',"
                    + " " + ShopStaffPermissions_.CONFIRM_ORDERS + " boolean NOT NULL default 'f',"
                    + " " + ShopStaffPermissions_.SET_ORDERS_PACKED + " boolean NOT NULL default 'f',"
                    + " " + ShopStaffPermissions_.HANDOVER_TO_DELIVERY + " boolean NOT NULL default 'f',"
                    + " " + ShopStaffPermissions_.MARK_ORDERS_DELIVERED + " boolean NOT NULL default 'f',"
                    + " " + ShopStaffPermissions_.ACCEPT_PAYMENTS_FROM_DELIVERY + " boolean NOT NULL default 'f',"
                    + " " + ShopStaffPermissions_.ACCEPT_RETURNS + " boolean NOT NULL default 'f',"

//                    + " " + StaffPermissions.PERMIT_TAXI_PROFILE_UPDATE + " boolean NOT NULL default 'f',"
//                    + " " + StaffPermissions.PERMIT_ACCEPT_PAYMENTS + " boolean NOT NULL default 'f',"
//                    + " " + StaffPermissions.PERMIT_ADD_EDIT_TAXI_IMAGES + " boolean NOT NULL default 'f',"
//                    + " " + StaffPermissions.PERMIT_APPROVE_TAXI_IMAGES + " boolean NOT NULL default 'f',"

                    + " FOREIGN KEY(" + ShopStaffPermissions_.STAFF_ID +") REFERENCES " + User.TABLE_NAME + "(" + User.USER_ID + ") ON DELETE CASCADE "
                    + ")";




    // instance variables
    private int permissionID;
    private int staffUserID;
    private String designation;
    private double latCurrent;
    private double lonCurrent;
    private boolean permitTaxiRegistrationAndRenewal;

    private double rt_distance;



    // getter and setters



    public double getRt_distance() {
        return rt_distance;
    }

    public void setRt_distance(double rt_distance) {
        this.rt_distance = rt_distance;
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

    public int getPermissionID() {
        return permissionID;
    }

    public void setPermissionID(int permissionID) {
        this.permissionID = permissionID;
    }

    public int getStaffUserID() {
        return staffUserID;
    }

    public void setStaffUserID(int staffUserID) {
        this.staffUserID = staffUserID;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public boolean isPermitTaxiRegistrationAndRenewal() {
        return permitTaxiRegistrationAndRenewal;
    }

    public void setPermitTaxiRegistrationAndRenewal(boolean permitTaxiRegistrationAndRenewal) {
        this.permitTaxiRegistrationAndRenewal = permitTaxiRegistrationAndRenewal;
    }
}
