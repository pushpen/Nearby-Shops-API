package org.nearbyshops.DAORoles;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.ModelRoles.PhoneVerificationCode;
import org.nearbyshops.ModelRoles.User;

import java.sql.*;

/**
 * Created by sumeet on 22/4/17.
 */
public class DAOPhoneVerificationCodes {

    private HikariDataSource dataSource = Globals.getDataSource();



    public boolean checkPhoneVerificationCode(String phone, String verificationCode)
    {


        String query = "SELECT " + PhoneVerificationCode.PHONE + ""
                + " FROM "   + PhoneVerificationCode.TABLE_NAME
                + " WHERE "  + PhoneVerificationCode.PHONE + " = ? "
                + " AND "    + PhoneVerificationCode.VERIFICATION_CODE + " = ? "
                + " AND "    + PhoneVerificationCode.TIMESTAMP_EXPIRES + " > now()";



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
            statement.setString(++i,phone);
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









    public PhoneVerificationCode checkPhoneVerificationCode(String phone)
    {

        String query = "SELECT "

                + PhoneVerificationCode.PHONE_CODE_ID + ","
                + PhoneVerificationCode.PHONE + ","
                + PhoneVerificationCode.VERIFICATION_CODE + ","
                + PhoneVerificationCode.TIMESTAMP_EXPIRES + ""

                + " FROM " + PhoneVerificationCode.TABLE_NAME
                + " WHERE " + PhoneVerificationCode.PHONE + " = ?"
                + " AND "    + PhoneVerificationCode.TIMESTAMP_EXPIRES + " > now()";



        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;


        //Distributor distributor = null;
        PhoneVerificationCode verificationCode = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);

            int i = 0;


            statement.setString(++i,phone); // email

            rs = statement.executeQuery();

            while(rs.next())
            {
                verificationCode = new PhoneVerificationCode();

                verificationCode.setPhone(phone);
                verificationCode.setTimestampExpires(rs.getTimestamp(PhoneVerificationCode.TIMESTAMP_EXPIRES));
                verificationCode.setVerificationCode(rs.getString(PhoneVerificationCode.VERIFICATION_CODE));

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





    public int insertPhoneVerificationCode(String phone, String verificationCode,
                                           Timestamp expires,
                                           boolean getRowCount)
    {

        Connection connection = null;
        PreparedStatement statement = null;

        int idOfInsertedRow = -1;
        int rowCountItems = -1;

        String insertItemSubmission =

                "INSERT INTO " + PhoneVerificationCode.TABLE_NAME
                         + "(" + PhoneVerificationCode.PHONE + ","
                                + PhoneVerificationCode.VERIFICATION_CODE + ","
                                + PhoneVerificationCode.TIMESTAMP_EXPIRES + ""
                + ") values(?,?,?)"
                + " ON CONFLICT (" + PhoneVerificationCode.PHONE + ")"
                + " DO UPDATE "
                + " SET " + PhoneVerificationCode.VERIFICATION_CODE + "= excluded." + PhoneVerificationCode.VERIFICATION_CODE + " , "
                          + PhoneVerificationCode.TIMESTAMP_EXPIRES + "= excluded." + PhoneVerificationCode.TIMESTAMP_EXPIRES;

//        + EmailVerificationCode.TABLE_NAME


        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            statement = connection.prepareStatement(insertItemSubmission,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;

            statement.setString(++i,phone);
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
