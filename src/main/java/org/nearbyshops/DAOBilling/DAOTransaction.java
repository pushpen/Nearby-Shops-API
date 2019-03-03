package org.nearbyshops.DAOBilling;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.ModelBilling.Transaction;
import org.nearbyshops.ModelBilling.TransactionEndpoint;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by sumeet on 13/8/17.
 */
public class DAOTransaction {

    private HikariDataSource dataSource = Globals.getDataSource();



    public TransactionEndpoint getTransactions(
            Integer userID,
            Boolean isCredit,
            Integer transactionType,
            String sortBy,
            Integer limit, Integer offset,
            boolean getRowCount,
            boolean getOnlyMetadata)
    {


        boolean isfirst = true;

        String queryCount = "";


        String queryJoin = "SELECT DISTINCT "

                + Transaction.TABLE_NAME + "." + Transaction.TRANSACTION_ID + ","
                + Transaction.TABLE_NAME + "." + Transaction.USER_ID + ","
                + Transaction.TABLE_NAME + "." + Transaction.TITLE + ","
                + Transaction.TABLE_NAME + "." + Transaction.DESCRIPTION + ","
                + Transaction.TABLE_NAME + "." + Transaction.TRANSACTION_TYPE + ","
                + Transaction.TABLE_NAME + "." + Transaction.TRANSACTION_AMOUNT + ","
                + Transaction.TABLE_NAME + "." + Transaction.IS_CREDIT + ","
                + Transaction.TABLE_NAME + "." + Transaction.TIMESTAMP_OCCURRED + ","
                + Transaction.TABLE_NAME + "." + Transaction.BALANCE_AFTER_TRANSACTION + ""

                + " FROM " + Transaction.TABLE_NAME
                + " WHERE " + Transaction.TABLE_NAME + "." + Transaction.USER_ID + " = ? ";




        if(isCredit != null)
        {
            queryJoin = queryJoin + " AND " + Transaction.TABLE_NAME + "." + Transaction.IS_CREDIT + " = ?";
        }


        if(transactionType != null)
        {
            queryJoin = queryJoin + " AND " + Transaction.TABLE_NAME + "." + Transaction.TRANSACTION_TYPE + " = ?";
        }



        // all the non-aggregate columns which are present in select must be present in group by also.
        queryJoin = queryJoin
                + " group by "
                + Transaction.TABLE_NAME + "." + Transaction.TRANSACTION_ID ;


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


        TransactionEndpoint endPoint = new TransactionEndpoint();

        ArrayList<Transaction> itemList = new ArrayList<>();
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

                statement.setObject(++i,userID);

                if(isCredit != null)
                {
                    statement.setObject(++i,isCredit);
                }

                if(transactionType!=null)
                {
                    statement.setObject(++i,transactionType);
                }



                rs = statement.executeQuery();

                while(rs.next())
                {

                    Transaction transaction = new Transaction();

                    transaction.setTransactionID(rs.getInt(Transaction.TRANSACTION_ID));
                    transaction.setUserID(rs.getInt(Transaction.USER_ID));
                    transaction.setTitle(rs.getString(Transaction.TITLE));
                    transaction.setDescription(rs.getString(Transaction.DESCRIPTION));
                    transaction.setTransactionType(rs.getInt(Transaction.TRANSACTION_TYPE));
                    transaction.setTransactionAmount(rs.getDouble(Transaction.TRANSACTION_AMOUNT));
                    transaction.setCredit(rs.getBoolean(Transaction.IS_CREDIT));
                    transaction.setTimestampOccurred(rs.getTimestamp(Transaction.TIMESTAMP_OCCURRED));
                    transaction.setServiceBalanceAfterTransaction(rs.getDouble(Transaction.BALANCE_AFTER_TRANSACTION));


                    itemList.add(transaction);
                }


                endPoint.setResults(itemList);

            }


            if(getRowCount)
            {
                statementCount = connection.prepareStatement(queryCount);

                i = 0;

                statementCount.setObject(++i,userID);

                if(isCredit != null)
                {
                    statementCount.setObject(++i,isCredit);
                }

                if(transactionType!=null)
                {
                    statementCount.setObject(++i,transactionType);
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
