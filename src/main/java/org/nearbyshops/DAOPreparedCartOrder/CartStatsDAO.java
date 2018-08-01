package org.nearbyshops.DAOPreparedCartOrder;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Cart;
import org.nearbyshops.Model.CartItem;
import org.nearbyshops.Model.ShopItem;
import org.nearbyshops.ModelStats.CartStats;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sumeet on 1/6/16.
 */
public class CartStatsDAO {


    private HikariDataSource dataSource = Globals.getDataSource();


    public List<CartStats> getCartStats(int endUserID, Integer cartID, Integer shopID)
    {

        /*

        select sum(item_quantity*item_price) as Cart_Total,count(cart_item.item_id) as Items_In_Cart,cart.shop_id
        from shop_item, cart, cart_item where cart.cart_id = cart_item.cart_id and shop_item.shop_id = cart.shop_id
        and shop_item.item_id = cart_item.item_id and end_user_id = 1 group by cart.shop_id

         */


        String queryTwo =  " select " +
                        " sum(" + CartItem.ITEM_QUANTITY + "*" + ShopItem.ITEM_PRICE + ") as Cart_Total," +
                        " count(" + CartItem.TABLE_NAME + "." + CartItem.ITEM_ID + ") as Items_In_Cart," +
                        Cart.TABLE_NAME + "." + Cart.SHOP_ID + "," +
                        Cart.TABLE_NAME + "." + Cart.CART_ID  +
                        " from " + ShopItem.TABLE_NAME + "," + Cart.TABLE_NAME + "," + CartItem.TABLE_NAME + " " +
                        " where " + Cart.TABLE_NAME + "." + Cart.CART_ID + " = " + CartItem.TABLE_NAME + "." + CartItem.CART_ID +
                        " and " + "shop_item.shop_id" + " = " + "cart.shop_id " +
                        " and " + "shop_item.item_id" + " = " + "cart_item.item_id " +
                        " and " + "end_user_id = " + endUserID ;


        String query =  " select " +
                    " sum(" + CartItem.ITEM_QUANTITY + "*" + ShopItem.ITEM_PRICE + ") as Cart_Total," +
                    " count(" + CartItem.TABLE_NAME + "." + CartItem.ITEM_ID + ") as Items_In_Cart," +
                    Cart.TABLE_NAME + "." + Cart.SHOP_ID + "," +
                    Cart.TABLE_NAME + "." + Cart.CART_ID  +
                    " from " + ShopItem.TABLE_NAME +
                    " INNER JOIN " + Cart.TABLE_NAME + " ON ( " + Cart.TABLE_NAME + "." + Cart.SHOP_ID + " = "  + ShopItem.TABLE_NAME + "." + ShopItem.SHOP_ID + " )" +
                    " INNER JOIN " + CartItem.TABLE_NAME + " ON ( " + Cart.TABLE_NAME + "." + Cart.CART_ID + " = " + CartItem.TABLE_NAME + "."  + CartItem.CART_ID + ")" +
//                    " INNER JOIN " + ShopItem.TABLE_NAME + " ON ( " + ShopItem.TABLE_NAME + "." + ShopItem.ITEM_ID + " = " + CartItem.TABLE_NAME + "." + CartItem.ITEM_ID + ")" +
                    " where "
                    + " ( " + ShopItem.TABLE_NAME + "." + ShopItem.ITEM_ID + " = " + CartItem.TABLE_NAME + "." + CartItem.ITEM_ID + " ) "
                    + " and " + Cart.END_USER_ID + " = " + endUserID ;




        if(cartID != null)
        {
            query = query + "and " + Cart.TABLE_NAME + "." + Cart.CART_ID + " = " + cartID;
        }



        if(shopID != null)
        {
            query = query + " and " + Cart.TABLE_NAME + "." + Cart.SHOP_ID + " = " + shopID;
        }


        String groupByQueryPart =  " group by " +
                                    " cart.shop_id," + Cart.TABLE_NAME + "." + Cart.CART_ID;

        query = query + groupByQueryPart;


        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;


        ArrayList<CartStats> cartStatsList = new ArrayList<>();

        try {

//            DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL,
//                    JDBCContract.CURRENT_USERNAME,JDBCContract.CURRENT_PASSWORD)

            connection = dataSource.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);

            while(rs.next())
            {
                CartStats cartStats = new CartStats();

                cartStats.setCartID(rs.getInt(Cart.CART_ID));
                cartStats.setShopID(rs.getInt(Cart.SHOP_ID));
                cartStats.setItemsInCart(rs.getInt("items_in_cart"));
                cartStats.setCart_Total(rs.getDouble("cart_total"));

                cartStatsList.add(cartStats);
            }


            //System.out.println("Total itemCategories queried " + itemCategoryList.size());



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

        return cartStatsList;
    }

}
