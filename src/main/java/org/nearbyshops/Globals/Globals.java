package org.nearbyshops.Globals;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.sargue.mailgun.Configuration;
import org.nearbyshops.DAOAnalytics.DAOItemAnalytics;
import org.nearbyshops.DAOBilling.DAOAddBalance;
import org.nearbyshops.DAOBilling.DAOTransaction;
import org.nearbyshops.DAOPreparedCartOrder.CartItemService;
import org.nearbyshops.DAOPreparedCartOrder.CartService;
import org.nearbyshops.DAOPreparedCartOrder.CartStatsDAO;
import org.nearbyshops.DAOPreparedCartOrder.DeliveryAddressService;
import org.nearbyshops.DAOPreparedItemSpecification.*;
import org.nearbyshops.DAOPreparedOrders.*;
import org.nearbyshops.DAOPreparedReviewItem.FavoriteItemDAOPrepared;
import org.nearbyshops.DAOPreparedReviewItem.ItemReviewDAOPrepared;
import org.nearbyshops.DAOPreparedReviewItem.ItemReviewThanksDAOPrepared;
import org.nearbyshops.DAOPreparedReviewShop.FavoriteBookDAOPrepared;
import org.nearbyshops.DAOPreparedReviewShop.ShopReviewDAOPrepared;
import org.nearbyshops.DAOPreparedReviewShop.ShopReviewThanksDAOPrepared;
import org.nearbyshops.DAOPreparedSettings.ServiceConfigurationDAOPrepared;
import org.nearbyshops.DAOPushNotifications.DAOOneSignal;
import org.nearbyshops.DAORoles.*;
import org.nearbyshops.DAOsPrepared.*;
import org.nearbyshops.RESTEndpointRoles.UserSignUpRESTEndpoint;

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

    public static ShopItemByShopDAO shopItemByShopDAO = new ShopItemByShopDAO();
    public static ShopItemDAO shopItemDAO = new ShopItemDAO();
    public static ShopItemByItemDAO shopItemByItemDAO = new ShopItemByItemDAO();


    public static CartService cartService = new CartService();
    public static CartItemService cartItemService = new CartItemService();
    public static CartStatsDAO cartStatsDAO = new CartStatsDAO();

    public static OrderService orderService = new OrderService();
    public static DAOOrderStaff daoOrderStaff = new DAOOrderStaff();
    public static DAOOrderDeliveryGuy daoOrderDeliveryGuy = new DAOOrderDeliveryGuy();
    public static DeliveryAddressService deliveryAddressService = new DeliveryAddressService();
    public static OrderItemService orderItemService = new OrderItemService();
    public static PlaceOrderDAO pladeOrderDAO = new PlaceOrderDAO();


    public static FavoriteItemDAOPrepared favoriteItemDAOPrepared = new FavoriteItemDAOPrepared();
    public static ItemReviewDAOPrepared itemReviewDAOPrepared = new ItemReviewDAOPrepared();
    public static ItemReviewThanksDAOPrepared itemReviewThanksDAOPrepared = new ItemReviewThanksDAOPrepared();

    public static FavoriteBookDAOPrepared favoriteBookDAOPrepared = new FavoriteBookDAOPrepared();
    public static ShopReviewDAOPrepared shopReviewDAOPrepared = new ShopReviewDAOPrepared();
    public static ShopReviewThanksDAOPrepared shopReviewThanksDAO = new ShopReviewThanksDAOPrepared();


    public static ItemImagesDAO itemImagesDAO = new ItemImagesDAO();
    public static ShopImageDAO shopImageDAO = new ShopImageDAO();

//    public static DAOUserNotifications userNotifications = new DAOUserNotifications();
    public static DAOOneSignal oneSignalNotifications = new DAOOneSignal();


    public static DAOUserNew daoUserNew = new DAOUserNew();
    public static DAOStaff daoStaff = new DAOStaff();
    public static DAOShopStaff daoShopStaff = new DAOShopStaff();
    public static DAODeliveryGuy daoDeliveryGuy = new DAODeliveryGuy();

    public static DAOResetPassword daoResetPassword= new DAOResetPassword();
    public static DAOUserSignUp daoUserSignUp = new DAOUserSignUp();
    public static DAOEmailVerificationCodes daoEmailVerificationCodes=new DAOEmailVerificationCodes();
    public static DAOPhoneVerificationCodes daoPhoneVerificationCodes=new DAOPhoneVerificationCodes();


    public static ServiceConfigurationDAOPrepared serviceConfigDAO = new ServiceConfigurationDAOPrepared();

    // static reference for holding security accountApproved


    public static ItemSpecificationNameDAO itemSpecNameDAO = new ItemSpecificationNameDAO();
    public static ItemSpecNameDAOOuterJoin itemSpecNameDAOOuterJoin = new ItemSpecNameDAOOuterJoin();
    public static ItemSpecNameDAOInnerJoin itemSpecNameDAOInnerJoin = new ItemSpecNameDAOInnerJoin();

    public static ItemSpecificationValueDAO itemSpecificationValueDAO = new ItemSpecificationValueDAO();
    public static ItemSpecValueDAOJoinOuter itemSpecValueDAOJoinOuter = new ItemSpecValueDAOJoinOuter();
    public static ItemSpecValueDAOInnerJoin itemSpecValueDAOInnerJoin = new ItemSpecValueDAOInnerJoin();

    public static ItemSpecificationItemDAO itemSpecificationItemDAO = new ItemSpecificationItemDAO();

    public static DAOAddBalance daoAddBalance = new DAOAddBalance();
    public static DAOTransaction daoTransaction = new DAOTransaction();


    public static DAOItemAnalytics daoItemAnalytics = new DAOItemAnalytics();



    public static Object accountApproved;



    // Configure Connection Pooling


    private static HikariDataSource dataSource;


    public static HikariDataSource getDataSource() {


        if (dataSource == null) {


            org.apache.commons.configuration2.Configuration configuration = GlobalConfig.getConfiguration();


            if(configuration==null)
            {
                System.out.println("failed to load api configuration ... " +
                        "Configuration is null ...  : getDataSource() HikariCP !");

                return null ;
            }



//            String connection_url = configuration.getString("");
//            String username = configuration.getString(ConfigurationKeys.POSTGRES_USERNAME);
//            String password = configuration.getString(ConfigurationKeys.POSTGRES_PASSWORD);



            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(GlobalConstants.POSTGRES_CONNECTION_URL);
            config.setUsername(GlobalConstants.POSTGRES_USERNAME);
            config.setPassword(GlobalConstants.POSTGRES_PASSWORD);


            dataSource = new HikariDataSource(config);
        }

        return dataSource;
    }



//    // mailgun configuration

    private static Configuration configurationMailgun;


    public static Configuration getMailgunConfiguration()
    {

        if(configurationMailgun==null)
        {

            configurationMailgun = new Configuration()
                    .domain(GlobalConstants.MAILGUN_DOMAIN)
                    .apiKey(GlobalConstants.MAILGUN_API_KEY)
                    .from(GlobalConstants.MAILGUN_NAME,GlobalConstants.MAILGUN_EMAIL);

            return configurationMailgun;
        }
        else
        {
            return configurationMailgun;
        }
    }


}
