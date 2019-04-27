package org.nearbyshops.ModelDelivery;


import org.nearbyshops.ModelRoles.User;

/**
 * Created by sumeet on 10/6/16.
 */
public class DeliveryAddress {


    // Table Name
    public static final String TABLE_NAME = "DELIVERY_ADDRESS";

    // column Names
    public static final String ID = "ID";
    public static final String NAME = "DISTRIBUTOR_NAME";
    public static final String PHONE_NUMBER = "PHONE_NUMBER";

    public static final String DELIVERY_ADDRESS = "DELIVERY_ADDRESS";
    public static final String CITY = "CITY";

    public static final String PINCODE = "PINCODE";
    public static final String LANDMARK = "LANDMARK";
    public static final String END_USER_ID = "END_USER_ID"; // Primary Key

    public static final String LATITUDE = "LATITUDE";
    public static final String LONGITUDE = "LONGITUDE";





    // create Delivery Address

    public static final String createTableDeliveryAddressPostgres = "CREATE TABLE IF NOT EXISTS " + DeliveryAddress.TABLE_NAME + "("
            + " " + DeliveryAddress.ID + " SERIAL PRIMARY KEY,"
            + " " + DeliveryAddress.END_USER_ID + " INT,"
            + " " + DeliveryAddress.CITY + " text,"
            + " " + DeliveryAddress.DELIVERY_ADDRESS + " text,"
            + " " + DeliveryAddress.LANDMARK + " text,"
            + " " + DeliveryAddress.NAME + " text,"
            + " " + DeliveryAddress.LATITUDE + " FLOAT,"
            + " " + DeliveryAddress.LONGITUDE + " FLOAT,"
            + " " + DeliveryAddress.PHONE_NUMBER + " BIGINT,"
            + " " + DeliveryAddress.PINCODE + " BIGINT,"
            + " FOREIGN KEY(" + DeliveryAddress.END_USER_ID +") REFERENCES " + User.TABLE_NAME + "(" + User.USER_ID + ") ON DELETE CASCADE "
            + ")";







    // instance variables
    private int id;
    private String name;
    private long phoneNumber;
    private String deliveryAddress;
    private String city;
    private long pincode;
    private String landmark;
    private Double latitude;
    private Double longitude;
    private int endUserID;

    private Double rt_distance;


    public Double getRt_distance() {
        return rt_distance;
    }

    public void setRt_distance(Double rt_distance) {
        this.rt_distance = rt_distance;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public int getEndUserID() {
        return endUserID;
    }

    public void setEndUserID(int endUserID) {
        this.endUserID = endUserID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public long getPincode() {
        return pincode;
    }

    public void setPincode(long pincode) {
        this.pincode = pincode;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }
}
