package org.nearbyshops.Globals;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.DAOPreparedCartOrder.CartItemService;
import org.nearbyshops.DAOPreparedCartOrder.CartService;
import org.nearbyshops.DAOPreparedCartOrder.CartStatsDAO;
import org.nearbyshops.DAOPreparedCartOrder.DeliveryAddressService;
import org.nearbyshops.DAOPreparedOrders.OrderItemService;
import org.nearbyshops.DAOPreparedOrders.OrderService;
import org.nearbyshops.DAOPreparedOrders.PlaceOrderDAO;
import org.nearbyshops.DAOPreparedReviewItem.FavoriteItemDAOPrepared;
import org.nearbyshops.DAOPreparedReviewItem.ItemReviewDAOPrepared;
import org.nearbyshops.DAOPreparedReviewItem.ItemReviewThanksDAOPrepared;
import org.nearbyshops.DAOPreparedReviewShop.FavoriteBookDAOPrepared;
import org.nearbyshops.DAOPreparedReviewShop.ShopReviewDAOPrepared;
import org.nearbyshops.DAOPreparedReviewShop.ShopReviewThanksDAOPrepared;
import org.nearbyshops.DAORoles.DAOShopStaff;
import org.nearbyshops.DAORoles.DAOUserNew;
import org.nearbyshops.DAOsPrepared.*;

import java.security.SecureRandom;

/**
 * Created by sumeet on 22/3/17.
 */
public class Globals {


    // secure randon for generating tokens
    public static SecureRandom random = new SecureRandom();


    public static ItemCategoryDAO itemCategoryDAO = new ItemCategoryDAO();
    public static ItemDAO itemDAO = new ItemDAO();
    public static ItemDAOJoinOuter itemDAOJoinOuter = new ItemDAOJoinOuter();
    public static ShopDAO shopDAO = new ShopDAO();


    public static CartService cartService = new CartService();
    public static CartItemService cartItemService = new CartItemService();
    public static CartStatsDAO cartStatsDAO = new CartStatsDAO();
    public static OrderService orderService = new OrderService();
    public static DeliveryAddressService deliveryAddressService = new DeliveryAddressService();
    public static OrderItemService orderItemService = new OrderItemService();


    public static ShopItemByShopDAO shopItemByShopDAO = new ShopItemByShopDAO();
    public static ShopItemDAO shopItemDAO = new ShopItemDAO();
    public static ShopItemByItemDAO shopItemByItemDAO = new ShopItemByItemDAO();


    // Item Image and Item Specification DAO'

    public static ItemImagesDAO itemImagesDAO = new ItemImagesDAO();
    public static DAOUserNew daoUserNew = new DAOUserNew();




    public static FavoriteItemDAOPrepared favoriteItemDAOPrepared = new FavoriteItemDAOPrepared();
    public static ItemReviewDAOPrepared itemReviewDAOPrepared = new ItemReviewDAOPrepared();
    public static ItemReviewThanksDAOPrepared itemReviewThanksDAOPrepared = new ItemReviewThanksDAOPrepared();



    public static FavoriteBookDAOPrepared favoriteBookDAOPrepared = new FavoriteBookDAOPrepared();
    public static ShopReviewDAOPrepared shopReviewDAOPrepared = new ShopReviewDAOPrepared();
    public static ShopReviewThanksDAOPrepared shopReviewThanksDAO = new ShopReviewThanksDAOPrepared();




    public static DAOShopStaff daoShopStaff = new DAOShopStaff();

    public static PlaceOrderDAO pladeOrderDAO = new PlaceOrderDAO();



    // static reference for holding security accountApproved

    public static Object accountApproved;







    // Configure Connection Pooling


    private static HikariDataSource dataSource;



    public static HikariDataSource getDataSource() {


        if (dataSource == null) {


            org.apache.commons.configuration2.Configuration configuration = GlobalConfig.getConfiguration();


            if(configuration==null)
            {
                System.out.println("Configuration is null : getDataSource() HikariCP !");

                return null ;
            }



            String connection_url = configuration.getString(ConfigurationKeys.CONNECTION_URL);
            String username = configuration.getString(ConfigurationKeys.POSTGRES_USERNAME);
            String password = configuration.getString(ConfigurationKeys.POSTGRES_PASSWORD);



            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(connection_url);
            config.setUsername(username);
            config.setPassword(password);

            dataSource = new HikariDataSource(config);
        }

        return dataSource;
    }




}
