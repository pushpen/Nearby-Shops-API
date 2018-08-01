package org.nearbyshops;

import org.apache.commons.configuration2.Configuration;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.nearbyshops.Globals.ConfigurationKeys;
import org.nearbyshops.Globals.GlobalConfig;
import org.nearbyshops.Globals.GlobalConstants;

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

        createDB();
//        upgradeTables();

        createTables();
        startJettyServer();

        
    }





    public static void createDB()
    {

        Connection conn = null;
        Statement stmt = null;



        Configuration configuration = GlobalConfig.getConfiguration();


        if(configuration==null)
        {
            System.out.println("Configuration is null : create DB !");

            return;
        }


        String connection_url = configuration.getString(ConfigurationKeys.CONNECTION_URL_CREATE_DB);
        String username = configuration.getString(ConfigurationKeys.POSTGRES_USERNAME);
        String password = configuration.getString(ConfigurationKeys.POSTGRES_PASSWORD);



        try {

//            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
//                    JDBCContract.CURRENT_USERNAME,
//                    JDBCContract.CURRENT_PASSWORD);

            conn = DriverManager.getConnection(connection_url, username, password);

            stmt = conn.createStatement();

            String createDB = "CREATE DATABASE \"TaxiReferralDB\" WITH ENCODING='UTF8' OWNER=postgres CONNECTION LIMIT=-1";

            stmt.executeUpdate(createDB);

        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        finally{


            // close the connection and statement accountApproved

            if(stmt !=null)
            {

                try {
                    stmt.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }


            if(conn!=null)
            {
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }





    private static void createTables()
    {

        Connection connection = null;
        Statement statement = null;



        try {

            connection = DriverManager.getConnection(GlobalConstants.POSTGRES_CONNECTION_URL,
                    GlobalConstants.POSTGRES_USERNAME, GlobalConstants.POSTGRES_PASSWORD);


            statement = connection.createStatement();


//            statement.executeUpdate(User.createTable);
//            statement.executeUpdate(StaffPermissions.createTablePostgres);



            System.out.println("Tables Created ... !");


            // developers Note: whenever adding a table please check that its dependencies are already created.

            // Insert the default administrator if it does not exit




//            User admin = new User();
//            admin.setUsername("admin");
//            admin.setRole(1);
//            admin.setPassword("password");
//
//            try
//            {
//                int rowCount = Globals.daoUserSignUp.registerUsingUsername(admin,true);
//
//                if(rowCount==1)
//                {
//                    System.out.println("Admin Account created !");
//                }
//            }
//            catch (Exception ex)
//            {
//                System.out.println(ex.toString());
//            }




            // Insert Default Settings

            // Insert Default Service Configuration




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

