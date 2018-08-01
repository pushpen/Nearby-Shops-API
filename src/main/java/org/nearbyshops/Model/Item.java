package org.nearbyshops.Model;


import org.nearbyshops.ModelStats.ItemStats;

import java.sql.Timestamp;

public class Item {



	// Table Name
	public static final String TABLE_NAME = "ITEM";

	// column names
	public static final String ITEM_ID = "ITEM_ID";
	public static final String ITEM_NAME = "ITEM_NAME";
	public static final String ITEM_DESC = "ITEM_DESC";

	public static final String ITEM_IMAGE_URL = "ITEM_IMAGE_URL";
	public static final String ITEM_CATEGORY_ID = "ITEM_CATEGORY_ID";


	// recently added
	public static final String QUANTITY_UNIT = "QUANTITY_UNIT";
	public static final String DATE_TIME_CREATED = "DATE_TIME_CREATED";
	public static final String TIMESTAMP_UPDATED = "TIMESTAMP_UPDATED";
	public static final String ITEM_DESCRIPTION_LONG = "ITEM_DESCRIPTION_LONG";

	public static final String LIST_PRICE = "LIST_PRICE";
	public static final String BARCODE = "BARCODE";
	public static final String BARCODE_FORMAT = "BARCODE_FORMAT";
	public static final String IMAGE_COPYRIGHTS = "IMAGE_COPYRIGHTS";





	// Create Table Statement
	public static final String createTableItemPostgres = "CREATE TABLE IF NOT EXISTS "
			+ Item.TABLE_NAME + "("
			+ " " + Item.ITEM_ID + " SERIAL PRIMARY KEY,"
			+ " " + Item.ITEM_NAME + " text,"
			+ " " + Item.ITEM_DESC + " text,"

			+ " " + Item.ITEM_IMAGE_URL + " text,"
			+ " " + Item.ITEM_CATEGORY_ID + " INT,"

			+ " " + Item.QUANTITY_UNIT + " text,"
			+ " " + Item.DATE_TIME_CREATED + "  timestamp with time zone NOT NULL DEFAULT now(),"
			+ " " + Item.TIMESTAMP_UPDATED + "  timestamp with time zone ,"
			+ " " + Item.ITEM_DESCRIPTION_LONG + " text,"

			+ " " + Item.LIST_PRICE + " float,"
			+ " " + Item.BARCODE + " text,"
			+ " " + Item.BARCODE_FORMAT + " text,"
			+ " " + Item.IMAGE_COPYRIGHTS + " text,"

			+ " FOREIGN KEY(" + Item.ITEM_CATEGORY_ID +") REFERENCES " + ItemCategory.TABLE_NAME + "(" + ItemCategory.ITEM_CATEGORY_ID + ")"
			+ ")";





//	public static final String upgradeTableSchema =
//			"ALTER TABLE IF EXISTS " + Item.TABLE_NAME
//					+ " ADD COLUMN IF NOT EXISTS " + Item.GIDB_ITEM_ID + " int,"
//					+ " ADD COLUMN IF NOT EXISTS " + Item.GIDB_SERVICE_URL + " text,"
//					+ " ADD COLUMN IF NOT EXISTS " + Item.IMAGE_COPYRIGHTS + " text,"
//					+ " ADD COLUMN IF NOT EXISTS " + Item.BARCODE + " text,"
//					+ " ADD COLUMN IF NOT EXISTS " + Item.BARCODE_FORMAT + " text,"
//					+ " ADD COLUMN IF NOT EXISTS " + Item.LIST_PRICE + " float,"
//					+ " ADD COLUMN IF NOT EXISTS " + Item.TIMESTAMP_UPDATED + " timestamp with time zone,"
//					+ " DROP COLUMN IF EXISTS " + Item.VERSION + ","
//					+ " DROP COLUMN IF EXISTS " + Item.IS_ENABLED + ","
//					+ " DROP COLUMN IF EXISTS " + Item.IS_WAITLISTED + "";





	// Instance Variables

	private int itemID;
	private String itemName;
	private String itemDescription;

	private String itemImageURL;
	private Integer itemCategoryID;

	// recently added
	private String quantityUnit;
	private Timestamp dateTimeCreated;
	private Timestamp timestampUpdated;
	private String itemDescriptionLong;

	private float listPrice;
	private String barcode;
	private String barcodeFormat;
	private String imageCopyrights;

	private ItemStats itemStats;
	private ItemCategory itemCategory;

	private Float rt_rating_avg;
	private Float rt_rating_count;
	private String rt_gidb_service_url;







	// Getter and Setter Statements


	public ItemStats getItemStats() {
		return itemStats;
	}

	public void setItemStats(ItemStats itemStats) {
		this.itemStats = itemStats;
	}

	public Timestamp getTimestampUpdated() {
		return timestampUpdated;
	}

	public void setTimestampUpdated(Timestamp timestampUpdated) {
		this.timestampUpdated = timestampUpdated;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getBarcodeFormat() {
		return barcodeFormat;
	}

	public void setBarcodeFormat(String barcodeFormat) {
		this.barcodeFormat = barcodeFormat;
	}

	public String getImageCopyrights() {
		return imageCopyrights;
	}

	public void setImageCopyrights(String imageCopyrights) {
		this.imageCopyrights = imageCopyrights;
	}

	public String getRt_gidb_service_url() {
		return rt_gidb_service_url;
	}

	public void setRt_gidb_service_url(String rt_gidb_service_url) {
		this.rt_gidb_service_url = rt_gidb_service_url;
	}

	public Float getRt_rating_avg() {
		return rt_rating_avg;
	}

	public void setRt_rating_avg(Float rt_rating_avg) {
		this.rt_rating_avg = rt_rating_avg;
	}

	public Float getRt_rating_count() {
		return rt_rating_count;
	}

	public void setRt_rating_count(Float rt_rating_count) {
		this.rt_rating_count = rt_rating_count;
	}




	//No-args constructor


	public float getListPrice() {
		return listPrice;
	}

	public void setListPrice(float listPrice) {
		this.listPrice = listPrice;
	}

	public Integer getItemCategoryID() {
		return itemCategoryID;
	}

	public void setItemCategoryID(Integer itemCategoryID) {
		this.itemCategoryID = itemCategoryID;
	}

	public String getQuantityUnit() {
		return quantityUnit;
	}

	public void setQuantityUnit(String quantityUnit) {
		this.quantityUnit = quantityUnit;
	}

	public Timestamp getDateTimeCreated() {
		return dateTimeCreated;
	}

	public void setDateTimeCreated(Timestamp dateTimeCreated) {
		this.dateTimeCreated = dateTimeCreated;
	}

	public String getItemDescriptionLong() {
		return itemDescriptionLong;
	}

	public void setItemDescriptionLong(String itemDescriptionLong) {
		this.itemDescriptionLong = itemDescriptionLong;
	}

	public ItemCategory getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(ItemCategory itemCategory) {
		this.itemCategory = itemCategory;
	}

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public String getItemImageURL() {
		return itemImageURL;
	}

	public void setItemImageURL(String itemImageURL) {
		this.itemImageURL = itemImageURL;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
}
