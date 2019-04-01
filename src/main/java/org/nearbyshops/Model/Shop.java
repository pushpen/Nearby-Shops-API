package org.nearbyshops.Model;
import org.nearbyshops.ModelRoles.User;

import java.sql.Timestamp;

public class Shop {


	// Shop Table Name
	public static final String TABLE_NAME = "SHOP";

	// Shop columns

	public static final String SHOP_ID = "SHOP_ID";
	public static final String SHOP_NAME = "SHOP_NAME";
	public static final String DELIVERY_RANGE = "DELIVERY_RANGE";
	public static final String LAT_CENTER = "LAT_CENTER";
	public static final String LON_CENTER = "LON_CENTER";

	public static final String DELIVERY_CHARGES = "DELIVERY_CHARGES";
	public static final String BILL_AMOUNT_FOR_FREE_DELIVERY = "BILL_AMOUNT_FOR_FREE_DELIVERY";
	// to be added
	public static final String PICK_FROM_SHOP_AVAILABLE = "PICK_FROM_SHOP_AVAILABLE";
	public static final String HOME_DELIVERY_AVAILABLE = "HOME_DELIVERY_AVAILABLE";

	public static final String LOGO_IMAGE_PATH = "LOGO_IMAGE_PATH";

	// recently Added
	public static final String SHOP_ADDRESS = "SHOP_ADDRESS";
	public static final String CITY = "CITY";
	public static final String PINCODE = "PINCODE";
	public static final String LANDMARK = "LANDMARK";

	public static final String CUSTOMER_HELPLINE_NUMBER = "CUSTOMER_HELPLINE_NUMBER";
	public static final String DELIVERY_HELPLINE_NUMBER = "DELIVERY_HELPLINE_NUMBER";
	public static final String SHORT_DESCRIPTION = "SHORT_DESCRIPTION";
	public static final String LONG_DESCRIPTION = "LONG_DESCRIPTION";
	public static final String TIMESTAMP_CREATED = "TIMESTAMP_CREATED";
	public static final String IS_OPEN = "IS_SHOP_OPEN";

	// to be added
	public static final String SHOP_ENABLED = "SHOP_ENABLED";
	public static final String SHOP_WAITLISTED = "SHOP_WAITLISTED";

	public static final String SHOP_ADMIN_ID = "SHOP_ADMIN_ID";

	public static final String GOVERNMENT_ID_NUMBER = "GOVERNMENT_ID_NUMBER";
	public static final String GOVERNMENT_ID_NAME = "GOVERNMENT_ID_NAME";

	public static final String ACCOUNT_BALANCE = "ACCOUNT_BALANCE";
	public static final String EXTENDED_CREDIT_LIMIT = "EXTENDED_CREDIT_LIMIT";



	// deprecated columns
//	public static final String LAT_MAX = "LAT_MAX";
//	public static final String LON_MAX = "LON_MAX";
//	public static final String LAT_MIN = "LAT_MIN";
//	public static final String LON_MIN = "LON_MIN";
//	public static final String DISTRIBUTOR_ID = "Distributor";


	// deleted columns

//	+ " " + Shop.DISTRIBUTOR_ID + " INT,"

//			+ " " + Shop.LON_MAX + " FLOAT,"
//			+ " " + Shop.LAT_MAX + " FLOAT,"
//			+ " " + Shop.LON_MIN + " FLOAT,"
//			+ " " + Shop.LAT_MIN + " FLOAT,"

	//	public static final String IMAGE_PATH = "IMAGE_PATH";
//	public static final String BACKDROP_IMAGE_PATH = "BACKDROP_IMAGE_PATH";

	// shop admin fields / columns
//			+ " " + ShopAdmin.NAME_ADMIN + " text,"
//			+ " " + ShopAdmin.USERNAME + " text UNIQUE,"
//			+ " " + ShopAdmin.PASSWORD + " text,"
//			+ " " + ShopAdmin.PROFILE_IMAGE_URL + " text,"
//			+ " " + ShopAdmin.PHONE_NUMBER + " text,"
//			+ " " + ShopAdmin.ADMIN_ENABLED + " boolean,"
//			+ " " + ShopAdmin.ADMIN_WAITLISTED + " boolean" +




	// query postgres

