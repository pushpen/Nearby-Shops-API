package org.nearbyshops.Globals;

import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

import static org.nearbyshops.Globals.GlobalConstants.MSG91_SMS_SERVICE_API_KEY;

public class GlobalConfig {



    public static void loadGlobalConfiguration()
    {

        org.apache.commons.configuration2.Configuration configuration = getConfiguration();


        if(configuration==null)
        {
            System.out.println("Configuration is null : Unable to load Global Configuration !");
            return;
        }



        GlobalConstants.BASE_URI = configuration.getString("base.url");
        GlobalConstants.POSTGRES_CONNECTION_URL = configuration.getString("connection.url");

        GlobalConstants.POSTGRES_USERNAME = configuration.getString("postgres.username");
        GlobalConstants.POSTGRES_PASSWORD = configuration.getString("postgres.password");

        GlobalConstants.MSG91_SMS_SERVICE_API_KEY = configuration.getString("msg91.apikey");

        GlobalConstants.FIREBASE_DRIVER_KEY = configuration.getString("firebase.driverkey");
        GlobalConstants.FIREBASE_END_USER_KEY = configuration.getString("firebase.enduserkey");

        GlobalConstants.REFERRAL_CREDIT_FOR_END_USER_REGISTRATION = configuration.getInt("referralcredit.enduser.registration");
        GlobalConstants.REFERRAL_CREDIT_FOR_DRIVER_REGISTRATION = configuration.getInt("referralcredit.driver.registration");

//        GlobalConstants.JOINING_CREDIT_FOR_DRIVER = configuration.getInt(ConfigurationKeys.JOINING_CREDIT_DRIVER);
        GlobalConstants.JOINING_CREDIT_FOR_END_USER = configuration.getInt("joiningcredit.enduser");

        GlobalConstants.TOKEN_DURATION_MINUTES = configuration.getInt("token_duration_minutes");
        GlobalConstants.EMAIL_VERIFICATION_CODE_EXPIRY_MINUTES = configuration.getInt("email_verification_code_expiry_minutes");
        GlobalConstants.PHONE_OTP_EXPIRY_MINUTES = configuration.getInt("phone_otp_expiry_minutes");
        GlobalConstants.PASSWORD_RESET_CODE_EXPIRY_MINUTES = configuration.getInt("password_reset_code_expiry_minutes");


        GlobalConstants.max_limit = configuration.getInt("max_limit");

        GlobalConstants.MIN_TAX_ACCOUNT_BALANCE = configuration.getInt("min_tax_account_balance");

        GlobalConstants.MIN_SERVICE_ACCOUNT_BALANCE = configuration.getInt("min_service_account_balance");

        GlobalConstants.NOTIFICATION_SERVER_HOST_MQTT = configuration.getString("mqtt_server_address");



        printGlobalConfiguration();
    }







    static void printGlobalConfiguration()
    {
        System.out.println("Printing API Configuration :  ");

        System.out.println("Base URI : " + GlobalConstants.BASE_URI);

        System.out.println("Postgres Connection URL : " + GlobalConstants.POSTGRES_CONNECTION_URL);
        System.out.println("Connection URL Create DB : " + GlobalConstants.CONNECTION_URL_CREATE_DB);

        System.out.println("Postgres Username : " + GlobalConstants.POSTGRES_USERNAME);
        System.out.println("Postgres Password : " + GlobalConstants.POSTGRES_PASSWORD);

        System.out.println("MSG91_KEY : " + GlobalConstants.MSG91_SMS_SERVICE_API_KEY);

        System.out.println("FIREBASE_DRIVER_KEY : " + GlobalConstants.FIREBASE_DRIVER_KEY);
        System.out.println("FIREBASE_END_USER_KEY : " + GlobalConstants.FIREBASE_END_USER_KEY);

        System.out.println("REFERRAL CREDIT FOR DRIVER REGISTRATION : " + GlobalConstants.REFERRAL_CREDIT_FOR_DRIVER_REGISTRATION);
        System.out.println("REFERRAL CREDIT FOR END USER REGISTRATION : " + GlobalConstants.REFERRAL_CREDIT_FOR_END_USER_REGISTRATION);

        System.out.println("Joining Credit For Driver : " + GlobalConstants.JOINING_CREDIT_FOR_DRIVER);
        System.out.println("Joining Credit For End-User : " + GlobalConstants.JOINING_CREDIT_FOR_END_USER);

        System.out.println("Token Duration Minutes : " + GlobalConstants.TOKEN_DURATION_MINUTES);
        System.out.println("Email Verification Code Expiry Minutes : " + GlobalConstants.EMAIL_VERIFICATION_CODE_EXPIRY_MINUTES);

        System.out.println("Phone OTP Expiry Minutes : " + GlobalConstants.PHONE_OTP_EXPIRY_MINUTES);
        System.out.println("Password Reset Code Expiry Minutes : " + GlobalConstants.PASSWORD_RESET_CODE_EXPIRY_MINUTES);

        System.out.println("Trip Request Code Expiry Minutes : " + GlobalConstants.TRIP_REQUEST_EXPIRY_MINUTES);
        System.out.println("Trip Request Code Expiry Extension Minutes : " + GlobalConstants.TRIP_REQUEST_EXPIRY_EXTENSION_MINUTES);


        System.out.println("Months to extend Taxi Registration Minimum : " + GlobalConstants.MONTHS_TO_EXTEND_TAXI_REGISTRATION_MIN);
        System.out.println("Months to extend Taxi Registration Maximum : " + GlobalConstants.MONTHS_TO_EXTEND_TAXI_REGISTRATION_MAX);

        System.out.println("MAX LIMIT : " + GlobalConstants.max_limit);
        System.out.println("MQTT Host address : " + GlobalConstants.NOTIFICATION_SERVER_HOST_MQTT);
    }




    public static void reloadConfiguration()
    {

        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.properties()
                                .setFileName("api_config.properties"));


        try
        {
            GlobalConfig.configuration = builder.getConfiguration();

        }
        catch(ConfigurationException cex)
        {
            // loading of the configuration file failed

            System.out.println(cex.getStackTrace());

            configuration = null;
        }
    }







    private static org.apache.commons.configuration2.Configuration configuration;


    public static org.apache.commons.configuration2.Configuration getConfiguration() {
        if (configuration == null) {
            Parameters params = new Parameters();
            FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                    new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                            .configure(params.properties()
                                    .setFileName("api_config.properties"));


            try {
                configuration = builder.getConfiguration();

            } catch (ConfigurationException cex) {
                // loading of the configuration file failed

                System.out.println(cex.getStackTrace());

                configuration = null;
            }

            return configuration;
        } else {
            return configuration;
        }
    }



}
