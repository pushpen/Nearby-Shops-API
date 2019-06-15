package org.nearbyshops.DAOBilling;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Shop;
import org.nearbyshops.ModelBilling.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DAOAddBalance {


    private HikariDataSource dataSource = Globals.getDataSource();



    public int add_balance_to_shop(int shopAdminID,double amount_to_add)
    {



        Connection connection = null;
        PreparedStatement statementUpdate = null;
        PreparedStatement statementTransaction = null;


        int rowCountItems = -1;

        String update = "";
        String createTransaction = "";





        update =  " UPDATE " + Shop.TABLE_NAME
                + " SET "    + Shop.ACCOUNT_BALANCE + " = " + Shop.ACCOUNT_BALANCE + " + ? "
                + " WHERE "  + Shop.SHOP_ADMIN_ID  + " = ?";




//
//        transactionEntry = "INSERT INTO "
//                + Transaction.TABLE_NAME
//                + "("
//
//
//                + Transaction.USER_ID + ","
//                + Transaction.TITLE + ","
//                + Transaction.DESCRIPTION + ","
//
//                + Transaction.TRANSACTION_TYPE + ","
//                + Transaction.TRANSACTION_AMOUNT + ","
//                + Transaction.IS_CREDIT + ","
//                + Transaction.SERVICE_BALANCE_AFTER_TRANSACTION + ""
//
//                + ") "
//                + "VALUES(?,?,?, ?,?,?,?)";





        createTransaction = "INSERT INTO " + Transaction.TABLE_NAME
                + "("

                + Transaction.USER_ID + ","

                + Transaction.TITLE + ","
                + Transaction.DESCRIPTION + ","

                + Transaction.TRANSACTION_TYPE + ","
                + Transaction.TRANSACTION_AMOUNT + ","

                + Transaction.IS_CREDIT + ","

                + Transaction.BALANCE_AFTER_TRANSACTION + ""

                + ") "
                + " SELECT "

                + Shop.TABLE_NAME + "." + Shop.SHOP_ADMIN_ID + ","
                + " 'Credit added',"
                + " 'Credit added by staff',"

//                + " ? ,"
//                + " ? ,"

                + Transaction.TRANSACTION_TYPE_PAYMENTS + ","
                + "? ,"

                + " true " + ","
                + Shop.TABLE_NAME + "." + Shop.ACCOUNT_BALANCE + ""

                + " FROM " + Shop.TABLE_NAME
                + " WHERE " + Shop.TABLE_NAME + "." + Shop.SHOP_ADMIN_ID + " = ? ";












        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);



            statementUpdate = connection.prepareStatement(update,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;

            statementUpdate.setObject(++i,amount_to_add);
            statementUpdate.setObject(++i,shopAdminID);


            rowCountItems = statementUpdate.executeUpdate();


            if(rowCountItems==1)
            {
                statementTransaction = connection.prepareStatement(createTransaction,PreparedStatement.RETURN_GENERATED_KEYS);
                i = 0;

//                statementTransaction.setObject(++i,title);
//                statementTransaction.setObject(++i,description);
                statementTransaction.setObject(++i,amount_to_add);
                statementTransaction.setObject(++i,shopAdminID);


                rowCountItems = rowCountItems + statementTransaction.executeUpdate();
            }



//            System.out.println("Shop Admin ID : " + shopAdminID + " | " + " Amount to Add : " + amount_to_add);


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

            if (statementUpdate != null) {
                try {
                    statementUpdate.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (statementTransaction != null) {
                try {
                    statementTransaction.close();
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



}
