package org.nearbyshops.DAORoles;

import com.google.gson.Gson;
import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.ModelRoles.EmailVerificationCode;
import org.nearbyshops.ModelRoles.PhoneVerificationCode;
import org.nearbyshops.ModelRoles.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sumeet on 21/4/17.
 */
public class DAOUserNew {

    private HikariDataSource dataSource = Globals.getDataSource();


    // insert user : check if email or phone is verified
        // registerUsingEmail
        // RegisterUsingPhone
        // RegisterUsingUsername
    // update user : email, phone, password update is excluded because they have special requirements for update
    // update email : check while update whether email is verified or not
    // update phone : check while update whether phone is verified or not
    // update password (Change password by user) : check old password while updating


    // generate email verification code : checks code is expired or not and if yes then generates new
        // check email verification code : check if verification code exist and not expired for the given e-mail
        // update email verification code : if the previous code expired or does not exist

    // update password (Forgot password): update password using reset code

    // delete user

    // verify user | for authentication filter
    // get profile | for login

    // check username exists | to check username unique at time of registration
    // check email exists | to check email unique at time of registration
    // check phone exists | to check phone unique at time of registration
    // check email verification code correct | to check verification code at time of registration
    // check phone verification code correct | to check verification code at time of registration

    // check google ID | to check whether the person is registered or not
    // save google profile | create google account




