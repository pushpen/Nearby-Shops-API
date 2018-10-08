package org.nearbyshops.DAOPreparedOrders;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Order;
import org.nearbyshops.Model.OrderItem;
import org.nearbyshops.ModelOrderStatus.OrderStatusHomeDelivery;
import org.nearbyshops.ModelOrderStatus.OrderStatusPickFromShop;
import org.nearbyshops.ModelReviewItem.ItemReview;
import org.nearbyshops.ModelRoles.Endpoints.UserEndpoint;
import org.nearbyshops.ModelRoles.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOOrderStaff {


    private HikariDataSource dataSource = Globals.getDataSource();



    public int confirmOrder(int orderID)
    {
        String updateStatement = "UPDATE " + Order.TABLE_NAME

                + " SET " + " " + Order.STATUS_HOME_DELIVERY + " = ?"
                + " WHERE " + Order.ORDER_ID + " = ?"
                + " AND "  + Order.STATUS_HOME_DELIVERY + " = ? ";


        Connection connection = null;
        PreparedStatement statement = null;
        int updatedRows = -1;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);

            int i = 0;

            statement.setObject(++i, OrderStatusHomeDelivery.ORDER_CONFIRMED);
            statement.setObject(++i,orderID);
            statement.setObject(++i, OrderStatusHomeDelivery.ORDER_PLACED);

            updatedRows = statement.executeUpdate();
            System.out.println("Total rows updated: " + updatedRows);

            //conn.close();

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

        return updatedRows;
    }




    public int setOrderPacked(int orderID)
    {
        String updateStatement = "UPDATE " + Order.TABLE_NAME

                + " SET " + " " + Order.STATUS_HOME_DELIVERY + " = ?"
                + " WHERE " + Order.ORDER_ID + " = ?"
                + " AND "  + Order.STATUS_HOME_DELIVERY + " = ? ";


        Connection connection = null;
        PreparedStatement statement = null;
        int updatedRows = -1;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);

            int i = 0;

            statement.setObject(++i, OrderStatusHomeDelivery.ORDER_PACKED);
            statement.setObject(++i,orderID);
            statement.setObject(++i, OrderStatusHomeDelivery.ORDER_CONFIRMED);




            updatedRows = statement.executeUpdate();
            System.out.println("Total rows updated: " + updatedRows);

            //conn.close();

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

        return updatedRows;
    }




    public int handoverToDelivery(int orderID,int deliveryGuyID)
    {
        String updateStatement = "UPDATE " + Order.TABLE_NAME

                + " SET " + Order.STATUS_HOME_DELIVERY + " = ?,"
                        + Order.DELIVERY_GUY_SELF_ID + " = ?"
                + " WHERE " + Order.ORDER_ID + " = ?";



        Connection connection = null;
        PreparedStatement statement = null;
        int updatedRows = -1;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);
            int i = 0;

            statement.setObject(++i,OrderStatusHomeDelivery.HANDOVER_REQUESTED);
            statement.setObject(++i,deliveryGuyID);
            statement.setObject(++i,orderID);


            updatedRows = statement.executeUpdate();
            System.out.println("Total rows updated: " + updatedRows);

            //conn.close();

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



        return updatedRows;
    }




    public int undoHandover(int orderID)
    {
        String updateStatement = "UPDATE " + Order.TABLE_NAME
                                + " SET " + Order.STATUS_HOME_DELIVERY + " = ?,"
                                + Order.DELIVERY_GUY_SELF_ID + " = ?"

                                + " WHERE " + Order.ORDER_ID + " = ?";



        Connection connection = null;
        PreparedStatement statement = null;
        int updatedRows = -1;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);
            int i = 0;


//            statement.setObject(1,order.getEndUserID());
//            statement.setObject(1,order.getShopID());
            statement.setObject(++i,OrderStatusHomeDelivery.ORDER_PACKED);
