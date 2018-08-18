package org.nearbyshops;

import okhttp3.internal.http2.Settings;
import org.apache.commons.configuration2.Configuration;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.nearbyshops.Globals.GlobalConfig;
import org.nearbyshops.Globals.GlobalConstants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.*;
import org.nearbyshops.ModelBilling.Transaction;
import org.nearbyshops.ModelDelivery.DeliveryAddress;
import org.nearbyshops.ModelItemSpecification.ItemSpecificationItem;
import org.nearbyshops.ModelItemSpecification.ItemSpecificationName;
import org.nearbyshops.ModelItemSpecification.ItemSpecificationValue;
import org.nearbyshops.ModelReviewItem.FavouriteItem;
import org.nearbyshops.ModelReviewItem.ItemReview;
import org.nearbyshops.ModelReviewItem.ItemReviewThanks;
import org.nearbyshops.ModelReviewShop.FavouriteShop;
import org.nearbyshops.ModelReviewShop.ShopReview;
import org.nearbyshops.ModelReviewShop.ShopReviewThanks;
import org.nearbyshops.ModelRoles.*;
import org.nearbyshops.ModelSettings.ServiceConfigurationLocal;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Main__ class.
 *
 */






public class Main {

    // Base URI the Grizzly HTTP server will listen on

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */



    public static void startJettyServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in org.taxireferral.api package
        final ResourceConfig rc = new ResourceConfig().packages("org.nearbyshops");


