package org.nearbyshops.ModelRoles;

import org.nearbyshops.Model.Shop;

import java.sql.Timestamp;

/**
 * Created by sumeet on 29/5/16.
 */
public class ShopStaffPermissionsOld {

    // Table Name for Distributor
    public static final String TABLE_NAME = "SHOP_STAFF";

    // Column names for Distributor

    public static final String STAFF_ID = "STAFF_ID";
    public static final String STAFF_NAME = "STAFF_NAME";
//    public static final String USER_NAME = "USER_NAME";
    public static final String PASSWORD = "PASSWORD";

    public static final String SHOP_ID = "SHOP_ID";

    public static final String ABOUT = "ABOUT";
    public static final String PROFILE_IMAGE_URL = "PROFILE_IMAGE_URL";
    public static final String PHONE_NUMBER = "PHONE_NUMBER";
    public static final String DESIGNATION = "DESIGNATION";

    // to be Implemented
    public static final String IS_ENABLED = "IS_ENABLED";

    // privacy
    // private accounts will be hidden from public displays in the end user app
    public static final String ACCOUNT_PRIVATE = "ACCOUNT_PRIVATE";

    public static final String GOVERNMENT_ID_NUMBER = "GOVERNMENT_ID_NUMBER";
    public static final String GOVERNMENT_ID_NAME = "GOVERNMENT_ID_NAME";
    public static final String TIMESTAMP_CREATED = "TIMESTAMP_CREATED";


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

    // permissions : Pick from Shop
    public static final String CANCEL_ORDERS_PFS = "CANCEL_ORDERS_PFS";

    public static final String CONFIRM_ORDERS_PFS = "CONFIRM_ORDERS_PFS";
    public static final String SET_ORDERS_PACKED_PFS = "SET_ORDERS_PACKED_PFS";
    public static final String SET_READY_FOR_PICKUP_PFS = "SET_READY_FOR_PICKUP_PFS";
    public static final String SET_PAYMENT_RECEIVED_PFS = "SET_PAYMENT_RECEIVED_PFS";
    public static final String MARK_DELIVERED_PFS = "MARK_DELIVERED_PFS";






    // Create Table CurrentServiceConfiguration Provider
    public static final String createTableShopStaffPostgres =
            "CREATE TABLE IF NOT EXISTS " + ShopStaffPermissionsOld.TABLE_NAME + "("
            + " " + ShopStaffPermissionsOld.STAFF_ID + " INT UNIQUE NOT NULL,"
            + " " + ShopStaffPermissionsOld.STAFF_NAME + " text,"

            + " " + ShopStaffPermissionsOld.SHOP_ID + " INT NOT NULL,"

            + " " + ShopStaffPermissionsOld.ABOUT + " text,"
            + " " + ShopStaffPermissionsOld.PROFILE_IMAGE_URL + " text,"

            + " " + ShopStaffPermissionsOld.PHONE_NUMBER + " text,"
            + " " + ShopStaffPermissionsOld.DESIGNATION + " text,"
            + " " + ShopStaffPermissionsOld.IS_ENABLED + " boolean NOT NULL,"
            + " " + ShopStaffPermissionsOld.ACCOUNT_PRIVATE + " boolean,"

            + " " + ShopStaffPermissionsOld.GOVERNMENT_ID_NAME + " text,"
            + " " + ShopStaffPermissionsOld.GOVERNMENT_ID_NUMBER + " text,"
            + " " + ShopStaffPermissionsOld.TIMESTAMP_CREATED + " timestamp with time zone NOT NULL DEFAULT now(),"

            + " " + ShopStaffPermissionsOld.ADD_REMOVE_ITEMS_FROM_SHOP + " boolean ,"
            + " " + ShopStaffPermissionsOld.UPDATE_STOCK + " boolean ,"

            + " " + ShopStaffPermissionsOld.CANCEL_ORDERS + " boolean ,"
            + " " + ShopStaffPermissionsOld.CONFIRM_ORDERS + " boolean ,"
            + " " + ShopStaffPermissionsOld.SET_ORDERS_PACKED + " boolean ,"
            + " " + ShopStaffPermissionsOld.HANDOVER_TO_DELIVERY + " boolean ,"
            + " " + ShopStaffPermissionsOld.MARK_ORDERS_DELIVERED + " boolean ,"
            + " " + ShopStaffPermissionsOld.ACCEPT_PAYMENTS_FROM_DELIVERY + " boolean ,"
            + " " + ShopStaffPermissionsOld.ACCEPT_RETURNS + " boolean ,"

            + " " + ShopStaffPermissionsOld.CANCEL_ORDERS_PFS + " boolean ,"
            + " " + ShopStaffPermissionsOld.CONFIRM_ORDERS_PFS + " boolean ,"
            + " " + ShopStaffPermissionsOld.SET_ORDERS_PACKED_PFS + " boolean ,"
            + " " + ShopStaffPermissionsOld.SET_READY_FOR_PICKUP_PFS + " boolean ,"
            + " " + ShopStaffPermissionsOld.SET_PAYMENT_RECEIVED_PFS + " boolean ,"
            + " " + ShopStaffPermissionsOld.MARK_DELIVERED_PFS + " boolean ,"

            + " FOREIGN KEY(" + ShopStaffPermissionsOld.SHOP_ID +") REFERENCES " + Shop.TABLE_NAME + "(" + Shop.SHOP_ID + "),"
            + " FOREIGN KEY(" + ShopStaffPermissionsOld.STAFF_ID +") REFERENCES " + User.TABLE_NAME + "(" + User.USER_ID + ")"
            + ")";


    
    // Instance Variables
    private int staffID;

