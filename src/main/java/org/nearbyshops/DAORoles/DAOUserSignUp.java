package org.nearbyshops.DAORoles;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.GlobalConstants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Shop;
import org.nearbyshops.ModelBilling.Transaction;
import org.nearbyshops.ModelRoles.EmailVerificationCode;
import org.nearbyshops.ModelRoles.PhoneVerificationCode;
import org.nearbyshops.ModelRoles.ShopStaffPermissions;
import org.nearbyshops.ModelRoles.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sumeet on 14/8/17.
 */
public class DAOUserSignUp {




    private HikariDataSource dataSource = Globals.getDataSource();



    public int registerUsingEmailNoCredits(
            User user, boolean getRowCount
    )
    {

        Connection connection = null;
        PreparedStatement statement = null;
        PreparedStatement statementInsertVehicle = null;


        int idOfInsertedRow = -1;
        int rowCountItems = -1;


        String insertItemSubmission = "";
        String insertVehicle = "";



        insertItemSubmission = "INSERT INTO "
                + User.TABLE_NAME
                + "("

                + User.PASSWORD + ","
                + User.E_MAIL + ","

                + User.NAME + ","
                + User.GENDER + ","

                + User.PROFILE_IMAGE_URL + ","
                + User.ROLE + ","
                + User.IS_ACCOUNT_PRIVATE + ","
                + User.REFERRED_BY + ","
                + User.IS_REFERRER_CREDITED + ","
                + User.ABOUT + ""
                + ") "
                + " Select "
                + " ?,? ,?,? ,?,?,?,?,?,? "
                + " from " + EmailVerificationCode.TABLE_NAME
                + " WHERE "
                + "("
                + "(" + EmailVerificationCode.TABLE_NAME + "." + EmailVerificationCode.EMAIL + " = ? " + ")"
                + " and "
                + "(" + EmailVerificationCode.VERIFICATION_CODE + " = ?" + ")"
                + " and "
                + "(" + EmailVerificationCode.TIMESTAMP_EXPIRES + " > now()" + ")"
                + ")";





        insertVehicle = " INSERT INTO " + Shop.TABLE_NAME
                                        + "("
                                        + Shop.SHOP_ADMIN_ID + ""
                                        + ") " +
                        " VALUES( ? )";




        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            statement = connection.prepareStatement(insertItemSubmission,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;

//            statement.setString(++i,user.getUsername());
            statement.setString(++i,user.getPassword());
            statement.setString(++i,user.getEmail());

//            statement.setString(++i,user.getPhone());
            statement.setString(++i,user.getName());
            statement.setObject(++i,user.getGender());

            statement.setString(++i,user.getProfileImagePath());
            statement.setObject(++i,user.getRole());
            statement.setObject(++i,user.isAccountPrivate());
            statement.setObject(++i,user.getReferredBy());

            statement.setObject(++i,false);

            statement.setString(++i,user.getAbout());


//             check username is not null
//            statement.setString(++i,user.getUsername());


            // check email is verification code to ensure email belongs to user
            statement.setString(++i,user.getEmail());
            statement.setString(++i,user.getRt_email_verification_code());

            // check phone is verified or not to ensure phone belongs to user
//            statement.setString(++i,user.getPhone());
//            statement.setString(++i,user.getRt_phone_verification_code());

            rowCountItems = statement.executeUpdate();


            ResultSet rs = statement.getGeneratedKeys();

            if(rs.next())
            {
                idOfInsertedRow = rs.getInt(1);
            }



            if(user.getRole()== GlobalConstants.ROLE_SHOP_ADMIN_CODE)
            {
                if (rowCountItems == 1)
                {

                    statementInsertVehicle = connection.prepareStatement(insertVehicle);
                    i = 0;

                    statementInsertVehicle.setObject(++i,idOfInsertedRow);
                    statementInsertVehicle.executeUpdate();
                }
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


            if (statementInsertVehicle != null) {
                try {
                    statementInsertVehicle.close();
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







    public int registerUsingEmail(User user, boolean getRowCount)
    {

        Connection connection = null;
        PreparedStatement statement = null;
        PreparedStatement statementInsertVehicle = null;


        PreparedStatement statementUpdateDUES = null;
        PreparedStatement statementCreateTransaction = null;

        PreparedStatement statementUpdateDUESReferral = null;
        PreparedStatement statementTransactionReferral = null;


        int idOfInsertedRow = -1;
        int rowCountItems = -1;


        String insertItemSubmission = "";

        String insertShop = "";

        String updateDUES = "";
        String createTransaction = "";

        String updateDUESReferral = "";
        String createTransactionReferral = "";



        insertItemSubmission = "INSERT INTO "
                + User.TABLE_NAME
                + "("

                + User.PASSWORD + ","
                + User.E_MAIL + ","

                + User.NAME + ","
                + User.GENDER + ","

                + User.PROFILE_IMAGE_URL + ","
                + User.ROLE + ","
                + User.IS_ACCOUNT_PRIVATE + ","
                + User.REFERRED_BY + ","
                + User.IS_REFERRER_CREDITED + ","
                + User.ABOUT + ""
                + ") "
                + " Select "
                + " ?,? ,?,? ,?,?,?,?,?,? "
                + " from " + EmailVerificationCode.TABLE_NAME
                + " WHERE "
                + "("
                + "(" + EmailVerificationCode.TABLE_NAME + "." + EmailVerificationCode.EMAIL + " = ? " + ")"
                + " and "
                + "(" + EmailVerificationCode.VERIFICATION_CODE + " = ?" + ")"
                + " and "
                + "(" + EmailVerificationCode.TIMESTAMP_EXPIRES + " > now()" + ")"
                + ")";





        insertShop = " INSERT INTO " + Shop.TABLE_NAME
                + "("
                + Shop.SHOP_ADMIN_ID + ""
                + ") " +
                " VALUES( ? )";




        // add joining credit to the users account
        updateDUES =  " UPDATE " + User.TABLE_NAME
                    + " SET " + User.SERVICE_ACCOUNT_BALANCE + " = " + User.SERVICE_ACCOUNT_BALANCE + " + ?"
                    + " WHERE " + User.TABLE_NAME + "." + User.USER_ID + " = ? ";


        createTransaction = "INSERT INTO " + Transaction.TABLE_NAME
                            + "("

                            + Transaction.USER_ID + ","

                            + Transaction.TITLE + ","
                            + Transaction.DESCRIPTION + ","

                            + Transaction.TRANSACTION_TYPE + ","
                            + Transaction.TRANSACTION_AMOUNT + ","

                            + Transaction.IS_CREDIT + ","

//                            + Transaction.CURRENT_DUES_BEFORE_TRANSACTION + ","
                            + Transaction.SERVICE_BALANCE_AFTER_TRANSACTION + ""

                            + ") "
                            + " SELECT "

                            + User.TABLE_NAME + "." + User.USER_ID + ","
                            + " '" + Transaction.TITLE_JOINING_CREDIT_FOR_DRIVER + "',"
                            + " '" + Transaction.DESCRIPTION_JOINING_CREDIT_FOR_DRIVEr + "',"

                            + Transaction.TRANSACTION_TYPE_JOINING_CREDIT + ","
                            + " ? ,"

                            + " true " + ","

//                            + User.TABLE_NAME + "." + User.SERVICE_ACCOUNT_BALANCE + " - ?,"
                            + User.TABLE_NAME + "." + User.SERVICE_ACCOUNT_BALANCE + ""

                            + " FROM " + User.TABLE_NAME
                            + " WHERE " + User.TABLE_NAME + "." + User.USER_ID + " = ?";




//
//        // add referral charges to the user bill
//        updateDUESReferral =  " UPDATE " + User.TABLE_NAME
//                        + " SET "
//                        + User.SERVICE_ACCOUNT_BALANCE + " = " + User.SERVICE_ACCOUNT_BALANCE + " - ?,"
//                        + User.TOTAL_CREDITS + " = " + User.TOTAL_CREDITS + " - ?"
//                        + " FROM " + User.TABLE_NAME + " as registered_user"
//                        + " WHERE " + "registered_user." + User.REFERRED_BY + " = " + User.TABLE_NAME + "." + User.USER_ID
//                        + " AND " + "registered_user." + User.USER_ID + " = ? ";






        // add referral credit
        updateDUESReferral =  " UPDATE " + User.TABLE_NAME
                + " SET " + User.SERVICE_ACCOUNT_BALANCE + " = " + User.SERVICE_ACCOUNT_BALANCE + " + ?"
                + " WHERE " + User.TABLE_NAME + "." + User.USER_ID + " = ? ";




        createTransactionReferral = "INSERT INTO " + Transaction.TABLE_NAME
                + "("

                + Transaction.USER_ID + ","

                + Transaction.TITLE + ","
                + Transaction.DESCRIPTION + ","

                + Transaction.TRANSACTION_TYPE + ","
                + Transaction.TRANSACTION_AMOUNT + ","

                + Transaction.IS_CREDIT + ","

//                + Transaction.CURRENT_DUES_BEFORE_TRANSACTION + ","
                + Transaction.SERVICE_BALANCE_AFTER_TRANSACTION + ""

                + ") "
                + " SELECT "

                + User.TABLE_NAME + "." + User.USER_ID + ","
                + " '" + Transaction.TITLE_REFERRAL_CREDIT_APPLIED + "',"
                + " '" + Transaction.DESCRIPTION_REFERRAL_CREDIT_APPLIED + "',"

                + Transaction.TRANSACTION_TYPE_USER_REFERRAL_CREDIT + ","
                + " ? ,"

                + " true " + ","

//                + User.TABLE_NAME + "." + User.SERVICE_ACCOUNT_BALANCE + " - ?,"
                + User.TABLE_NAME + "." + User.SERVICE_ACCOUNT_BALANCE + ""

                + " FROM " + User.TABLE_NAME
                + " WHERE " + User.TABLE_NAME + "." + User.USER_ID + " = ?";





        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            statement = connection.prepareStatement(insertItemSubmission,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;

//            statement.setString(++i,user.getUsername());
            statement.setString(++i,user.getPassword());
            statement.setString(++i,user.getEmail());

//            statement.setString(++i,user.getPhone());
            statement.setString(++i,user.getName());
            statement.setObject(++i,user.getGender());

            statement.setString(++i,user.getProfileImagePath());
            statement.setObject(++i,user.getRole());
            statement.setObject(++i,user.isAccountPrivate());
            statement.setObject(++i,user.getReferredBy());

            if(user.getRole()== GlobalConstants.ROLE_END_USER_CODE)
            {
                statement.setObject(++i,true);
            }
            else
            {
                statement.setObject(++i,false);
            }

            statement.setString(++i,user.getAbout());


//             check username is not null
//            statement.setString(++i,user.getUsername());


            // check email is verification code to ensure email belongs to user
            statement.setString(++i,user.getEmail());
            statement.setString(++i,user.getRt_email_verification_code());

            // check phone is verified or not to ensure phone belongs to user
//            statement.setString(++i,user.getPhone());
//            statement.setString(++i,user.getRt_phone_verification_code());

            rowCountItems = statement.executeUpdate();


            ResultSet rs = statement.getGeneratedKeys();

            if(rs.next())
            {
                idOfInsertedRow = rs.getInt(1);
            }






            if(user.getRole()== GlobalConstants.ROLE_SHOP_ADMIN_CODE)
            {
                if (rowCountItems == 1)
                {

                    statementInsertVehicle = connection.prepareStatement(insertShop);
                    i = 0;

                    statementInsertVehicle.setObject(++i,idOfInsertedRow);
                    statementInsertVehicle.executeUpdate();
                }
            }





            if(rowCountItems == 1)
            {

                statementUpdateDUES = connection.prepareStatement(updateDUES);
                i = 0;

                if(user.getRole()== GlobalConstants.ROLE_SHOP_ADMIN_CODE)
                {
                    statementUpdateDUES.setObject(++i, GlobalConstants.JOINING_CREDIT_FOR_DRIVER);
                    statementUpdateDUES.setObject(++i, GlobalConstants.JOINING_CREDIT_FOR_DRIVER);
                }
                else if(user.getRole()== GlobalConstants.ROLE_END_USER_CODE)
                {
                    statementUpdateDUES.setObject(++i, GlobalConstants.JOINING_CREDIT_FOR_END_USER);
                    statementUpdateDUES.setObject(++i, GlobalConstants.JOINING_CREDIT_FOR_END_USER);
                }
                else
                {
                    statementUpdateDUES.setObject(++i,0);
                    statementUpdateDUES.setObject(++i,0);
                }


                statementUpdateDUES.setObject(++i,idOfInsertedRow);

                rowCountItems = statementUpdateDUES.executeUpdate();




                statementCreateTransaction = connection.prepareStatement(createTransaction);
                i = 0;


                if(user.getRole()== GlobalConstants.ROLE_SHOP_ADMIN_CODE)
                {
//                    statementCreateTransaction.setObject(++i,GlobalConstantsNBS.JOINING_CREDIT_FOR_DRIVER);
                    statementCreateTransaction.setObject(++i, GlobalConstants.JOINING_CREDIT_FOR_DRIVER);
                }
                else if(user.getRole()== GlobalConstants.ROLE_END_USER_CODE)
                {
//                    statementCreateTransaction.setObject(++i,GlobalConstantsNBS.JOINING_CREDIT_FOR_END_USER);
                    statementCreateTransaction.setObject(++i, GlobalConstants.JOINING_CREDIT_FOR_END_USER);
                }
                else
                {
//                    statementCreateTransaction.setObject(++i,0);
                    statementCreateTransaction.setObject(++i,0);
                }

                statementCreateTransaction.setObject(++i,idOfInsertedRow);
                rowCountItems = statementCreateTransaction.executeUpdate();






                if(user.getRole()== GlobalConstants.ROLE_END_USER_CODE)
                {
                    // apply referral credit

                    statementUpdateDUESReferral = connection.prepareStatement(updateDUESReferral);
                    i = 0;


                    if(user.getRole()== GlobalConstants.ROLE_END_USER_CODE)
                    {
                        statementUpdateDUESReferral.setObject(++i, GlobalConstants.REFERRAL_CREDIT_FOR_END_USER_REGISTRATION);
                        statementUpdateDUESReferral.setObject(++i, GlobalConstants.REFERRAL_CREDIT_FOR_END_USER_REGISTRATION);
                    }
                    else
                    {
                        statementUpdateDUESReferral.setObject(++i,0);
                        statementUpdateDUESReferral.setObject(++i,0);
                    }



                    statementUpdateDUESReferral.setObject(++i,user.getReferredBy());
                    rowCountItems = statementUpdateDUESReferral.executeUpdate();




                    statementTransactionReferral = connection.prepareStatement(createTransactionReferral);
                    i = 0;


                    if(user.getRole()== GlobalConstants.ROLE_END_USER_CODE)
                    {
//                        statementTransactionReferral.setObject(++i,GlobalConstantsNBS.REFERRAL_CREDIT_FOR_END_USER_REGISTRATION);
                        statementTransactionReferral.setObject(++i, GlobalConstants.REFERRAL_CREDIT_FOR_END_USER_REGISTRATION);
                    }
                    else
                    {
//                        statementTransactionReferral.setObject(++i,0);
                        statementTransactionReferral.setObject(++i,0);
                    }

                    statementTransactionReferral.setObject(++i,user.getReferredBy());
                    rowCountItems = statementTransactionReferral.executeUpdate();

                }

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


            if (statementInsertVehicle != null) {
                try {
                    statementInsertVehicle.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }



            if (statementUpdateDUES != null) {
                try {
                    statementUpdateDUES.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


            if (statementCreateTransaction != null) {
                try {
                    statementCreateTransaction.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }




            if (statementUpdateDUESReferral != null) {
                try {
                    statementUpdateDUESReferral.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


            if (statementTransactionReferral != null) {
                try {
                    statementTransactionReferral.close();
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





    public int registerUsingPhone(User user, boolean getRowCount,
                                  double joiningCredit,
                                  double referralCredit,
                                  boolean isReferrerCredited)

    {

        Connection connection = null;
        PreparedStatement statement = null;

        PreparedStatement statementInsertShop = null;

        // for applying joining credit
        PreparedStatement statementUpdateDUES = null;
        PreparedStatement statementCreateTransaction = null;

        // for applying referral credit
        PreparedStatement statementUpdateDUESReferral = null;
        PreparedStatement statementTransactionReferral = null;


        int idOfInsertedRow = -1;
        int rowCountItems = -1;

        String insertShop = "";

        String updateDUES = "";
        String createTransaction = "";

        String updateDUESReferral = "";
        String createTransactionReferral = "";



        String insertProfile = "INSERT INTO "
                + User.TABLE_NAME
                + "("

                + User.PASSWORD + ","
                + User.PHONE + ","

                + User.NAME + ","
                + User.GENDER + ","

                + User.PROFILE_IMAGE_URL + ","
                + User.ROLE + ","
                + User.IS_ACCOUNT_PRIVATE + ","
                + User.REFERRED_BY + ","
                + User.IS_REFERRER_CREDITED + ","
                + User.ABOUT + ""
                + ") "
                + " Select "
                + " ?,? ,?,? ,?,?,?,?,?,? "
                + " from " + PhoneVerificationCode.TABLE_NAME
                + " WHERE "
                + "("
                + "(" + PhoneVerificationCode.TABLE_NAME + "." + PhoneVerificationCode.PHONE + " = ? " + ")"
                + " and "
                + "(" + PhoneVerificationCode.VERIFICATION_CODE + " = ?" + ")"
                + " and "
                + "(" + PhoneVerificationCode.TIMESTAMP_EXPIRES + " > now()" + ")"
                + ")";




        insertShop = " INSERT INTO " + Shop.TABLE_NAME
                        + "(" + Shop.SHOP_ADMIN_ID + ","
                              + Shop.SHOP_ENABLED + ","
                                + Shop.SHOP_WAITLISTED + ""

                        + ") " +
                        " VALUES( ?,?,? )";




        // add joining credit
        updateDUES =  " UPDATE " + User.TABLE_NAME
                    + " SET " + User.SERVICE_ACCOUNT_BALANCE + " = " + User.SERVICE_ACCOUNT_BALANCE + " + ?"
                    + " WHERE " + User.TABLE_NAME + "." + User.USER_ID + " = ? ";




        createTransaction = "INSERT INTO " + Transaction.TABLE_NAME
                + "("

                + Transaction.USER_ID + ","

                + Transaction.TITLE + ","
                + Transaction.DESCRIPTION + ","

                + Transaction.TRANSACTION_TYPE + ","
                + Transaction.TRANSACTION_AMOUNT + ","

                + Transaction.IS_CREDIT + ","

//                + Transaction.CURRENT_DUES_BEFORE_TRANSACTION + ","
                + Transaction.SERVICE_BALANCE_AFTER_TRANSACTION + ""

                + ") "
                + " SELECT "

                + User.TABLE_NAME + "." + User.USER_ID + ","
                + " '" + Transaction.TITLE_JOINING_CREDIT_FOR_DRIVER + "',"
                + " '" + Transaction.DESCRIPTION_JOINING_CREDIT_FOR_DRIVEr + "',"

                + Transaction.TRANSACTION_TYPE_JOINING_CREDIT + ","
                + " ? ,"

                + " true " + ","

//                + User.TABLE_NAME + "." + User.SERVICE_ACCOUNT_BALANCE + " - ?,"
                + User.TABLE_NAME + "." + User.SERVICE_ACCOUNT_BALANCE + ""

                + " FROM " + User.TABLE_NAME
                + " WHERE " + User.TABLE_NAME + "." + User.USER_ID + " = ?";




//
//        // add referral charges to the user bill
//        updateDUESReferral =  " UPDATE " + User.TABLE_NAME
//                        + " SET "
//                        + User.SERVICE_ACCOUNT_BALANCE + " = " + User.SERVICE_ACCOUNT_BALANCE + " - ?,"
//                        + User.TOTAL_CREDITS + " = " + User.TOTAL_CREDITS + " - ?"
//                        + " FROM " + User.TABLE_NAME + " as registered_user"
//                        + " WHERE " + "registered_user." + User.REFERRED_BY + " = " + User.TABLE_NAME + "." + User.USER_ID
//                        + " AND " + "registered_user." + User.USER_ID + " = ? ";






        // add referral credit to the user
        updateDUESReferral =  " UPDATE " + User.TABLE_NAME
                + " SET " + User.SERVICE_ACCOUNT_BALANCE + " = " + User.SERVICE_ACCOUNT_BALANCE + " + ?"
                + " WHERE " + User.TABLE_NAME + "." + User.USER_ID + " = ? ";




        createTransactionReferral = "INSERT INTO " + Transaction.TABLE_NAME
                + "("

                + Transaction.USER_ID + ","

                + Transaction.TITLE + ","
                + Transaction.DESCRIPTION + ","

                + Transaction.TRANSACTION_TYPE + ","
                + Transaction.TRANSACTION_AMOUNT + ","

                + Transaction.IS_CREDIT + ","

//                + Transaction.CURRENT_DUES_BEFORE_TRANSACTION + ","
                + Transaction.SERVICE_BALANCE_AFTER_TRANSACTION + ""

                + ") "
                + " SELECT "

                + User.TABLE_NAME + "." + User.USER_ID + ","
                + " '" + Transaction.TITLE_REFERRAL_CREDIT_APPLIED + "',"
                + " '" + Transaction.DESCRIPTION_REFERRAL_CREDIT_APPLIED + "',"

                + Transaction.TRANSACTION_TYPE_USER_REFERRAL_CREDIT + ","
                + " ? ,"

                + " true " + ","

//                + User.TABLE_NAME + "." + User.SERVICE_ACCOUNT_BALANCE + " - ?,"
                + User.TABLE_NAME + "." + User.SERVICE_ACCOUNT_BALANCE + ""

                + " FROM " + User.TABLE_NAME
                + " WHERE " + User.TABLE_NAME + "." + User.USER_ID + " = ?";


        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            statement = connection.prepareStatement(insertProfile, PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;

//            statement.setString(++i,user.getUsername());

            statement.setString(++i, user.getPassword());
            statement.setString(++i, user.getPhone());

            statement.setString(++i, user.getName());
            statement.setObject(++i, user.getGender());

            statement.setString(++i, user.getProfileImagePath());
            statement.setObject(++i, user.getRole());
            statement.setObject(++i, user.isAccountPrivate());
            statement.setObject(++i, user.getReferredBy());


//            if (user.getRole() == GlobalConstants.ROLE_END_USER_CODE) {
//                statement.setObject(++i, true);
//            } else {
//                statement.setObject(++i, false);
//            }


            statement.setObject(++i,isReferrerCredited);


            statement.setString(++i, user.getAbout());


            // check phone is verified or not to ensure phone belongs to user
            statement.setString(++i, user.getPhone());
            statement.setString(++i, user.getRt_phone_verification_code());

            rowCountItems = statement.executeUpdate();


            ResultSet rs = statement.getGeneratedKeys();

            if (rs.next()) {
                idOfInsertedRow = rs.getInt(1);
            }



            if(user.getRole()== GlobalConstants.ROLE_SHOP_ADMIN_CODE)
            {
                if (rowCountItems == 1)
                {

                    statementInsertShop = connection.prepareStatement(insertShop);
                    i = 0;

                    statementInsertShop.setObject(++i,idOfInsertedRow);
                    statementInsertShop.setObject(++i,false);
                    statementInsertShop.setObject(++i,false);

                    statementInsertShop.executeUpdate();
                }
            }



            if(rowCountItems == 1)
            {

                statementUpdateDUES = connection.prepareStatement(updateDUES);
                i = 0;

                statementUpdateDUES.setObject(++i,joiningCredit);
//                statementUpdateDUES.setObject(++i,joiningCredit);
                statementUpdateDUES.setObject(++i,idOfInsertedRow);

                rowCountItems = statementUpdateDUES.executeUpdate();







                statementCreateTransaction = connection.prepareStatement(createTransaction);
                i = 0;

                statementCreateTransaction.setObject(++i,joiningCredit);
                statementCreateTransaction.setObject(++i,idOfInsertedRow);

                rowCountItems = statementCreateTransaction.executeUpdate();


                statementUpdateDUESReferral = connection.prepareStatement(updateDUESReferral);
                i = 0;

                statementUpdateDUESReferral.setObject(++i,referralCredit);
//                statementUpdateDUESReferral.setObject(++i,referralCredit);

                statementUpdateDUESReferral.setObject(++i,user.getReferredBy());
                rowCountItems = statementUpdateDUESReferral.executeUpdate();




                statementTransactionReferral = connection.prepareStatement(createTransactionReferral);
                i = 0;


                statementTransactionReferral.setObject(++i,referralCredit);

                statementTransactionReferral.setObject(++i,user.getReferredBy());
                rowCountItems = statementTransactionReferral.executeUpdate();

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


            if (statementInsertShop != null) {
                try {
                    statementInsertShop.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }



            if (statementUpdateDUES != null) {
                try {
                    statementUpdateDUES.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


            if (statementCreateTransaction != null) {
                try {
                    statementCreateTransaction.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }




            if (statementUpdateDUESReferral != null) {
                try {
                    statementUpdateDUESReferral.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


            if (statementTransactionReferral != null) {
                try {
                    statementTransactionReferral.close();
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








    public int registerUsingPhoneNoCredits(User user, boolean getRowCount)
    {

        Connection connection = null;
        PreparedStatement statement = null;
        PreparedStatement statementPermissions = null;


        int idOfInsertedRow = -1;
        int rowCountItems = -1;




        String insertUser = "INSERT INTO "
                + User.TABLE_NAME
                + "("

                + User.PASSWORD + ","
                + User.PHONE + ","

                + User.NAME + ","
                + User.GENDER + ","

                + User.PROFILE_IMAGE_URL + ","
                + User.ROLE + ","
                + User.IS_ACCOUNT_PRIVATE + ","
                + User.ABOUT + ""
                + ") "
                + " Select "
                + " ?,? ,?,? ,?,?,?,? "
                + " from " + PhoneVerificationCode.TABLE_NAME
                + " WHERE "
                + "("
                + "(" + PhoneVerificationCode.TABLE_NAME + "." + PhoneVerificationCode.PHONE + " = ? " + ")"
                + " and "
                + "(" + PhoneVerificationCode.VERIFICATION_CODE + " = ?" + ")"
                + " and "
                + "(" + PhoneVerificationCode.TIMESTAMP_EXPIRES + " > now()" + ")"
                + ")";



        String insertStaffPermissions =

                "INSERT INTO " + ShopStaffPermissions.TABLE_NAME
                        + "("
                        + ShopStaffPermissions.STAFF_ID + ","
                        + ShopStaffPermissions.SHOP_ID + ""
                        + ") values(?,?)";





        // add referral charges to the user bill


        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            statement = connection.prepareStatement(insertUser,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;

//            statement.setString(++i,user.getUsername());

            statement.setString(++i,user.getPassword());
            statement.setString(++i,user.getPhone());

            statement.setString(++i,user.getName());
            statement.setObject(++i,user.getGender());

            statement.setString(++i,user.getProfileImagePath());
            statement.setObject(++i,user.getRole());
            statement.setObject(++i,user.isAccountPrivate());

//            statement.setObject(++i,user.getReferredBy());
//
//            if(user.getRole()==GlobalConstantsNBS.ROLE_END_USER_CODE)
//            {
//                statement.setObject(++i,true);
//            }
//            else
//            {
//                statement.setObject(++i,false);
//            }


            statement.setString(++i,user.getAbout());


            // check phone is verified or not to ensure phone belongs to user
            statement.setString(++i,user.getPhone());
            statement.setString(++i,user.getRt_phone_verification_code());

            rowCountItems = statement.executeUpdate();


            ResultSet rs = statement.getGeneratedKeys();

            if(rs.next())
            {
                idOfInsertedRow = rs.getInt(1);
            }





            if(idOfInsertedRow!=-1)
            {

                statementPermissions = connection.prepareStatement(insertStaffPermissions,PreparedStatement.RETURN_GENERATED_KEYS);
                i = 0;


                ShopStaffPermissions permissions = user.getRt_shop_staff_permissions();

                statementPermissions.setInt(++i,idOfInsertedRow);
                statementPermissions.setInt(++i,permissions.getShopID());

                rowCountItems = statementPermissions.executeUpdate();
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



            if (statementPermissions != null) {
                try {
                    statementPermissions.close();
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







    public int registerUsingUsername(User user, boolean getRowCount)
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

                + User.NAME + ","
                + User.GENDER + ","

                + User.PROFILE_IMAGE_URL + ","
                + User.ROLE + ","
                + User.IS_ACCOUNT_PRIVATE + ","
                + User.ABOUT + ""
                + ") values(?,? ,?,? ,?,?,?,? )";


        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            statement = connection.prepareStatement(insertItemSubmission,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;

            statement.setString(++i,user.getUsername());
            statement.setString(++i,user.getPassword());

            statement.setString(++i,user.getName());
            statement.setObject(++i,user.getGender());

            statement.setString(++i,user.getProfileImagePath());
            statement.setObject(++i,user.getRole());
            statement.setObject(++i,user.isAccountPrivate());
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





    public boolean checkUsernameExists(String username)
    {

        String query = "SELECT " + User.USERNAME
                + " FROM " + User.TABLE_NAME
                + " WHERE "
                + User.USERNAME + " = ?" + " OR "
                + User.E_MAIL + " = ? " + " OR "
                + User.PHONE + " = ?";


        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        System.out.println("Checked Username : " + username);

//		ShopAdmin shopAdmin = null;



        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);

            int i = 0;
            statement.setObject(++i,username);
            statement.setObject(++i,username);
            statement.setObject(++i,username);


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




}
