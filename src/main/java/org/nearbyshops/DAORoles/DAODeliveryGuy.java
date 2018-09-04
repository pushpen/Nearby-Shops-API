package org.nearbyshops.DAORoles;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.GlobalConstants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.ModelRoles.DeliveryGuyData;
import org.nearbyshops.ModelRoles.Endpoints.UserEndpoint;
import org.nearbyshops.ModelRoles.ShopStaffPermissions;
import org.nearbyshops.ModelRoles.StaffPermissions;
import org.nearbyshops.ModelRoles.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class DAODeliveryGuy {


	private HikariDataSource dataSource = Globals.getDataSource();


	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}






	public int getShopIDforDeliveryGuy(int staffUserID) {

		String query = "SELECT " + DeliveryGuyData.SHOP_ID + ""
					+ " FROM "   + DeliveryGuyData.TABLE_NAME
					+ " WHERE "  + DeliveryGuyData.STAFF_USER_ID + " = ?";



		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;


		//Distributor distributor = null;
		int shopID = -1;

		try {

			connection = dataSource.getConnection();
			statement = connection.prepareStatement(query);

			statement.setObject(1,staffUserID);

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




	public int updateDeliveryGuyBySelf(User user)
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






	public int updateDeliveryGuyByAdmin(User user)
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






//		String updatePermissions = "UPDATE " + DeliveryGuyData.TABLE_NAME
//				+ " SET " + DeliveryGuyData.CURRENT_BALANCE + "=?"
//
//				+ " WHERE " + DeliveryGuyData.STAFF_USER_ID + " = ?";




		Connection connection = null;
		PreparedStatement statement = null;
		PreparedStatement statementDeliveryGuyData = null;

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
//			System.out.println("Total rows updated: " + rowCountUpdated);


//			statementDeliveryGuyData = connection.prepareStatement(updatePermissions,PreparedStatement.RETURN_GENERATED_KEYS);
//			i = 0;
//
//			DeliveryGuyData data = user.getRt_delivery_guy_data();
//
//
//			if(data!=null)
//			{
//				statementDeliveryGuyData.setObject(++i,data.getLatCurrent());
//				statementDeliveryGuyData.setObject(++i,data.getLonCurrent());
//				statementDeliveryGuyData.setObject(++i,data.getCurrentBalance());
//
//				statementDeliveryGuyData.setObject(++i,user.getUserID());
//
//				statementDeliveryGuyData.executeUpdate();
//			}






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




	public int updateDeliveryGuyLocation(DeliveryGuyData data)
	{


		String updateLocation = "UPDATE " + DeliveryGuyData.TABLE_NAME
				             + " SET " + DeliveryGuyData.LAT_CURRENT + "=?,"
									  + DeliveryGuyData.LON_CURRENT + "=?"
							 + " WHERE " + DeliveryGuyData.STAFF_USER_ID + " = ?";





//		String insertStaffPermissions =
//
//				"INSERT INTO " + DeliveryGuyData.TABLE_NAME
//						+ "("
//						+ ShopStaffPermissions.STAFF_ID + ","
//						+ ShopStaffPermissions.LAT_CURRENT + ","
//						+ ShopStaffPermissions.LON_CURRENT + ""
//						+ ") values(?,?,?)"
//						+ " ON CONFLICT (" + ShopStaffPermissions.STAFF_ID + ")"
//						+ " DO UPDATE "
//						+ " SET "
//						+ ShopStaffPermissions.LAT_CURRENT + "= excluded." + ShopStaffPermissions.LAT_CURRENT + " , "
//						+ ShopStaffPermissions.LON_CURRENT + "= excluded." + ShopStaffPermissions.LON_CURRENT;




		Connection connection = null;
		PreparedStatement statement = null;

		int rowCountUpdated = 0;

		try {

			connection = dataSource.getConnection();
			connection.setAutoCommit(false);


			statement = connection.prepareStatement(updateLocation,PreparedStatement.RETURN_GENERATED_KEYS);
			int i = 0;


			if(data!=null)
			{
				statement.setObject(++i,data.getLatCurrent());
				statement.setObject(++i,data.getLonCurrent());
				statement.setObject(++i,data.getStaffUserID());

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




	public DeliveryGuyData getDeliveryGuyData(int userID)
	{

		boolean isFirst = true;

		String query = "SELECT "

				+ DeliveryGuyData.LAT_CURRENT + ","
				+ DeliveryGuyData.LON_CURRENT + ","
				+ DeliveryGuyData.IS_EMPLOYED_BY_SHOP + ","
				+ DeliveryGuyData.SHOP_ID + ","
				+ DeliveryGuyData.CURRENT_BALANCE + ","

				+ " FROM "  + DeliveryGuyData.TABLE_NAME
				+ " WHERE " + DeliveryGuyData.STAFF_USER_ID  + " = ? ";



		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;


		//Distributor distributor = null;
		DeliveryGuyData deliveryGuyData = null;

		try {

			connection = dataSource.getConnection();
			statement = connection.prepareStatement(query);

			int i = 0;


			statement.setObject(++i,userID); // username


			rs = statement.executeQuery();

			while(rs.next())
			{
				deliveryGuyData = new DeliveryGuyData();

				deliveryGuyData.setLatCurrent(rs.getDouble(DeliveryGuyData.LAT_CURRENT));
				deliveryGuyData.setLonCurrent(rs.getDouble(DeliveryGuyData.LON_CURRENT));
				deliveryGuyData.setEmployedByShop(rs.getBoolean(DeliveryGuyData.IS_EMPLOYED_BY_SHOP));
				deliveryGuyData.setShopID(rs.getInt(DeliveryGuyData.SHOP_ID));
				deliveryGuyData.setCurrentBalance(rs.getDouble(DeliveryGuyData.CURRENT_BALANCE));

				deliveryGuyData.setStaffUserID(rs.getInt(ShopStaffPermissions.STAFF_ID));

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

		return deliveryGuyData;
	}





	public UserEndpoint getDeliveryGuyForShopAdmin(
			Double latPickUp, Double lonPickUp,
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
				+ latPickUp + ")) * cos( radians(" +  DeliveryGuyData.LAT_CURRENT +  ") ) * cos(radians(" + DeliveryGuyData.LON_CURRENT +  ") - radians("
				+ lonPickUp + "))"
				+ " + sin( radians(" + latPickUp + ")) * sin(radians(" + DeliveryGuyData.LAT_CURRENT + "))) as distance" + ","

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

				+ DeliveryGuyData.TABLE_NAME + "." + DeliveryGuyData.LAT_CURRENT + ","
				+ DeliveryGuyData.TABLE_NAME + "." + DeliveryGuyData.LON_CURRENT + ","
				+ DeliveryGuyData.TABLE_NAME + "." + DeliveryGuyData.IS_EMPLOYED_BY_SHOP + ","
				+ DeliveryGuyData.TABLE_NAME + "." + DeliveryGuyData.SHOP_ID + ","
				+ DeliveryGuyData.TABLE_NAME + "." + DeliveryGuyData.CURRENT_BALANCE + ""

				+ " FROM " + User.TABLE_NAME
				+ " LEFT OUTER JOIN " + DeliveryGuyData.TABLE_NAME + " ON (" + DeliveryGuyData.TABLE_NAME + "." + DeliveryGuyData.STAFF_USER_ID + " = " + User.TABLE_NAME + "." + User.USER_ID + ")"
				+ " WHERE " + DeliveryGuyData.TABLE_NAME + "." + DeliveryGuyData.SHOP_ID + " = ? "
				+ " AND ( " + User.TABLE_NAME + "." + User.ROLE + " = " + GlobalConstants.ROLE_DELIVERY_GUY_SELF_CODE + " ) ";



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
				+ DeliveryGuyData.TABLE_NAME + "." + DeliveryGuyData.DATA_ID + ","
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


					DeliveryGuyData deliveryGuyData = new DeliveryGuyData();

					deliveryGuyData.setLatCurrent(rs.getDouble(DeliveryGuyData.LAT_CURRENT));
					deliveryGuyData.setLonCurrent(rs.getDouble(DeliveryGuyData.LON_CURRENT));
					deliveryGuyData.setEmployedByShop(rs.getBoolean(DeliveryGuyData.IS_EMPLOYED_BY_SHOP));
					deliveryGuyData.setShopID(rs.getInt(DeliveryGuyData.SHOP_ID));
					deliveryGuyData.setCurrentBalance(rs.getDouble(DeliveryGuyData.CURRENT_BALANCE));
//					deliveryGuyData.setStaffUserID(rs.getInt(DeliveryGuyData.STAFF_USER_ID));

					deliveryGuyData.setRt_distance(rs.getDouble("distance"));





					user.setRt_delivery_guy_data(deliveryGuyData);

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






}
