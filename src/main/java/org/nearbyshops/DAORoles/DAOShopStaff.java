package org.nearbyshops.DAORoles;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.GlobalConstants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.ModelRoles.Endpoints.UserEndpoint;
import org.nearbyshops.ModelRoles.ShopStaffPermissions;
import org.nearbyshops.ModelRoles.ShopStaffPermissionsOld;
import org.nearbyshops.ModelRoles.StaffPermissions;
import org.nearbyshops.ModelRoles.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class DAOShopStaff {


	private HikariDataSource dataSource = Globals.getDataSource();


	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}





	public int getShopIDforShopStaff(int shopStaffID) {

		String query = "SELECT " + ShopStaffPermissions.SHOP_ID + ""
					+ " FROM "   + ShopStaffPermissions.TABLE_NAME
					+ " WHERE "  + ShopStaffPermissions.STAFF_ID + " = ?";



		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;


		//Distributor distributor = null;
		int shopID = -1;

		try {

			connection = dataSource.getConnection();
			statement = connection.prepareStatement(query);

			statement.setObject(1,shopStaffID);

			rs = statement.executeQuery();


			while(rs.next())
			{
				shopID = rs.getInt(ShopStaffPermissions.SHOP_ID);
			}




			//System.out.println("Total itemCategories queried " + itemCategoryList.size());


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally

		{

			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {

				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {

				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return shopID;
	}


	public void logMessage(String message)
	{
		System.out.println(message);
	}






	public int updateShopStaffProfile(User user)
	{

		String updateStatement = "UPDATE " + User.TABLE_NAME

				+ " SET "

//                + User.USERNAME + "=?,"
//                + User.PASSWORD + "=?,"
//                + User.E_MAIL + "=?,"
//                + User.PHONE + "=?,"
				+ User.NAME + "=?,"
				+ User.GENDER + "=?,"

				+ User.PROFILE_IMAGE_URL + "=?,"
				+ User.IS_ACCOUNT_PRIVATE + "=?,"
				+ User.ABOUT + "=?"

				+ " WHERE " + User.USER_ID + " = ?";


		Connection connection = null;
		PreparedStatement statement = null;

		int rowCountUpdated = 0;

		try {

			connection = dataSource.getConnection();
			statement = connection.prepareStatement(updateStatement);

			int i = 0;

//            statement.setString(++i,user.getUsername());
//            statement.setString(++i,user.getPassword());
//            statement.setString(++i,user.getEmail());
//            statement.setString(++i,user.getPhone());

			statement.setString(++i,user.getName());
			statement.setObject(++i,user.getGender());

			statement.setString(++i,user.getProfileImagePath());
			statement.setObject(++i,user.isAccountPrivate());
			statement.setString(++i,user.getAbout());

			statement.setObject(++i,user.getUserID());


			rowCountUpdated = statement.executeUpdate();


			System.out.println("Total rows updated: " + rowCountUpdated);


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










	public int updateShopStaffByAdmin(User user)
	{

		String updateStatement = "UPDATE " + User.TABLE_NAME

				+ " SET "

				+ User.NAME + "=?,"
				+ User.GENDER + "=?,"

				+ User.PROFILE_IMAGE_URL + "=?,"
				+ User.IS_ACCOUNT_PRIVATE + "=?,"
				+ User.ABOUT + "=?,"
				+ User.IS_VERIFIED + "=?"

				+ " WHERE " + User.USER_ID + " = ?";




		String updatePermissions = "UPDATE " + ShopStaffPermissions.TABLE_NAME
				+ " SET "
				+ ShopStaffPermissions.DESIGNATION + "=?,"
				+ ShopStaffPermissions.ADD_REMOVE_ITEMS_FROM_SHOP + "=?,"

				+ ShopStaffPermissions.UPDATE_STOCK + "=?,"
				+ ShopStaffPermissions.CANCEL_ORDERS + "=?,"
				+ ShopStaffPermissions.CONFIRM_ORDERS + "=?,"

				+ ShopStaffPermissions.SET_ORDERS_PACKED + "=?,"
				+ ShopStaffPermissions.HANDOVER_TO_DELIVERY + "=?,"
				+ ShopStaffPermissions.MARK_ORDERS_DELIVERED + "=?,"

				+ ShopStaffPermissions.ACCEPT_PAYMENTS_FROM_DELIVERY + "=?,"
				+ ShopStaffPermissions.ACCEPT_RETURNS + "=?"

				+ " WHERE " + ShopStaffPermissions.STAFF_ID + " = ?";





//		String insertStaffPermissions =
//
//				"INSERT INTO " + ShopStaffPermissions.TABLE_NAME
//						+ "("
//						+ ShopStaffPermissions.STAFF_ID + ","
//						+ ShopStaffPermissions.DESIGNATION + ","
//						+ ShopStaffPermissions.ADD_REMOVE_ITEMS_FROM_SHOP + ","
//
//						+ ShopStaffPermissions.UPDATE_STOCK + ","
//						+ ShopStaffPermissions.CANCEL_ORDERS + ","
//						+ ShopStaffPermissions.CONFIRM_ORDERS + ","
//
//						+ ShopStaffPermissions.SET_ORDERS_PACKED + ","
//						+ ShopStaffPermissions.HANDOVER_TO_DELIVERY + ","
//						+ ShopStaffPermissions.MARK_ORDERS_DELIVERED + ","
//
//						+ ShopStaffPermissions.ACCEPT_PAYMENTS_FROM_DELIVERY + ","
//						+ ShopStaffPermissions.ACCEPT_RETURNS + ""
//						+ ") values(?,?,?, ?,?,?, ?,?,?, ?,?)"
//						+ " ON CONFLICT (" + ShopStaffPermissions.STAFF_ID + ")"
//						+ " DO UPDATE "
//						+ " SET "
//						+ ShopStaffPermissions.DESIGNATION + "= excluded." + ShopStaffPermissions.DESIGNATION + " , "
//						+ ShopStaffPermissions.ADD_REMOVE_ITEMS_FROM_SHOP + "= excluded." + ShopStaffPermissions.ADD_REMOVE_ITEMS_FROM_SHOP + " , "
//						+ ShopStaffPermissions.UPDATE_STOCK + "= excluded." + ShopStaffPermissions.UPDATE_STOCK  + ","
//						+ ShopStaffPermissions.CANCEL_ORDERS + "= excluded." + ShopStaffPermissions.CANCEL_ORDERS  + ","
//						+ ShopStaffPermissions.CONFIRM_ORDERS + "= excluded." + ShopStaffPermissions.CONFIRM_ORDERS  + ","
//						+ ShopStaffPermissions.SET_ORDERS_PACKED + "= excluded." + ShopStaffPermissions.SET_ORDERS_PACKED  + ","
//						+ ShopStaffPermissions.HANDOVER_TO_DELIVERY + "= excluded." + ShopStaffPermissions.HANDOVER_TO_DELIVERY  + ","
//						+ ShopStaffPermissions.MARK_ORDERS_DELIVERED + "= excluded." + ShopStaffPermissions.MARK_ORDERS_DELIVERED  + ","
//						+ ShopStaffPermissions.ACCEPT_PAYMENTS_FROM_DELIVERY + "= excluded." + ShopStaffPermissions.ACCEPT_PAYMENTS_FROM_DELIVERY  + ","
//						+ ShopStaffPermissions.ACCEPT_RETURNS + "= excluded." + ShopStaffPermissions.ACCEPT_RETURNS;
//





		Connection connection = null;
		PreparedStatement statement = null;
		PreparedStatement statementUpdatePermissions = null;

		int rowCountUpdated = 0;

		try {

			connection = dataSource.getConnection();
			connection.setAutoCommit(false);



			statement = connection.prepareStatement(updateStatement);

			int i = 0;

			statement.setString(++i,user.getName());
			statement.setObject(++i,user.getGender());

			statement.setString(++i,user.getProfileImagePath());
			statement.setObject(++i,user.isAccountPrivate());
			statement.setString(++i,user.getAbout());
			statement.setObject(++i,user.isVerified());

			statement.setObject(++i,user.getUserID());


			rowCountUpdated = statement.executeUpdate();
			System.out.println("Total rows updated: " + rowCountUpdated);


			statementUpdatePermissions = connection.prepareStatement(updatePermissions,PreparedStatement.RETURN_GENERATED_KEYS);
			i = 0;

			ShopStaffPermissions permissions = user.getRt_shop_staff_permissions();


			if(permissions!=null)
			{


				statementUpdatePermissions.setString(++i,permissions.getDesignation());
				statementUpdatePermissions.setObject(++i,permissions.isPermitAddRemoveItems());

				statementUpdatePermissions.setObject(++i,permissions.isPermitUpdateItemsInShop());
				statementUpdatePermissions.setObject(++i,permissions.isPermitCancelOrders());
				statementUpdatePermissions.setObject(++i,permissions.isPermitConfirmOrders());

				statementUpdatePermissions.setObject(++i,permissions.isPermitSetOrdersPacked());
				statementUpdatePermissions.setObject(++i,permissions.isPermitHandoverToDelivery());
				statementUpdatePermissions.setObject(++i,permissions.isPermitMarkOrdersDelivered());

				statementUpdatePermissions.setObject(++i,permissions.isPermitAcceptPaymentsFromDelivery());
				statementUpdatePermissions.setObject(++i,permissions.isPermitAcceptReturns());

				statementUpdatePermissions.setObject(++i,user.getUserID());

				statementUpdatePermissions.executeUpdate();
			}






			connection.commit();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			if (connection != null) {
				try {

//                    idOfInsertedRow=-1;
//                    rowCountItems = 0;

					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
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






	public int updateShopStaffLocation(ShopStaffPermissions permissions)
	{


		String insertStaffPermissions =

				"INSERT INTO " + ShopStaffPermissions.TABLE_NAME
						+ "("
						+ ShopStaffPermissions.STAFF_ID + ","
						+ ShopStaffPermissions.LAT_CURRENT + ","
						+ ShopStaffPermissions.LON_CURRENT + ""
						+ ") values(?,?,?)"
						+ " ON CONFLICT (" + ShopStaffPermissions.STAFF_ID + ")"
						+ " DO UPDATE "
						+ " SET "
						+ ShopStaffPermissions.LAT_CURRENT + "= excluded." + ShopStaffPermissions.LAT_CURRENT + " , "
						+ ShopStaffPermissions.LON_CURRENT + "= excluded." + ShopStaffPermissions.LON_CURRENT;




		Connection connection = null;
		PreparedStatement statement = null;

		int rowCountUpdated = 0;

		try {

			connection = dataSource.getConnection();
			connection.setAutoCommit(false);


			statement = connection.prepareStatement(insertStaffPermissions,PreparedStatement.RETURN_GENERATED_KEYS);
			int i = 0;


			if(permissions!=null)
			{
				statement.setObject(++i,permissions.getStaffUserID());
				statement.setObject(++i,permissions.getLatCurrent());
				statement.setObject(++i,permissions.getLonCurrent());

				rowCountUpdated = statement.executeUpdate();
			}


			connection.commit();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			if (connection != null) {
				try {

//                    idOfInsertedRow=-1;
//                    rowCountItems = 0;

					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
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











	public ShopStaffPermissions getShopStaffPermissions(int staffID)
	{

		boolean isFirst = true;

		String query = "SELECT "

				+ ShopStaffPermissions.STAFF_ID + ","
				+ ShopStaffPermissions.SHOP_ID + ","

				+ ShopStaffPermissions.ADD_REMOVE_ITEMS_FROM_SHOP + ","
				+ ShopStaffPermissions.UPDATE_STOCK + ","

				+ ShopStaffPermissions.CANCEL_ORDERS + ","
				+ ShopStaffPermissions.CONFIRM_ORDERS + ","
				+ ShopStaffPermissions.SET_ORDERS_PACKED + ","
				+ ShopStaffPermissions.HANDOVER_TO_DELIVERY + ","
				+ ShopStaffPermissions.MARK_ORDERS_DELIVERED + ","
				+ ShopStaffPermissions.ACCEPT_PAYMENTS_FROM_DELIVERY + ","
				+ ShopStaffPermissions.ACCEPT_RETURNS + ""

				+ " FROM "  + ShopStaffPermissions.TABLE_NAME
				+ " WHERE " + ShopStaffPermissions.STAFF_ID  + " = ? ";



		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;


		//Distributor distributor = null;
		ShopStaffPermissions permissions = null;

		try {

			connection = dataSource.getConnection();
			statement = connection.prepareStatement(query);

			int i = 0;


			statement.setObject(++i,staffID); // username


			rs = statement.executeQuery();

			while(rs.next())
			{
				permissions = new ShopStaffPermissions();

				permissions.setStaffUserID(rs.getInt(ShopStaffPermissions.STAFF_ID));
				permissions.setShopID(rs.getInt(ShopStaffPermissions.SHOP_ID));

				permissions.setPermitAddRemoveItems(rs.getBoolean(ShopStaffPermissions.ADD_REMOVE_ITEMS_FROM_SHOP));
				permissions.setPermitUpdateItemsInShop(rs.getBoolean(ShopStaffPermissions.UPDATE_STOCK));

				permissions.setPermitCancelOrders(rs.getBoolean(ShopStaffPermissions.CANCEL_ORDERS));
				permissions.setPermitConfirmOrders(rs.getBoolean(ShopStaffPermissions.CONFIRM_ORDERS));
				permissions.setPermitSetOrdersPacked(rs.getBoolean(ShopStaffPermissions.SET_ORDERS_PACKED));
				permissions.setPermitHandoverToDelivery(rs.getBoolean(ShopStaffPermissions.HANDOVER_TO_DELIVERY));
				permissions.setPermitMarkOrdersDelivered(rs.getBoolean(ShopStaffPermissions.MARK_ORDERS_DELIVERED));
				permissions.setPermitAcceptPaymentsFromDelivery(rs.getBoolean(ShopStaffPermissions.ACCEPT_PAYMENTS_FROM_DELIVERY));
				permissions.setPermitAcceptReturns(rs.getBoolean(ShopStaffPermissions.ACCEPT_RETURNS));

			}


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

		return permissions;
	}









	public UserEndpoint getShopStaffForShopAdmin(
			Double latPickUp, Double lonPickUp,
			Boolean permitCreateUpdateItemCat,
			Boolean permitCreateUpdateItems,
			Boolean gender,
			int shopID,
			String sortBy,
			Integer limit, Integer offset,
			boolean getRowCount,
			boolean getOnlyMetadata
	) {


		boolean isfirst = true;

		String queryCount = "";


		String queryJoin = "SELECT DISTINCT "


				+ "6371 * acos( cos( radians("
				+ latPickUp + ")) * cos( radians(" +  StaffPermissions.LAT_CURRENT +  ") ) * cos(radians(" + StaffPermissions.LON_CURRENT +  ") - radians("
				+ lonPickUp + "))"
				+ " + sin( radians(" + latPickUp + ")) * sin(radians(" + StaffPermissions.LAT_CURRENT + "))) as distance" + ","

				+ User.TABLE_NAME + "." + User.USER_ID + ","
				+ User.TABLE_NAME + "." + User.USERNAME + ","
				+ User.TABLE_NAME + "." + User.E_MAIL + ","
				+ User.TABLE_NAME + "." + User.PHONE + ","

				+ User.TABLE_NAME + "." + User.NAME + ","
				+ User.TABLE_NAME + "." + User.GENDER + ","

				+ User.TABLE_NAME + "." + User.PROFILE_IMAGE_URL + ","
				+ User.TABLE_NAME + "." + User.IS_ACCOUNT_PRIVATE + ","
				+ User.TABLE_NAME + "." + User.ABOUT + ","

				+ User.TABLE_NAME + "." + User.TIMESTAMP_CREATED + ","
				+ User.TABLE_NAME + "." + User.IS_VERIFIED + ","

				+ ShopStaffPermissions.TABLE_NAME + "." + ShopStaffPermissions.DESIGNATION + ","
				+ ShopStaffPermissions.TABLE_NAME + "." + ShopStaffPermissions.ADD_REMOVE_ITEMS_FROM_SHOP + ","
				+ ShopStaffPermissions.TABLE_NAME + "." + ShopStaffPermissions.UPDATE_STOCK + ","

				+ ShopStaffPermissions.TABLE_NAME + "." + ShopStaffPermissions.CANCEL_ORDERS + ","
				+ ShopStaffPermissions.TABLE_NAME + "." + ShopStaffPermissions.CONFIRM_ORDERS + ","
				+ ShopStaffPermissions.TABLE_NAME + "." + ShopStaffPermissions.SET_ORDERS_PACKED + ","
				+ ShopStaffPermissions.TABLE_NAME + "." + ShopStaffPermissions.HANDOVER_TO_DELIVERY + ","
				+ ShopStaffPermissions.TABLE_NAME + "." + ShopStaffPermissions.MARK_ORDERS_DELIVERED + ","
				+ ShopStaffPermissions.TABLE_NAME + "." + ShopStaffPermissions.ACCEPT_PAYMENTS_FROM_DELIVERY + ","
				+ ShopStaffPermissions.TABLE_NAME + "." + ShopStaffPermissions.ACCEPT_RETURNS + ""

				+ " FROM " + User.TABLE_NAME
				+ " LEFT OUTER JOIN " + ShopStaffPermissions.TABLE_NAME + " ON (" + ShopStaffPermissions.TABLE_NAME + "." + ShopStaffPermissions.STAFF_ID + " = " + User.TABLE_NAME + "." + User.USER_ID + ")"
				+ " WHERE " + ShopStaffPermissions.TABLE_NAME + "." + ShopStaffPermissions.SHOP_ID + " = ? "
				+ " AND ( " + User.TABLE_NAME + "." + User.ROLE + " = " + GlobalConstants.ROLE_SHOP_STAFF_CODE
				+ " OR " + User.TABLE_NAME + "." + User.ROLE + " = " + GlobalConstants.ROLE_SHOP_ADMIN_CODE + " ) ";



		if(gender != null)
		{
			queryJoin = queryJoin + " AND " + User.TABLE_NAME + "." + User.GENDER + " = ?";
		}
//


//        if(permitProfileUpdate!=null && permitProfileUpdate)
//        {
////            queryJoin = queryJoin + " AND " + StaffPermissions.TABLE_NAME + "." + StaffPermissions.PERMIT_TAXI_PROFILE_UPDATE + " = TRUE ";
//        }
//
//
//        if(permitRegistrationAndRenewal !=null && permitRegistrationAndRenewal)
//        {
//            queryJoin = queryJoin + " AND " + StaffPermissions.TABLE_NAME + "." + StaffPermissions.PERMIT_TAXI_REGISTRATION_AND_RENEWAL + " = TRUE ";
//        }





		// all the non-aggregate columns which are present in select must be present in group by also.
		queryJoin = queryJoin

				+ " group by "
				+ ShopStaffPermissions.TABLE_NAME + "." + ShopStaffPermissions.PERMISSION_ID + ","
				+ User.TABLE_NAME + "." + User.USER_ID;


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

			if(offset!=null)
			{
				queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + offset;

			}else
			{
				queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + 0;
			}


//			queryNormal = queryNormal + queryPartLimitOffset;
			queryJoin = queryJoin + queryPartLimitOffset;
		}






		/*

		Applying filters Ends

		 */

		// Applying filters




		queryCount = "SELECT COUNT(*) as item_count FROM (" + queryCount + ") AS temp";


		UserEndpoint endPoint = new UserEndpoint();

		ArrayList<User> itemList = new ArrayList<>();
		Connection connection = null;

		PreparedStatement statement = null;
		ResultSet rs = null;

		PreparedStatement statementCount = null;
		ResultSet resultSetCount = null;

		try {

			connection = dataSource.getConnection();

			int i = 0;


			if(!getOnlyMetadata)
			{
				statement = connection.prepareStatement(queryJoin);


				statement.setObject(++i,shopID);


				if(gender!=null)
				{
					statement.setObject(++i,gender);
				}


				rs = statement.executeQuery();

				while(rs.next())
				{

					User user = new User();

					user.setUserID(rs.getInt(User.USER_ID));
					user.setUsername(rs.getString(User.USERNAME));
					user.setEmail(rs.getString(User.E_MAIL));
					user.setPhone(rs.getString(User.PHONE));


					user.setName(rs.getString(User.NAME));
					user.setGender(rs.getBoolean(User.GENDER));


					user.setProfileImagePath(rs.getString(User.PROFILE_IMAGE_URL));
					user.setAccountPrivate(rs.getBoolean(User.IS_ACCOUNT_PRIVATE));
					user.setAbout(rs.getString(User.ABOUT));

					user.setTimestampCreated(rs.getTimestamp(User.TIMESTAMP_CREATED));
					user.setVerified(rs.getBoolean(User.IS_VERIFIED));

					ShopStaffPermissions permissions = new ShopStaffPermissions();

					permissions.setDesignation(rs.getString(StaffPermissions.DESIGNATION));
					permissions.setPermitAddRemoveItems(rs.getBoolean(ShopStaffPermissions.ADD_REMOVE_ITEMS_FROM_SHOP));
					permissions.setPermitUpdateItemsInShop(rs.getBoolean(ShopStaffPermissions.UPDATE_STOCK));

					permissions.setPermitCancelOrders(rs.getBoolean(ShopStaffPermissions.CANCEL_ORDERS));
					permissions.setPermitConfirmOrders(rs.getBoolean(ShopStaffPermissions.CONFIRM_ORDERS));
					permissions.setPermitSetOrdersPacked(rs.getBoolean(ShopStaffPermissions.SET_ORDERS_PACKED));
					permissions.setPermitHandoverToDelivery(rs.getBoolean(ShopStaffPermissions.HANDOVER_TO_DELIVERY));
					permissions.setPermitMarkOrdersDelivered(rs.getBoolean(ShopStaffPermissions.MARK_ORDERS_DELIVERED));
					permissions.setPermitAcceptPaymentsFromDelivery(rs.getBoolean(ShopStaffPermissions.ACCEPT_PAYMENTS_FROM_DELIVERY));
					permissions.setPermitAcceptReturns(rs.getBoolean(ShopStaffPermissions.ACCEPT_RETURNS));

					user.setRt_shop_staff_permissions(permissions);

					itemList.add(user);
				}

				endPoint.setResults(itemList);

			}


			if(getRowCount)
			{
				statementCount = connection.prepareStatement(queryCount);

				i = 0;

				statementCount.setObject(++i,shopID);


				if(gender!=null)
				{
					statementCount.setObject(++i,gender);
				}


				resultSetCount = statementCount.executeQuery();

				while(resultSetCount.next())
				{
					endPoint.setItemCount(resultSetCount.getInt("item_count"));
				}
			}






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

				if(connection!=null)
				{connection.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return endPoint;
	}







	public UserEndpoint getShopStaffListPublic(
			Double latPickUp, Double lonPickUp,
			Boolean permitProfileUpdate,
			Boolean permitRegistrationAndRenewal,
			Boolean gender,
			String sortBy,
			Integer limit, Integer offset,
			boolean getRowCount,
			boolean getOnlyMetadata
	) {


		boolean isfirst = true;

		String queryCount = "";


		String queryJoin = "SELECT DISTINCT "


				+ "6371 * acos( cos( radians("
				+ latPickUp + ")) * cos( radians(" +  StaffPermissions.LAT_CURRENT +  ") ) * cos(radians(" + StaffPermissions.LON_CURRENT +  ") - radians("
				+ lonPickUp + "))"
				+ " + sin( radians(" + latPickUp + ")) * sin(radians(" + StaffPermissions.LAT_CURRENT + "))) as distance" + ","


				+ User.TABLE_NAME + "." + User.USER_ID + ","
				+ User.TABLE_NAME + "." + User.USERNAME + ","
				+ User.TABLE_NAME + "." + User.E_MAIL + ","
				+ User.TABLE_NAME + "." + User.PHONE + ","

				+ User.TABLE_NAME + "." + User.NAME + ","
				+ User.TABLE_NAME + "." + User.GENDER + ","

				+ User.TABLE_NAME + "." + User.PROFILE_IMAGE_URL + ","
				+ User.TABLE_NAME + "." + User.IS_ACCOUNT_PRIVATE + ","
				+ User.TABLE_NAME + "." + User.ABOUT + ","

				+ User.TABLE_NAME + "." + User.TIMESTAMP_CREATED + ","
				+ User.TABLE_NAME + "." + User.IS_VERIFIED + ","

				+ ShopStaffPermissions.TABLE_NAME + "." + ShopStaffPermissions.DESIGNATION + ","
				+ ShopStaffPermissions.TABLE_NAME + "." + ShopStaffPermissions.ADD_REMOVE_ITEMS_FROM_SHOP + ","
				+ ShopStaffPermissions.TABLE_NAME + "." + ShopStaffPermissions.UPDATE_STOCK + ","

				+ ShopStaffPermissions.TABLE_NAME + "." + ShopStaffPermissions.CANCEL_ORDERS + ","
				+ ShopStaffPermissions.TABLE_NAME + "." + ShopStaffPermissions.CONFIRM_ORDERS + ","
				+ ShopStaffPermissions.TABLE_NAME + "." + ShopStaffPermissions.SET_ORDERS_PACKED + ","
				+ ShopStaffPermissions.TABLE_NAME + "." + ShopStaffPermissions.HANDOVER_TO_DELIVERY + ","
				+ ShopStaffPermissions.TABLE_NAME + "." + ShopStaffPermissions.MARK_ORDERS_DELIVERED + ","
				+ ShopStaffPermissions.TABLE_NAME + "." + ShopStaffPermissions.ACCEPT_PAYMENTS_FROM_DELIVERY + ","
				+ ShopStaffPermissions.TABLE_NAME + "." + ShopStaffPermissions.ACCEPT_RETURNS + ""

				+ " FROM " + User.TABLE_NAME
				+ " LEFT OUTER JOIN " + StaffPermissions.TABLE_NAME + " ON (" + StaffPermissions.TABLE_NAME + "." + StaffPermissions.STAFF_ID + " = " + User.TABLE_NAME + "." + User.USER_ID + ")"
				+ " WHERE TRUE "
				+ " AND ( " + User.TABLE_NAME + "." + User.ROLE + " = " + GlobalConstants.ROLE_SHOP_STAFF_CODE
				+ " OR "
				+ User.TABLE_NAME + "." + User.ROLE + " = " + GlobalConstants.ROLE_SHOP_ADMIN_CODE + " ) ";


//        + " AND " + User.TABLE_NAME + "." + User.ROLE + " = " + GlobalConstantsNBS.ROLE_STAFF_CODE;




		if(gender != null)
		{
			queryJoin = queryJoin + " AND " + User.TABLE_NAME + "." + User.GENDER + " = ? ";
		}
//


//        if(permitProfileUpdate!=null && permitProfileUpdate)
//        {
////            queryJoin = queryJoin + " AND " + StaffPermissions.TABLE_NAME + "." + StaffPermissions.PERMIT_TAXI_PROFILE_UPDATE + " = TRUE ";
//        }
//
//
//        if(permitRegistrationAndRenewal !=null && permitRegistrationAndRenewal)
//        {
//            queryJoin = queryJoin + " AND " + StaffPermissions.TABLE_NAME + "." + StaffPermissions.PERMIT_TAXI_REGISTRATION_AND_RENEWAL + " = TRUE ";
//        }




		// all the non-aggregate columns which are present in select must be present in group by also.
		queryJoin = queryJoin

				+ " group by "
				+ ShopStaffPermissions.TABLE_NAME + "." + ShopStaffPermissions.PERMISSION_ID + ","
				+ User.TABLE_NAME + "." + User.USER_ID;


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

			if(offset!=null)
			{
				queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + offset;

			}else
			{
				queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + 0;
			}


//			queryNormal = queryNormal + queryPartLimitOffset;
			queryJoin = queryJoin + queryPartLimitOffset;
		}






		/*

		Applying filters Ends

		 */

		// Applying filters




		queryCount = "SELECT COUNT(*) as item_count FROM (" + queryCount + ") AS temp";


		UserEndpoint endPoint = new UserEndpoint();

		ArrayList<User> itemList = new ArrayList<>();
		Connection connection = null;

		PreparedStatement statement = null;
		ResultSet rs = null;

		PreparedStatement statementCount = null;
		ResultSet resultSetCount = null;

		try {

			connection = dataSource.getConnection();

			int i = 0;


			if(!getOnlyMetadata)
			{
				statement = connection.prepareStatement(queryJoin);


				if(gender!=null)
				{
					statement.setObject(++i,gender);
				}


				rs = statement.executeQuery();

				while(rs.next())
				{

					User user = new User();

					user.setUserID(rs.getInt(User.USER_ID));
					user.setUsername(rs.getString(User.USERNAME));
					user.setEmail(rs.getString(User.E_MAIL));
					user.setPhone(rs.getString(User.PHONE));


					user.setName(rs.getString(User.NAME));
					user.setGender(rs.getBoolean(User.GENDER));


					user.setProfileImagePath(rs.getString(User.PROFILE_IMAGE_URL));
					user.setAccountPrivate(rs.getBoolean(User.IS_ACCOUNT_PRIVATE));
					user.setAbout(rs.getString(User.ABOUT));

					user.setTimestampCreated(rs.getTimestamp(User.TIMESTAMP_CREATED));
					user.setVerified(rs.getBoolean(User.IS_VERIFIED));



					ShopStaffPermissions permissions = new ShopStaffPermissions();

					permissions.setDesignation(rs.getString(StaffPermissions.DESIGNATION));

					permissions.setPermitAddRemoveItems(rs.getBoolean(ShopStaffPermissions.ADD_REMOVE_ITEMS_FROM_SHOP));
					permissions.setPermitUpdateItemsInShop(rs.getBoolean(ShopStaffPermissions.UPDATE_STOCK));

					permissions.setPermitCancelOrders(rs.getBoolean(ShopStaffPermissions.CANCEL_ORDERS));
					permissions.setPermitConfirmOrders(rs.getBoolean(ShopStaffPermissions.CONFIRM_ORDERS));
					permissions.setPermitSetOrdersPacked(rs.getBoolean(ShopStaffPermissions.SET_ORDERS_PACKED));
					permissions.setPermitHandoverToDelivery(rs.getBoolean(ShopStaffPermissions.HANDOVER_TO_DELIVERY));
					permissions.setPermitMarkOrdersDelivered(rs.getBoolean(ShopStaffPermissions.MARK_ORDERS_DELIVERED));
					permissions.setPermitAcceptPaymentsFromDelivery(rs.getBoolean(ShopStaffPermissions.ACCEPT_PAYMENTS_FROM_DELIVERY));
					permissions.setPermitAcceptReturns(rs.getBoolean(ShopStaffPermissions.ACCEPT_RETURNS));



					permissions.setRt_distance(rs.getFloat("distance"));

					user.setRt_shop_staff_permissions(permissions);

					itemList.add(user);
				}

				endPoint.setResults(itemList);

			}





			if(getRowCount)
			{
				statementCount = connection.prepareStatement(queryCount);

				i = 0;

				if(gender!=null)
				{
					statementCount.setObject(++i,gender);
				}


				resultSetCount = statementCount.executeQuery();

				while(resultSetCount.next())
				{
					endPoint.setItemCount(resultSetCount.getInt("item_count"));
				}
			}






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

				if(connection!=null)
				{connection.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return endPoint;
	}





}
