package org.nearbyshops.DAORoles;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.ModelRoles.EmailVerificationCode;
import org.nearbyshops.ModelRoles.User;

import java.sql.*;

/**
 * Created by sumeet on 22/4/17.
 */
public class DAOEmailVerificationCodes {

    private HikariDataSource dataSource = Globals.getDataSource();




    public boolean checkEmailVerificationCode(String email, String verificationCode)
    {


        String query = "SELECT " + EmailVerificationCode.EMAIL + ""
                + " FROM "   + EmailVerificationCode.TABLE_NAME
                + " WHERE "  + EmailVerificationCode.EMAIL + " = ? "
                + " AND "    + EmailVerificationCode.VERIFICATION_CODE + " = ? "
                + " AND "    + EmailVerificationCode.TIMESTAMP_EXPIRES + " > now()";



        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;


        //Distributor distributor = null;
        User user = null;

        try {

//            System.out.println(query);

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);

            int i = 0;
            statement.setString(++i,email);
            statement.setString(++i,verificationCode);


            rs = statement.executeQuery();

            while(rs.next())
            {
                return true;
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

        return false;
    }






    public EmailVerificationCode checkEmailVerificationCode(String email)
    {

        String query = "SELECT "

                + EmailVerificationCode.EMAIL_CODE_ID + ","
                + EmailVerificationCode.EMAIL + ","
                + EmailVerificationCode.VERIFICATION_CODE + ","
                + EmailVerificationCode.TIMESTAMP_EXPIRES + ""

                + " FROM " + EmailVerificationCode.TABLE_NAME
                + " WHERE " + EmailVerificationCode.EMAIL + " = ?"
                + " AND "    + EmailVerificationCode.TIMESTAMP_EXPIRES + " > now()";



        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;


        //Distributor distributor = null;
        EmailVerificationCode verificationCode = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);

            int i = 0;


            statement.setString(++i,email); // email

            rs = statement.executeQuery();

            while(rs.next())
            {
                verificationCode = new EmailVerificationCode();

                verificationCode.setEmail(email);
                verificationCode.setTimestampExpires(rs.getTimestamp(EmailVerificationCode.TIMESTAMP_EXPIRES));
                verificationCode.setVerificationCode(rs.getString(EmailVerificationCode.VERIFICATION_CODE));

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

        return verificationCode;
    }






//    public int updateEmailVerificationCode(String email, String verificationCode, Timestamp expires)
//    {
//
//
//
//        String updateStatement = "UPDATE " + EmailVerificationCode.TABLE_NAME
//
//                + " SET "
//                + EmailVerificationCode.VERIFICATION_CODE + "=?,"
//                + EmailVerificationCode.TIMESTAMP_EXPIRES + "=?"
//
//                + " WHERE "
//                + "( "
//                + "(" + EmailVerificationCode.EMAIL + " = ?)"
//                + " AND "
//                + "( " + EmailVerificationCode.TIMESTAMP_EXPIRES + " < now() )"
//                + ")";
//
//
//        Connection connection = null;
//        PreparedStatement statement = null;
//
//        int rowCountUpdated = 0;
//
//        try {
//
//            connection = dataSource.getConnection();
//            statement = connection.prepareStatement(updateStatement);
//
//            int i = 0;
//
//            statement.setString(++i,verificationCode);
//            statement.setTimestamp(++i,expires);
//            statement.setString(++i,email);
//
//
//            rowCountUpdated = statement.executeUpdate();
//
//            System.out.println("Total rows updated: " + rowCountUpdated);
//
//
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        finally
//
//        {
//
//            try {
//
//                if(statement!=null)
//                {statement.close();}
//            } catch (SQLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//            try {
//
//                if(connection!=null)
//                {connection.close();}
//            } catch (SQLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//
//        return rowCountUpdated;
//    }




    public int insertEmailVerificationCode(String email, String verificationCode,
                                           Timestamp expires,
                                           boolean getRowCount)
    {

        Connection connection = null;
        PreparedStatement statement = null;

        int idOfInsertedRow = -1;
        int rowCountItems = -1;

        String insertItemSubmission =

                "INSERT INTO " + EmailVerificationCode.TABLE_NAME
                        + "(" + EmailVerificationCode.EMAIL + ","
                                + EmailVerificationCode.VERIFICATION_CODE + ","
                                + EmailVerificationCode.TIMESTAMP_EXPIRES + ""
                + ") values(?,?,?)"
                + " ON CONFLICT (" + EmailVerificationCode.EMAIL + ")"
                + " DO UPDATE "
                + " SET " + EmailVerificationCode.VERIFICATION_CODE + "= excluded." + EmailVerificationCode.VERIFICATION_CODE + " , "
                          + EmailVerificationCode.TIMESTAMP_EXPIRES + "= excluded." + EmailVerificationCode.TIMESTAMP_EXPIRES;

//        + EmailVerificationCode.TABLE_NAME


        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            statement = connection.prepareStatement(insertItemSubmission,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;

            statement.setString(++i,email);
            statement.setString(++i,verificationCode);
            statement.setTimestamp(++i,expires);

//            statement.setString(++i,email);

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
