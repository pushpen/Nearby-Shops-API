package org.nearbyshops.Globals;

/**
 * Created by sumeet on 22/3/17.
 */
public class GlobalConstants {



    public static String BASE_URI = "http://0.0.0.0:5500";

	public static String POSTGRES_CONNECTION_URL;
    public static String CONNECTION_URL_CREATE_DB;
	public static String POSTGRES_USERNAME;
	public static String POSTGRES_PASSWORD;

//    public static final int NOTIFICATION_TYPE_TRIP_REQUESTS = 1;
//    public static final int NOTIFICATION_TYPE_CURRENT_TRIP = 2;



    public static String MAILGUN_DOMAIN;
    public static String MAILGUN_API_KEY;
    public static String MAILGUN_NAME;
    public static String MAILGUN_EMAIL;



    // credits and offers
    public static int REFERRAL_CREDIT_FOR_DRIVER_REGISTRATION; // credited in the account of the one who refers
    public static int REFERRAL_CREDIT_FOR_END_USER_REGISTRATION; // referral credit - credited into the account of the one who refers

    public static int JOINING_CREDIT_FOR_DRIVER; // credit applied when a driver joins the platform
    public static int JOINING_CREDIT_FOR_END_USER; // credit applied when a end user joins the platform


    public static String ONE_SIGNAL_API_KEY_ADMIN_APP;
    public static String ONE_SIGNAL_APP_ID_ADMIN_APP;

    public static String ONE_SIGNAL_API_KEY_SHOP_OWNER_APP;
    public static String ONE_SIGNAL_APP_ID_SHOP_OWNER_APP;

    public static String ONE_SIGNAL_API_KEY_END_USER_APP;
    public static String ONE_SIGNAL_APP_ID_END_USER_APP;



    // constants
    public static int TOKEN_DURATION_MINUTES; // 24 hours for expiry of a token
    public static int EMAIL_VERIFICATION_CODE_EXPIRY_MINUTES;
    public static int PHONE_OTP_EXPIRY_MINUTES;
    public static int PASSWORD_RESET_CODE_EXPIRY_MINUTES;
    public static int TRIP_REQUEST_EXPIRY_MINUTES;
    public static int TRIP_REQUEST_EXPIRY_EXTENSION_MINUTES;

    public static int MONTHS_TO_EXTEND_TAXI_REGISTRATION_MIN;
    public static int MONTHS_TO_EXTEND_TAXI_REGISTRATION_MAX;

    public static int max_limit;

    public static int MIN_TAX_ACCOUNT_BALANCE;
    public static int MIN_SERVICE_ACCOUNT_BALANCE;


    // style url for maps
    public static String NOTIFICATION_SERVER_HOST_MQTT;



    public static String FIREBASE_DRIVER_KEY = null;
    public static String FIREBASE_END_USER_KEY = null;
    public static String MSG91_SMS_SERVICE_API_KEY = null;



    // role codes
    public static final int ROLE_ADMIN_CODE = 1;
    public static final int ROLE_STAFF_CODE = 2;
    public static final int ROLE_SHOP_ADMIN_CODE = 3;
    public static final int ROLE_SHOP_STAFF_CODE = 4;
    public static final int ROLE_DELIVERY_GUY_CODE = 5;
    public static final int ROLE_END_USER_CODE = 6;






    // Constants for the Roles in the Application
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_STAFF = "STAFF";
    public static final String ROLE_SHOP_ADMIN = "ROLE_SHOP_ADMIN";
    public static final String ROLE_SHOP_STAFF = "SHOP_STAFF";
    public static final String ROLE_DELIVERY_GUY = "ROLE_DELIVERY_GUY";
    public static final String ROLE_END_USER = "END_USER";

}
