package org.nearbyshops.DAORoles;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.ModelRoles.ShopStaffPermissions;

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

			shopID = rs.getInt(ShopStaffPermissions.SHOP_ID);


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




}
