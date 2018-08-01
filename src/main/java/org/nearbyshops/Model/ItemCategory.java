package org.nearbyshops.Model;

public class ItemCategory {

	// contract class describing the Globals schema for the ItemCategory

	// Table Name
	public static final String TABLE_NAME = "ITEM_CATEGORY";


	// Column Names
	public static final String ITEM_CATEGORY_ID = "ID";
	public static final String ITEM_CATEGORY_NAME = "ITEM_CATEGORY_NAME";
	public static final String ITEM_CATEGORY_DESCRIPTION = "ITEM_CATEGORY_DESC";
	public static final String ITEM_CATEGORY_DESCRIPTION_SHORT = "ITEM_CATEGORY_DESCRIPTION_SHORT";
	public static final String PARENT_CATEGORY_ID = "PARENT_CATEGORY_ID";
	public static final String IMAGE_PATH = "IMAGE_PATH";
	public static final String CATEGORY_ORDER = "CATEGORY_ORDER";

	public static final String IS_ABSTRACT = "IS_ABSTRACT";
	public static final String IS_LEAF_NODE = "IS_LEAF";



	// to be Implemented
//	public static final String IS_ENABLED = "IS_ENABLED";
//	public static final String IS_WAITLISTED = "IS_WAITLISTED";


//	public static final String GIDB_ITEM_CAT_ID = "GIDB_ITEM_CAT_ID";
//	public static final String GIDB_SERVICE_URL = "GIDB_SERVICE_URL";




	// Create Table Statement


	public static final String createTableItemCategoryPostgres = "CREATE TABLE IF NOT EXISTS "
			+ ItemCategory.TABLE_NAME + "("
			+ " " + ItemCategory.ITEM_CATEGORY_ID + " SERIAL PRIMARY KEY,"
			+ " " + ItemCategory.ITEM_CATEGORY_NAME + " text,"
			+ " " + ItemCategory.ITEM_CATEGORY_DESCRIPTION + " text,"
			+ " " + ItemCategory.ITEM_CATEGORY_DESCRIPTION_SHORT + " text,"

			+ " " + ItemCategory.PARENT_CATEGORY_ID + " INT,"
			+ " " + ItemCategory.IMAGE_PATH + " text,"
			+ " " + ItemCategory.CATEGORY_ORDER + " INT,"
			+ " " + ItemCategory.IS_ABSTRACT + " boolean,"
			+ " " + ItemCategory.IS_LEAF_NODE + " boolean,"


			+ " FOREIGN KEY(" + ItemCategory.PARENT_CATEGORY_ID +") REFERENCES " + ItemCategory.TABLE_NAME + "(" + ItemCategory.ITEM_CATEGORY_ID + ")"
			+ ")";


//	public static final String upgradeTableSchema =
//			"ALTER TABLE IF EXISTS " + ItemCategory.TABLE_NAME
//					+ " ADD COLUMN  IF NOT EXISTS " + ItemCategory.GIDB_SERVICE_URL + " text,"
//					+ " ADD COLUMN  IF NOT EXISTS " + ItemCategory.GIDB_ITEM_CAT_ID + " int";



	// Instance Variables


	private int itemCategoryID;
	private String categoryName;
	private String categoryDescription;
	private Integer parentCategoryID;
	private boolean isLeafNode;
	private String imagePath;
	private Integer categoryOrder;
	// recently added
	private boolean isAbstractNode;
	private String descriptionShort;

	ItemCategory parentCategory = null;



	//no-args Constructor
	public ItemCategory() {
		super();
		// TODO Auto-generated constructor stub
	}



	//Getters and Setters

	public Integer getCategoryOrder() {
		return categoryOrder;
	}

	public void setCategoryOrder(Integer categoryOrder) {
		this.categoryOrder = categoryOrder;
	}

	public Boolean getisAbstractNode() {
		return isAbstractNode;
	}

	public void setisAbstractNode(Boolean abstractNode) {
		isAbstractNode = abstractNode;
	}

	public String getDescriptionShort() {
		return descriptionShort;
	}

	public void setDescriptionShort(String descriptionShort) {
		this.descriptionShort = descriptionShort;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	public int getItemCategoryID() {
		return itemCategoryID;
	}

	public void setItemCategoryID(int itemCategoryID) {
		this.itemCategoryID = itemCategoryID;
	}


	public Integer getParentCategoryID() {
		return parentCategoryID;
	}

	public void setParentCategoryID(Integer parentCategoryID) {
		this.parentCategoryID = parentCategoryID;
	}

	public boolean getIsLeafNode() {
		return isLeafNode;
	}

	public void setIsLeafNode(boolean isLeafNode) {
		this.isLeafNode = isLeafNode;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
}