    private String staffName;
    private String username;
    private String password;

    private int shopID;

    private String about;
    private String profileImageURL;

    private String phone;
    private String designation;

    private Boolean isEnabled;
    private boolean accountPrivate;

    private String govtIDName;
    private String govtIDNumber;
    private Timestamp timestampCreated;

    // permissions General
    private boolean addRemoveItemsFromShop;
    private boolean updateStock;

    // permissions home delivery orders
    private boolean cancelOrders;
    private boolean confirmOrders;
    private boolean setOrdersPacked;
    private boolean handoverToDelivery;
    private boolean markOrdersDelivered;
    private boolean acceptPaymentsFromDelivery;
    private boolean acceptReturns;

    // permissions pick from shop orders
    private boolean permitCancelOrdersPFS;

    private boolean permitConfirmOrdersPFS;
    private boolean permitSetOrdersPackedPFS;
    private boolean permitSetReadyForPickupPFS;
    private boolean permitSetPaymentReceivedPFS;
    private boolean permitMarkDeliveredPFS;



    // Getter and Setters


    public boolean isPermitCancelOrdersPFS() {
        return permitCancelOrdersPFS;
    }

    public void setPermitCancelOrdersPFS(boolean permitCancelOrdersPFS) {
        this.permitCancelOrdersPFS = permitCancelOrdersPFS;
    }

    public boolean isPermitConfirmOrdersPFS() {
        return permitConfirmOrdersPFS;
    }

    public void setPermitConfirmOrdersPFS(boolean permitConfirmOrdersPFS) {
        this.permitConfirmOrdersPFS = permitConfirmOrdersPFS;
    }

    public boolean isPermitSetOrdersPackedPFS() {
        return permitSetOrdersPackedPFS;
    }

    public void setPermitSetOrdersPackedPFS(boolean permitSetOrdersPackedPFS) {
        this.permitSetOrdersPackedPFS = permitSetOrdersPackedPFS;
    }

    public boolean isPermitSetReadyForPickupPFS() {
        return permitSetReadyForPickupPFS;
    }

    public void setPermitSetReadyForPickupPFS(boolean permitSetReadyForPickupPFS) {
        this.permitSetReadyForPickupPFS = permitSetReadyForPickupPFS;
    }

    public boolean isPermitSetPaymentReceivedPFS() {
        return permitSetPaymentReceivedPFS;
    }

    public void setPermitSetPaymentReceivedPFS(boolean permitSetPaymentReceivedPFS) {
        this.permitSetPaymentReceivedPFS = permitSetPaymentReceivedPFS;
    }

    public boolean isPermitMarkDeliveredPFS() {
        return permitMarkDeliveredPFS;
    }

    public void setPermitMarkDeliveredPFS(boolean permitMarkDeliveredPFS) {
        this.permitMarkDeliveredPFS = permitMarkDeliveredPFS;
    }

    public int getShopID() {
        return shopID;
    }

    public void setShopID(int shopID) {
        this.shopID = shopID;
    }

    public boolean isAddRemoveItemsFromShop() {
        return addRemoveItemsFromShop;
    }

    public void setAddRemoveItemsFromShop(boolean addRemoveItemsFromShop) {
        this.addRemoveItemsFromShop = addRemoveItemsFromShop;
    }

    public boolean isUpdateStock() {
        return updateStock;
    }

    public void setUpdateStock(boolean updateStock) {
        this.updateStock = updateStock;
    }

    public boolean isCancelOrders() {
        return cancelOrders;
    }

    public void setCancelOrders(boolean cancelOrders) {
        this.cancelOrders = cancelOrders;
    }

    public boolean isConfirmOrders() {
        return confirmOrders;
    }

    public void setConfirmOrders(boolean confirmOrders) {
        this.confirmOrders = confirmOrders;
    }

    public boolean isSetOrdersPacked() {
        return setOrdersPacked;
    }

    public void setSetOrdersPacked(boolean setOrdersPacked) {
        this.setOrdersPacked = setOrdersPacked;
    }

    public boolean isHandoverToDelivery() {
        return handoverToDelivery;
    }

    public void setHandoverToDelivery(boolean handoverToDelivery) {
        this.handoverToDelivery = handoverToDelivery;
    }

    public boolean isMarkOrdersDelivered() {
        return markOrdersDelivered;
    }

    public void setMarkOrdersDelivered(boolean markOrdersDelivered) {
        this.markOrdersDelivered = markOrdersDelivered;
    }

    public boolean isAcceptPaymentsFromDelivery() {
        return acceptPaymentsFromDelivery;
    }

    public void setAcceptPaymentsFromDelivery(boolean acceptPaymentsFromDelivery) {
        this.acceptPaymentsFromDelivery = acceptPaymentsFromDelivery;
    }

    public boolean isAcceptReturns() {
        return acceptReturns;
    }

    public void setAcceptReturns(boolean acceptReturns) {
        this.acceptReturns = acceptReturns;
    }

    public boolean isAccountPrivate() {
        return accountPrivate;
    }

    public void setAccountPrivate(boolean accountPrivate) {
        this.accountPrivate = accountPrivate;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getGovtIDName() {
        return govtIDName;
    }

    public void setGovtIDName(String govtIDName) {
        this.govtIDName = govtIDName;
    }

    public String getGovtIDNumber() {
        return govtIDNumber;
    }

    public void setGovtIDNumber(String govtIDNumber) {
        this.govtIDNumber = govtIDNumber;
    }

    public Timestamp getTimestampCreated() {
        return timestampCreated;
    }

    public void setTimestampCreated(Timestamp timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }
}
