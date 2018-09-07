package org.nearbyshops.DAOPreparedOrders;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.*;
import org.nearbyshops.ModelStats.CartStats;

import java.sql.*;
import java.util.List;


/**
 * Created by sumeet on 7/6/16.
 */
public class PlaceOrderDAO {


    private HikariDataSource dataSource = Globals.getDataSource();

//    private ShopDAO shopDAO = Globals.shopDAO;
//    private ShopItemDAO shopItemDAO = Globals.shopItemDAO;


    public int placeOrderNew(Order order, int cartID) {

        Connection connection = null;

        PreparedStatement statementCopyCartToOrder = null;
        PreparedStatement statementCopyCartItemToOrderItem = null;
        PreparedStatement statementUpdateQuantity = null;
        PreparedStatement statementDeleteCart = null;
        PreparedStatement statementDeleteCartItem = null;
//        PreparedStatement statementUpdateOrder = null;


        Cart cart = Globals.cartService.readCart(cartID);
        List<CartStats> cartStats = Globals.cartStatsDAO.getCartStats(cart.getEndUserID(),cartID,cart.getShopID());
        Shop shop = Globals.shopDAO.getShop(cart.getShopID(),null,null);


        int orderID = -1;
        int copiedItemsCount = -1;
        int updatedItemsCount = -1;


        String copyCartToOrder = " insert into " + Order.TABLE_NAME
                + " ( "
                + Order.END_USER_ID + ","
                + Order.SHOP_ID + ","

                + " " + Order.STATUS_HOME_DELIVERY + ","
                + " " + Order.STATUS_PICK_FROM_SHOP + ","

                + " " + Order.PAYMENT_RECEIVED + ","
                + " " + Order.DELIVERY_RECEIVED + ","

                + " " + Order.DELIVERY_CHARGES + ","
                + " " + Order.DELIVERY_ADDRESS_ID + ","
//                + OrderPFS.DELIVERY_GUY_SELF_ID + ","
                + Order.PICK_FROM_SHOP + ""
                + " ) " +
                " select "
                + Cart.END_USER_ID + ","
                + Cart.SHOP_ID + ","
                + " 1 " + ","
                + " 1 " + ","

                + " false " + ","
                + " false " + ","

                + " ? " + ","
                + " ? " + ","

                + " false " + ""
                + " from " + Cart.TABLE_NAME
                + " where " + Cart.CART_ID + " = ?";

//        String copyCartToOrder = "insert into customer_order " +
//                "(end_user_id,shop_id) " +
//                "select end_user_id , shop_id from cart where cart_id = ?";


        String copyCartItemToOrderItem =

                "insert into " + OrderItem.TABLE_NAME +
                        " ("
                        + OrderItem.ORDER_ID  + ","
                        + OrderItem.ITEM_ID + ","
                        + OrderItem.ITEM_PRICE_AT_ORDER + ","
                        + OrderItem.ITEM_QUANTITY + ") " +





                        " select " + " ? " + ","
                        + ShopItem.TABLE_NAME+ "." + ShopItem.ITEM_ID + ","
                        + ShopItem.TABLE_NAME + "." + ShopItem.ITEM_PRICE + ","
                        + CartItem.TABLE_NAME + "." + CartItem.ITEM_QUANTITY
                        + " from "
                        + CartItem.TABLE_NAME + ","
                        + Cart.TABLE_NAME + ","
                        + ShopItem.TABLE_NAME  +
                        " where "
                        + Cart.TABLE_NAME + "." + Cart.CART_ID + " = " + CartItem.TABLE_NAME + "." + CartItem.CART_ID +
                        " and "
                        + ShopItem.TABLE_NAME + "." + ShopItem.SHOP_ID + " = " + Cart.TABLE_NAME + "." + Cart.SHOP_ID +
                        " and "
                        + ShopItem.TABLE_NAME + "." + ShopItem.ITEM_ID + " = " + CartItem.TABLE_NAME + "." + CartItem.ITEM_ID +
                        " and "
                        + Cart.TABLE_NAME + "." + Cart.CART_ID + " = ? ";




        String copyCartItemToOrderItemBackup =

                "insert into " +
                        " order_item" +
                        " (order_id" + "," + "item_id" + "," + " item_price_at_order" + ","
                        + "item_quantity) " +

                        " select " + " ? " +
                        ", shop_item.item_id,item_price, cart_item.item_quantity from cart_item, cart,shop_item " +
                        " where " +
                        " cart.cart_id = cart_item.cart_id " +
                        " and " +
                        "shop_item.shop_id = cart.shop_id" +
                        " and " +
                        " shop_item.item_id = cart_item.item_id " +
                        " and " +
                        " cart.cart_id = ? ";



        String updateQuantity =
                        " Update " + ShopItem.TABLE_NAME +
                        " SET " +  ShopItem.AVAILABLE_ITEM_QUANTITY + " = " +  ShopItem.AVAILABLE_ITEM_QUANTITY + " - " +  OrderItem.ITEM_QUANTITY +
                        " from " +  OrderItem.TABLE_NAME + "," + Order.TABLE_NAME +
                        " where " + OrderItem.TABLE_NAME + "." + OrderItem.ITEM_ID + " = " + ShopItem.TABLE_NAME + "." + ShopItem.ITEM_ID +
                        " and " + Order.TABLE_NAME+ "." + Order.ORDER_ID + " = " + OrderItem.TABLE_NAME+ "."  + OrderItem.ORDER_ID +
                        " and " + ShopItem.TABLE_NAME + "." + ShopItem.SHOP_ID + " = " + Order.TABLE_NAME + "." + Order.SHOP_ID +
                        " and " + Order.TABLE_NAME + "." + Order.ORDER_ID + " = ?";




        String updateQuantityBackup =

                " Update shop_item SET available_item_quantity = available_item_quantity - item_quantity from order_item,customer_order " +
                        " where order_item.item_id = shop_item.item_id " +
                        " and customer_order.order_id = order_item.order_id " +
                        " and shop_item.shop_id = customer_order.shop_id " +
                        " and customer_order.order_id = " + " ? ";



        String deleteCart = " DELETE FROM " + CartItem.TABLE_NAME +
                            " WHERE " + Cart.CART_ID + " = ?";


        String deleteCartItems = " DELETE FROM " + Cart.TABLE_NAME +
                                 " WHERE " + Cart.CART_ID + " = ?";



        String updateOrder = "UPDATE " + Order.TABLE_NAME

                + " SET "
                + Order.END_USER_ID + " = ?,"
                + " " + Order.SHOP_ID + " = ?,"
                + " " + Order.STATUS_HOME_DELIVERY + " = ?,"
                + " " + Order.STATUS_PICK_FROM_SHOP + " = ?,"
                + " " + Order.PAYMENT_RECEIVED + " = ?,"
                + " " + Order.DELIVERY_RECEIVED + " = ?,"
                + " " + Order.DELIVERY_CHARGES + " = ?,"
                + " " + Order.DELIVERY_ADDRESS_ID + " = ?,"
                + Order.DELIVERY_GUY_SELF_ID + " = ?,"
                + Order.PICK_FROM_SHOP + " = ?"
                + " WHERE " + Order.ORDER_ID + " = ?";



        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            int deliveryCharges = 0;

            if(cartStats.size()==1)
            {
                if(cartStats.get(0).getCart_Total() < shop.getBillAmountForFreeDelivery())
                {
                    deliveryCharges = (int) shop.getDeliveryCharges();
                }
            }



            statementCopyCartToOrder = connection.prepareStatement(copyCartToOrder,PreparedStatement.RETURN_GENERATED_KEYS);
            statementCopyCartToOrder.setInt(1,deliveryCharges);
            statementCopyCartToOrder.setInt(2,order.getDeliveryAddressID());
            statementCopyCartToOrder.setInt(3,cartID);
            statementCopyCartToOrder.executeUpdate();

            ResultSet rsCopyCartToOrder = statementCopyCartToOrder.getGeneratedKeys();
            if(rsCopyCartToOrder.next())
            {
                orderID = rsCopyCartToOrder.getInt(1);
            }


            statementCopyCartItemToOrderItem = connection.prepareStatement(copyCartItemToOrderItem,PreparedStatement.RETURN_GENERATED_KEYS);
            statementCopyCartItemToOrderItem.setInt(1,orderID);
            statementCopyCartItemToOrderItem.setInt(2,cartID);
            copiedItemsCount = statementCopyCartItemToOrderItem.executeUpdate();


            statementUpdateQuantity = connection.prepareStatement(updateQuantity,PreparedStatement.RETURN_GENERATED_KEYS);
            statementUpdateQuantity.setInt(1,orderID);
            updatedItemsCount = statementUpdateQuantity.executeUpdate();



            statementDeleteCart = connection.prepareStatement(deleteCart,PreparedStatement.RETURN_GENERATED_KEYS);
            statementDeleteCart.setInt(1,cartID);
            statementDeleteCart.executeUpdate();


            statementDeleteCartItem = connection.prepareStatement(deleteCartItems,PreparedStatement.RETURN_GENERATED_KEYS);
            statementDeleteCartItem.setInt(1,cartID);
            statementDeleteCartItem.executeUpdate();


//            setupOrder(order,orderID);
//            statementUpdateOrder = connection.prepareStatement(updateOrder,PreparedStatement.RETURN_GENERATED_KEYS);
//            int i = 0;
//            statementUpdateOrder.setObject(++i,order.getEndUserID());
//            statementUpdateOrder.setObject(++i,order.getShopID());
//            statementUpdateOrder.setObject(++i,order.getStatusHomeDelivery());
//            statementUpdateOrder.setObject(++i,order.getStatusPickFromShop());
//            statementUpdateOrder.setObject(++i,order.getPaymentReceived());
//            statementUpdateOrder.setObject(++i,order.getDeliveryReceived());
//            statementUpdateOrder.setObject(++i,order.getDeliveryCharges());
//            statementUpdateOrder.setObject(++i,order.getDeliveryAddressID());
//            statementUpdateOrder.setObject(++i,order.getDeliveryGuySelfID());
//            statementUpdateOrder.setObject(++i,order.getPickFromShop());
//            statementUpdateOrder.setObject(++i,order.getOrderID());
//            statementUpdateOrder.executeUpdate();


            connection.commit();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            if (connection != null) {
                try {

//                    rowIdUserID = -1;
                    orderID = -1;
                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }

        } finally {




            if (statementCopyCartToOrder != null) {
                try {

                    statementCopyCartToOrder.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (statementCopyCartItemToOrderItem != null) {
                try {

                    statementCopyCartItemToOrderItem.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }



            if (statementUpdateQuantity != null) {
                try {

                    statementUpdateQuantity.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }



            if (statementDeleteCart != null) {
                try {

                    statementDeleteCart.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }




            if (statementDeleteCartItem != null) {
                try {

                    statementDeleteCartItem.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }



//            if (statementUpdateOrder != null) {
//                try {
//
//                    statementUpdateOrder.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }




            try {

                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


//        if(orderID!=-1)
//        {
//            setupOrder(order,orderID);
//        }

        return orderID;
    }










//    void setupOrder(Order order, int orderID)
//    {
//
//        Order tempOrder = readOrder(orderID);
//
//        order.setOrderID(orderID);
//        order.setEndUserID(tempOrder.getEndUserID());
//        order.setShopID(tempOrder.getShopID());
//
//        if(order.getPickFromShop())
//        {
//            order.setDeliveryCharges(0);
//            order.setStatusPickFromShop(1);
//
//        }else
//        {
//            Shop shop = Globals.shopDAO.getShop(tempOrder.getShopID(),null,null);
//            order.setDeliveryCharges((int)shop.getDeliveryCharges());
//            order.setStatusHomeDelivery(1);
//        }
//
//        order.setPaymentReceived(false);
//        order.setDeliveryReceived(false);
//
//        updateOrder(order);
//    }



//    public Order placeOrder(Order order, int cartID)
//    {
//
//        /*
//
//        1. Copy cart to order
//        2. Copy cart_item to order_item
//        3. update available_item_quantity from order to shop_item
//        4. delete cart_item
//        5. delete cart
//         */
//
//
//        int orderID = -1;
//        int rowCount = -1;
//        int rowCountAvailableItemQuantity= -1;
//        int status = 0;
//
//        orderID = copyCartToOrder(cartID);
//        rowCount = copyCartItemToOrderItem(orderID,cartID);
//        rowCountAvailableItemQuantity = Globals.shopItemDAO.updateAvailableItemQuantity(orderID);
//        Globals.cartItemService.deleteCartItem(null,cartID);
//        Globals.cartService.deleteCart(cartID);
//
//
//
//
//
//        if(orderID>0 && rowCount>0)
//        {
//            Order tempOrder = readOrder(orderID);
//
//            order.setOrderID(orderID);
//            order.setEndUserID(tempOrder.getEndUserID());
//            order.setShopID(tempOrder.getShopID());
//
//            if(order.getPickFromShop())
//            {
//               order.setDeliveryCharges(0);
//                order.setStatusPickFromShop(1);
//
//            }else
//            {
//                order.setDeliveryCharges((int) Globals.shopDAO.getShop(tempOrder.getShopID(),null,null).getDeliveryCharges());
//                order.setStatusHomeDelivery(1);
//            }
//
//            order.setPaymentReceived(false);
//            order.setDeliveryReceived(false);
//
//
//            updateOrder(order);
//
//            return readOrder(orderID);
//
//        }
//
//        return null;
//    }



//    private int copyCartToOrder(int cartID)
//    {
//
//        String copyCartToOrder = "insert into customer_order " +
//                "(end_user_id,shop_id) " +
//                "select end_user_id , shop_id from cart where cart_id = " +
//                cartID;
//
//
//        Connection connection = null;
//        Statement statement = null;
//        int rowIdOfInsertedRow = -1;
//
//        try {
//
//            connection = dataSource.getConnection();
//            statement = connection.createStatement();
//
//            rowIdOfInsertedRow = statement.executeUpdate(copyCartToOrder,Statement.RETURN_GENERATED_KEYS);
//            ResultSet rs = statement.getGeneratedKeys();
//
//
//            if(rs.next())
//            {
//                rowIdOfInsertedRow = rs.getInt(1);
//            }
//
//
//
//            System.out.println("Key autogenerated OrderPFS: " + rowIdOfInsertedRow);
//
//
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        finally
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
//
//        return rowIdOfInsertedRow;
//    }




//    private int copyCartItemToOrderItem(int orderID, int cartID)
//    {
//
//
//
//        String copyCartItemToOrderItem =
//
//                "insert into " +
//                        " order_item" +
//                        " (order_id" + "," + "item_id" + "," + " item_price_at_order" + ","
//                        + "item_quantity) " +
//
//                        " select " + orderID +
//                ", shop_item.item_id,item_price, cart_item.item_quantity from cart_item, cart,shop_item " +
//                " where " +
//                " cart.cart_id = cart_item.cart_id " +
//                " and " +
//                "shop_item.shop_id = cart.shop_id" +
//                " and " +
//                " shop_item.item_id = cart_item.item_id " +
//                " and " +
//                " cart.cart_id = " + cartID;
//
//
//
//
//        Connection connection = null;
//        Statement statement = null;
//        int rowCount = -1;
//
//
//
//        try {
//
//            connection = dataSource.getConnection();
//            statement = connection.createStatement();
//
//            rowCount = statement.executeUpdate(copyCartItemToOrderItem,Statement.RETURN_GENERATED_KEYS);
//
//            //ResultSet rs = stmt.getGeneratedKeys();
//
//            //if(rs.next())
//            //{
//            //    rowIdOfInsertedRow = rs.getInt(1);
//            //}
//
//
//
//            System.out.println("Rows updated : copy cart item to order item : " + rowCount);
//
//
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        finally
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
//
//        return rowCount;
//    }




//    public Order readOrder(int orderID)
//    {
//
//        String query = "SELECT "
//
//                 + Order.ORDER_ID + ","
//                 + Order.DELIVERY_ADDRESS_ID + ","
//                 + Order.DATE_TIME_PLACED + ","
//
//                 + Order.DELIVERY_CHARGES + ","
//                 + Order.DELIVERY_RECEIVED + ","
//                 + Order.PAYMENT_RECEIVED + ","
//
//                 + Order.DELIVERY_GUY_SELF_ID + ","
//                 + Order.END_USER_ID + ","
//                 + Order.PICK_FROM_SHOP + ","
//
//                + Order.SHOP_ID + ","
//                + Order.STATUS_HOME_DELIVERY + ","
//                + Order.STATUS_PICK_FROM_SHOP + ""
//
//                + " FROM " + Order.TABLE_NAME
//                + " WHERE " + Order.ORDER_ID + " = " + orderID;
//
//        Connection connection = null;
//        Statement statement = null;
//        ResultSet rs = null;
//
//        Order order = null;
//
//        try {
//
//            connection = dataSource.getConnection();
//            statement = connection.createStatement();
//            rs = statement.executeQuery(query);
//
//            while(rs.next())
//            {
//                order = new Order();
//
//                order.setShopID(rs.getInt(Order.SHOP_ID));
//                order.setDeliveryCharges(rs.getInt(Order.DELIVERY_CHARGES));
//                order.setEndUserID(rs.getInt(Order.END_USER_ID));
//
//                order.setOrderID(rs.getInt(Order.ORDER_ID));
//                order.setPickFromShop(rs.getBoolean(Order.PICK_FROM_SHOP));
//                order.setDateTimePlaced(rs.getTimestamp(Order.DATE_TIME_PLACED));
//
//                order.setStatusHomeDelivery(rs.getInt(Order.STATUS_HOME_DELIVERY));
//                order.setStatusPickFromShop(rs.getInt(Order.STATUS_PICK_FROM_SHOP));
//                order.setDeliveryReceived(rs.getBoolean(Order.DELIVERY_RECEIVED));
//
//                order.setPaymentReceived(rs.getBoolean(Order.PAYMENT_RECEIVED));
//                order.setDeliveryAddressID((Integer) rs.getObject(Order.DELIVERY_ADDRESS_ID));
//                order.setDeliveryGuySelfID((Integer) rs.getObject(Order.DELIVERY_GUY_SELF_ID));
//            }
//
//
//            //System.out.println("Total itemCategories queried " + itemCategoryList.size());
//
//
//
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } finally
//
//        {
//
//            try {
//                if(rs!=null)
//                {rs.close();}
//            } catch (SQLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
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
//        return order;
//    }



//
//    public int updateOrder(Order order)
//    {
//        String updateStatement = "UPDATE " + Order.TABLE_NAME
//
//                + " SET "
//                + Order.END_USER_ID + " = ?,"
//                + " " + Order.SHOP_ID + " = ?,"
//                + " " + Order.STATUS_HOME_DELIVERY + " = ?,"
//                + " " + Order.STATUS_PICK_FROM_SHOP + " = ?,"
//                + " " + Order.PAYMENT_RECEIVED + " = ?,"
//                + " " + Order.DELIVERY_RECEIVED + " = ?,"
//                + " " + Order.DELIVERY_CHARGES + " = ?,"
//                + " " + Order.DELIVERY_ADDRESS_ID + " = ?,"
//                      + Order.DELIVERY_GUY_SELF_ID + " = ?,"
//                      + Order.PICK_FROM_SHOP + " = ?"
//                + " WHERE " + Order.ORDER_ID + " = ?";
//
//
//
//        Connection connection = null;
//        PreparedStatement statement = null;
//        int updatedRows = -1;
//
//        try {
//
//            connection = dataSource.getConnection();
//            statement = connection.prepareStatement(updateStatement);
//
//            statement.setObject(1,order.getEndUserID());
//            statement.setObject(2,order.getShopID());
//            statement.setObject(3,order.getStatusHomeDelivery());
//            statement.setObject(4,order.getStatusPickFromShop());
//            statement.setObject(5,order.getPaymentReceived());
//            statement.setObject(6,order.getDeliveryReceived());
//            statement.setObject(7,order.getDeliveryCharges());
//            statement.setObject(8,order.getDeliveryAddressID());
//            statement.setObject(9,order.getDeliveryGuySelfID());
//            statement.setObject(10,order.getPickFromShop());
//            statement.setObject(11,order.getOrderID());
//
//
//            updatedRows = statement.executeUpdate();
//            System.out.println("Total rows updated: " + updatedRows);
//
//            //conn.close();
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
//        return updatedRows;
//    }


}
