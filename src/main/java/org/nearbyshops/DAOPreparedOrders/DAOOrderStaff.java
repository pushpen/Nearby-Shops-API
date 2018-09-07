package org.nearbyshops.DAOPreparedOrders;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Order;
import org.nearbyshops.ModelOrderStatus.OrderStatusHomeDelivery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DAOOrderStaff {


    private HikariDataSource dataSource = Globals.getDataSource();



    public int confirmOrder(Order order)
    {
        String updateStatement = "UPDATE " + Order.TABLE_NAME

                + " SET "
//                + OrderPFS.END_USER_ID + " = ?,"
//                + " " + OrderPFS.SHOP_ID + " = ?,"
                + " " + Order.STATUS_HOME_DELIVERY + " = ?"
//                + " " + OrderPFS.STATUS_PICK_FROM_SHOP + " = ?"
//                + " " + OrderPFS.PAYMENT_RECEIVED + " = ?,"
//                + " " + OrderPFS.DELIVERY_RECEIVED + " = ?"
//                + " " + OrderPFS.DELIVERY_CHARGES + " = ?,"
//                + " " + OrderPFS.DELIVERY_ADDRESS_ID + " = ?,"
//                + OrderPFS.DELIVERY_GUY_SELF_ID + " = ?"
//                + OrderPFS.PICK_FROM_SHOP + " = ?"
                + " WHERE " + Order.ORDER_ID + " = ?"
                + " AND "  + Order.STATUS_HOME_DELIVERY + " = ? ";


//        + " AND "  + Order.SHOP_ID + " = ? "



        Connection connection = null;
        PreparedStatement statement = null;
        int updatedRows = -1;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);

            int i = 0;

//            statement.setObject(1,order.getEndUserID());
//            statement.setObject(1,order.getShopID());
            statement.setObject(++i, OrderStatusHomeDelivery.ORDER_CONFIRMED);
//            statement.setObject(4,order.getStatusPickFromShop());
//            statement.setObject(2,order.getPaymentReceived());
//            statement.setObject(3,order.getDeliveryReceived());
//            statement.setObject(7,order.getDeliveryCharges());
//            statement.setObject(8,order.getDeliveryAddressID());
//            statement.setObject(2,order.getDeliveryGuySelfID());
//            statement.setObject(10,order.getPickFromShop());
            statement.setObject(++i,order.getOrderID());
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



}
