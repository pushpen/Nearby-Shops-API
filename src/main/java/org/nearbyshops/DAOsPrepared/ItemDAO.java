package org.nearbyshops.DAOsPrepared;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Item;
import org.nearbyshops.Model.ItemCategory;
import org.nearbyshops.Model.Shop;
import org.nearbyshops.Model.ShopItem;
import org.nearbyshops.ModelEndpoint.ItemEndPoint;
import org.nearbyshops.ModelItemSpecification.ItemSpecificationItem;
import org.nearbyshops.ModelItemSpecification.ItemSpecificationValue;
import org.nearbyshops.ModelReviewItem.ItemReview;
import org.nearbyshops.ModelStats.ItemStats;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ItemDAO {


	private HikariDataSource dataSource = Globals.getDataSource();


	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}
		
	


	public int saveItem(Item item, boolean getRowCount)
	{


		Connection connection = null;
		PreparedStatement statement = null;
		int idOfInsertedRow = -1;
		int rowCountItems = 0;

		String insertItemCategory = "INSERT INTO "
				+ Item.TABLE_NAME
				+ "("
				+ Item.ITEM_NAME + ","
				+ Item.ITEM_DESC + ","

				+ Item.ITEM_IMAGE_URL + ","
				+ Item.ITEM_CATEGORY_ID + ","

				+ Item.QUANTITY_UNIT + ","
				+ Item.TIMESTAMP_UPDATED + ","
				+ Item.ITEM_DESCRIPTION_LONG + ","

				+ Item.LIST_PRICE + ","
				+ Item.BARCODE + ","
				+ Item.BARCODE_FORMAT + ","
				+ Item.IMAGE_COPYRIGHTS + ""

				+ ") VALUES(?,? ,?,?, ?,?,?, ?,?,?,?)";

		try {

			connection = dataSource.getConnection();
			statement = connection.prepareStatement(insertItemCategory,PreparedStatement.RETURN_GENERATED_KEYS);

			int i = 0;
			statement.setString(++i,item.getItemName());
			statement.setString(++i,item.getItemDescription());

			statement.setString(++i,item.getItemImageURL());
			statement.setInt(++i,item.getItemCategoryID());

			statement.setString(++i,item.getQuantityUnit());
			statement.setTimestamp(++i,new Timestamp(System.currentTimeMillis()));
			statement.setString(++i,item.getItemDescriptionLong());

			statement.setFloat(++i,item.getListPrice());
			statement.setString(++i,item.getBarcode());
			statement.setString(++i,item.getBarcodeFormat());
			statement.setString(++i,item.getImageCopyrights());

//			statement.setObject(++i,item.getGidbItemID());
//			statement.setString(++i,item.getGidbServiceURL());



			rowCountItems = statement.executeUpdate();

			ResultSet rs = statement.getGeneratedKeys();

			if(rs.next())
			{
				idOfInsertedRow = rs.getInt(1);
			}


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally
		{

			try {

				if(statement!=null)
				{statement.close();}

			}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {

				if(connection!=null)
				{connection.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if(getRowCount)
		{
			return rowCountItems;
		}
		else
		{
			return idOfInsertedRow;
		}
	}




	public int changeParent(Item item)
	{

		String updateStatement = "UPDATE " + Item.TABLE_NAME

				+ " SET "

//				+ " " + Item.ITEM_NAME + " = ?,"
//				+ " " + Item.ITEM_DESC + " = ?,"
//				+ " " + Item.ITEM_IMAGE_URL + " = ?,"

				+ " " + Item.ITEM_CATEGORY_ID + " = ?"
//				+ " " + Item.QUANTITY_UNIT + " = ?,"
//				+ " " + Item.ITEM_DESCRIPTION_LONG + " = ?"

				+ " WHERE " + Item.ITEM_ID + " = ?";


		Connection connection = null;
		PreparedStatement statement = null;

		int rowCountUpdated = 0;

		try {

			connection = dataSource.getConnection();
			statement = connection.prepareStatement(updateStatement);

//			statement.setString(1,item.getItemName());
//			statement.setString(2,item.getItemDescription());
//			statement.setString(3,item.getItemImageURL());

			if(item.getItemCategoryID()!=null && (item.getItemCategoryID()==-1||item.getItemCategoryID()==0))
			{
				item.setItemCategoryID(null);
			}

			statement.setObject(1,item.getItemCategoryID());
//			statement.setString(5,item.getQuantityUnit());
//			statement.setString(6,item.getItemDescriptionLong());
			statement.setInt(2,item.getItemID());

			rowCountUpdated = statement.executeUpdate();
//			System.out.println("Total rows updated: " + rowCountUpdated);


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally

		{

			try {

				if(statement!=null)
				{statement.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {

				if(connection!=null)
				{connection.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return rowCountUpdated;
	}



	public int changeParentBulk(List<Item> itemList)
	{

		String updateStatement = "UPDATE " + Item.TABLE_NAME

				+ " SET "

//				+ " " + Item.ITEM_NAME + " = ?,"
//				+ " " + Item.ITEM_DESC + " = ?,"
//				+ " " + Item.ITEM_IMAGE_URL + " = ?,"

				+ " " + Item.ITEM_CATEGORY_ID + " = ?"
//				+ " " + Item.QUANTITY_UNIT + " = ?,"
//				+ " " + Item.ITEM_DESCRIPTION_LONG + " = ?"

				+ " WHERE " + Item.ITEM_ID + " = ?";


		Connection connection = null;
		PreparedStatement statement = null;

		int rowCountUpdated = 0;

		try {

			connection = dataSource.getConnection();
			statement = connection.prepareStatement(updateStatement);


			for(Item item : itemList)
			{
//				statement.setString(1,item.getItemName());
//				statement.setString(2,item.getItemDescription());
//				statement.setString(3,item.getItemImageURL());
				if(item.getItemCategoryID()!=null && item.getItemCategoryID()==-1)
				{
					item.setItemCategoryID(null);
				}

				statement.setObject(1,item.getItemCategoryID());
//				statement.setString(5,item.getQuantityUnit());
//				statement.setString(6,item.getItemDescriptionLong());
				statement.setInt(2,item.getItemID());

				statement.addBatch();
			}


			int[] totalsArray = statement.executeBatch();

			for(int i : totalsArray)
			{
				rowCountUpdated = rowCountUpdated + i;
			}

//			System.out.println("Total rows updated: UPDATE BULK " + rowCountUpdated);


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally

		{

			try {

				if(statement!=null)
				{statement.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {

				if(connection!=null)
				{connection.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return rowCountUpdated;
	}



	public int updateItem(Item item)
	{

		String updateStatement = "UPDATE " + Item.TABLE_NAME

				+ " SET "

				+ Item.ITEM_NAME + "=?,"
				+ Item.ITEM_DESC + "=?,"

				+ Item.ITEM_IMAGE_URL + "=?,"
				+ Item.ITEM_CATEGORY_ID + "=?,"

				+ Item.QUANTITY_UNIT + "=?,"
				+ Item.TIMESTAMP_UPDATED + "=?,"
				+ Item.ITEM_DESCRIPTION_LONG + "=?,"

				+ Item.LIST_PRICE + "=?,"
				+ Item.BARCODE + "=?,"
				+ Item.BARCODE_FORMAT + "=?,"
				+ Item.IMAGE_COPYRIGHTS + "=?"

				+ " WHERE " + Item.ITEM_ID + " = ?";


		Connection connection = null;
		PreparedStatement statement = null;

		int rowCountUpdated = 0;

		try {

			connection = dataSource.getConnection();
			statement = connection.prepareStatement(updateStatement);

			int i = 0;
			statement.setString(++i,item.getItemName());
			statement.setString(++i,item.getItemDescription());

			statement.setString(++i,item.getItemImageURL());
			statement.setInt(++i,item.getItemCategoryID());

			statement.setString(++i,item.getQuantityUnit());
			statement.setTimestamp(++i,new Timestamp(System.currentTimeMillis()));
			statement.setString(++i,item.getItemDescriptionLong());

			statement.setFloat(++i,item.getListPrice());
			statement.setString(++i,item.getBarcode());
			statement.setString(++i,item.getBarcodeFormat());
			statement.setString(++i,item.getImageCopyrights());


			statement.setObject(++i,item.getItemID());


			rowCountUpdated = statement.executeUpdate();
//			System.out.println("Total rows updated: " + rowCountUpdated);


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally

		{

			try {

				if(statement!=null)
				{statement.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {

				if(connection!=null)
				{connection.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return rowCountUpdated;
	}




	public int deleteItem(int itemID)
	{

		String deleteStatement = "DELETE FROM " + Item.TABLE_NAME + " WHERE " + Item.ITEM_ID + " = ?";

		Connection connection= null;
		PreparedStatement statement = null;
		int rowCountDeleted = 0;
		try {
			
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(deleteStatement);
			statement.setInt(1,itemID);

			rowCountDeleted = statement.executeUpdate();
			
//			System.out.println("Rows Deleted: " + rowCountDeleted);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{



			try {
			
				if(statement!=null)
				{statement.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				
				if(connection!=null)
				{connection.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return rowCountDeleted;
	}




	
	public ItemEndPoint getItems(
					Integer itemCategoryID, Integer shopID,
					Double latCenter, Double lonCenter,
					String itemSpecValues,
					Double deliveryRangeMin,Double deliveryRangeMax,
					Double proximity,
					String searchString,
					String sortBy,
					Integer limit, Integer offset,
					boolean getRowCount,
					boolean getOnlyMetadata
	)
	{

		String queryCount = "";

//		String query = "";


		String queryJoin = "SELECT "
				+ "min(" + ShopItem.ITEM_PRICE + ") as min_price" + ","
				+ "max(" + ShopItem.ITEM_PRICE + ") as max_price" + ","
				+ "avg(" + ShopItem.ITEM_PRICE + ") as avg_price" + ","
				+ "count( DISTINCT " + ShopItem.TABLE_NAME + "." + ShopItem.SHOP_ID + ") as shop_count" + ","

				+ Item.TABLE_NAME + "." + Item.ITEM_ID + ","

				+ Item.TABLE_NAME + "." + Item.ITEM_NAME + ","
				+ Item.TABLE_NAME + "." + Item.ITEM_DESC + ","

				+ Item.TABLE_NAME + "." + Item.ITEM_IMAGE_URL + ","
				+ Item.TABLE_NAME + "." + Item.ITEM_CATEGORY_ID + ","

				+ Item.TABLE_NAME + "." + Item.QUANTITY_UNIT + ","
//				+ Item.TABLE_NAME + "." + Item.DATE_TIME_CREATED + ","
//				+ Item.TABLE_NAME + "." + Item.TIMESTAMP_UPDATED + ","
				+ Item.TABLE_NAME + "." + Item.ITEM_DESCRIPTION_LONG + ","

				+ Item.TABLE_NAME + "." + Item.LIST_PRICE + ","
				+ Item.TABLE_NAME + "." + Item.BARCODE + ","
				+ Item.TABLE_NAME + "." + Item.BARCODE_FORMAT + ","
				+ Item.TABLE_NAME + "." + Item.IMAGE_COPYRIGHTS + ","


				+  "avg(" + ItemReview.TABLE_NAME + "." + ItemReview.RATING + ") as avg_rating" + ","
				+  "count( DISTINCT " + ItemReview.TABLE_NAME + "." + ItemReview.END_USER_ID + ") as rating_count" + ","
				+  "(avg(" + ItemReview.TABLE_NAME + "." + ItemReview.RATING + ")* count( DISTINCT " + ItemReview.TABLE_NAME + "." + ItemReview.END_USER_ID + ") ) as popularity"


				+ " FROM " + ItemCategory.TABLE_NAME
				+ " INNER JOIN " + Item.TABLE_NAME + " ON ( " + Item.TABLE_NAME + "." + Item.ITEM_CATEGORY_ID + " = " + ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_ID + " ) "
				+ " INNER JOIN " + ShopItem.TABLE_NAME + " ON ( " + ShopItem.TABLE_NAME + "." + ShopItem.ITEM_ID + "=" + Item.TABLE_NAME + "." + Item.ITEM_ID + " ) "
				+ " INNER JOIN " + Shop.TABLE_NAME + " ON ( " + Shop.TABLE_NAME + "." + Shop.SHOP_ID + "=" + ShopItem.TABLE_NAME + "." + ShopItem.SHOP_ID + " ) "
				+ " LEFT OUTER JOIN " + ItemReview.TABLE_NAME + " ON ( " + ItemReview.TABLE_NAME + "." + ItemReview.ITEM_ID + " = " + Item.TABLE_NAME + "." + Item.ITEM_ID + " ) "
				+ " LEFT OUTER JOIN " + ItemSpecificationItem.TABLE_NAME + " ON ( " + Item.TABLE_NAME + "." + Item.ITEM_ID + " = "  +  ItemSpecificationItem.TABLE_NAME + "." + ItemSpecificationItem.ITEM_ID + " ) "
				+ " LEFT OUTER JOIN " + ItemSpecificationValue.TABLE_NAME + " ON ( " + ItemSpecificationValue.TABLE_NAME + "." + ItemSpecificationValue.ID + " = " + ItemSpecificationItem.TABLE_NAME + "." + ItemSpecificationItem.ITEM_SPECIFICATION_VALUE_ID + " ) "

				+ " WHERE " + Shop.TABLE_NAME + "." + Shop.IS_OPEN + " = TRUE "
				+ " AND " + Shop.TABLE_NAME + "." + Shop.SHOP_ENABLED + " = TRUE "
				+ " AND " + ShopItem.TABLE_NAME + "." + ShopItem.ITEM_PRICE + " > 0 ";


//		+ " AND " + ShopItem.TABLE_NAME + "." + ShopItem.AVAILABLE_ITEM_QUANTITY + " > 0 "


		if(itemSpecValues!=null)
		{
			queryJoin = queryJoin + " AND " + ItemSpecificationValue.TABLE_NAME + "." + ItemSpecificationValue.ID + " IN ( " + itemSpecValues + " ) ";
		}




		if(shopID != null)
		{
				queryJoin = queryJoin + " AND "
						+ Shop.TABLE_NAME
						+ "."
						+ Shop.SHOP_ID + " = " + shopID;
			
		}



		if(itemCategoryID != null)
		{
			queryJoin = queryJoin + " AND "
					+ Item.TABLE_NAME
					+ "."
					+ Item.ITEM_CATEGORY_ID + " = " + itemCategoryID;
			
			
			
			//" WHERE ITEM_CATEGORY_ID = " + itemCategoryID
			
//			queryNormal = queryNormal + " WHERE "
//					+ Item.TABLE_NAME
//					+ "."
//					+ Item.ITEM_CATEGORY_ID + " = " + itemCategoryID;
			
		}



		// Applying filters

		if(latCenter !=null && lonCenter !=null)
		{
			// Applying shop visibility filter. Gives all the shops which are visible at the given location defined by
			// latCenter and lonCenter. For more information see the API documentation.


			/*String queryPartLatLonBounding = "";

			queryPartLatLonBounding = queryPartLatLonBounding
					+ " AND "
					+ Shop.TABLE_NAME
					+ "."
					+ Shop.LAT_MAX
					+ " >= " + latCenter
					+ " AND "
					+ Shop.TABLE_NAME
					+ "."
					+ Shop.LAT_MIN
					+ " <= " + latCenter
					+ " AND "
					+ Shop.TABLE_NAME
					+ "."
					+ Shop.LON_MAX
					+ " >= " + lonCenter
					+ " AND "
					+ Shop.TABLE_NAME
					+ "."
					+ Shop.LON_MIN
					+ " <= " + lonCenter;*/

			//+ " BETWEEN " + latmax + " AND " + latmin;

			String queryPartlatLonCenter = "";

			queryPartlatLonCenter = queryPartlatLonCenter + " 6371.01 * acos( cos( radians("
					+ latCenter + ")) * cos( radians( lat_center) ) * cos(radians( lon_center ) - radians("
					+ lonCenter + "))"
					+ " + sin( radians(" + latCenter + ")) * sin(radians(lat_center))) <= delivery_range ";




			String queryPartlatLonCenterNew = "";

			queryPartlatLonCenterNew = queryPartlatLonCenterNew
					+ " (6371.01 * acos(cos( radians(" + latCenter + ")) * cos( radians("
					+ Shop.LAT_CENTER + " )) * cos(radians( "
					+ Shop.LON_CENTER + ") - radians(" + lonCenter + "))"
					+ " + sin( radians(" + latCenter + ")) * sin(radians(" + Shop.LAT_CENTER
					+ ")))) <= " + Shop.DELIVERY_RANGE ;


			queryJoin = queryJoin + " AND " + queryPartlatLonCenterNew;
		}




		if(deliveryRangeMin !=null  ||deliveryRangeMax !=null){

			// apply delivery range filter

			queryJoin = queryJoin
					+ " AND "
					+ Shop.TABLE_NAME
					+ "."
					+ Shop.DELIVERY_RANGE
					+ " BETWEEN " + deliveryRangeMin + " AND " + deliveryRangeMax;

			//+ " <= " + deliveryRange;
		}




		// proximity cannot be greater than the delivery range if the delivery range is supplied. Otherwise this condition is
		// not required.
		if(proximity !=null)
		{

			String queryPartProximity = "";

			// filter using Haversine formula using SQL math functions
			queryPartProximity = queryPartProximity
					+ " (6371.01 * acos(cos( radians("
					+ latCenter
					+ ")) * cos( radians("
					+ Shop.LAT_CENTER
					+ " )) * cos(radians( "
					+ Shop.LON_CENTER
					+ ") - radians("
					+ lonCenter
					+ "))"
					+ " + sin( radians("
					+ latCenter
					+ ")) * sin(radians("
					+ Shop.LAT_CENTER
					+ ")))) <= "
					+ proximity ;


			queryJoin = queryJoin + " AND " + queryPartProximity;
		}




		if(searchString !=null)
		{
			String queryPartSearch = Item.TABLE_NAME + "." + Item.ITEM_NAME +" ilike '%" + searchString + "%'";

			queryJoin = queryJoin + " AND " + queryPartSearch;
		}



		// all the non-aggregate columns which are present in select must be present in group by also.
		queryJoin = queryJoin

				+ " group by "
				+ Item.TABLE_NAME + "." + Item.ITEM_ID ;

				/*+ ","
				+ Item.TABLE_NAME + "." + Item.ITEM_CATEGORY_ID + ","
				+ Item.TABLE_NAME + "." + Item.ITEM_IMAGE_URL + ","
				+ Item.TABLE_NAME + "." + Item.ITEM_NAME + ","
				+ Item.TABLE_NAME + "." + Item.ITEM_DESC*/


		// + ShopItemContract.TABLE_NAME + "." + ShopItemContract.ITEM_ID
		//



		queryCount = queryJoin;



		if(sortBy!=null)
		{
			if(!sortBy.equals(""))
			{
				String queryPartSortBy = " ORDER BY " + sortBy;

//				queryNormal = queryNormal + queryPartSortBy;
				queryJoin = queryJoin + queryPartSortBy;
			}
		}



		if(limit != null)
		{

			String queryPartLimitOffset = "";

			if(offset>0)
			{
				queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + offset;

			}else
			{
				queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + 0;
			}


//			queryNormal = queryNormal + queryPartLimitOffset;
			queryJoin = queryJoin + queryPartLimitOffset;
		}



		queryCount = "SELECT COUNT(*) as item_count FROM (" + queryCount + ") AS temp";




		ItemEndPoint endPoint = new ItemEndPoint();
		
		ArrayList<Item> itemList = new ArrayList<Item>();
		
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;

		PreparedStatement statementCount = null;
		ResultSet resultSetCount = null;


		try {
			
			connection = dataSource.getConnection();

			int i = 0;


			if(!getOnlyMetadata) {


				statement = connection.prepareStatement(queryJoin);

				rs = statement.executeQuery();

				while (rs.next()) {
					Item item = new Item();

					item.setItemID(rs.getInt(Item.ITEM_ID));
					item.setItemName(rs.getString(Item.ITEM_NAME));
					item.setItemDescription(rs.getString(Item.ITEM_DESC));

					item.setItemImageURL(rs.getString(Item.ITEM_IMAGE_URL));
					item.setItemCategoryID(rs.getInt(Item.ITEM_CATEGORY_ID));

					item.setItemDescriptionLong(rs.getString(Item.ITEM_DESCRIPTION_LONG));
//				item.setDateTimeCreated(rs.getTimestamp(Item.DATE_TIME_CREATED));
					item.setQuantityUnit(rs.getString(Item.QUANTITY_UNIT));

					item.setListPrice(rs.getFloat(Item.LIST_PRICE));
					item.setBarcode(rs.getString(Item.BARCODE));
					item.setBarcodeFormat(rs.getString(Item.BARCODE_FORMAT));
					item.setImageCopyrights(rs.getString(Item.IMAGE_COPYRIGHTS));


//				if(isJoinQuery)
//				{
					ItemStats itemStats = new ItemStats();
					itemStats.setMax_price(rs.getDouble("max_price"));
					itemStats.setMin_price(rs.getDouble("min_price"));
					itemStats.setAvg_price(rs.getDouble("avg_price"));
					itemStats.setShopCount(rs.getInt("shop_count"));
					item.setItemStats(itemStats);

					item.setRt_rating_avg(rs.getFloat("avg_rating"));
					item.setRt_rating_count(rs.getFloat("rating_count"));

//				}

					itemList.add(item);
				}

				endPoint.setResults(itemList);
			}



			if(getRowCount)
			{
				statementCount = connection.prepareStatement(queryCount);

				i = 0;



				resultSetCount = statementCount.executeQuery();

				while(resultSetCount.next())
				{

						endPoint.setItemCount(resultSetCount.getInt("item_count"));
//					System.out.println("Item Count ItemDAO : " + String.valueOf(endPoint.getItemCount()));

					}
			}


//			System.out.println("Item By CategoryID " + itemList.size());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{




			try {
				if(resultSetCount!=null)
				{resultSetCount.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {

				if(statementCount!=null)
				{statementCount.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}



			
			try {
					if(rs!=null)
					{rs.close();}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			try {
			
				if(statement!=null)
				{statement.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				
				if(connection!=null)
				{connection.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return endPoint;
	}











//	public ItemEndPoint getEndPointMetadata(
//			Integer itemCategoryID, Integer shopID,
//			Double latCenter, Double lonCenter,
//			Double deliveryRangeMin,Double deliveryRangeMax,
//			Double proximity, String searchString)
//	{
//
//
//		String query = "";
//
////		String queryNormal = "SELECT "
////				+ "count( DISTINCT " + Item.ITEM_ID + ") as item_count" + ""
////				+ " FROM " + Item.TABLE_NAME;
//
//
//		String queryJoin = "SELECT "
//
//				+ "count( DISTINCT " + Item.TABLE_NAME + "." + Item.ITEM_ID + ") as item_count" + ""
//
//				+ " FROM "
//				+ Shop.TABLE_NAME  + "," + ShopItem.TABLE_NAME + ","
//				+ Item.TABLE_NAME + "," + ItemCategory.TABLE_NAME
//				+ " WHERE " + Shop.TABLE_NAME + "." + Shop.SHOP_ID + "=" + ShopItem.TABLE_NAME + "." + ShopItem.SHOP_ID
//				+ " AND " + ShopItem.TABLE_NAME + "." + ShopItem.ITEM_ID + "=" + Item.TABLE_NAME + "." + Item.ITEM_ID
//				+ " AND " + Item.TABLE_NAME + "." + Item.ITEM_CATEGORY_ID + "=" + ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_ID
//				+ " AND " + Shop.TABLE_NAME + "." + Shop.IS_OPEN + " = TRUE "
//				+ " AND " + Shop.TABLE_NAME + "." + Shop.SHOP_ENABLED + " = TRUE "
//				+ " AND " + ShopItem.TABLE_NAME + "." + ShopItem.ITEM_PRICE + " > 0 ";
//
//
//
//		if(shopID != null)
//		{
//			queryJoin = queryJoin + " AND "
//					+ Shop.TABLE_NAME
//					+ "."
//					+ Shop.SHOP_ID + " = " + shopID;
//
//		}
//
//		if(itemCategoryID != null)
//		{
//			queryJoin = queryJoin + " AND "
//					+ Item.TABLE_NAME
//					+ "."
//					+ Item.ITEM_CATEGORY_ID + " = " + itemCategoryID;
//
//
//
//			//" WHERE ITEM_CATEGORY_ID = " + itemCategoryID
//
////			queryNormal = queryNormal + " WHERE "
////					+ Item.TABLE_NAME
////					+ "."
////					+ Item.ITEM_CATEGORY_ID + " = " + itemCategoryID;
//
//		}
//
//
//
//		// Applying filters
//
//		if(latCenter !=null && lonCenter !=null)
//		{
//			// Applying shop visibility filter. Gives all the shops which are visible at the given location defined by
//			// latCenter and lonCenter. For more information see the API documentation.
//
//
//			/*
//			*
//			* Caution : Do not delete the commented out bounding code. It might / may be useful in future.
//			*
//			* */
//
////			String queryPartLatLonBounding = "";
////
////			queryPartLatLonBounding = queryPartLatLonBounding
////					+ " AND "
////					+ ShopContract.TABLE_NAME
////					+ "."
////					+ ShopContract.LAT_MAX
////					+ " >= " + latCenter
////					+ " AND "
////					+ ShopContract.TABLE_NAME
////					+ "."
////					+ ShopContract.LAT_MIN
////					+ " <= " + latCenter
////					+ " AND "
////					+ ShopContract.TABLE_NAME
////					+ "."
////					+ ShopContract.LON_MAX
////					+ " >= " + lonCenter
////					+ " AND "
////					+ ShopContract.TABLE_NAME
////					+ "."
////					+ ShopContract.LON_MIN
////					+ " <= " + lonCenter;
//
//			//+ " BETWEEN " + latmax + " AND " + latmin;
//
//			String queryPartlatLonCenter = "";
//
//			queryPartlatLonCenter = queryPartlatLonCenter + " 6371.01 * acos( cos( radians("
//					+ latCenter + ")) * cos( radians( lat_center) ) * cos(radians( lon_center ) - radians("
//					+ lonCenter + "))"
//					+ " + sin( radians(" + latCenter + ")) * sin(radians(lat_center))) <= delivery_range ";
//
//
//
//			queryJoin = queryJoin + " AND " + queryPartlatLonCenter;
//
//
//		}
//
//
//
//
//		if(deliveryRangeMin !=null  ||deliveryRangeMax !=null){
//
//			// apply delivery range filter
//
//			queryJoin = queryJoin
//					+ " AND "
//					+ Shop.TABLE_NAME
//					+ "."
//					+ Shop.DELIVERY_RANGE
//					+ " BETWEEN " + deliveryRangeMin + " AND " + deliveryRangeMax;
//
//			//+ " <= " + deliveryRange;
//		}
//
//
//
//
//		// proximity cannot be greater than the delivery range if the delivery range is supplied. Otherwise this condition is
//		// not required.
//		if(proximity !=null)
//		{
//
//
//
//			//proximity !=null && (deliveryRangeMax == null || (deliveryRangeMax !=null && proximity <= deliveryRangeMax))
//
//
//
//			/*// generate bounding coordinates for the shop based on the required location and its
//			center = GeoLocation.fromDegrees(latCenter,lonCenter);
//			minMaxArray = center.boundingCoordinates(proximity,6371.01);
//
//			pointOne = minMaxArray[0];
//			pointTwo = minMaxArray[1];
//
//			double latMin = pointOne.getLatitudeInDegrees();
//			double lonMin = pointOne.getLongitudeInDegrees();
//			double latMax = pointTwo.getLatitudeInDegrees();
//			double lonMax = pointTwo.getLongitudeInDegrees();
//
//
//			// Make sure that shop center lies between the bounding coordinates generated by proximity bounding box
//
//			String queryPartProximityBounding = "";
//
//			queryPartProximityBounding = queryPartProximityBounding
//
//					+ " AND "
//					+ ShopContract.TABLE_NAME
//					+ "."
//					+ ShopContract.LAT_CENTER
//					+ " < " + latMax
//
//					+ " AND "
//					+ ShopContract.TABLE_NAME
//					+ "."
//					+ ShopContract.LAT_CENTER
//					+ " > " + latMin
//
//					+ " AND "
//					+ ShopContract.TABLE_NAME
//					+ "."
//					+ ShopContract.LON_CENTER
//					+ " < " + lonMax
//
//					+ " AND "
//					+ ShopContract.TABLE_NAME
//					+ "."
//					+ ShopContract.LON_CENTER
//					+ " > " + lonMin;
//*/
//
//
//			String queryPartProximity = "";
//
//			// filter using Haversine formula using SQL math functions
//			queryPartProximity = queryPartProximity
//					+ " (6371.01 * acos(cos( radians("
//					+ latCenter
//					+ ")) * cos( radians("
//					+ Shop.LAT_CENTER
//					+ " )) * cos(radians( "
//					+ Shop.LON_CENTER
//					+ ") - radians("
//					+ lonCenter
//					+ "))"
//					+ " + sin( radians("
//					+ latCenter
//					+ ")) * sin(radians("
//					+ Shop.LAT_CENTER
//					+ ")))) <= "
//					+ proximity ;
//
//
//			queryJoin = queryJoin + " AND " + queryPartProximity;
//
//
//		}
//
//
//
//		if(searchString !=null)
//		{
//			String queryPartSearch = Item.TABLE_NAME + "." + Item.ITEM_NAME +" ilike '%" + searchString + "%'";
//
//
//
//			/*if(isFirst)
//			{
////				queryJoin = queryJoin + " WHERE " + queryPartSearch;
//
//				queryNormal = queryNormal + " WHERE " + queryPartSearch;
//
//				isFirst = false;
//			}
//			else
//			{
//				queryNormal = queryNormal + " AND " + queryPartSearch;
//			}*/
//
//			queryJoin = queryJoin + " AND " + queryPartSearch;
//		}
//
//
//
//		/*
//
//		Applying filters Ends
//
//		 */
//
//
//
//
//
////		boolean isJoinQuery = false;
//
////		if(shopID != null || (latCenter!= null && lonCenter!=null))
////		{
////			query = queryJoin;
////			isJoinQuery = true;
////
////		}else
////		{
////			query = queryNormal;
////		}
//
//		query = queryJoin;
//
//
//
////		ArrayList<Item> itemList = new ArrayList<Item>();
//
//
//		ItemEndPoint endPoint = new ItemEndPoint();
//
//
//		Connection connection = null;
//		Statement statement = null;
//		ResultSet rs = null;
//
//		try {
//
//			connection = dataSource.getConnection();
//			statement = connection.createStatement();
//			rs = statement.executeQuery(query);
//
//			while(rs.next())
//			{
//				endPoint.setItemCount(rs.getInt("item_count"));
//			}
//
////			System.out.println("Item Count : " + endPoint.getItemCount());
//
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//
//		finally
//
//		{
//
//			try {
//				if(rs!=null)
//				{rs.close();}
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			try {
//
//				if(statement!=null)
//				{statement.close();}
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			try {
//
//				if(connection!=null)
//				{connection.close();}
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//
//		return endPoint;
//	}





	public Item getItemImageURL(
			Integer itemID
	) {


		boolean isfirst = true;

		String query = "";

//		String queryNormal = "SELECT * FROM " + Item.TABLE_NAME;


		String queryJoin = "SELECT DISTINCT "
//				+ Item.TABLE_NAME + "." + Item.ITEM_CATEGORY_ID + ","
//				+ Item.TABLE_NAME + "." + Item.ITEM_ID + ","
				+ Item.TABLE_NAME + "." + Item.ITEM_IMAGE_URL + ""
//				+ Item.TABLE_NAME + "." + Item.ITEM_NAME + ","
//				+ Item.TABLE_NAME + "." + Item.ITEM_DESC + ","

//				+ Item.TABLE_NAME + "." + Item.QUANTITY_UNIT + ","
//				+ Item.TABLE_NAME + "." + Item.DATE_TIME_CREATED + ","
//				+ Item.TABLE_NAME + "." + Item.ITEM_DESCRIPTION_LONG + ""

				+ " FROM " + Item.TABLE_NAME
				+ " WHERE " + Item.ITEM_ID + " = " + itemID;





//
//		if(itemID != null)
//		{
//			queryJoin = queryJoin + " WHERE "
//					+ Item.TABLE_NAME
//					+ "."
//					+ Item.ITEM_ID + " = " + itemID;
//
//
//			isfirst = false;
//		}






		/*

		Applying filters Ends

		 */


//		boolean isJoinQuery = false;

		query = queryJoin;
//		isJoinQuery = true;

//		ArrayList<Item> itemList = new ArrayList<Item>();
		Item item = null;
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;

		try {

			connection = dataSource.getConnection();
			statement = connection.createStatement();

//			System.out.println(query);
			rs = statement.executeQuery(queryJoin);

			while(rs.next())
			{
				item = new Item();

				item.setItemID(itemID);
//				item.setItemName(rs.getString(Item.ITEM_NAME));
//				item.setItemDescription(rs.getString(Item.ITEM_DESC));

				item.setItemImageURL(rs.getString(Item.ITEM_IMAGE_URL));
//				item.setItemCategoryID(rs.getInt(Item.ITEM_CATEGORY_ID));

//				item.setItemDescriptionLong(rs.getString(Item.ITEM_DESCRIPTION_LONG));
//				item.setDateTimeCreated(rs.getTimestamp(Item.DATE_TIME_CREATED));
//				item.setQuantityUnit(rs.getString(Item.QUANTITY_UNIT));
			}



//			System.out.println("Item By CategoryID " + itemList.size());

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		finally

		{

			try {
				if(rs!=null)
				{rs.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {

				if(statement!=null)
				{statement.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {

				if(connection!=null)
				{connection.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return item;
	}


}