    public int updateEmail(User user)
    {

        Connection connection = null;
        PreparedStatement statement = null;

//        int idOfInsertedRow = -1;
        int rowCountItems = -1;

        String insertItemSubmission = " UPDATE " + User.TABLE_NAME
                                    + " SET "    + User.E_MAIL + " = " + EmailVerificationCode.TABLE_NAME + "." + EmailVerificationCode.EMAIL
                                    + " FROM "   + EmailVerificationCode.TABLE_NAME
                                    + " WHERE "  + EmailVerificationCode.TABLE_NAME + "." + EmailVerificationCode.EMAIL + " = ? "
                                    + " AND "    + EmailVerificationCode.TABLE_NAME + "." + EmailVerificationCode.VERIFICATION_CODE + " = ?"
                                    + " AND "    + EmailVerificationCode.TABLE_NAME + "." + EmailVerificationCode.TIMESTAMP_EXPIRES + " > now()"
                                    + " AND "    + User.TABLE_NAME + "." + User.USER_ID + " = ?"
                                    + " AND "    + User.TABLE_NAME + "." + User.PASSWORD + " = ?";


        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            statement = connection.prepareStatement(insertItemSubmission,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;


            // check email is verification code to ensure email belongs to user
            statement.setString(++i,user.getEmail());
            statement.setString(++i,user.getRt_email_verification_code());
            statement.setObject(++i,user.getUserID());
            statement.setString(++i,user.getPassword());


            rowCountItems = statement.executeUpdate();

            connection.commit();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            if (connection != null) {
                try {

//                    idOfInsertedRow=-1;
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



        return rowCountItems;
    }



    public int updatePhone(User user)
    {

        Connection connection = null;
        PreparedStatement statement = null;

//        int idOfInsertedRow = -1;
        int rowCountItems = -1;

        String insertItemSubmission = " UPDATE " + User.TABLE_NAME
                + " SET "    + User.PHONE + " = " + PhoneVerificationCode.TABLE_NAME + "." + PhoneVerificationCode.PHONE
                + " FROM "   + PhoneVerificationCode.TABLE_NAME
                + " WHERE "  + PhoneVerificationCode.TABLE_NAME + "." + PhoneVerificationCode.PHONE + " = ? "
                + " AND "    + PhoneVerificationCode.TABLE_NAME + "." + PhoneVerificationCode.VERIFICATION_CODE + " = ?"
                + " AND "    + PhoneVerificationCode.TABLE_NAME + "." + PhoneVerificationCode.TIMESTAMP_EXPIRES + " > now()"
                + " AND "    + User.TABLE_NAME + "." + User.USER_ID + " = ?"
                + " AND "    + User.TABLE_NAME + "." + User.PASSWORD + " = ?";


        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            statement = connection.prepareStatement(insertItemSubmission,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;


            // check email is verification code to ensure email belongs to user
            statement.setString(++i,user.getPhone());
            statement.setString(++i,user.getRt_phone_verification_code());
            statement.setObject(++i,user.getUserID());
            statement.setString(++i,user.getPassword());


            rowCountItems = statement.executeUpdate();

            connection.commit();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            if (connection != null) {
                try {

//                    idOfInsertedRow=-1;
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



        return rowCountItems;
    }



    public User getProfile(int userID)
    {

        boolean isFirst = true;

        String query = "SELECT "

                + User.USER_ID + ","
                + User.USERNAME + ","
                + User.E_MAIL + ","
                + User.PHONE + ","
                + User.NAME + ","
                + User.GENDER + ","
                + User.PROFILE_IMAGE_URL + ","
                + User.ROLE + ","
                + User.IS_ACCOUNT_PRIVATE + ","
                + User.ABOUT + ""

                + " FROM " + User.TABLE_NAME
                + " WHERE " + User.USER_ID  + " = ? ";



        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;


        //Distributor distributor = null;
        User user = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);

            int i = 0;


            statement.setObject(++i,userID); // username


            rs = statement.executeQuery();

            while(rs.next())
            {
                user = new User();

                user.setUserID(rs.getInt(User.USER_ID));
                user.setUsername(rs.getString(User.USERNAME));
                user.setEmail(rs.getString(User.E_MAIL));
                user.setPhone(rs.getString(User.PHONE));
                user.setName(rs.getString(User.NAME));
                user.setGender(rs.getBoolean(User.GENDER));
                user.setProfileImagePath(rs.getString(User.PROFILE_IMAGE_URL));
                user.setRole(rs.getInt(User.ROLE));
                user.setAccountPrivate(rs.getBoolean(User.IS_ACCOUNT_PRIVATE));
                user.setAbout(rs.getString(User.ABOUT));
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

        return user;
    }



    public User getProfile(String username, String password)
    {

        boolean isFirst = true;

        String query = "SELECT "

                + User.USER_ID + ","
                + User.USERNAME + ","
//                + User.PASSWORD + ","
                + User.E_MAIL + ","
                + User.PHONE + ","
//                + User.IS_PHONE_VERIFIED + ","
                + User.NAME + ","
                + User.GENDER + ","
                + User.PROFILE_IMAGE_URL + ","
                + User.ROLE + ","
                + User.IS_ACCOUNT_PRIVATE + ","
                + User.ABOUT + ","
                + User.SERVICE_ACCOUNT_BALANCE + ","
                + User.IS_ACCOUNT_PRIVATE + ""
//                + User.TOKEN + ","
//                + User.TIMESTAMP_TOKEN_EXPIRES + ""

                + " FROM " + User.TABLE_NAME
                + " WHERE "
                + " ( " + User.USERNAME + " = ? "
                + " OR " + " CAST ( " +  User.USER_ID + " AS text ) " + " = ? "
                + " OR " + " ( " + User.E_MAIL + " = ?" + ")"
                + " OR " + " ( " + User.PHONE + " = ?" + ")"
                + ")"
                + " AND " + User.PASSWORD + " = ? ";



//                + " ( " + User.USERNAME + " = ? "
//                + " OR " + " CAST ( " +  User.USER_ID + " AS text ) " + " = ? )";

//        CAST (" + User.TIMESTAMP_TOKEN_EXPIRES + " AS TIMESTAMP)"



        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;


        //Distributor distributor = null;
        User user = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);

            int i = 0;


            statement.setString(++i,username); // username
            statement.setString(++i,username); // userID
            statement.setString(++i,username); // email
            statement.setString(++i,username); // phone
            statement.setString(++i,password); // password


//            statement.setString(++i,username); // username
//            statement.setString(++i,username); // userID


            rs = statement.executeQuery();

            while(rs.next())
            {
                user = new User();

                user.setUserID(rs.getInt(User.USER_ID));
                user.setUsername(rs.getString(User.USERNAME));
//                user.setPassword(rs.getString(User.PASSWORD));
                user.setEmail(rs.getString(User.E_MAIL));
                user.setPhone(rs.getString(User.PHONE));
//                user.setPhoneVerified(rs.getBoolean(User.IS_PHONE_VERIFIED));
                user.setName(rs.getString(User.NAME));
                user.setGender(rs.getBoolean(User.GENDER));
                user.setProfileImagePath(rs.getString(User.PROFILE_IMAGE_URL));
                user.setRole(rs.getInt(User.ROLE));
                user.setAccountPrivate(rs.getBoolean(User.IS_ACCOUNT_PRIVATE));
                user.setServiceAccountBalance(rs.getDouble(User.SERVICE_ACCOUNT_BALANCE));
                user.setAbout(rs.getString(User.ABOUT));

//                user.setToken(rs.getString(User.TOKEN));
//                user.setTimestampTokenExpires(rs.getTimestamp(User.TIMESTAMP_TOKEN_EXPIRES));

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

        return user;
    }




    public User checkGoogleID(String googleID) {


        String query = "SELECT "

                + User.USER_ID + ","
                + User.USERNAME + ","
                + User.PASSWORD + ","
                + User.E_MAIL + ","
                + User.PHONE + ","
                + User.NAME + ","
                + User.GENDER + ","
//                + User.PROFILE_IMAGE_ID + ","
                + User.IS_ACCOUNT_PRIVATE + ","
                + User.ABOUT + ""

                + " FROM " + User.TABLE_NAME;




        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        System.out.println("Checked Google ID : " + googleID);


        User user = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);

            statement.setObject(1,googleID);

            rs = statement.executeQuery();


            while(rs.next())
            {
                user = new User();

                user.setUserID(rs.getInt(User.USER_ID));
                user.setUsername(rs.getString(User.USERNAME));
                user.setPassword(rs.getString(User.PASSWORD));
                user.setEmail(rs.getString(User.E_MAIL));
                user.setPhone(rs.getString(User.PHONE));
                user.setName(rs.getString(User.NAME));
                user.setGender(rs.getBoolean(User.GENDER));
//                user.setProfileImageID(rs.getInt(User.PROFILE_IMAGE_ID));
                user.setAccountPrivate(rs.getBoolean(User.IS_ACCOUNT_PRIVATE));
                user.setAbout(rs.getString(User.ABOUT));
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

        return user;
    }




    public int saveGoogleProfile(User user, boolean getRowCount)
    {

        Connection connection = null;
        PreparedStatement statement = null;

        int idOfInsertedRow = -1;
        int rowCountItems = -1;

        String insertItemSubmission = "INSERT INTO "
                + User.TABLE_NAME
                + "("

                + User.USERNAME + ","
                + User.PASSWORD + ","
                + User.E_MAIL + ","

                + User.PHONE + ","
                + User.NAME + ","
                + User.GENDER + ","

//                + User.PROFILE_IMAGE_ID + ","
                + User.ROLE + ","
                + User.IS_ACCOUNT_PRIVATE + ","

                + User.TOKEN + ","
                + User.TIMESTAMP_TOKEN_EXPIRES + ","

                + User.ABOUT + ""


                + ") VALUES(?,?,? ,?,?,? ,?,?,?  ,?,?, ?)";


        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            statement = connection.prepareStatement(insertItemSubmission,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;

            statement.setString(++i,user.getUsername());
            statement.setString(++i,user.getPassword());
            statement.setString(++i,user.getEmail());

            statement.setString(++i,user.getPhone());
            statement.setString(++i,user.getName());
            statement.setObject(++i,user.getGender());

//            statement.setInt(++i,user.getProfileImageID());
            statement.setObject(++i,user.getRole());
            statement.setObject(++i,user.isAccountPrivate());

            statement.setString(++i,user.getToken());
            statement.setTimestamp(++i,user.getTimestampTokenExpires());

            statement.setString(++i,user.getAbout());

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






    public int updateEndUser(User user)
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





    public int updateShopAdmin(User user)
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



    public int updatePassword(User user, String oldPassword)
    {

//        + User.USERNAME + " = ?"


        Gson gson = new Gson();
        System.out.println(gson.toJson(user) + "\nOldPassword : " + oldPassword);


        String updateStatement = "UPDATE " + User.TABLE_NAME

                + " SET "
                + User.PASSWORD + "=?"

                + " WHERE "
                + " ( " + User.USERNAME + " = ? "
                + " OR " +  User.USER_ID + " = ? "
                + " OR " + " ( " + User.E_MAIL + " = ?" + ")"
                + " OR " + " ( " + User.PHONE + " = ?" + ")"
                + ")"
                + " AND " + User.PASSWORD + " = ?";


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


            statement.setString(++i,user.getUsername());
            statement.setObject(++i,user.getUserID());
            statement.setString(++i,user.getEmail());
            statement.setString(++i,user.getPhone());

            statement.setString(++i,oldPassword);


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





    public int updateToken(User user)
    {




        String updateStatement = "UPDATE " + User.TABLE_NAME

                + " SET "
                + User.TOKEN + "=?,"
                + User.TIMESTAMP_TOKEN_EXPIRES + "=?"

                + " WHERE "
                + " ( " + User.USERNAME + " = ? "
                + " OR " +  User.USER_ID + " = ? "
                + " OR " + " ( " + User.E_MAIL + " = ?" + ")"
                + " OR " + " ( " + User.PHONE + " = ?" + ")"
                + ")"
                + " AND " + User.PASSWORD + " = ? ";

//        + " OR " + " CAST ( " +  User.USER_ID + " AS text ) " + " = ? "
//                + User.USERNAME + " = ?"
//                + " AND " + User.PASSWORD + " = ?";


        Connection connection = null;
        PreparedStatement statement = null;

        int rowCountUpdated = 0;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);

            int i = 0;

            statement.setString(++i,user.getToken());
            statement.setTimestamp(++i,user.getTimestampTokenExpires());

//            statement.setString(++i,username); // username
//            statement.setString(++i,username); // userID
//            statement.setString(++i,username); // email
//            statement.setString(++i,username); // phone
//            statement.setString(++i,token); // token

            statement.setString(++i,user.getUsername());
            statement.setObject(++i,user.getUserID());
            statement.setString(++i,user.getEmail());
            statement.setString(++i,user.getPhone());

            statement.setString(++i,user.getPassword());

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






    public User verifyUser(String username, String token)
    {

        boolean isFirst = true;

        String queryToken = "SELECT "

                + User.USER_ID + ","
                + User.USERNAME + ","
                + User.ROLE + ""

                + " FROM " + User.TABLE_NAME
                + " WHERE "
                + " ( " + User.USERNAME + " = ? "
                + " OR " + " CAST ( " +  User.USER_ID + " AS text ) " + " = ? "
                + " OR " + " ( " + User.E_MAIL + " = ?" + ")"
                + " OR " + " ( " + User.PHONE + " = ?" + ")" + ")"
                + " AND " + User.TOKEN + " = ? "
                + " AND " + User.TIMESTAMP_TOKEN_EXPIRES + " > now()";



        String queryPassword = "SELECT "

                + User.USER_ID + ","
                + User.USERNAME + ","
                + User.ROLE + ""

                + " FROM " + User.TABLE_NAME
                + " WHERE "
                + " ( " + User.USERNAME + " = ? "
                + " OR " + " CAST ( " +  User.USER_ID + " AS text ) " + " = ? "
                + " OR " + " ( " + User.E_MAIL + " = ?" + ")"
                + " OR " + " ( " + User.PHONE + " = ?" + ")" + ")"
                + " AND " + User.PASSWORD + " = ? ";



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
            statement.setString(++i,username); // username
            statement.setString(++i,username); // userID
            statement.setString(++i,username); // email
            statement.setString(++i,username); // phone
            statement.setString(++i,token); // token
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



}


