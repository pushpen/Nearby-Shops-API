package org.nearbyshops.DAOPushNotifications;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zaxxer.hikari.HikariDataSource;
import okhttp3.*;
import org.nearbyshops.Globals.GlobalConstants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Shop;
import org.nearbyshops.ModelOneSignal.*;
import org.nearbyshops.ModelRoles.ShopStaffPermissions;
import org.nearbyshops.ModelRoles.User;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DAOOneSignal {


    private HikariDataSource dataSource = Globals.getDataSource();

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");






    // end-user notifications
    public static final int ORDER_PLACED = 21;
    public static final int ORDER_CONFIRMED = 22;
    public static final int ORDER_PACKED = 23;
    public static final int ORDER_OUT_FOR_DELIVERY = 24;

    public static final int ORDER_CANCELLED_BY_SHOP = 25;







    public void sendNotificationToShopStaff(int userID,
                                             String largeIcon,
                                             String bigPicture,
                                             String sound,
                                             int priority,
                                             String title,
                                             String message,
                                             int screenToOpen,
                                             int notificationType,
                                             Object extraData)
    {

        sendNotificationToUser(userID,
                GlobalConstants.ONE_SIGNAL_APP_ID_SHOP_OWNER_APP,
                GlobalConstants.ONE_SIGNAL_API_KEY_SHOP_OWNER_APP,
                largeIcon,
                bigPicture,
                sound,
                priority,
                title,
                message,
                screenToOpen,
                notificationType,
                extraData);

    }






    public void sendNotificationToEndUser(int userID,
                                         String largeIcon,
                                         String bigPicture,
                                         String sound,
                                         int priority,
                                         String title,
                                         String message,
                                         int screenToOpen,
                                         int notificationType,
                                         Object extraData)
    {

        sendNotificationToUser(userID,
                GlobalConstants.ONE_SIGNAL_APP_ID_END_USER_APP,
                GlobalConstants.ONE_SIGNAL_API_KEY_END_USER_APP,
                largeIcon,
                bigPicture,
                sound,
                priority,
                title,
                message,
                screenToOpen,
                notificationType,
                extraData);

    }









//    public void sendNotificationToUser(
//            int userID,
//            String appID,
//            String apiKey,
//            String largeIcon,
//            String bigPicture,
//            String sound,
//            int priority,
//            String title,
//            String message,
//            int screenToOpen,
//            int notificationType,
//            Object extraData
//    )
//    {
//
//        User user = getPlayerID(userID);
//
//        SignalNotification notification  = new SignalNotification();
//        notification.setApp_id(appID);
//        notification.getInclude_player_ids().add(user.getRt_oneSignalPlayerID());
//        notification.setLarge_icon(largeIcon);
//        notification.setBig_picture(bigPicture);
//        notification.setAndroid_sound(sound);
//        notification.setPriority(priority);
//        notification.setHeadings(new Title(title));
//        notification.setContents(new Message(message));
//        notification.setData(new OneSignalData(notificationType,screenToOpen,extraData));
//
//
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        Gson gson = gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
//        String json = gson.toJson(notification);
//
//        apiKey = "basic " + apiKey;
//
//        final Request request = new Request.Builder()
//                .url("https://onesignal.com/api/v1/notifications")
//                .addHeader("Authorization",apiKey)
//                .addHeader("Content-Type","application/json")
//                .post(RequestBody.create(JSON,json))
//                .build();
//
//
//        System.out.print(json + "\n");
//
//
//        OkHttpClient client = new OkHttpClient();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//
//                System.out.print("Sending notification failed !");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//
//                if(response.isSuccessful())
//                {
//                    System.out.print(response.body().string() + "\n");
////                    FirebaseResponse firebaseResponse = gson.fromJson(response.body().string(),FirebaseResponse.class);
//                }
//                else
//                {
//                    System.out.print(response.toString()+ "\n");
//                }
//
//            }
//        });
//    }











    public void sendNotificationToUser(
            int userID,
            String appID,
            String apiKey,
            String largeIcon,
            String bigPicture,
            String sound,
            int priority,
            String title,
            String message,
            int screenToOpen,
            int notificationType,
            Object extraData

    )
    {
        User user = getPlayerID(userID);

        SignalNotification notification  = new SignalNotification();
        notification.setApp_id(appID);
        notification.getInclude_player_ids().add(user.getRt_oneSignalPlayerID());
        notification.setLarge_icon(largeIcon);
        notification.setBig_picture(bigPicture);
        notification.setAndroid_sound(sound);
        notification.setPriority(priority);
        notification.setHeadings(new Title(title));
        notification.setContents(new Message(message));
        notification.setData(new OneSignalData(notificationType,screenToOpen,extraData));


        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        String json = gson.toJson(notification);

        apiKey = "basic " + apiKey;

        final Request request = new Request.Builder()
                .url("https://onesignal.com/api/v1/notifications")
                .addHeader("Authorization",apiKey)
                .addHeader("Content-Type","application/json")
                .post(RequestBody.create(JSON,json))
                .build();


        System.out.print(json + "\n");


        OkHttpClient client = new OkHttpClient();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


                System.out.print("Sending notification failed !");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


                if(response.isSuccessful())
                {
                    System.out.print(response.body().string() + "\n");
//                    FirebaseResponse firebaseResponse = gson.fromJson(response.body().string(),FirebaseResponse.class);
                }
                else
                {
                    System.out.print(response.toString()+ "\n");
                }

            }
        });
    }





    public void sendNotificationToUser(
            List<String> playerIDs,
            String appID,
            String apiKey,
            String largeIcon,
            String bigPicture,
            String sound,
            int priority,
            String title,
            String message,
            int screenToOpen,
            int notificationType,
            Object extraData
    )
    {

        SignalNotification notification  = new SignalNotification();
        notification.setApp_id(appID);
        notification.getInclude_player_ids().addAll(playerIDs);
        notification.setLarge_icon(largeIcon);
        notification.setBig_picture(bigPicture);
        notification.setAndroid_sound(sound);
        notification.setPriority(priority);
        notification.setHeadings(new Title(title));
        notification.setContents(new Message(message));
        notification.setData(new OneSignalData(notificationType,screenToOpen,extraData));


        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        String json = gson.toJson(notification);

        apiKey = "basic " + apiKey;

        final Request request = new Request.Builder()
                .url("https://onesignal.com/api/v1/notifications")
                .addHeader("Authorization",apiKey)
                .addHeader("Content-Type","application/json")
                .post(RequestBody.create(JSON,json))
                .build();


        System.out.print(json + "\n");


        OkHttpClient client = new OkHttpClient();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


                System.out.print("Sending notification failed !");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


                if(response.isSuccessful())
                {
                    System.out.print(response.body().string() + "\n");
//                    FirebaseResponse firebaseResponse = gson.fromJson(response.body().string(),FirebaseResponse.class);
                }
                else
                {
                    System.out.print(response.toString()+ "\n");
                }

            }
        });
    }






    public User getPlayerID(int userID)
    {

        boolean isFirst = true;

        String query = "SELECT " + OneSignalIDs.PLAYER_ID + ""
                    + " FROM " + OneSignalIDs.TABLE_NAME
                    + " WHERE " + OneSignalIDs.USER_ID + " = ? ";


        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;


        //Distributor distributor = null;
        User user = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);

            int i = 0;
            statement.setObject(++i,userID); // username



            rs = statement.executeQuery();

            while(rs.next())
            {
                user = new User();

//                user.setUserID(rs.getInt(OneSignalIDs.USER_ID));
                user.setRt_oneSignalPlayerID(rs.getString(OneSignalIDs.PLAYER_ID));
            }


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

        return user;
    }




    public int updateOneSignalID(int userID,String playerID)
    {


        String insert =

                "INSERT INTO " + OneSignalIDs.TABLE_NAME
                        + "("
                        + OneSignalIDs.USER_ID + ","
                        + OneSignalIDs.PLAYER_ID + ""
                        + ") values(?,?)"
                        + " ON CONFLICT (" + OneSignalIDs.USER_ID + ")"
                        + " DO UPDATE "
                        + " SET "
                        + OneSignalIDs.PLAYER_ID + "= excluded." + OneSignalIDs.PLAYER_ID ;




        Connection connection = null;
        PreparedStatement statement = null;

        int rowCountUpdated = 0;

        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            statement = connection.prepareStatement(insert,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;


            statement.setObject(++i,userID);
            statement.setObject(++i,playerID);

            rowCountUpdated = statement.executeUpdate();


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

        return rowCountUpdated;
    }









    public ArrayList<String> getPlayerIDsForShopStaff(int shopID) {


        String query = "SELECT "    +  OneSignalIDs.PLAYER_ID
                    + " FROM "      +  ShopStaffPermissions.TABLE_NAME
                    + " INNER JOIN " + OneSignalIDs.TABLE_NAME + " ON (" + OneSignalIDs.TABLE_NAME + "." + OneSignalIDs.USER_ID + " = " + ShopStaffPermissions.TABLE_NAME + "." + ShopStaffPermissions.STAFF_ID + ")"
                    + " WHERE "     + ShopStaffPermissions.SHOP_ID + " = ?";



        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;


        //Distributor distributor = null;
        ArrayList<String> playerIDs = new ArrayList<>();

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);

            statement.setObject(1,shopID);

            rs = statement.executeQuery();


            while(rs.next())
            {
                playerIDs.add(rs.getString(OneSignalIDs.PLAYER_ID));
            }




            //System.out.println("Total itemCategories queried " + itemCategoryList.size());


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally

        {

            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }



        return playerIDs;
    }





    public String getPlayerIDforShopAdmin(int shopID) {

        String query = "SELECT " + OneSignalIDs.PLAYER_ID + ""
                    + " FROM "   + Shop.TABLE_NAME
                    + " INNER JOIN " + OneSignalIDs.TABLE_NAME + " ON ( " + OneSignalIDs.TABLE_NAME + "." + OneSignalIDs.USER_ID + " = " + Shop.TABLE_NAME + "." + Shop.SHOP_ADMIN_ID + " )"
                    + " WHERE "  + Shop.SHOP_ID + " = ?";



        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;


        //Distributor distributor = null;
        String playerID = "";

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);

            statement.setObject(1,shopID);

            rs = statement.executeQuery();


            while(rs.next())
            {
                playerID = rs.getString(OneSignalIDs.PLAYER_ID);
            }



            //System.out.println("Total itemCategories queried " + itemCategoryList.size());


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally

        {

            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return playerID;
    }





}
