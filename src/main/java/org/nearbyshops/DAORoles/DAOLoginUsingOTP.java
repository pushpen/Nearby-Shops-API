package org.nearbyshops.DAORoles;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.GlobalConstants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.ModelAnalytics.ItemAnalytics;
import org.nearbyshops.ModelRoles.User;

import java.sql.*;

public class DAOLoginUsingOTP {

    private HikariDataSource dataSource = Globals.getDataSource();




    public int upsertUserProfile(User userProfile,
                                 boolean getRowCount)
    {

        Connection connection = null;
        PreparedStatement statement = null;

        int idOfInsertedRow = -1;
        int rowCountItems = -1;



        String insertItemSubmission =

                "INSERT INTO " + User.TABLE_NAME
                        + "(" + User.PHONE + ","
                        + User.ROLE + ","
                        + User.PASSWORD + ""
                        + ") values(?,?,?)"
                        + " ON CONFLICT (" + User.PHONE + ")"
                        + " DO UPDATE "
                        + " SET " + User.PASSWORD + " = " + " excluded." + User.PASSWORD + "";


        //        + EmailVerificationCode.TABLE_NAME




        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            statement = connection.prepareStatement(insertItemSubmission,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;

            statement.setObject(++i,userProfile.getPhone());
            statement.setObject(++i, GlobalConstants.ROLE_END_USER_CODE);
            statement.setObject(++i,userProfile.getPassword());





            rowCountItems = statement.executeUpdate();


            ResultSet rs = statement.getGeneratedKeys();

            if(rs.next())
            {
                idOfInsertedRow = rs.getInt(1);
            }



            connection.commit();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            if (connection != null) {
                try {

                    idOfInsertedRow=-1;
                    rowCountItems = 0;

                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
        finally
        {

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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

}
