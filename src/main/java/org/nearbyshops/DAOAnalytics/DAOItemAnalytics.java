package org.nearbyshops.DAOAnalytics;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.ModelAnalytics.ItemAnalytics;
import org.nearbyshops.ModelAnalytics.ItemAnalyticsEndpoint;
import org.nearbyshops.ModelReviewItem.FavouriteItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOItemAnalytics {


        private HikariDataSource dataSource = Globals.getDataSource();


        public int saveItemAnalytics(ItemAnalytics itemAnalytics)
        {


            Connection conn = null;
            PreparedStatement statement = null;
            int idOfInsertedRow = 0;


            String insertStatement = "INSERT INTO "
                    + ItemAnalytics.TABLE_NAME
                    + "("
                    + ItemAnalytics.ITEM_ID + ","
                    + ItemAnalytics.END_USER_ID + ","
                    + ItemAnalytics.DETAIL_VIEW_CLICK_COUNT + ","
                    + ItemAnalytics.LAST_UPDATE + ","

                    + ") VALUES(?,?,?,now())";

            try {

                conn = dataSource.getConnection();
                statement = conn.prepareStatement(insertStatement,PreparedStatement.RETURN_GENERATED_KEYS);

                statement.setInt(1,itemAnalytics.getItemID());
                statement.setInt(2,itemAnalytics.getEndUserID());
                statement.setLong(3,itemAnalytics.getDetailViewClickCount());
//                statement.setTimestamp(4,);



                idOfInsertedRow = statement.executeUpdate();

                ResultSet rs = statement.getGeneratedKeys();

                if(rs.next())
                {
                    idOfInsertedRow = rs.getInt(1);
                }


            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            finally
            {

                try {

                    if(statement!=null)
                    {statement.close();}

                }
                catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                try {

                    if(conn!=null)
                    {conn.close();}
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            return idOfInsertedRow;

        }




        public int upsertItemAnalytics(ItemAnalytics itemAnalytics,
                                               boolean getRowCount)
        {

            Connection connection = null;
            PreparedStatement statement = null;

            int idOfInsertedRow = -1;
            int rowCountItems = -1;

            String insertItemSubmission =

                    "INSERT INTO " + ItemAnalytics.TABLE_NAME
                            + "(" + ItemAnalytics.ITEM_ID + ","
                            + ItemAnalytics.END_USER_ID + ","
                            + ItemAnalytics.DETAIL_VIEW_CLICK_COUNT + ","
                            + ItemAnalytics.LAST_UPDATE + ""
                            + ") values(?,?,?,?)"
                            + " ON CONFLICT (" + ItemAnalytics.ITEM_ID + "," + ItemAnalytics.END_USER_ID + ")"
                            + " DO UPDATE "
                            + " SET " + ItemAnalytics.DETAIL_VIEW_CLICK_COUNT + " = " + ItemAnalytics.TABLE_NAME + "." + ItemAnalytics.DETAIL_VIEW_CLICK_COUNT + " +  excluded." + ItemAnalytics.DETAIL_VIEW_CLICK_COUNT + " , "
                            + ItemAnalytics.LAST_UPDATE + "= excluded." + ItemAnalytics.LAST_UPDATE;





    //        + EmailVerificationCode.TABLE_NAME


            try {

                connection = dataSource.getConnection();
                connection.setAutoCommit(false);


                statement = connection.prepareStatement(insertItemSubmission,PreparedStatement.RETURN_GENERATED_KEYS);
                int i = 0;

                statement.setObject(++i,itemAnalytics.getItemID());
                statement.setObject(++i,itemAnalytics.getEndUserID());
                statement.setObject(++i,itemAnalytics.getDetailViewClickCountIncrement());
                statement.setObject(++i,new Timestamp(System.currentTimeMillis()));



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





        public int deleteItemAnalytics(int itemID, int endUserID)
            {

                String deleteStatement = "DELETE FROM " + ItemAnalytics.TABLE_NAME
                        + " WHERE " + ItemAnalytics.ITEM_ID + " = ?"
                        + " AND " + ItemAnalytics.END_USER_ID + " = ?";


                Connection connection= null;
                PreparedStatement statement = null;
                int rowCountDeleted = 0;
                try {


                    connection = dataSource.getConnection();
                    statement = connection.prepareStatement(deleteStatement);

                    statement.setInt(1,itemID);
                    statement.setInt(2,endUserID);

                    rowCountDeleted = statement.executeUpdate();
                    System.out.println("Row Count Deleted Item Analytics: " + rowCountDeleted);


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

                return rowCountDeleted;
            }






        public ItemAnalyticsEndpoint getItemAnalytics(
                Integer itemID,
                Integer endUserID,
                String sortBy,
                Integer limit, Integer offset,
                boolean getRowCount,
                boolean getOnlyMetadata
        ) {



            String queryCount = "";



            String queryJoin = "SELECT "
                    + ItemAnalytics.TABLE_NAME + "." + ItemAnalytics.ITEM_ID + ","
                    + ItemAnalytics.TABLE_NAME + "." + ItemAnalytics.END_USER_ID + ","
                    + ItemAnalytics.TABLE_NAME + "." + ItemAnalytics.DETAIL_VIEW_CLICK_COUNT + ","
                    + ItemAnalytics.TABLE_NAME + "." + ItemAnalytics.DATE_TIME_CREATED + ","
                    + ItemAnalytics.TABLE_NAME + "." + ItemAnalytics.LAST_UPDATE + ""
                    + " FROM " + ItemAnalytics.TABLE_NAME
                    + " WHERE TRUE ";





            if(itemID != null)
            {
                queryJoin = queryJoin + " AND " + ItemAnalytics.TABLE_NAME + "."  + ItemAnalytics.ITEM_ID + " = ? ";
            }



            if(endUserID!=null){

                String queryPartMemberID;

                queryPartMemberID = ItemAnalytics.TABLE_NAME + "." + ItemAnalytics.END_USER_ID + " = ? ";

                queryJoin = queryJoin + " AND " + queryPartMemberID;
            }






            queryCount = queryJoin;



            // Applying filters



            if(sortBy!=null)
            {
                if(!sortBy.equals(""))
                {
                    String queryPartSortBy = " ORDER BY " + sortBy;

                    queryJoin = queryJoin + queryPartSortBy;
                }
            }






            if(limit != null)
            {

                String queryPartLimitOffset = "";

                if(offset>0)
                {
                    queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + offset;

                }else
                {
                    queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + 0;
                }



                queryJoin = queryJoin + queryPartLimitOffset;
            }






            queryCount = "SELECT COUNT(*) as item_count FROM (" + queryCount + ") AS temp";






            ItemAnalyticsEndpoint endpoint = new ItemAnalyticsEndpoint();

            ArrayList<ItemAnalytics> itemsList = new ArrayList<>();



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

                    if(itemID!=null)
                    {
                        statement.setObject(++i,itemID);
                    }


                    if(endUserID!=null)
                    {
                        statement.setObject(++i,endUserID);
                    }




                    rs = statement.executeQuery();

                    while(rs.next())
                    {
                        ItemAnalytics item = new ItemAnalytics();

                        item.setEndUserID(rs.getInt(ItemAnalytics.END_USER_ID));
                        item.setItemID(rs.getInt(ItemAnalytics.ITEM_ID));
                        item.setDetailViewClickCount(rs.getLong(ItemAnalytics.DETAIL_VIEW_CLICK_COUNT));
                        item.setDateTimeCreated(rs.getTimestamp(ItemAnalytics.DATE_TIME_CREATED));
                        item.setLastUpdate(rs.getTimestamp(ItemAnalytics.LAST_UPDATE));

                        itemsList.add(item);
                    }


                    endpoint.setResults(itemsList);
                }



                if(getRowCount)
                {
                    statementCount = connection.prepareStatement(queryCount);

                    i = 0;



                    if(itemID!=null)
                    {
                        statementCount.setObject(++i,itemID);
                    }


                    if(endUserID!=null)
                    {
                        statementCount.setObject(++i,endUserID);
                    }



                    resultSetCount = statementCount.executeQuery();

                    while(resultSetCount.next())
                    {
                        endpoint.setItemCount(resultSetCount.getInt("item_count"));
                    }

                }



//                System.out.println("Item Analytics List Size " + itemsList.size());

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            finally
            {



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

            return endpoint;
        }



}
