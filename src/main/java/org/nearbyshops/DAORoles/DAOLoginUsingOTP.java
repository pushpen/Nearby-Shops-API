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






    public int upsertUserProfileNew(User userProfile,
                                 boolean getRowCount)
    {

        Connection connection = null;
        PreparedStatement statement = null;

        int idOfInsertedRow = -1;
        int rowCountItems = -1;



        String insertItemSubmission =

                "INSERT INTO " + User.TABLE_NAME
                        + "("
                        + User.PHONE + ","
                        + User.E_MAIL + ","
                        + User.ROLE + ","
                        + User.PASSWORD + ""
                        + ") values(?,?,?,?)"
                        + " ON CONFLICT (" + User.PHONE+ "," + User.E_MAIL + ")"
                        + " DO UPDATE "
                        + " SET " + User.PASSWORD + " = " + " excluded." + User.PASSWORD + "";


        //        + EmailVerificationCode.TABLE_NAME




        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            statement = connection.prepareStatement(insertItemSubmission,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;


            statement.setObject(++i,userProfile.getPhone());
            statement.setObject(++i,userProfile.getEmail());
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









    public User checkUserExists(String email, String phone)
    {

        boolean isFirst = true;

//
//        String queryPassword = "SELECT "
//
//                + User.USER_ID + ","
//                + User.USERNAME + ","
//                + User.ROLE + ""
//
//                + " FROM " + User.TABLE_NAME
//                + " WHERE "
//                + " ( " + User.USERNAME + " = ? "
//                + " OR " + " CAST ( " +  User.USER_ID + " AS text ) " + " = ? "
//                + " OR " + " ( " + User.E_MAIL + " = ?" + ")"
//                + " OR " + " ( " + User.PHONE + " = ?" + ")" + ")";


        String queryPassword = "SELECT "

                + User.USER_ID + ","
                + User.USERNAME + ","
                + User.ROLE + ""

                + " FROM " + User.TABLE_NAME
                + " WHERE "
                + " ( " + " ( " + User.E_MAIL + " = ?" + ")"
                + " OR " + " ( " + User.PHONE + " = ?" + ")" + ")";



//        CAST (" + User.TIMESTAMP_TOKEN_EXPIRES + " AS TIMESTAMP)"



        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;


        //Distributor distributor = null;
        User user = null;

        try {

//            System.out.println(query);

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(queryPassword);

            int i = 0;
//            statement.setString(++i,username); // username
//            statement.setString(++i,username); // userID
            statement.setString(++i,email); // email
            statement.setString(++i,phone); // phone
//            statement.setTimestamp(++i,new Timestamp(System.currentTimeMillis()));


            rs = statement.executeQuery();

            while(rs.next())
            {
                user = new User();

                user.setUserID(rs.getInt(User.USER_ID));
                user.setUsername(rs.getString(User.USERNAME));
                user.setRole(rs.getInt(User.ROLE));
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

        return user;
    }





    public int updateUserProfile(User user)
    {

//        + User.USERNAME + " = ?"




//        Gson gson = new Gson();
//        System.out.println(gson.toJson(user) + "\nOldPassword : " + oldPassword);


        String updateStatement = "UPDATE " + User.TABLE_NAME

                + " SET "
                + User.PASSWORD + "=?,"

                + User.E_MAIL + "=?,"
                + User.PHONE + "=?,"
                + User.NAME + "=?,"
                + User.GENDER + "=?,"

                + User.IS_ACCOUNT_PRIVATE + "=?,"
                + User.ABOUT + "=?"

                + " WHERE " + " ( " + " ( " + User.E_MAIL + " = ?" + ")" + " OR " + " ( " + User.PHONE + " = ?" + ")" + ")";





        Connection connection = null;
        PreparedStatement statement = null;

        int rowCountUpdated = 0;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);

            int i = 0;

//            statement.setString(++i,user.getToken());
//            statement.setTimestamp(++i,user.getTimestampTokenExpires());

            statement.setString(++i,user.getPassword());
            statement.setString(++i,user.getEmail());
            statement.setString(++i,user.getPhone());
            statement.setString(++i,user.getName());
            statement.setBoolean(++i,user.getGender());

            statement.setBoolean(++i,user.isAccountPrivate());
            statement.setString(++i,user.getAbout());



            statement.setString(++i,user.getEmail());
            statement.setString(++i,user.getPhone());


            rowCountUpdated = statement.executeUpdate();

//            System.out.println("Profile Updated Using GLobal : Total rows updated: " + rowCountUpdated);


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




    public int insertUserProfile(User user, boolean getRowCount)
    {

        Connection connection = null;
        PreparedStatement statement = null;

        int idOfInsertedRow = -1;
        int rowCountItems = -1;

        String insertItemSubmission = "INSERT INTO "
                + User.TABLE_NAME
                + "("
                + User.ROLE + ","
                + User.PASSWORD + ","

                + User.E_MAIL + ","
                + User.PHONE + ","
                + User.NAME + ","
                + User.GENDER + ","

                + User.IS_ACCOUNT_PRIVATE + ","
                + User.ABOUT + ""
                + ") values(?,?, ?,?,?,?, ?,?)";




        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            statement = connection.prepareStatement(insertItemSubmission,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;

            statement.setObject(++i,GlobalConstants.ROLE_END_USER_CODE);
            statement.setString(++i,user.getPassword());

            statement.setString(++i,user.getEmail());
            statement.setString(++i,user.getPhone());
            statement.setString(++i,user.getName());
            statement.setBoolean(++i,user.getGender());

            statement.setBoolean(++i,user.isAccountPrivate());
            statement.setString(++i,user.getAbout());



            rowCountItems = statement.executeUpdate();


            ResultSet rs = statement.getGeneratedKeys();

            if(rs.next())
            {
                idOfInsertedRow = rs.getInt(1);
            }


            connection.commit();





            System.out.println("Profile Created using Global Profile : Row count : " + rowCountItems);


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