	public static final String createTableShopPostgres =
			"CREATE TABLE IF NOT EXISTS " + Shop.TABLE_NAME + "("
			+ " " + Shop.SHOP_ID + " SERIAL PRIMARY KEY,"
			+ " " + Shop.SHOP_NAME + " text,"

			+ " " + Shop.DELIVERY_RANGE + " FLOAT,"
			+ " " + Shop.LON_CENTER + " FLOAT,"
			+ " " + Shop.LAT_CENTER + " FLOAT,"

			+ " " + Shop.DELIVERY_CHARGES + " FLOAT,"
			+ " " + Shop.BILL_AMOUNT_FOR_FREE_DELIVERY + " INT,"
			+ " " + Shop.PICK_FROM_SHOP_AVAILABLE + " boolean,"
			+ " " + Shop.HOME_DELIVERY_AVAILABLE + " boolean,"

			+ " " + Shop.SHOP_ENABLED + " boolean,"
			+ " " + Shop.SHOP_WAITLISTED + " boolean NOT NULL,"

			+ " " + Shop.LOGO_IMAGE_PATH + " text,"

			+ " " + Shop.SHOP_ADDRESS + " text,"
			+ " " + Shop.CITY + " text,"
			+ " " + Shop.PINCODE + " INT,"
			+ " " + Shop.LANDMARK + " text,"

			+ " " + Shop.CUSTOMER_HELPLINE_NUMBER + " text,"
			+ " " + Shop.DELIVERY_HELPLINE_NUMBER + " text,"

			+ " " + Shop.SHORT_DESCRIPTION + " text,"
			+ " " + Shop.LONG_DESCRIPTION + " text,"

			+ " " + Shop.GOVERNMENT_ID_NAME + " text,"
			+ " " + Shop.GOVERNMENT_ID_NUMBER + " text,"

			+ " " + Shop.TIMESTAMP_CREATED + " timestamp with time zone NOT NULL DEFAULT now(),"
			+ " " + Shop.IS_OPEN + " boolean ,"
			+ " " + Shop.SHOP_ADMIN_ID + " INT UNIQUE NOT NULL ,"

			+ " " + Shop.ACCOUNT_BALANCE + " float NOT NULL default 0 ,"
			+ " " + Shop.EXTENDED_CREDIT_LIMIT + " float NOT NULL default 0 ,"


			+ " FOREIGN KEY(" + Shop.SHOP_ADMIN_ID +") REFERENCES " + User.TABLE_NAME + "(" + User.USER_ID + ")"
			+ ")" ;



	public static final String addColumns =
					" ALTER TABLE IF EXISTS " + Shop.TABLE_NAME
					+ "  ADD COLUMN IF NOT EXISTS  " + Shop.ACCOUNT_BALANCE + "  float NOT NULL default 0 ,"
					+ "  ADD COLUMN IF NOT EXISTS  " + Shop.EXTENDED_CREDIT_LIMIT + "  float NOT NULL default 0 ";


//	alter table tbl alter col1 drop not null,

	
	public static final String removeNotNull =
			" ALTER TABLE IF EXISTS " + Shop.TABLE_NAME  + "  ALTER  " + Shop.SHOP_ENABLED + "  drop NOT NULL ";





	// normal variables
	private int shopID;
	
	private String shopName;

	// the radius of the circle considering shop location as its center.
	//This is the distance upto which shop can deliver its items
	private double deliveryRange;

	// latitude and longitude for storing the location of the shop
	private double latCenter;
	private double lonCenter;

	// delivery charger per order
	private double deliveryCharges;
	private int billAmountForFreeDelivery;
	private boolean pickFromShopAvailable;
	private boolean homeDeliveryAvailable;

	private boolean shopEnabled;
	private boolean shopWaitlisted;


	
	private String logoImagePath;


	// added recently
	private String shopAddress;
	private String city;
	private long pincode;
	private String landmark;

	private String customerHelplineNumber;
	private String deliveryHelplineNumber;

	private String shortDescription;
	private String longDescription;

	private Timestamp timestampCreated;
	private boolean isOpen;
	private int shopAdminID;


	// real time variables
	private double rt_distance;
	private float rt_rating_avg;
	private float rt_rating_count;
	private double rt_min_balance;

	private double accountBalance;
	private double extendedCreditLimit;

	private User shopAdminProfile;





