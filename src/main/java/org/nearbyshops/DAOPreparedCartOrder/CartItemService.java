package org.nearbyshops.DAOPreparedCartOrder;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Cart;
import org.nearbyshops.Model.CartItem;
import org.nearbyshops.Model.Item;
import org.nearbyshops.Model.ShopItem;

import java.sql.*;
import java.util.ArrayList;


public class CartItemService {

	private HikariDataSource dataSource = Globals.getDataSource();

	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();	
	}
	
	
	
	public int saveCartItem(CartItem cartItem)
	{	
		
		Connection connection = null;
		PreparedStatement statement = null;
		int rowCount = -1;

		String insertEndUser = "INSERT INTO "
				+ CartItem.TABLE_NAME
				+ "("  
				+ CartItem.CART_ID + ","
				+ CartItem.ITEM_ID + ","
				+ CartItem.ITEM_QUANTITY + ""
				+ ") VALUES(?,?,?)";

		/*+ "" + cartItem.getCartID() + ","
				+ "" + cartItem.getItemID() + ","
				+ "" + cartItem.getItemQuantity()*/

		try {
			
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(insertEndUser,Statement.RETURN_GENERATED_KEYS);

			statement.setObject(1,cartItem.getCartID());
			statement.setObject(2,cartItem.getItemID());
			statement.setObject(3,cartItem.getItemQuantity());


			rowCount = statement.executeUpdate();
			
			ResultSet rs = statement.getGeneratedKeys();

			//if(rs.next())
			//{
			//	rowCount = rs.getInt(1);
			//}
			

			
			
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
	

	public int updateCartItem(CartItem cartItem)
	{	
		String updateStatement = "UPDATE "
				+ CartItem.TABLE_NAME
				+ " SET "
				+ CartItem.ITEM_ID + " = ?,"
				+ CartItem.CART_ID + " = ?,"
				+ CartItem.ITEM_QUANTITY + " = ?"
				+ " WHERE "
				+ CartItem.CART_ID + " = ?" + " AND "
				+ CartItem.ITEM_ID + " = ?";


		Connection connection = null;
		PreparedStatement statement = null;
		int updatedRows = -1;
		
		try {
			
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(updateStatement);

			statement.setObject(1,cartItem.getItemID());
			statement.setObject(2,cartItem.getCartID());
			statement.setObject(3,cartItem.getItemQuantity());

			statement.setObject(4,cartItem.getCartID());
			statement.setObject(5,cartItem.getItemID());

			updatedRows = statement.executeUpdate();
			System.out.println("Total rows updated CartItem : " + updatedRows);
			
			//conn.close();
			
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

		return updatedRows;
	}



	public int deleteCartItem(Integer itemID,Integer cartID)
	{

		String deleteStatement = "DELETE FROM " + CartItem.TABLE_NAME;

//				+ " WHERE " + CartItem.ITEM_ID + " = ?"
//				+ " AND " + CartItem.CART_ID + " = ?";



//		deleteStatement = deleteStatement + " WHERE " + CartItem.ITEM_ID + " = " + itemID;
//		deleteStatement = deleteStatement + " AND " + CartItem.CART_ID + " = " + cartID;

		boolean isFirst = true;

		if(itemID !=null)
		{

			deleteStatement = deleteStatement + " WHERE " + CartItem.ITEM_ID + " = " + itemID;
			isFirst = false;
		}

		if(cartID !=null)
		{
			if(isFirst)
			{
				deleteStatement = deleteStatement + " WHERE " + CartItem.CART_ID + " = " + cartID;
			}else
			{
				deleteStatement = deleteStatement + " AND " + CartItem.CART_ID + " = " + cartID;
			}

		}


		Connection connection= null;
		Statement statement = null;
		int rowsCountDeleted = 0;
		try {
			
			connection = dataSource.getConnection();
			statement = connection.createStatement();

//			statement.setObject(1,itemID);
//			statement.setObject(2,cartID);

			rowsCountDeleted = statement.executeUpdate(deleteStatement);
			System.out.println(" Deleted Count CartItem : " + rowsCountDeleted);
			
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
	
		
		return rowsCountDeleted;
	}



	
	public ArrayList<CartItem> getCartItem(Integer cartID, Integer itemID, Integer endUserID)
	{

		String query = "SELECT * FROM " + CartItem.TABLE_NAME + "," + Cart.TABLE_NAME
				+ " WHERE " + CartItem.TABLE_NAME + "."+ CartItem.CART_ID  + " = "
				+ Cart.TABLE_NAME + "." + Cart.CART_ID ;



		if(endUserID != null)
		{
			query = query + " AND " + Cart.END_USER_ID + " = " + endUserID;
		}



		ArrayList<CartItem> cartItemList = new ArrayList<CartItem>();

		//boolean isFirst = true;

		if(cartID != null)
		{
			query = query + " AND " + CartItem.TABLE_NAME + "." + CartItem.CART_ID + " = " + cartID;

		//	isFirst = false;
		}


		if(itemID != null)
		{

			query = query + " AND " + CartItem.ITEM_ID + " = " + itemID;

			/*
			if(isFirst)
			{
				query = query + " AND " + CartItem.ITEM_ID + " = " + itemID;

				isFirst = false;

			}else
			{

			}*/

		}

		
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		
		try {
			
			connection = dataSource.getConnection();
			statement = connection.createStatement();

			rs = statement.executeQuery(query);
			
			while(rs.next())
			{
				CartItem cartItem = new CartItem();

				cartItem.setCartID(rs.getInt(CartItem.CART_ID));
				cartItem.setItemID(rs.getInt(CartItem.ITEM_ID));
				cartItem.setItemQuantity(rs.getInt(CartItem.ITEM_QUANTITY));

				cartItemList.add(cartItem);
			}
			

			
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

		return cartItemList;
	}






	public ArrayList<CartItem> getCartItem(Integer endUserID, Integer shopID)
	{


		String query = "SELECT " +
						"cart.cart_id," +
						"cart_item.item_id," +
						" available_item_quantity," +
						" item_price," +
						" item_quantity, " +
						" (item_quantity * item_price) as Item_total" +
						" FROM " +
						" shop_item, cart_item, cart " +
						" Where " +
						"shop_item.shop_id = cart.shop_id " +
						" and " +
						" shop_item.item_id = cart_item.item_id " +
						" and " +
						" cart.cart_id = cart_item.cart_id " ;



		if(endUserID!=null)
		{
			query = query + " and end_user_id = " + endUserID;
		}


		if(shopID !=null)
		{
			query = query + " and cart.shop_id = " + shopID;
		}



		ArrayList<CartItem> cartItemList = new ArrayList<CartItem>();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while(rs.next())
			{
				CartItem cartItem = new CartItem();

				cartItem.setCartID(rs.getInt(CartItem.CART_ID));
				cartItem.setItemID(rs.getInt(CartItem.ITEM_ID));
				cartItem.setItemQuantity(rs.getInt(CartItem.ITEM_QUANTITY));
				cartItem.setRt_availableItemQuantity(rs.getInt("available_item_quantity"));
				cartItem.setRt_itemPrice(rs.getDouble("item_price"));


				//cartItem.setItem(Globals.itemService.getItem(cartItem.getItemID()));

				cartItemList.add(cartItem);
			}



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

		return cartItemList;
	}





	public ArrayList<CartItem> getCartItemRefined(Integer endUserID, Integer shopID,
                                                  String sortBy,
                                                  Integer limit, Integer offset)
	{

		String query = "SELECT "
				+ Cart.TABLE_NAME + "." + Cart.CART_ID + ","
				+ CartItem.TABLE_NAME + "." + CartItem.ITEM_ID + ","
				+ CartItem.TABLE_NAME + "." + CartItem.ITEM_QUANTITY + ","

				+ ShopItem.AVAILABLE_ITEM_QUANTITY + ","
				+ ShopItem.ITEM_PRICE + ","

				+ Item.TABLE_NAME + "." + Item.ITEM_CATEGORY_ID + ","
				+ Item.TABLE_NAME + "." + Item.ITEM_ID + ","
				+ Item.TABLE_NAME + "." + Item.ITEM_IMAGE_URL + ","
				+ Item.TABLE_NAME + "." + Item.ITEM_NAME + ","
				+ Item.TABLE_NAME + "." + Item.ITEM_DESC + ","

				+ Item.TABLE_NAME + "." + Item.QUANTITY_UNIT + ","
				+ Item.TABLE_NAME + "." + Item.DATE_TIME_CREATED + ","
				+ Item.TABLE_NAME + "." + Item.ITEM_DESCRIPTION_LONG + ""

				+ " FROM " + CartItem.TABLE_NAME
				+ " LEFT OUTER JOIN " + Cart.TABLE_NAME + " ON(" + CartItem.TABLE_NAME + "." + CartItem.CART_ID + " = " + Cart.TABLE_NAME + "." + Cart.CART_ID + ")"
				+ " LEFT OUTER JOIN " + Item.TABLE_NAME + " ON(" + Item.TABLE_NAME + "." + Item.ITEM_ID + " = " + CartItem.TABLE_NAME + "." + CartItem.ITEM_ID + "),"
				+ ShopItem.TABLE_NAME
				+ " Where " + ShopItem.TABLE_NAME + "." + ShopItem.SHOP_ID + " = " + Cart.TABLE_NAME + "." + Cart.SHOP_ID
				+ " and " + ShopItem.TABLE_NAME + "." + ShopItem.ITEM_ID + " = " + CartItem.TABLE_NAME + "." + CartItem.ITEM_ID;


		//" shop_item.item_id = cart_item.item_id " +
//		+ " (item_quantity * item_price) as Item_total,"
//		+ " item_quantity, "

		//+ " available_item_quantity,"
		//+ " item_price," +


		if(endUserID!=null)
		{
			query = query + " and " + Cart.END_USER_ID + " = " + endUserID;
		}


		if(shopID !=null)
		{
			query = query + " and " + Cart.TABLE_NAME + "." + Cart.SHOP_ID + " = " + shopID;
		}


		if(sortBy!=null)
		{
			if(!sortBy.equals(""))
			{
				String queryPartSortBy = " ORDER BY " + sortBy;

				query = query + queryPartSortBy;
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

			query = query + queryPartLimitOffset;
		}




		ArrayList<CartItem> cartItemList = new ArrayList<CartItem>();

		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;

		try {

			connection = dataSource.getConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery(query);

			while(rs.next())
			{
				CartItem cartItem = new CartItem();

				cartItem.setCartID(rs.getInt(CartItem.CART_ID));
				cartItem.setItemID(rs.getInt(CartItem.ITEM_ID));
				cartItem.setItemQuantity(rs.getInt(CartItem.ITEM_QUANTITY));

				cartItem.setRt_availableItemQuantity(rs.getInt(ShopItem.AVAILABLE_ITEM_QUANTITY));
				cartItem.setRt_itemPrice(rs.getDouble(ShopItem.ITEM_PRICE));

				Item item = new Item();

				item.setItemID(rs.getInt(Item.ITEM_ID));
				item.setItemName(rs.getString(Item.ITEM_NAME));
				item.setItemDescription(rs.getString(Item.ITEM_DESC));

				item.setItemImageURL(rs.getString(Item.ITEM_IMAGE_URL));
				item.setItemCategoryID(rs.getInt(Item.ITEM_CATEGORY_ID));

				item.setItemDescriptionLong(rs.getString(Item.ITEM_DESCRIPTION_LONG));
				item.setDateTimeCreated(rs.getTimestamp(Item.DATE_TIME_CREATED));
				item.setQuantityUnit(rs.getString(Item.QUANTITY_UNIT));


				cartItem.setItem(item);

				cartItemList.add(cartItem);
			}



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


		return cartItemList;
	}

}
