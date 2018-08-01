package org.nearbyshops.DAORoles;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zaxxer.hikari.HikariDataSource;
import okhttp3.*;
import org.nearbyshops.Globals.GlobalConstants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.ModelRoles.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sumeet on 7/8/17.
 */
public class DAOUserNotifications {


    private HikariDataSource dataSource = Globals.getDataSource();

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");




    public static String baseEncoding()
    {
        String credentials = "AIzaSyC4eRU7FEf-DiRheyaQZrde-vI6W32taLU";

//        Base64.encodeBytes(credentials.getBytes(), Base64.NO_OPTIONS)
        String basic =
                "Key=" + credentials;

        return basic;
    }



    public static String baseEncodingDriverApp()
    {
        String credentials = "AIzaSyCujFExkfJ5bYwDYpMfbSWLr4NVemwD0KY";

//        Base64.encodeBytes(credentials.getBytes(), Base64.NO_OPTIONS)
        String basic =
                "Key=" + credentials;

        return basic;
    }



    public void sendNotificationToEndUser(int userID,
                                          int notificationType,
                                          int notificationSubType)
    {

        sendNotificationToUser(
                userID,
                notificationType,
                notificationSubType,
                GlobalConstants.FIREBASE_END_USER_KEY
        );

//        GlobalConstantsNBS.FIREBASE_END_USER_KEY
    }






    public void sendNotificationToDriver(int userID,
                                         int notificationType,
                                         int notificationSubType)
    {

        sendNotificationToUser(
                userID,
                notificationType,
                notificationSubType,
                GlobalConstants.FIREBASE_DRIVER_KEY
        );


//        GlobalConstantsNBS.FIREBASE_DRIVER_KEY
    }










    public void sendNotificationToUser(
            int userID,
            int notificationType,
            int notificationSubType,
            String firebase_server_key
    )
    {
        User user = getFirebaseToken(userID);

        NotificationData data = new NotificationData();
        data.setNotificationType(notificationType);
        data.setNotificationSubType(notificationSubType);

        FirebaseNotification firebaseNotification = new FirebaseNotification();
        firebaseNotification.setTo(user.getFirebaseID());
        firebaseNotification.setData(data);


        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        String json = gson.toJson(firebaseNotification);

        final Request request = new Request.Builder()
                .url("https://fcm.googleapis.com/fcm/send")
                .addHeader("Authorization",firebase_server_key)
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










    public User getFirebaseToken(int userID)
    {

        boolean isFirst = true;

        String query = "SELECT "

                + User.USER_ID + ","
                + User.FIREBASE_ID + ""

                + " FROM " + User.TABLE_NAME
                + " WHERE " + User.USER_ID + " = ? ";


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

                user.setUserID(rs.getInt(User.USER_ID));
                user.setFirebaseID(rs.getString(User.FIREBASE_ID));
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







    public int updateFirebaseID(User user)
    {

        String updateStatement =  " UPDATE " + User.TABLE_NAME
                                + " SET "    + User.FIREBASE_ID + "=?"
                                + " WHERE "  + User.USER_ID + "=?";

        Connection connection = null;
        PreparedStatement statement = null;

        int rowCountUpdated = 0;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);

            int i = 0;
            statement.setString(++i,user.getFirebaseID());
            statement.setInt(++i,user.getUserID());

            rowCountUpdated = statement.executeUpdate();

            System.out.println("Total rows updated: " + rowCountUpdated);


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

        return rowCountUpdated;
    }


}
