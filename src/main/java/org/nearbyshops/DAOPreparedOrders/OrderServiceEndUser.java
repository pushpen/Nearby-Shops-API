package org.nearbyshops.DAOPreparedOrders;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Order;
import org.nearbyshops.Model.OrderItem;
import org.nearbyshops.Model.Shop;
import org.nearbyshops.ModelDelivery.DeliveryAddress;
import org.nearbyshops.ModelEndpoint.OrderEndPoint;
import org.nearbyshops.ModelOrderStatus.OrderStatusHomeDelivery;
import org.nearbyshops.ModelReviewShop.ShopReview;
import org.nearbyshops.ModelStats.OrderStats;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by sumeet on 7/6/16.
 */



public class OrderServiceEndUser {


    private HikariDataSource dataSource = Globals.getDataSource();

//    private ShopDAO shopDAO = Globals.shopDAO;
//    private ShopItemDAO shopItemDAO = Globals.shopItemDAO;



    public ArrayList<Order> readOrders(Integer orderID, Integer endUserID, Integer shopID,
                                       Boolean pickFromShop,
                                       Integer homeDeliveryStatus, Integer pickFromShopStatus,
                                       Integer deliveryGuyID,
                                       Boolean paymentsReceived,
                                       Boolean deliveryReceived,
                                       Double latCenter, Double lonCenter,
                                       Boolean pendingOrders,
                                       String searchString,
                                       String sortBy,
                                       Integer limit, Integer offset)
    {


        String query = "SELECT "

                + "6371 * acos(cos( radians("
                + latCenter + ")) * cos( radians( lat_center) ) * cos(radians( lon_center ) - radians("
                + lonCenter + "))" + " + sin( radians(" + latCenter + ")) * sin(radians(lat_center))) as distance_shop" + ","

                + Shop.TABLE_NAME + "." + Shop.SHOP_ID + ","
                + Shop.SHOP_NAME + ","

                + Shop.DELIVERY_RANGE + ","
                + Shop.LAT_CENTER + ","
                + Shop.LON_CENTER + ","

                + Shop.DELIVERY_CHARGES + ","
                + Shop.BILL_AMOUNT_FOR_FREE_DELIVERY + ","
                + Shop.PICK_FROM_SHOP_AVAILABLE + ","
                + Shop.HOME_DELIVERY_AVAILABLE + ","

                + Shop.SHOP_ENABLED + ","
                + Shop.SHOP_WAITLISTED + ","

                + Shop.LOGO_IMAGE_PATH + ","

                + Shop.SHOP_ADDRESS + ","
                + Shop.CITY + ","
                + Shop.PINCODE + ","
                + Shop.LANDMARK + ","

                + Shop.CUSTOMER_HELPLINE_NUMBER + ","
                + Shop.DELIVERY_HELPLINE_NUMBER + ","

                + Shop.SHORT_DESCRIPTION + ","
                + Shop.LONG_DESCRIPTION + ","

                + Shop.TIMESTAMP_CREATED + ","
                + Shop.IS_OPEN + ","

                +  "avg(" + ShopReview.TABLE_NAME + "." + ShopReview.RATING + ") as avg_rating" + ","
                +  "count( DISTINCT " + ShopReview.TABLE_NAME + "." + ShopReview.END_USER_ID + ") as rating_count" + ","

                + "6371 * acos( cos( radians("
                + latCenter + ")) * cos( radians( " + DeliveryAddress.LATITUDE + ") ) * cos(radians( " + DeliveryAddress.LONGITUDE + " ) - radians("
                + lonCenter + "))"
                + " + sin( radians(" + latCenter + ")) * sin(radians( " + DeliveryAddress.LATITUDE + " ))) as distance" + ","

                + Order.TABLE_NAME + "." + Order.ORDER_ID + ","
                + Order.TABLE_NAME + "." + Order.DELIVERY_ADDRESS_ID + ","
                + Order.TABLE_NAME + "." + Order.DATE_TIME_PLACED + ","

                + Order.TABLE_NAME + "." + Order.DELIVERY_CHARGES + ","
                + Order.TABLE_NAME + "." + Order.DELIVERY_RECEIVED + ","
                + Order.TABLE_NAME + "." + Order.PAYMENT_RECEIVED + ","

                + Order.TABLE_NAME + "." + Order.DELIVERY_GUY_SELF_ID + ","
                + Order.TABLE_NAME + "." + Order.END_USER_ID + ","
                + Order.TABLE_NAME + "." + Order.PICK_FROM_SHOP + ","

                + Order.TABLE_NAME + "." + Order.SHOP_ID + ","
                + Order.TABLE_NAME + "." + Order.STATUS_HOME_DELIVERY + ","
                + Order.TABLE_NAME + "." + Order.STATUS_PICK_FROM_SHOP + ","

                + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.END_USER_ID + ","
                + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.CITY + ","
                + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.DELIVERY_ADDRESS + ","

                + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.ID + ","
                + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.LANDMARK + ","
                + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.NAME + ","

                + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.LATITUDE + ","
                + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.LONGITUDE + ","

                + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.PHONE_NUMBER + ","
                + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.PINCODE + ","

//                + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.LATITUDE + ","
//                + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.LONGITUDE + ","

                + " count( " + OrderItem.ITEM_ID + " ) as item_count, "
                + " sum( " + OrderItem.ITEM_PRICE_AT_ORDER + " * " + OrderItem.ITEM_QUANTITY + ") as item_total "
                + " FROM " + Order.TABLE_NAME
                + " LEFT OUTER JOIN " + OrderItem.TABLE_NAME + " ON (" + Order.TABLE_NAME + "." + Order.ORDER_ID + " = " + OrderItem.TABLE_NAME + "." + OrderItem.ORDER_ID + " ) "
                + " LEFT OUTER JOIN " + DeliveryAddress.TABLE_NAME + " ON (" + Order.TABLE_NAME + "." + Order.DELIVERY_ADDRESS_ID + " = " + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.ID + ")"
                + " INNER JOIN " + Shop.TABLE_NAME + " ON (" + Order.TABLE_NAME + "." + Order.SHOP_ID + " = " + Shop.TABLE_NAME + "." + Shop.SHOP_ID + ")"
                + " LEFT OUTER JOIN " + ShopReview.TABLE_NAME + " ON (" + ShopReview.TABLE_NAME + "." + ShopReview.SHOP_ID + " = " + Shop.TABLE_NAME + "." + Shop.SHOP_ID + ")";



        boolean isFirst = true;

        if(endUserID !=null)
        {
            query = query + " WHERE " + Order.TABLE_NAME + "." + Order.END_USER_ID + " = " + endUserID;

            isFirst = false;
        }

        if(shopID != null)
        {
            if(isFirst)
            {
                query = query + " WHERE " + Order.SHOP_ID + " = " + shopID;

                isFirst = false;

            }else
            {
                query = query + " AND " + Order.SHOP_ID + " = " + shopID;
            }

        }


        if(searchString != null)
        {

//            String queryPart = ;

            String queryPartSearch = " ( " + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.NAME +" ilike '%" + searchString + "%'"
                    + " or CAST ( " + Order.TABLE_NAME + "." + Order.ORDER_ID + " AS text )" + " ilike '%" + searchString + "%'" + ") ";

            //+ " or " + Item.TABLE_NAME + "." + Item.ITEM_NAME + " ilike '%" + searchString + "%'"

            if(isFirst)
            {
                query = query + " WHERE " + queryPartSearch;

                isFirst = false;

            }else
            {
                query = query + " AND " + queryPartSearch;
            }

        }




        if(pendingOrders!=null)
        {
            String queryPartPending = "";


            if(pendingOrders)
            {
                queryPartPending =
                        "( " + "(" + Order.STATUS_HOME_DELIVERY + " <= " + OrderStatusHomeDelivery.HANDOVER_ACCEPTED + ")"
                                + " OR "
                                + "((" + Order.STATUS_HOME_DELIVERY + " = " + OrderStatusHomeDelivery.PENDING_DELIVERY_ACCEPT_PENDING_PAYMENT + ")"
                                + " AND " + "((" + Order.PAYMENT_RECEIVED + " = " + " FALSE ) OR " + " (" + Order.DELIVERY_RECEIVED + " = " + " FALSE ))" + " )" + " )";

            }
            else
            {
                queryPartPending =
                        "( " + "((" + Order.STATUS_HOME_DELIVERY + " = " + OrderStatusHomeDelivery.PENDING_DELIVERY_ACCEPT_PENDING_PAYMENT + ")"
                                + " AND " + "((" + Order.PAYMENT_RECEIVED + " = " + " TRUE ) AND " + " (" + Order.DELIVERY_RECEIVED + " = " + " TRUE ))" + " )" + " )";

            }


            if(isFirst)
            {
                query = query + " WHERE " + queryPartPending;

                isFirst = false;

            }else
            {
                query = query + " AND " + queryPartPending;
            }
        }




        if(orderID!=null)
        {

            if(isFirst)
            {
                query = query + " WHERE " + Order.TABLE_NAME + "." + Order.ORDER_ID + " = " + orderID;

                isFirst = false;

            }else
            {
                query = query + " AND " + Order.TABLE_NAME + "." + Order.ORDER_ID + " = " + orderID;

            }
        }



        if(homeDeliveryStatus != null)
        {
            if(isFirst)
            {
                query = query + " WHERE " + Order.STATUS_HOME_DELIVERY + " = " + homeDeliveryStatus;

                isFirst = false;

            }else
            {
                query = query + " AND " + Order.STATUS_HOME_DELIVERY + " = " + homeDeliveryStatus;

            }

        }


        if(pickFromShopStatus != null)
        {
            if(isFirst)
            {
                query = query + " WHERE " + Order.STATUS_PICK_FROM_SHOP + " = " + pickFromShopStatus;

                isFirst = false;

            }else
            {
                query = query + " AND " + Order.STATUS_PICK_FROM_SHOP + " = " + pickFromShopStatus;
            }
        }



        if(pickFromShop != null)
        {
            if(isFirst)
            {
                query = query + " WHERE " + Order.PICK_FROM_SHOP + " = " + pickFromShop;

                isFirst = false;

            }else
            {
                query = query + " AND " + Order.PICK_FROM_SHOP + " = " + pickFromShop;
            }

        }



        if(deliveryGuyID != null)
        {
            if(isFirst)
            {
                query = query + " WHERE " + Order.DELIVERY_GUY_SELF_ID + " = " + deliveryGuyID;

                isFirst = false;

            }else
            {
                query = query + " AND " + Order.DELIVERY_GUY_SELF_ID + " = " + deliveryGuyID;
            }

        }



        if(paymentsReceived!=null)
        {

            if(isFirst)
            {
                query = query + " WHERE " + Order.PAYMENT_RECEIVED + " = " + paymentsReceived;

                isFirst = false;

            }else
            {
                query = query + " AND " + Order.PAYMENT_RECEIVED + " = " + paymentsReceived;
            }

        }



        if(deliveryReceived!=null)
        {

            if(isFirst)
            {
                query = query + " WHERE " + Order.DELIVERY_RECEIVED + " = " + deliveryReceived;

                isFirst = false;

            }else
            {
                query = query + " AND " + Order.DELIVERY_RECEIVED + " = " + deliveryReceived;
            }

        }







        // all the non-aggregate columns which are present in select must be present in group by also.
        query = query
                + " group by "
                + Order.TABLE_NAME + "." + Order.ORDER_ID + ","
                + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.ID ;



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




        ArrayList<Order> ordersList = new ArrayList<Order>();

        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.createStatement();


            rs = statement.executeQuery(query);

            while(rs.next())
            {
                Order order = new Order();

                order.setShopID((Integer)rs.getObject(Order.SHOP_ID));
                order.setDeliveryCharges(rs.getInt(Order.DELIVERY_CHARGES));
                order.setEndUserID(rs.getInt(Order.END_USER_ID));

                order.setOrderID(rs.getInt(Order.ORDER_ID));
                order.setPickFromShop(rs.getBoolean(Order.PICK_FROM_SHOP));
                order.setDateTimePlaced(rs.getTimestamp(Order.DATE_TIME_PLACED));

                order.setStatusHomeDelivery(rs.getInt(Order.STATUS_HOME_DELIVERY));
                order.setStatusPickFromShop(rs.getInt(Order.STATUS_PICK_FROM_SHOP));
                order.setDeliveryReceived(rs.getBoolean(Order.DELIVERY_RECEIVED));

                order.setPaymentReceived(rs.getBoolean(Order.PAYMENT_RECEIVED));
                order.setDeliveryAddressID((Integer) rs.getObject(Order.DELIVERY_ADDRESS_ID));
                order.setDeliveryGuySelfID((Integer) rs.getObject(Order.DELIVERY_GUY_SELF_ID));


                /*if(getDeliveryAddress!=null && getDeliveryAddress)
                {*/

                DeliveryAddress address = new DeliveryAddress();

                address.setEndUserID(rs.getInt(DeliveryAddress.END_USER_ID));
                address.setCity(rs.getString(DeliveryAddress.CITY));
                address.setDeliveryAddress(rs.getString(DeliveryAddress.DELIVERY_ADDRESS));

                address.setId(rs.getInt(DeliveryAddress.ID));
                address.setLandmark(rs.getString(DeliveryAddress.LANDMARK));
                address.setName(rs.getString(DeliveryAddress.NAME));

                address.setPhoneNumber(rs.getLong(DeliveryAddress.PHONE_NUMBER));
                address.setPincode(rs.getLong(DeliveryAddress.PINCODE));


                address.setLatitude(rs.getDouble(DeliveryAddress.LATITUDE));
                address.setLongitude(rs.getDouble(DeliveryAddress.LONGITUDE));
                address.setRt_distance(rs.getDouble("distance"));

                order.setDeliveryAddress(address);
//                }


                OrderStats orderStats = new OrderStats();
                orderStats.setOrderID(rs.getInt("order_id"));
                orderStats.setItemCount(rs.getInt("item_count"));
                orderStats.setItemTotal(rs.getInt("item_total"));
                order.setOrderStats(orderStats);


                Shop shop = new Shop();

                shop.setRt_distance(rs.getDouble("distance"));
                shop.setRt_rating_avg(rs.getFloat("avg_rating"));
                shop.setRt_rating_count(rs.getFloat("rating_count"));

                shop.setShopID(rs.getInt(Shop.SHOP_ID));

                shop.setShopName(rs.getString(Shop.SHOP_NAME));
                shop.setDeliveryRange(rs.getDouble(Shop.DELIVERY_RANGE));
                shop.setLatCenter(rs.getFloat(Shop.LAT_CENTER));
                shop.setLonCenter(rs.getFloat(Shop.LON_CENTER));

                shop.setDeliveryCharges(rs.getFloat(Shop.DELIVERY_CHARGES));
                shop.setBillAmountForFreeDelivery(rs.getInt(Shop.BILL_AMOUNT_FOR_FREE_DELIVERY));
                shop.setPickFromShopAvailable(rs.getBoolean(Shop.PICK_FROM_SHOP_AVAILABLE));
                shop.setHomeDeliveryAvailable(rs.getBoolean(Shop.HOME_DELIVERY_AVAILABLE));

                shop.setShopEnabled(rs.getBoolean(Shop.SHOP_ENABLED));
                shop.setShopWaitlisted(rs.getBoolean(Shop.SHOP_WAITLISTED));

                shop.setLogoImagePath(rs.getString(Shop.LOGO_IMAGE_PATH));

                shop.setShopAddress(rs.getString(Shop.SHOP_ADDRESS));
                shop.setCity(rs.getString(Shop.CITY));
                shop.setPincode(rs.getLong(Shop.PINCODE));
                shop.setLandmark(rs.getString(Shop.LANDMARK));

                shop.setCustomerHelplineNumber(rs.getString(Shop.CUSTOMER_HELPLINE_NUMBER));
                shop.setDeliveryHelplineNumber(rs.getString(Shop.DELIVERY_HELPLINE_NUMBER));

                shop.setShortDescription(rs.getString(Shop.SHORT_DESCRIPTION));
                shop.setLongDescription(rs.getString(Shop.LONG_DESCRIPTION));

                shop.setTimestampCreated(rs.getTimestamp(Shop.TIMESTAMP_CREATED));
                shop.setOpen(rs.getBoolean(Shop.IS_OPEN));

                order.setShop(shop);
                ordersList.add(order);
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


        return ordersList;
    }


    public OrderEndPoint endPointMetaDataOrders(Integer orderID, Integer endUserID, Integer shopID,
                                                Boolean pickFromShop,
                                                Integer homeDeliveryStatus, Integer pickFromShopStatus,
                                                Integer deliveryGuyID,
                                                Boolean paymentsReceived,
                                                Boolean deliveryReceived,
                                                Boolean pendingOrders,
                                                String searchString)
    {

        String query = "SELECT " + " count( DISTINCT " + Order.TABLE_NAME + "." + Order.ORDER_ID + ") as item_count"
                    + " FROM " + Order.TABLE_NAME
                    + " LEFT OUTER JOIN " + OrderItem.TABLE_NAME + " ON (" + Order.TABLE_NAME + "." + Order.ORDER_ID + " = " + OrderItem.TABLE_NAME + "." + OrderItem.ORDER_ID + " ) "
                    + " LEFT OUTER JOIN " + DeliveryAddress.TABLE_NAME + " ON (" + Order.TABLE_NAME + "." + Order.DELIVERY_ADDRESS_ID + " = " + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.ID + ")";



/*        if(getDeliveryAddress!=null && getDeliveryAddress)
        {

            String addressJoin = " INNER JOIN "
                    + DeliveryAddress.TABLE_NAME
                    + " ON (" + OrderPFS.TABLE_NAME + "." + OrderPFS.DELIVERY_ADDRESS_ID
                    + " = " + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.DELIVERY_GUY_SELF_ID + ")";

            query = query + addressJoin;
        }*/


        boolean isFirst = true;

        if(endUserID !=null)
        {
            query = query + " WHERE " + Order.TABLE_NAME + "." + Order.END_USER_ID + " = " + endUserID;

            isFirst = false;
        }

        if(shopID != null)
        {
            if(isFirst)
            {
                query = query + " WHERE " + Order.SHOP_ID + " = " + shopID;

                isFirst = false;

            }else
            {
                query = query + " AND " + Order.SHOP_ID + " = " + shopID;

            }

        }



        if(searchString != null)
        {

//            String queryPart = ;

            String queryPartSearch = " ( " + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.NAME +" ilike '%" + searchString + "%'"
                    + " or CAST ( " + Order.TABLE_NAME + "." + Order.ORDER_ID + " AS text )" + " ilike '%" + searchString + "%'" + ") ";

            //+ " or " + Item.TABLE_NAME + "." + Item.ITEM_NAME + " ilike '%" + searchString + "%'"

            if(isFirst)
            {
                query = query + " WHERE " + queryPartSearch;

                isFirst = false;

            }else
            {
                query = query + " AND " + queryPartSearch;
            }

        }



        if(pendingOrders!=null)
        {
            String queryPartPending = "";


            if(pendingOrders)
            {
                queryPartPending =
                        "( " + "(" + Order.STATUS_HOME_DELIVERY + " <= " + OrderStatusHomeDelivery.HANDOVER_ACCEPTED + ")"
                                + " OR "
                                + "((" + Order.STATUS_HOME_DELIVERY + " = " + OrderStatusHomeDelivery.PENDING_DELIVERY_ACCEPT_PENDING_PAYMENT + ")"
                                + " AND " + "((" + Order.PAYMENT_RECEIVED + " = " + " FALSE ) OR " + " (" + Order.DELIVERY_RECEIVED + " = " + " FALSE ))" + " )" + " )";

            }
            else
            {
                queryPartPending =
                        "( " + "((" + Order.STATUS_HOME_DELIVERY + " = " + OrderStatusHomeDelivery.PENDING_DELIVERY_ACCEPT_PENDING_PAYMENT + ")"
                                + " AND " + "((" + Order.PAYMENT_RECEIVED + " = " + " TRUE ) AND " + " (" + Order.DELIVERY_RECEIVED + " = " + " TRUE ))" + " )" + " )";

            }


            if(isFirst)
            {
                query = query + " WHERE " + queryPartPending;

                isFirst = false;

            }else
            {
                query = query + " AND " + queryPartPending;
            }
        }



        if(orderID!=null)
        {

            if(isFirst)
            {
                query = query + " WHERE " + Order.TABLE_NAME + "." + Order.ORDER_ID + " = " + orderID;

                isFirst = false;

            }else
            {
                query = query + " AND " + Order.TABLE_NAME + "." + Order.ORDER_ID + " = " + orderID;

            }
        }



        if(homeDeliveryStatus != null)
        {
            if(isFirst)
            {
                query = query + " WHERE " + Order.STATUS_HOME_DELIVERY + " = " + homeDeliveryStatus;

                isFirst = false;

            }else
            {
                query = query + " AND " + Order.STATUS_HOME_DELIVERY + " = " + homeDeliveryStatus;

            }

        }


        if(pickFromShopStatus != null)
        {
            if(isFirst)
            {
                query = query + " WHERE " + Order.STATUS_PICK_FROM_SHOP + " = " + pickFromShopStatus;

                isFirst = false;

            }else
            {
                query = query + " AND " + Order.STATUS_PICK_FROM_SHOP + " = " + pickFromShopStatus;
            }
        }



        if(pickFromShop != null)
        {
            if(isFirst)
            {
                query = query + " WHERE " + Order.PICK_FROM_SHOP + " = " + pickFromShop;

                isFirst = false;

            }else
            {
                query = query + " AND " + Order.PICK_FROM_SHOP + " = " + pickFromShop;
            }

        }



        if(deliveryGuyID != null)
        {
            if(isFirst)
            {
                query = query + " WHERE " + Order.DELIVERY_GUY_SELF_ID + " = " + deliveryGuyID;

                isFirst = false;

            }else
            {
                query = query + " AND " + Order.DELIVERY_GUY_SELF_ID + " = " + deliveryGuyID;
            }

        }



        if(paymentsReceived!=null)
        {

            if(isFirst)
            {
                query = query + " WHERE " + Order.PAYMENT_RECEIVED + " = " + paymentsReceived;

                isFirst = false;

            }else
            {
                query = query + " AND " + Order.PAYMENT_RECEIVED + " = " + paymentsReceived;
            }

        }



        if(deliveryReceived!=null)
        {

            if(isFirst)
            {
                query = query + " WHERE " + Order.DELIVERY_RECEIVED + " = " + deliveryReceived;

                isFirst = false;

            }else
            {
                query = query + " AND " + Order.DELIVERY_RECEIVED + " = " + deliveryReceived;
            }

        }



        OrderEndPoint endPoint = new OrderEndPoint();


        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);


            while(rs.next())
            {
                endPoint.setItemCount(rs.getInt("item_count"));
            }

            System.out.println("Item Count : " + endPoint.getItemCount());



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




}