        System.out.println("Base URL : " + GlobalConstants.BASE_URI);
        JettyHttpContainerFactory.createServer(URI.create(GlobalConstants.BASE_URI),rc);
    }








    public static void main(String[] args) throws IOException {


        GlobalConfig.loadGlobalConfiguration();

//        createDB();
//        upgradeTables();

        createTables();
        startJettyServer();

    }








    private static void createTables()
    {

        Connection connection = null;
        Statement statement = null;



        try {

            connection = DriverManager.getConnection(GlobalConstants.POSTGRES_CONNECTION_URL,
                    GlobalConstants.POSTGRES_USERNAME, GlobalConstants.POSTGRES_PASSWORD);


            statement = connection.createStatement();

            statement.executeUpdate(User.createTable);
            statement.executeUpdate(StaffPermissions.createTablePostgres);
            statement.executeUpdate(EmailVerificationCode.createTablePostgres);
            statement.executeUpdate(PhoneVerificationCode.createTablePostgres);

            statement.executeUpdate(Transaction.createTablePostgres);


            statement.executeUpdate(ItemCategory.createTableItemCategoryPostgres);
            statement.executeUpdate(Item.createTableItemPostgres);
            statement.executeUpdate(Shop.createTableShopPostgres);
            statement.executeUpdate(ShopItem.createTableShopItemPostgres);

            statement.executeUpdate(ShopStaffPermissions.createTablePostgres);


            statement.executeUpdate(Cart.createTableCartPostgres);
            statement.executeUpdate(CartItem.createtableCartItemPostgres);
            statement.executeUpdate(DeliveryAddress.createTableDeliveryAddressPostgres);

            statement.executeUpdate(Order.createTableOrderPostgres);
            statement.executeUpdate(OrderItem.createtableOrderItemPostgres);



            // tables for shop reviews
            statement.executeUpdate(ShopReview.createTableShopReviewPostgres);
            statement.executeUpdate(FavouriteShop.createTableFavouriteBookPostgres);
            statement.executeUpdate(ShopReviewThanks.createTableShopReviewThanksPostgres);

            // tables for Item reviews
            statement.executeUpdate(ItemReview.createTableItemReviewPostgres);
            statement.executeUpdate(FavouriteItem.createTableFavouriteItemPostgres);
            statement.executeUpdate(ItemReviewThanks.createTableItemReviewThanksPostgres);




            statement.executeUpdate(ItemImage.createTableItemImagesPostgres);


            statement.executeUpdate(ItemSpecificationName.createTableItemSpecNamePostgres);
            statement.executeUpdate(ItemSpecificationValue.createTableItemSpecificationValuePostgres);
            statement.executeUpdate(ItemSpecificationItem.createTableItemSpecificationItemPostgres);



            statement.executeUpdate(ServiceConfigurationLocal.createTablePostgres);


//            statement.executeUpdate(User.createTable);
//            statement.executeUpdate(StaffPermissions.createTablePostgres);



            System.out.println("Tables Created ... !");


            // developers Note: whenever adding a table please check that tables it depends on are created first


            // Insert the default administrator if it does not exit

            User admin = new User();
            admin.setUsername("admin");
            admin.setRole(1);
            admin.setPassword("password");

            try
            {
                int rowCount = Globals.daoUserSignUp.registerUsingUsername(admin,true);

                if(rowCount==1)
                {
                    System.out.println("Admin Account created !");
                }
            }
            catch (Exception ex)
            {
                System.out.println(ex.toString());
            }




            // Insert the root category whose DELIVERY_GUY_SELF_ID is 1

            String insertItemCategory = "";

            // The root ItemCategory has id 1. If the root category does not exist then insert it.
            if(Globals.itemCategoryDAO.checkRoot(1) == null)
            {

                insertItemCategory = "INSERT INTO "
                        + ItemCategory.TABLE_NAME
                        + "("
                        + ItemCategory.ITEM_CATEGORY_ID + ","
                        + ItemCategory.ITEM_CATEGORY_NAME + ","
                        + ItemCategory.PARENT_CATEGORY_ID + ","
                        + ItemCategory.ITEM_CATEGORY_DESCRIPTION + ","
                        + ItemCategory.IMAGE_PATH + ","
                        + ItemCategory.IS_LEAF_NODE + ") VALUES("
                        + "" + "1"	+ ","
                        + "'" + "ROOT"	+ "',"
                        + "" + "NULL" + ","
                        + "'" + "This is the root Category. Do not modify it." + "',"
                        + "'" + " " + "',"
                        + "'" + "FALSE" + "'"
                        + ")";



                statement.executeUpdate(insertItemCategory);

            }






            // Insert Default Service Configuration

            String insertServiceConfig = "";

            if(Globals.serviceConfigDAO.getServiceConfiguration(null,null)==null)
            {

                ServiceConfigurationLocal defaultConfiguration = new ServiceConfigurationLocal();

//                defaultConfiguration.setServiceLevel(GlobalConstants.SERVICE_LEVEL_CITY);
//                defaultConfiguration.setServiceType(GlobalConstants.SERVICE_TYPE_NONPROFIT);
                defaultConfiguration.setServiceID(1);
                defaultConfiguration.setServiceName("DEFAULT_CONFIGURATION");

                Globals.serviceConfigDAO.saveService(defaultConfiguration);

/*
				insertServiceConfig = "INSERT INTO "
						+ ServiceConfigurationLocal.TABLE_NAME
						+ "("
						+ ServiceConfigurationLocal.SERVICE_CONFIGURATION_ID + ","
						+ ServiceConfigurationLocal.SERVICE_NAME + ") VALUES ("
						+ "" + "1" + ","
						+ "'" + "ROOT_CONFIGURATION" + "')";


				stmt.executeUpdate(insertServiceConfig);*/
            }





            // create directory images

            final java.nio.file.Path BASE_DIR = Paths.get("./images");

            File theDir = new File(BASE_DIR.toString());

            // if the directory does not exist, create it
            if (!theDir.exists()) {

                System.out.println("Creating directory: " + BASE_DIR.toString());

                boolean result = false;

                try{
                    theDir.mkdir();
                    result = true;
                }
                catch(Exception se){
                    //handle it
                }
                if(result) {
                    System.out.println("DIR created");
                }
            }





        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        finally{


            // close the connection and statement accountApproved

            if(statement !=null)
            {

                try {
                    statement.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }


            if(connection!=null)
            {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }


}