//            statement.setObject(4,order.getStatusPickFromShop());
//            statement.setObject(2,order.getPaymentReceived());
//            statement.setObject(3,order.getDeliveryReceived());
//            statement.setObject(7,order.getDeliveryCharges());
//            statement.setObject(8,order.getDeliveryAddressID());
            statement.setObject(++i,null);
            statement.setObject(++i,orderID);


            updatedRows = statement.executeUpdate();
            System.out.println("Total rows updated: " + updatedRows);

            //conn.close();

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



        return updatedRows;
    }




    public int acceptReturn(int orderID)
    {

        String updateStatement = "UPDATE " + Order.TABLE_NAME
                                + " SET " + Order.STATUS_HOME_DELIVERY + " = ?,"
                                            + Order.DELIVERY_GUY_SELF_ID + " = ?"

                                + " WHERE " + Order.ORDER_ID + " = ?"
                                + " AND "  + Order.STATUS_HOME_DELIVERY + " = ? ";


        Connection connection = null;
        PreparedStatement statement = null;
        int updatedRows = -1;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);

            int i = 0;

            statement.setObject(++i, OrderStatusHomeDelivery.RETURNED_ORDERS);
            statement.setObject(++i, null);
            statement.setObject(++i, orderID);
            statement.setObject(++i, OrderStatusHomeDelivery.RETURN_REQUESTED);

            updatedRows = statement.executeUpdate();
            System.out.println("Total rows updated: " + updatedRows);

            //conn.close();

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

        return updatedRows;
    }




    public int unpackOrder_delete(int orderID)
    {

        String deleteStatement = "DELETE FROM " + OrderItem.TABLE_NAME
                                + " WHERE " + OrderItem.ORDER_ID + " = ?";

        String deleteStatementOrder = "DELETE FROM " + Order.TABLE_NAME
                                    + " WHERE " + Order.ORDER_ID + " = ?";


        Connection connection= null;
        PreparedStatement statement = null;
        int rowCountDeleted = 0;
        try {


            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(deleteStatement);
            statement.setInt(1,orderID);
            rowCountDeleted = statement.executeUpdate();


            statement = connection.prepareStatement(deleteStatementOrder);
            statement.setInt(1,orderID);
            rowCountDeleted = rowCountDeleted + statement.executeUpdate();


            System.out.println("Rows Deleted: " + rowCountDeleted);


            connection.commit();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();


            if (connection != null) {
                try {

//                    idOfInsertedRow=-1;
//                    rowCountItems = 0;

                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
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




    public int paymentReceived(int orderID)
    {
        String updateStatement = "UPDATE " + Order.TABLE_NAME

                + " SET " + " " + Order.STATUS_HOME_DELIVERY + " = ?"
                + " WHERE " + Order.ORDER_ID + " = ?"
                + " AND "  + Order.STATUS_HOME_DELIVERY + " = ? ";


        Connection connection = null;
        PreparedStatement statement = null;
        int updatedRows = -1;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);

            int i = 0;

            statement.setObject(++i, OrderStatusHomeDelivery.PAYMENT_RECEIVED);
            statement.setObject(++i,orderID);
            statement.setObject(++i, OrderStatusHomeDelivery.DELIVERED);

            updatedRows = statement.executeUpdate();
            System.out.println("Total rows updated: " + updatedRows);

            //conn.close();

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

        return updatedRows;
    }




    // fetch delivery guys assigned to the orders in the given shop with given status
    public UserEndpoint fetchDeliveryGuys(
            Integer shopID,
            Integer homeDeliveryStatus,
            String sortBy,
            Integer limit, Integer offset,
            boolean getRowCount,
            boolean getOnlyMetadata
    )
    {

        String queryCount = "";

        String query = "SELECT "

                + User.TABLE_NAME + "." + User.USER_ID + ","
                + User.TABLE_NAME + "." + User.NAME + ","
                + User.TABLE_NAME + "." + User.PHONE + ","
                + User.TABLE_NAME + "." + User.PROFILE_IMAGE_URL + ""

                + " FROM " + Order.TABLE_NAME
                + " INNER JOIN " + User.TABLE_NAME + " ON (" + Order.TABLE_NAME + "." + Order.DELIVERY_GUY_SELF_ID + " = " + User.TABLE_NAME + "." + User.USER_ID + ")"
                + " WHERE TRUE ";



//        boolean isFirst = true;



        if(shopID != null)
        {
            query = query + " AND " + Order.SHOP_ID + " = " + shopID;
        }







        if(homeDeliveryStatus != null)
        {

            query = query + " AND " + Order.STATUS_HOME_DELIVERY + " = " + homeDeliveryStatus;
        }






        // all the non-aggregate columns which are present in select must be present in group by also.
        query = query
                + " group by "
                + User.TABLE_NAME + "." + User.USER_ID ;


        queryCount = query;



        if(sortBy!=null)
        {
            if(!sortBy.equals(""))
            {
                String queryPartSortBy = " ORDER BY " + sortBy;

                query = query + queryPartSortBy;
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

            query = query + queryPartLimitOffset;
        }




        queryCount = "SELECT COUNT(*) as item_count FROM (" + queryCount + ") AS temp";




        UserEndpoint endPoint = new UserEndpoint();

        ArrayList<User> usersList = new ArrayList<>();
        Connection connection = null;


        PreparedStatement statement = null;
        ResultSet rs = null;


        PreparedStatement statementCount = null;
        ResultSet resultSetCount = null;


        try {

            connection = dataSource.getConnection();

            int i = 0;


            if(!getOnlyMetadata) {


//                statement = connection.prepareStatement(queryJoin);

                statement = connection.prepareStatement(query);


                rs = statement.executeQuery();

                while (rs.next()) {


                    User deliveryGuy = new User();
                    deliveryGuy.setUserID(rs.getInt(User.USER_ID));
                    deliveryGuy.setName(rs.getString(User.NAME));
                    deliveryGuy.setPhone(rs.getString(User.PHONE));
                    deliveryGuy.setProfileImagePath(rs.getString(User.PROFILE_IMAGE_URL));


                    usersList.add(deliveryGuy);
                }


                endPoint.setResults(usersList);
            }



            if(getRowCount)
            {
                statementCount = connection.prepareStatement(queryCount);

                i = 0;



                resultSetCount = statementCount.executeQuery();

                while(resultSetCount.next())
                {
                    System.out.println("Item Count : " + String.valueOf(endPoint.getItemCount()));
                    endPoint.setItemCount(resultSetCount.getInt("item_count"));
                }
            }



        } catch (SQLException e) {
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

                if(connection!=null)
                {connection.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


        return endPoint;
    }








    public int confirmOrderPFS(int orderID)
    {
        String updateStatement = "UPDATE " + Order.TABLE_NAME

                + " SET " + " " + Order.STATUS_PICK_FROM_SHOP + " = ?"
                + " WHERE " + Order.ORDER_ID + " = ?"
                + " AND "  + Order.STATUS_PICK_FROM_SHOP + " = ? ";


        Connection connection = null;
        PreparedStatement statement = null;
        int updatedRows = -1;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);

            int i = 0;

            statement.setObject(++i, OrderStatusPickFromShop.ORDER_CONFIRMED);
            statement.setObject(++i,orderID);
            statement.setObject(++i, OrderStatusPickFromShop.ORDER_PLACED);

            updatedRows = statement.executeUpdate();
            System.out.println("Total rows updated: " + updatedRows);

            //conn.close();

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

        return updatedRows;
    }



    public int setOrderPackedPFS(int orderID)
    {
        String updateStatement = "UPDATE " + Order.TABLE_NAME
                + " SET " + Order.STATUS_PICK_FROM_SHOP + " = ?"
                + " WHERE " + Order.ORDER_ID + " = ?"
                + " AND "  + Order.STATUS_PICK_FROM_SHOP + " = ? ";


//        + " AND "  + Order.SHOP_ID + " = ? "



        Connection connection = null;
        PreparedStatement statement = null;
        int updatedRows = -1;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);

            int i = 0;

            statement.setObject(++i, OrderStatusPickFromShop.ORDER_PACKED);
            statement.setObject(++i,orderID);
            statement.setObject(++i, OrderStatusPickFromShop.ORDER_CONFIRMED);




            updatedRows = statement.executeUpdate();
            System.out.println("Total rows updated: " + updatedRows);

            //conn.close();

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

        return updatedRows;
    }



    public int paymentReceivedPFS(int orderID)
    {
        String updateStatement = "UPDATE " + Order.TABLE_NAME

                + " SET " + " " + Order.STATUS_PICK_FROM_SHOP + " = ?"
                + " WHERE " + Order.ORDER_ID + " = ?"
                + " AND "  + Order.STATUS_PICK_FROM_SHOP + " = ? ";


        Connection connection = null;
        PreparedStatement statement = null;
        int updatedRows = -1;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);

            int i = 0;

            statement.setObject(++i, OrderStatusPickFromShop.DELIVERED);
            statement.setObject(++i,orderID);
            statement.setObject(++i, OrderStatusPickFromShop.ORDER_PACKED);

            updatedRows = statement.executeUpdate();
            System.out.println("Total rows updated: " + updatedRows);

            //conn.close();

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

        return updatedRows;
    }





}
