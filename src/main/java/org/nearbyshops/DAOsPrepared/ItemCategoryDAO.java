package org.nearbyshops.DAOsPrepared;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Item;
import org.nearbyshops.Model.ItemCategory;
import org.nearbyshops.Model.Shop;
import org.nearbyshops.Model.ShopItem;
import org.nearbyshops.ModelEndpoint.ItemCategoryEndPoint;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ItemCategoryDAO {

	private HikariDataSource dataSource = Globals.getDataSource();

//	private GeoLocation center;

//	private GeoLocation[] minMaxArray;
//	private GeoLocation pointOne;
//	private GeoLocation pointTwo;


	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();	
	}








	public int saveItemCategory(ItemCategory itemCategory, boolean getRowCount)
	{

		int idOfInsertedRow = 0;
		int rowCount = 0;

		Connection connection = null;
		PreparedStatement statement = null;


		String insertItemCategory = "";
//		System.out.println("isLeaf : " + itemCategory.getIsLeafNode());


		
			insertItemCategory = "INSERT INTO "
					+ ItemCategory.TABLE_NAME
					+ "("  
					+ ItemCategory.ITEM_CATEGORY_NAME + ","
					+ ItemCategory.ITEM_CATEGORY_DESCRIPTION + ","
					+ ItemCategory.PARENT_CATEGORY_ID + ","

					+ ItemCategory.IMAGE_PATH + ","
					+ ItemCategory.CATEGORY_ORDER + ","

					+ ItemCategory.ITEM_CATEGORY_DESCRIPTION_SHORT + ","
					+ ItemCategory.IS_ABSTRACT + ","
					+ ItemCategory.IS_LEAF_NODE
					+ ") VALUES(?,?,? ,?,? ,?,?,?)";


		try {
			
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(insertItemCategory,PreparedStatement.RETURN_GENERATED_KEYS);

			int i = 0;
			statement.setString(++i,itemCategory.getCategoryName());
			statement.setString(++i,itemCategory.getCategoryDescription());
			statement.setObject(++i,itemCategory.getParentCategoryID());

			statement.setString(++i,itemCategory.getImagePath());
			statement.setObject(++i,itemCategory.getCategoryOrder());

			statement.setString(++i,itemCategory.getDescriptionShort());
			statement.setBoolean(++i,itemCategory.getisAbstractNode());
			statement.setBoolean(++i,itemCategory.getIsLeafNode());


			rowCount = statement.executeUpdate();
			
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


		if(getRowCount)
		{
			return rowCount;
		}
		else
		{
			return idOfInsertedRow;
		}

	}






	public int changeParent(ItemCategory itemCategory)
	{
		int rowCount = 0;


		if(itemCategory.getParentCategoryID()!=null)
		{
			if(itemCategory.getParentCategoryID() == itemCategory.getItemCategoryID())
			{
				// an Item category cannot have itself as its own parent so abort this operation and return
				return 0;
			}

			// a hack for android app. The android parcelable does not support Non primitives.
			// So cant have null for the ID.
			// The value of -1 represents the NULL when the request coming from an android app.
			// So when you see a -1 for parent set it to null which really means a detached item category,
			// an item category not having any parent

			if(itemCategory.getParentCategoryID()==-1)
			{
				itemCategory.setParentCategoryID(null);
			}
		}


//		if(itemCategory.getParentCategoryID()!=null)
//		{
//
//		}

		String updateStatement = "";

		updateStatement = "UPDATE "

				+ ItemCategory.TABLE_NAME
				+ " SET "
//				+ " " + ItemCategory.ITEM_CATEGORY_NAME + " = ?,"
//				+ " " + ItemCategory.ITEM_CATEGORY_DESCRIPTION + " = ?,"
//				+ " " + ItemCategory.IMAGE_PATH + " = ?,"

				+ " " + ItemCategory.PARENT_CATEGORY_ID + " = ?"
//				+ " " + ItemCategory.ITEM_CATEGORY_DESCRIPTION_SHORT + " = ?,"
//				+ " " + ItemCategory.IS_ABSTRACT + " = ?,"

//				+ " " + ItemCategory.IS_LEAF_NODE + " = ?"

				+ " WHERE "
				+  ItemCategory.ITEM_CATEGORY_ID + "= ?";


		Connection connection = null;
//		Statement stmt = null;
		PreparedStatement statement = null;


		try {

			connection = dataSource.getConnection();
			statement = connection.prepareStatement(updateStatement);

//			statement.setString(1,itemCategory.getCategoryName());
//			statement.setString(2,itemCategory.getCategoryDescription());
//			statement.setString(3,itemCategory.getImagePath());

			statement.setObject(1,itemCategory.getParentCategoryID());
//			statement.setString(5,itemCategory.getDescriptionShort());
//			statement.setBoolean(6,itemCategory.getisAbstractNode());

//			statement.setBoolean(7,itemCategory.getIsLeafNode());
			statement.setInt(2,itemCategory.getItemCategoryID());

			rowCount = statement.executeUpdate();



//			System.out.println("Total rows updated: " + rowCount);

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

		return rowCount;
	}






	public int changeParentBulk(List<ItemCategory> itemCategoryList)
	{
		int rowCount = 0;

		String updateStatement = "";

		updateStatement = "UPDATE "

				+ ItemCategory.TABLE_NAME
				+ " SET "
//				+ " " + ItemCategory.ITEM_CATEGORY_NAME + " = ?,"
//				+ " " + ItemCategory.ITEM_CATEGORY_DESCRIPTION + " = ?,"
//				+ " " + ItemCategory.IMAGE_PATH + " = ?,"

				+ " " + ItemCategory.PARENT_CATEGORY_ID + " = ?"
//				+ " " + ItemCategory.ITEM_CATEGORY_DESCRIPTION_SHORT + " = ?,"
//				+ " " + ItemCategory.IS_ABSTRACT + " = ?,"

//				+ " " + ItemCategory.IS_LEAF_NODE + " = ?"

				+ " WHERE "
				+  ItemCategory.ITEM_CATEGORY_ID + "= ?";


		Connection connection = null;
//		Statement stmt = null;
		PreparedStatement statement = null;


		try {

			connection = dataSource.getConnection();
			statement = connection.prepareStatement(updateStatement);

//			statement.setString(1,itemCategory.getCategoryName());
//			statement.setString(2,itemCategory.getCategoryDescription());
//			statement.setString(3,itemCategory.getImagePath());


//			statement.setString(5,itemCategory.getDescriptionShort());
//			statement.setBoolean(6,itemCategory.getisAbstractNode());

//			statement.setBoolean(7,itemCategory.getIsLeafNode());

			for(ItemCategory itemCategory: itemCategoryList)
			{

				if(itemCategory.getParentCategoryID()!=null)
				{
					if(itemCategory.getParentCategoryID() == itemCategory.getItemCategoryID())
					{
						// an Item category cannot have itself as
						// its own parent so abort this operation and return
						continue;
					}

					if(itemCategory.getParentCategoryID()==-1)
					{
						itemCategory.setParentCategoryID(null);
					}

				}


				statement.setObject(1,itemCategory.getParentCategoryID());
				statement.setInt(2,itemCategory.getItemCategoryID());
				statement.addBatch();
			}


			int[] rowSumArray = statement.executeBatch();

			for(int rows : rowSumArray)
			{
				rowCount = rowCount + rows;
			}

//			System.out.println("Total rows updated: CHANGE PARENT BULK " + rowCount);

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

		return rowCount;
	}





	public int updateItemCategory(ItemCategory itemCategory)
	{
		int rowCount = 0;


		if(itemCategory.getParentCategoryID()!=null)
		{
			if(itemCategory.getParentCategoryID() == itemCategory.getItemCategoryID())
			{
				// an Item category cannot have itself as its own parent so abort this operation and return
				return 0;
			}


			// a hack for android app. The android parcelable does not support Non primitives.
			// So cant have null for the ID.
			// The value of -1 represents the NULL when the request coming from an android app.
			// So when you see a -1 for parent set it to null which really means a detached item category,
			// an item category not having any parent

			if(itemCategory.getParentCategoryID()==-1)
			{
				itemCategory.setParentCategoryID(null);
			}
		}


//		if(itemCategory.getParentCategoryID()!=null)
//		{
//		}
		
		String updateStatement = "";

		updateStatement = "UPDATE "

				+ ItemCategory.TABLE_NAME
				+ " SET "
				+ " " + ItemCategory.ITEM_CATEGORY_NAME + " = ?,"
				+ " " + ItemCategory.ITEM_CATEGORY_DESCRIPTION + " = ?,"
				+ " " + ItemCategory.IMAGE_PATH + " = ?,"
				+ " " + ItemCategory.CATEGORY_ORDER + " = ?,"

				+ " " + ItemCategory.PARENT_CATEGORY_ID + " = ?,"
				+ " " + ItemCategory.ITEM_CATEGORY_DESCRIPTION_SHORT + " = ?,"
				+ " " + ItemCategory.IS_ABSTRACT + " = ?,"

				+ " " + ItemCategory.IS_LEAF_NODE + " = ?"

				+ " WHERE "
				+  ItemCategory.ITEM_CATEGORY_ID + "= ?";


		Connection connection = null;
//		Statement stmt = null;
		PreparedStatement statement = null;


		try {
			
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(updateStatement);

			int i = 0;
			statement.setString(++i,itemCategory.getCategoryName());
			statement.setString(++i,itemCategory.getCategoryDescription());
			statement.setString(++i,itemCategory.getImagePath());
			statement.setObject(++i,itemCategory.getCategoryOrder());

			statement.setObject(++i,itemCategory.getParentCategoryID());
			statement.setString(++i,itemCategory.getDescriptionShort());
			statement.setBoolean(++i,itemCategory.getisAbstractNode());

			statement.setBoolean(++i,itemCategory.getIsLeafNode());
			statement.setInt(++i,itemCategory.getItemCategoryID());

			rowCount = statement.executeUpdate();


//			System.out.println("Total rows updated: " + rowCount);
			
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
		
		return rowCount;
	}


	

	public int deleteItemCategory(int itemCategoryID)
	{
		
		String deleteStatement = "DELETE FROM " + ItemCategory.TABLE_NAME + " WHERE "
								+ ItemCategory.ITEM_CATEGORY_ID + " = ?";

		
		Connection connection= null;
		PreparedStatement statement = null;

		int rowCountDeleted = 0;
		try {
			
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(deleteStatement);
			statement.setInt(1,itemCategoryID);

			rowCountDeleted = statement.executeUpdate();
//			System.out.println("row Count Deleted: " + rowCountDeleted);


			connection.close();
			
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


	public ItemCategoryEndPoint getItemCategoriesSimplePrepared(
			Integer parentID,
			Boolean parentIsNull,
			String searchString,
			String sortBy,
			Integer limit, Integer offset)
	{

		boolean queryNormalFirst = true;


		String queryNormal = "SELECT "

				+ "count(" + ItemCategory.ITEM_CATEGORY_ID + ") over() AS full_count " + ","
				+ ItemCategory.ITEM_CATEGORY_ID + ","
				+ ItemCategory.PARENT_CATEGORY_ID + ","
				+ ItemCategory.IMAGE_PATH + ","
				+ ItemCategory.CATEGORY_ORDER + ","

				+ ItemCategory.ITEM_CATEGORY_DESCRIPTION + ","
				+ ItemCategory.ITEM_CATEGORY_DESCRIPTION_SHORT + ","
				+ ItemCategory.IS_ABSTRACT + ","

				+ ItemCategory.IS_LEAF_NODE + ","
				+ ItemCategory.ITEM_CATEGORY_NAME +

				" FROM " + ItemCategory.TABLE_NAME;






		if(parentID!=null)
		{
			queryNormal = queryNormal + " WHERE "
					+ ItemCategory.PARENT_CATEGORY_ID + " = ?";

			queryNormalFirst = false;
		}



		if(parentIsNull!=null&& parentIsNull)
		{

			String queryNormalPart = ItemCategory.PARENT_CATEGORY_ID + " IS NULL";

			if(queryNormalFirst)
			{
				queryNormal = queryNormal + " WHERE " + queryNormalPart;
				queryNormalFirst = false;

			}else
			{
				queryNormal = queryNormal + " AND " + queryNormalPart;

			}
		}



		if(searchString !=null)
		{
			String queryPartSearch = " ( " + ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_DESCRIPTION_SHORT +" ilike ? "
					+ " or " + ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_DESCRIPTION + " ilike ?"
					+ " or " + ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_NAME + " ilike ? ) ";


//			String queryPartSearch = " ( " +ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_DESCRIPTION_SHORT +" ilike '%" + searchString + "%'"
//					+ " or " + ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_DESCRIPTION + " ilike '%" + searchString + "%'"
//					+ " or " + ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_NAME + " ilike '%" + searchString + "%'" + ") ";


			if(queryNormalFirst)
			{
				queryNormal = queryNormal + " WHERE " + queryPartSearch;
				queryNormalFirst = false;
			}
			else
			{
				queryNormal = queryNormal + " AND " + queryPartSearch;
			}
		}





		if(sortBy!=null)
		{
			if(!sortBy.equals(""))
			{
				String queryPartSortBy = " ORDER BY " + sortBy;

				queryNormal = queryNormal + queryPartSortBy;
			}
		}



		if(limit !=null)
		{

			String queryPartLimitOffset = "";

			queryPartLimitOffset = " LIMIT ? OFFSET ? ";


//			if(offset!=null)
//			{
//				queryPartLimitOffset = " LIMIT ?  OFFSET ? ";
//
//			}else
//			{
//				queryPartLimitOffset = " LIMIT ? OFFSET ? ";
//			}


			queryNormal = queryNormal + queryPartLimitOffset;
		}




		ArrayList<ItemCategory> itemCategoryList = new ArrayList<ItemCategory>();

		ItemCategoryEndPoint endPoint = new ItemCategoryEndPoint();


		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;

		boolean rootRemoved = false;

		try {

			connection = dataSource.getConnection();

			statement = connection.prepareStatement(queryNormal);





			// set placeholders
			int i = 0;
			if(parentID!=null)
			{
				statement.setObject(++i,parentID);
			}

			if(searchString!=null)
			{
				statement.setString(++i,"%" + searchString + "%");
				statement.setString(++i,"%" + searchString + "%");
				statement.setString(++i,"%" + searchString + "%");
			}


//			if(sortBy!=null)
//			{
//				statement.setString(++i,sortBy);
//			}

			if(limit!=null)
			{
				if(offset==null)
				{
					offset = 0;
				}

				statement.setObject(++i,limit);
				statement.setObject(++i,offset);
			}



			rs = statement.executeQuery();

			while(rs.next())
			{
				ItemCategory itemCategory = new ItemCategory();

				itemCategory.setItemCategoryID(rs.getInt(ItemCategory.ITEM_CATEGORY_ID));

				if(itemCategory.getItemCategoryID()==1)
				{
					rootRemoved=true;
					continue;
				}


				itemCategory.setParentCategoryID(rs.getInt(ItemCategory.PARENT_CATEGORY_ID));
				itemCategory.setIsLeafNode(rs.getBoolean(ItemCategory.IS_LEAF_NODE));
				itemCategory.setImagePath(rs.getString(ItemCategory.IMAGE_PATH));
				itemCategory.setCategoryOrder(rs.getInt(ItemCategory.CATEGORY_ORDER));

				itemCategory.setCategoryName(rs.getString(ItemCategory.ITEM_CATEGORY_NAME));

				itemCategory.setisAbstractNode(rs.getBoolean(ItemCategory.IS_ABSTRACT));
				itemCategory.setDescriptionShort(rs.getString(ItemCategory.ITEM_CATEGORY_DESCRIPTION_SHORT));

				itemCategory.setCategoryDescription(rs.getString(ItemCategory.ITEM_CATEGORY_DESCRIPTION));

				endPoint.setItemCount(rs.getInt("full_count"));


				itemCategoryList.add(itemCategory);
			}



			// cast to the pg extension interface
			org.postgresql.PGStatement pgstmt = statement.unwrap(org.postgresql.PGStatement.class);
			boolean usingServerPrepare = pgstmt.isUseServerPrepare();
//			System.out.println("Used server side: " + usingServerPrepare + " | Prepare threshold : " + pgstmt.getPrepareThreshold());


			if(rootRemoved && endPoint.getItemCount()!=0)
			{
				endPoint.setItemCount(endPoint.getItemCount()-1);
			}

//			System.out.println("Total itemCategories queried " + itemCategoryList.size());


			endPoint.setResults(itemCategoryList);

		}


		catch (SQLException e) {
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


		return endPoint;
	}





	public ItemCategoryEndPoint getItemCategoriesSimple(
			Integer parentID,
			Boolean parentIsNull,
			String searchString,
			String sortBy,
			Integer limit, Integer offset)
	{

		boolean queryNormalFirst = true;


		String queryNormal = "SELECT "

				+ "count(" + ItemCategory.ITEM_CATEGORY_ID + ") over() AS full_count " + ","
				+ ItemCategory.ITEM_CATEGORY_ID + ","
				+ ItemCategory.PARENT_CATEGORY_ID + ","
				+ ItemCategory.IMAGE_PATH + ","
				+ ItemCategory.CATEGORY_ORDER + ","

				+ ItemCategory.ITEM_CATEGORY_DESCRIPTION + ","

				+ ItemCategory.ITEM_CATEGORY_DESCRIPTION_SHORT + ","
				+ ItemCategory.IS_ABSTRACT + ","

				+ ItemCategory.IS_LEAF_NODE + ","
				+ ItemCategory.ITEM_CATEGORY_NAME +

				" FROM " + ItemCategory.TABLE_NAME;






		if(parentID!=null)
		{
			queryNormal = queryNormal + " WHERE "
					+ ItemCategory.PARENT_CATEGORY_ID + " = " + parentID ;

			queryNormalFirst = false;
		}



		if(parentIsNull!=null&& parentIsNull)
		{

			String queryNormalPart = ItemCategory.PARENT_CATEGORY_ID + " IS NULL";

			if(queryNormalFirst)
			{
				queryNormal = queryNormal + " WHERE " + queryNormalPart;
				queryNormalFirst = false;

			}else
			{
				queryNormal = queryNormal + " AND " + queryNormalPart;

			}
		}



		if(searchString !=null)
		{
			String queryPartSearch = " ( " + ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_DESCRIPTION_SHORT +" ilike '%" + searchString + "%'"
					+ " or " + ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_DESCRIPTION + " ilike '%" + searchString + "%'"
					+ " or " + ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_NAME + " ilike '%" + searchString + "%'" + ") ";


			if(queryNormalFirst)
			{
				queryNormal = queryNormal + " WHERE " + queryPartSearch;
				queryNormalFirst = false;
			}
			else
			{
				queryNormal = queryNormal + " AND " + queryPartSearch;
			}
		}





		if(sortBy!=null)
		{
			if(!sortBy.equals(""))
			{
				String queryPartSortBy = " ORDER BY " + sortBy;

				queryNormal = queryNormal + queryPartSortBy;
			}
		}



		if(limit !=null)
		{

			String queryPartLimitOffset = "";

			if(offset!=null)
			{
				queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + offset;

			}else
			{
				queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + 0;
			}


			queryNormal = queryNormal + queryPartLimitOffset;
		}




		ArrayList<ItemCategory> itemCategoryList = new ArrayList<ItemCategory>();

		ItemCategoryEndPoint endPoint = new ItemCategoryEndPoint();


		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;

		boolean rootRemoved = false;

		try {

			connection = dataSource.getConnection();

			statement = connection.createStatement();

			rs = statement.executeQuery(queryNormal);

			while(rs.next())
			{
				ItemCategory itemCategory = new ItemCategory();

				itemCategory.setItemCategoryID(rs.getInt(ItemCategory.ITEM_CATEGORY_ID));

				if(itemCategory.getItemCategoryID()==1)
				{
					rootRemoved=true;
					continue;
				}

				itemCategory.setParentCategoryID(rs.getInt(ItemCategory.PARENT_CATEGORY_ID));
				itemCategory.setIsLeafNode(rs.getBoolean(ItemCategory.IS_LEAF_NODE));
				itemCategory.setImagePath(rs.getString(ItemCategory.IMAGE_PATH));
				itemCategory.setCategoryOrder(rs.getInt(ItemCategory.CATEGORY_ORDER));

				itemCategory.setCategoryName(rs.getString(ItemCategory.ITEM_CATEGORY_NAME));
				itemCategory.setisAbstractNode(rs.getBoolean(ItemCategory.IS_ABSTRACT));
				itemCategory.setDescriptionShort(rs.getString(ItemCategory.ITEM_CATEGORY_DESCRIPTION_SHORT));

				itemCategory.setCategoryDescription(rs.getString(ItemCategory.ITEM_CATEGORY_DESCRIPTION));

				endPoint.setItemCount(rs.getInt("full_count"));


				itemCategoryList.add(itemCategory);
			}



			if(rootRemoved && endPoint.getItemCount()!=0)
			{
				endPoint.setItemCount(endPoint.getItemCount()-1);
			}



//			if(parentIsNull!=null&& parentIsNull)
//			{
//				 exclude the root category
//				for(ItemCategory itemCategory : itemCategoryList)
//				{
//					if(itemCategory.getItemCategoryID()==1)
//					{
//						itemCategoryList.remove(itemCategory);
//
//						break;
//					}
//				}
//			}


//			conn.close();

//			System.out.println("Total itemCategories queried " + itemCategoryList.size());


			endPoint.setResults(itemCategoryList);

		}


		catch (SQLException e) {
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


		return endPoint;
	}




	
	
	public ArrayList<ItemCategory> getItemCategoriesJoinRecursive(
			Integer shopID, Integer parentID, Boolean parentIsNull,
			Double latCenter, Double lonCenter,
			Double deliveryRangeMin, Double deliveryRangeMax,
			Double proximity, Boolean shopEnabled,
			String searchString,
			String sortBy,
			Integer limit, Integer offset)
	{

		String query = "";
		boolean isFirst = true;


		// a recursive CTE (Common table Expression) query. This query is used for retrieving hierarchical / tree set data.
		
		String withRecursiveStart = "WITH RECURSIVE category_tree(" 
					+ ItemCategory.ITEM_CATEGORY_ID + ","
					+ ItemCategory.PARENT_CATEGORY_ID + ","
					+ ItemCategory.IMAGE_PATH + ","
					+ ItemCategory.CATEGORY_ORDER + ","

					+ ItemCategory.ITEM_CATEGORY_DESCRIPTION + ","
					+ ItemCategory.ITEM_CATEGORY_DESCRIPTION_SHORT + ","
					+ ItemCategory.IS_ABSTRACT + ","

					+ ItemCategory.IS_LEAF_NODE + ","
					+ ItemCategory.ITEM_CATEGORY_NAME
					+ ") AS (";
		
		
		String queryJoin = "SELECT DISTINCT " 
		
				+ ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_ID + ","
				+ ItemCategory.TABLE_NAME + "." + ItemCategory.PARENT_CATEGORY_ID + ","
				+ ItemCategory.TABLE_NAME + "." + ItemCategory.IMAGE_PATH + ","
				+ ItemCategory.TABLE_NAME + "." + ItemCategory.CATEGORY_ORDER + ","

				+ ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_DESCRIPTION + ","
				+ ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_DESCRIPTION_SHORT + ","
				+ ItemCategory.TABLE_NAME + "." + ItemCategory.IS_ABSTRACT + ","

				+ ItemCategory.TABLE_NAME + "." + ItemCategory.IS_LEAF_NODE + ","
				+ ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_NAME

				+ " FROM " + Shop.TABLE_NAME  + "," + ShopItem.TABLE_NAME + "," + Item.TABLE_NAME + "," + ItemCategory.TABLE_NAME
				+ " WHERE " + Shop.TABLE_NAME + "." + Shop.SHOP_ID + "=" + ShopItem.TABLE_NAME + "." + ShopItem.SHOP_ID
				+ " AND " + ShopItem.TABLE_NAME + "." + ShopItem.ITEM_ID + "=" + Item.TABLE_NAME + "." + Item.ITEM_ID
				+ " AND " + Item.TABLE_NAME + "." + Item.ITEM_CATEGORY_ID + "=" + ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_ID;


		if(shopEnabled!=null && shopEnabled)
		{
			queryJoin = queryJoin + " AND " + Shop.TABLE_NAME + "." + Shop.IS_OPEN + " = TRUE "
					+ " AND " + Shop.TABLE_NAME + "." + Shop.SHOP_ENABLED + " = TRUE "
					+ " AND " + ShopItem.TABLE_NAME + "." + ShopItem.ITEM_PRICE + " > 0 ";

//			+ " AND " + ShopItem.TABLE_NAME + "." + ShopItem.AVAILABLE_ITEM_QUANTITY + " > 0 "

		}

		
		

		if(shopID!=null)
		{
				queryJoin = queryJoin + " AND "
						+ Shop.TABLE_NAME
						+ "."
						+ Shop.SHOP_ID + " = " + shopID;
			
		}
		
		if(latCenter!=null && lonCenter!=null)
		{
			// Applying shop visibility filter. Gives all the shops which are visible at the given location defined by
			// latCenter and lonCenter. For more information see the API documentation.



			/*String queryPartlatLonCenterTwo = "";

			queryPartlatLonCenterTwo = queryPartlatLonCenterTwo
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



		if(deliveryRangeMax !=null && deliveryRangeMin != null)
		{

			// apply delivery range filter
			queryJoin = queryJoin
					+ " AND "
					+ Shop.TABLE_NAME
					+ "."
					+ Shop.DELIVERY_RANGE
					+ " BETWEEN " + deliveryRangeMin + " AND " + deliveryRangeMax;
		}


		// proximity cannot be greater than the delivery range if the delivery range is supplied. Otherwise this condition is
		// not required.
		if(proximity != null)
		{

			/*

			(proximity != null) &&
				(deliveryRangeMax == null || (deliveryRangeMax !=null && proximity <= deliveryRangeMax))

			 */


			// generate bounding coordinates for the shop based on the required location and its

//			center = GeoLocation.fromDegrees(latCenter,lonCenter);
//			minMaxArray = center.boundingCoordinates(proximity,6371.01);

//			pointOne = minMaxArray[0];
//			pointTwo = minMaxArray[1];

//			double latMin = pointOne.getLatitudeInDegrees();
//			double lonMin = pointOne.getLongitudeInDegrees();
//			double latMax = pointTwo.getLatitudeInDegrees();
//			double lonMax = pointTwo.getLongitudeInDegrees();


			// Make sure that shop center lies between the bounding coordinates generated by proximity bounding box

			String queryPartProximityHaversine = "";
//			String queryPartProximityBounding = "";


//			queryPartProximityBounding = queryPartProximityBounding

//					+ " AND "
//					+ Shop.TABLE_NAME
//					+ "."
//					+ Shop.LAT_CENTER
//					+ " < " + latMax
//
//					+ " AND "
//					+ Shop.TABLE_NAME
//					+ "."
//					+ Shop.LAT_CENTER
//					+ " > " + latMin
//
//					+ " AND "
//					+ Shop.TABLE_NAME
//					+ "."
//					+ Shop.LON_CENTER
//					+ " < " + lonMax
//
//					+ " AND "
//					+ Shop.TABLE_NAME
//					+ "."
//					+ Shop.LON_CENTER
//					+ " > " + lonMin;



			// filter using Haversine formula using SQL math functions
			queryPartProximityHaversine = queryPartProximityHaversine
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


			queryJoin = queryJoin + " AND " + queryPartProximityHaversine;

		}

		
		String union = " UNION ";
		
		String querySelect = " SELECT "
				
				+ "cat." + ItemCategory.ITEM_CATEGORY_ID + ","
				+ "cat." + ItemCategory.PARENT_CATEGORY_ID + ","
				+ "cat." + ItemCategory.IMAGE_PATH + ","
				+ "cat." + ItemCategory.CATEGORY_ORDER + ","

				+ "cat." + ItemCategory.ITEM_CATEGORY_DESCRIPTION + ","
				+ "cat." + ItemCategory.ITEM_CATEGORY_DESCRIPTION_SHORT + ","
				+ "cat." + ItemCategory.IS_ABSTRACT + ","

				+ "cat." + ItemCategory.IS_LEAF_NODE + ","
				+ "cat." + ItemCategory.ITEM_CATEGORY_NAME

				+ " FROM category_tree tempCat," + 	ItemCategory.TABLE_NAME + " cat"
				+ " WHERE cat." + ItemCategory.ITEM_CATEGORY_ID
				+ " = tempcat." + ItemCategory.PARENT_CATEGORY_ID
				+ " )";
		
		
		String queryLast = " SELECT "
				+ ItemCategory.ITEM_CATEGORY_ID + ","
				+ ItemCategory.PARENT_CATEGORY_ID + ","
				+ ItemCategory.IMAGE_PATH + ","
				+ ItemCategory.CATEGORY_ORDER + ","

				+ ItemCategory.ITEM_CATEGORY_DESCRIPTION + ","
				+ ItemCategory.ITEM_CATEGORY_DESCRIPTION_SHORT + ","
				+ ItemCategory.IS_ABSTRACT + ","

				+ ItemCategory.IS_LEAF_NODE + ","
				+ ItemCategory.ITEM_CATEGORY_NAME
				+ " FROM category_tree";


		
		if(parentID!=null)
		{
			queryLast = queryLast + " WHERE " 
					+ ItemCategory.PARENT_CATEGORY_ID
					+ "=" + parentID ;

			isFirst = false;
		}



		if(searchString !=null)
		{
			String queryPartSearch = " ( " + ItemCategory.ITEM_CATEGORY_DESCRIPTION_SHORT +" ilike " + "'%" + searchString + "%'"
					+ " or " + ItemCategory.ITEM_CATEGORY_DESCRIPTION + " ilike " + "'%"  + searchString + "%'"
					+ " or " + ItemCategory.ITEM_CATEGORY_NAME + " ilike " + "'%"+  searchString  + "%'" + " ) ";


			if(isFirst)
			{
				queryLast = queryLast + " WHERE " + queryPartSearch;

					isFirst = false;
			}
			else
			{
				queryLast = queryLast + " AND " + queryPartSearch;
			}
		}


		
		String queryRecursive = withRecursiveStart + queryJoin + union + querySelect +  queryLast;




		if(sortBy!=null)
		{
			if(!sortBy.equals(""))
			{
				String queryPartSortBy = " ORDER BY " + sortBy;

//				queryNormal = queryNormal + queryPartSortBy;
				queryRecursive = queryRecursive + queryPartSortBy;
			}
		}



		if(limit !=null)
		{

			String queryPartLimitOffset = "";

			if(offset!=null)
			{
				queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + offset;

			}else
			{
				queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + 0;
			}


//			queryNormal = queryNormal + queryPartLimitOffset;
			queryRecursive = queryRecursive + queryPartLimitOffset;
		}


		
//		if(shopID==null && latCenter == null && lonCenter == null)
//		{
//			query = queryNormal;
//
//		}else
//		{
//			query = queryRecursive;
//		}

		query = queryRecursive;


//		System.out.println(query);
		
		
		ArrayList<ItemCategory> itemCategoryList = new ArrayList<ItemCategory>();
		
		
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		
		try {
			
			connection = dataSource.getConnection();
			
			statement = connection.createStatement();
			
			rs = statement.executeQuery(query);
			
			while(rs.next())
			{
				ItemCategory itemCategory = new ItemCategory();
				
				itemCategory.setItemCategoryID(rs.getInt(ItemCategory.ITEM_CATEGORY_ID));
				itemCategory.setParentCategoryID(rs.getInt(ItemCategory.PARENT_CATEGORY_ID));
				itemCategory.setIsLeafNode(rs.getBoolean(ItemCategory.IS_LEAF_NODE));
				itemCategory.setImagePath(rs.getString(ItemCategory.IMAGE_PATH));
				itemCategory.setCategoryOrder(rs.getInt(ItemCategory.CATEGORY_ORDER));
				itemCategory.setCategoryName(rs.getString(ItemCategory.ITEM_CATEGORY_NAME));

				itemCategory.setisAbstractNode(rs.getBoolean(ItemCategory.IS_ABSTRACT));
				itemCategory.setDescriptionShort(rs.getString(ItemCategory.ITEM_CATEGORY_DESCRIPTION_SHORT));

				itemCategory.setCategoryDescription(rs.getString(ItemCategory.ITEM_CATEGORY_DESCRIPTION));
				itemCategoryList.add(itemCategory);		
			}



			if(parentIsNull!=null&& parentIsNull)
			{
				// exclude the root category
				for(ItemCategory itemCategory : itemCategoryList)
				{
					if(itemCategory.getItemCategoryID()==1)
					{
						itemCategoryList.remove(itemCategory);
						break;
					}
				}
			}


//			conn.close();

//			System.out.println("Total itemCategories queried " + itemCategoryList.size());
			
		}


		catch (SQLException e) {
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
		
		return itemCategoryList;
	}



	public ItemCategoryEndPoint getEndPointMetadata(
			Integer shopID, Integer parentID, Boolean parentIsNull,
			Double latCenter, Double lonCenter,
			Double deliveryRangeMin, Double deliveryRangeMax,
			Double proximity)
	{

		String query = "";

		String queryNormal = "SELECT * FROM " + ItemCategory.TABLE_NAME;


		boolean queryNormalFirst = true;

		if(parentID!=null)
		{
			queryNormal = queryNormal + " WHERE "
					+ ItemCategory.PARENT_CATEGORY_ID
					+ "=" + parentID ;

			queryNormalFirst = false;
		}


		if(parentIsNull!=null&& parentIsNull)
		{

			String queryNormalPart = ItemCategory.PARENT_CATEGORY_ID + " IS NULL";

			if(queryNormalFirst)
			{
				queryNormal = queryNormal + " WHERE " + queryNormalPart;

			}else
			{
				queryNormal = queryNormal + " AND " + queryNormalPart;

			}
		}



		// a recursive CTE (Common table Expression) query. This query is used for retrieving hierarchical / tree set data.


		String withRecursiveStart = "WITH RECURSIVE category_tree("
				+ ItemCategory.ITEM_CATEGORY_ID + ","
				+ ItemCategory.PARENT_CATEGORY_ID
				+ ") AS (";


		String queryJoin = "SELECT DISTINCT "

				+ ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_ID + ","
				+ ItemCategory.TABLE_NAME + "." + ItemCategory.PARENT_CATEGORY_ID

				+ " FROM "
				+ Shop.TABLE_NAME  + "," + ShopItem.TABLE_NAME + ","
				+ Item.TABLE_NAME + "," + ItemCategory.TABLE_NAME
				+ " WHERE "
				+ Shop.TABLE_NAME + "." + Shop.SHOP_ID
				+ "="
				+ ShopItem.TABLE_NAME + "." + ShopItem.SHOP_ID
				+ " AND "
				+ ShopItem.TABLE_NAME + "." + ShopItem.ITEM_ID
				+ "="
				+ Item.TABLE_NAME + "." + Item.ITEM_ID
				+ " AND "
				+ Item.TABLE_NAME + "." + Item.ITEM_CATEGORY_ID
				+ "="
				+ ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_ID;



		if(shopID!=null)
		{
			queryJoin = queryJoin + " AND "
					+ Shop.TABLE_NAME
					+ "."
					+ Shop.SHOP_ID + " = " + shopID;

		}

		if(latCenter!=null && lonCenter!=null)
		{
			// Applying shop visibility filter. Gives all the shops which are visible at the given location defined by
			// latCenter and lonCenter. For more information see the API documentation.


			String queryPartlatLonCenter = "";

			queryPartlatLonCenter = queryPartlatLonCenter + " 6371.01 * acos( cos( radians("
					+ latCenter + ")) * cos( radians( lat_center) ) * cos(radians( lon_center ) - radians("
					+ lonCenter + "))"
					+ " + sin( radians(" + latCenter + ")) * sin(radians(lat_center))) <= delivery_range ";


			queryJoin = queryJoin + " AND " + queryPartlatLonCenter;


		}



		if(deliveryRangeMax !=null && deliveryRangeMin != null)
		{

			// apply delivery range filter
			queryJoin = queryJoin
					+ " AND "
					+ Shop.TABLE_NAME
					+ "."
					+ Shop.DELIVERY_RANGE
					+ " BETWEEN " + deliveryRangeMin + " AND " + deliveryRangeMax;
		}


		// proximity cannot be greater than the delivery range if the delivery range is supplied. Otherwise this condition is
		// not required.
		if(proximity != null)
		{


			String queryPartProximityHaversine = "";


			// filter using Haversine formula using SQL math functions
			queryPartProximityHaversine = queryPartProximityHaversine
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


			queryJoin = queryJoin + " AND " + queryPartProximityHaversine;

		}


		String union = " UNION ";

		String querySelect = " SELECT "

				+ "cat." + ItemCategory.ITEM_CATEGORY_ID + ","
				+ "cat." + ItemCategory.PARENT_CATEGORY_ID

				+ " FROM category_tree tempCat," + 	ItemCategory.TABLE_NAME + " cat"
				+ " WHERE cat." + ItemCategory.ITEM_CATEGORY_ID
				+ " = tempcat." + ItemCategory.PARENT_CATEGORY_ID
				+ " )";


		String queryLast = " SELECT "
				+ ItemCategory.ITEM_CATEGORY_ID + ","
				+ ItemCategory.PARENT_CATEGORY_ID
				+ " FROM category_tree";


		if(parentID!=null)
		{
			queryLast = queryLast + " WHERE "
					+ ItemCategory.PARENT_CATEGORY_ID
					+ "=" + parentID ;
		}

		String queryRecursive = withRecursiveStart + queryJoin + union + querySelect +  queryLast;



		if(shopID==null && latCenter == null && lonCenter == null)
		{
			query = queryNormal;

		}else
		{
			query = queryRecursive;
		}



//		System.out.println(query);


		ArrayList<ItemCategory> itemCategoryList = new ArrayList<ItemCategory>();


		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			conn = dataSource.getConnection();
			stmt = conn.createStatement();

			rs = stmt.executeQuery(query);

			while(rs.next())
			{
				ItemCategory itemCategory = new ItemCategory();

				itemCategory.setItemCategoryID(rs.getInt(ItemCategory.ITEM_CATEGORY_ID));
				itemCategory.setParentCategoryID(rs.getInt(ItemCategory.PARENT_CATEGORY_ID));

				itemCategoryList.add(itemCategory);
			}



			if(parentIsNull!=null&& parentIsNull)
			{
				// exclude the root category
				for(ItemCategory itemCategory : itemCategoryList)
				{
					if(itemCategory.getItemCategoryID()==1)
					{
						itemCategoryList.remove(itemCategory);
						break;
					}
				}
			}




//			conn.close();

//			System.out.println("Total itemCategories queried " + itemCategoryList.size());

		}


		catch (SQLException e) {
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

				if(stmt!=null)
				{stmt.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {

				if(conn!=null)
				{conn.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return null;
	}






	public ItemCategory checkRoot(int itemCategoryID)
	{
		
		String query = "SELECT " + ItemCategory.ITEM_CATEGORY_ID
					+ " FROM " + ItemCategory.TABLE_NAME
					+ " WHERE " +  ItemCategory.ITEM_CATEGORY_ID +  "= ?";

		
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		
		ItemCategory itemCategory = null;
		
		try {
			
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(query);

			statement.setObject(1,itemCategoryID);

			rs = statement.executeQuery();
			
			while(rs.next())
			{
				itemCategory = new ItemCategory();
				itemCategory.setItemCategoryID(rs.getInt(ItemCategory.ITEM_CATEGORY_ID));
			}
			
			
			//System.out.println("Total itemCategories queried " + itemCategoryList.size());	
	
		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally
		
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
	
		return itemCategory;
	}






	public ItemCategory getItemCatImageURL(
			Integer itemCatID
	) {

		String queryJoin = "SELECT DISTINCT "
				+ ItemCategory.TABLE_NAME + "." + ItemCategory.IMAGE_PATH + ""
				+ " FROM " + ItemCategory.TABLE_NAME
				+ " WHERE " + ItemCategory.ITEM_CATEGORY_ID + " = " + itemCatID;


		ItemCategory itemCategory = null;
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
				itemCategory = new ItemCategory();
				itemCategory.setItemCategoryID(itemCatID);
				itemCategory.setImagePath(rs.getString(ItemCategory.IMAGE_PATH));
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

		return itemCategory;
	}



}