	// deleted columns
	// bounding coordinates for the shop generated using shop center coordinates and delivery range.
//	private double latMax;
//	private double lonMax;
//	private double latMin;
//	private double lonMin;


	// getter and setters


	public double getExtendedCreditLimit() {
		return extendedCreditLimit;
	}

	public void setExtendedCreditLimit(double extendedCreditLimit) {
		this.extendedCreditLimit = extendedCreditLimit;
	}

	public double getRt_min_balance() {
		return rt_min_balance;
	}

	public void setRt_min_balance(double rt_min_balance) {
		this.rt_min_balance = rt_min_balance;
	}

	public double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public User getShopAdminProfile() {
		return shopAdminProfile;
	}

	public void setShopAdminProfile(User shopAdminProfile) {
		this.shopAdminProfile = shopAdminProfile;
	}

	public int getShopAdminID() {
		return shopAdminID;
	}

	public void setShopAdminID(int shopAdminID) {
		this.shopAdminID = shopAdminID;
	}

	public int getShopID() {
		return shopID;
	}

	public void setShopID(int shopID) {
		this.shopID = shopID;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public double getDeliveryRange() {
		return deliveryRange;
	}

	public void setDeliveryRange(double deliveryRange) {
		this.deliveryRange = deliveryRange;
	}

	public double getLatCenter() {
		return latCenter;
	}

	public void setLatCenter(double latCenter) {
		this.latCenter = latCenter;
	}

	public double getLonCenter() {
		return lonCenter;
	}

	public void setLonCenter(double lonCenter) {
		this.lonCenter = lonCenter;
	}

	public double getDeliveryCharges() {
		return deliveryCharges;
	}

	public void setDeliveryCharges(double deliveryCharges) {
		this.deliveryCharges = deliveryCharges;
	}

	public int getBillAmountForFreeDelivery() {
		return billAmountForFreeDelivery;
	}

	public void setBillAmountForFreeDelivery(int billAmountForFreeDelivery) {
		this.billAmountForFreeDelivery = billAmountForFreeDelivery;
	}

	public Boolean getPickFromShopAvailable() {
		return pickFromShopAvailable;
	}

	public void setPickFromShopAvailable(Boolean pickFromShopAvailable) {
		this.pickFromShopAvailable = pickFromShopAvailable;
	}

	public Boolean getHomeDeliveryAvailable() {
		return homeDeliveryAvailable;
	}

	public void setHomeDeliveryAvailable(Boolean homeDeliveryAvailable) {
		this.homeDeliveryAvailable = homeDeliveryAvailable;
	}

	public Boolean getShopEnabled() {
		return shopEnabled;
	}

	public void setShopEnabled(Boolean shopEnabled) {
		this.shopEnabled = shopEnabled;
	}

	public Boolean getShopWaitlisted() {
		return shopWaitlisted;
	}

	public void setShopWaitlisted(Boolean shopWaitlisted) {
		this.shopWaitlisted = shopWaitlisted;
	}

	public String getLogoImagePath() {
		return logoImagePath;
	}

	public void setLogoImagePath(String logoImagePath) {
		this.logoImagePath = logoImagePath;
	}

	public String getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
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

	public String getCustomerHelplineNumber() {
		return customerHelplineNumber;
	}

	public void setCustomerHelplineNumber(String customerHelplineNumber) {
		this.customerHelplineNumber = customerHelplineNumber;
	}

	public String getDeliveryHelplineNumber() {
		return deliveryHelplineNumber;
	}

	public void setDeliveryHelplineNumber(String deliveryHelplineNumber) {
		this.deliveryHelplineNumber = deliveryHelplineNumber;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public Timestamp getTimestampCreated() {
		return timestampCreated;
	}

	public void setTimestampCreated(Timestamp timestampCreated) {
		this.timestampCreated = timestampCreated;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean open) {
		isOpen = open;
	}

	public double getRt_distance() {
		return rt_distance;
	}

	public void setRt_distance(double rt_distance) {
		this.rt_distance = rt_distance;
	}

	public float getRt_rating_avg() {
		return rt_rating_avg;
	}

	public void setRt_rating_avg(float rt_rating_avg) {
		this.rt_rating_avg = rt_rating_avg;
	}

	public float getRt_rating_count() {
		return rt_rating_count;
	}

	public void setRt_rating_count(float rt_rating_count) {
		this.rt_rating_count = rt_rating_count;
	}
}
