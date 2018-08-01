package org.nearbyshops.ModelRoles;
/**
 * Created by sumeet on 28/8/17.
 */


public class StaffPermissions {

    // Table Name for User
    public static final String TABLE_NAME = "STAFF_PERMISSIONS";

    // Column names
    public static final String PERMISSION_ID = "PERMISSION_ID";
    public static final String STAFF_ID = "STAFF_ID";
    public static final String DESIGNATION = "DESIGNATION";
    public static final String LAT_CURRENT = "LAT_CURRENT";
    public static final String LON_CURRENT = "LON_CURRENT";
    public static final String PERMIT_TAXI_REGISTRATION_AND_RENEWAL = "PERMIT_TAXI_REGISTRATION_AND_RENEWAL";
//    public static final String PERMIT_TAXI_PROFILE_UPDATE = "PERMIT_TAXI_PROFILE_UPDATE";
//    public static final String PERMIT_ACCEPT_PAYMENTS = "PERMIT_ACCEPT_PAYMENTS";
//    public static final String PERMIT_ADD_EDIT_TAXI_IMAGES = "PERMIT_ADD_EDIT_TAXI_IMAGES";
//    public static final String PERMIT_APPROVE_TAXI_IMAGES = "PERMIT_APPROVE_TAXI_IMAGES";



    // Create Table CurrentServiceConfiguration Provider
    public static final String createTablePostgres =

            "CREATE TABLE IF NOT EXISTS "
                    + StaffPermissions.TABLE_NAME + "("
                    + " " + StaffPermissions.PERMISSION_ID + " SERIAL PRIMARY KEY,"
                    + " " + StaffPermissions.STAFF_ID + " int UNIQUE NOT NULL ,"
                    + " " + StaffPermissions.DESIGNATION + " text,"
                    + " " + StaffPermissions.LAT_CURRENT + " float not null default 0,"
                    + " " + StaffPermissions.LON_CURRENT + " float not null default 0,"
                    + " " + StaffPermissions.PERMIT_TAXI_REGISTRATION_AND_RENEWAL + " boolean NOT NULL default 'f',"
//                    + " " + StaffPermissions.PERMIT_TAXI_PROFILE_UPDATE + " boolean NOT NULL default 'f',"
//                    + " " + StaffPermissions.PERMIT_ACCEPT_PAYMENTS + " boolean NOT NULL default 'f',"
//                    + " " + StaffPermissions.PERMIT_ADD_EDIT_TAXI_IMAGES + " boolean NOT NULL default 'f',"
//                    + " " + StaffPermissions.PERMIT_APPROVE_TAXI_IMAGES + " boolean NOT NULL default 'f',"

                    + " FOREIGN KEY(" + StaffPermissions.STAFF_ID +") REFERENCES " + User.TABLE_NAME + "(" + User.USER_ID + ") ON DELETE CASCADE "
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
