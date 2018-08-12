package org.nearbyshops.DAORoles;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.GlobalConstants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.ModelRoles.Endpoints.UserEndpoint;
import org.nearbyshops.ModelRoles.StaffPermissions;
import org.nearbyshops.ModelRoles.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by sumeet on 28/8/17.
 */
public class DAOStaff {

    private HikariDataSource dataSource = Globals.getDataSource();


    /*

        saveStaffByAdmin(User staff)
    *  getStaffPublic()
    *  getStaffForAdmin()
    *
    *
    *
    * */


    public int updateStaffProfile(User user)
    {

        String updateStatement = "UPDATE " + User.TABLE_NAME

                + " SET "

//                + User.USERNAME + "=?,"
//                + User.PASSWORD + "=?,"
//                + User.E_MAIL + "=?,"
//                + User.PHONE + "=?,"
                + User.NAME + "=?,"
                + User.GENDER + "=?,"

                + User.PROFILE_IMAGE_URL + "=?,"
                + User.IS_ACCOUNT_PRIVATE + "=?,"
                + User.ABOUT + "=?"

                + " WHERE " + User.USER_ID + " = ?";


        Connection connection = null;
        PreparedStatement statement = null;

        int rowCountUpdated = 0;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);

            int i = 0;

//            statement.setString(++i,user.getUsername());
//            statement.setString(++i,user.getPassword());
//            statement.setString(++i,user.getEmail());
//            statement.setString(++i,user.getPhone());

            statement.setString(++i,user.getName());
            statement.setObject(++i,user.getGender());

            statement.setString(++i,user.getProfileImagePath());
            statement.setObject(++i,user.isAccountPrivate());
            statement.setString(++i,user.getAbout());

            statement.setObject(++i,user.getUserID());


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








    public int updateStaffByAdmin(User user)
    {

        String updateStatement = "UPDATE " + User.TABLE_NAME

                + " SET "

                + User.NAME + "=?,"
                + User.GENDER + "=?,"

                + User.PROFILE_IMAGE_URL + "=?,"
                + User.IS_ACCOUNT_PRIVATE + "=?,"
                + User.ABOUT + "=?,"
                + User.IS_VERIFIED + "=?"

                + " WHERE " + User.USER_ID + " = ?";





        String insertStaffPermissions =

                "INSERT INTO " + StaffPermissions.TABLE_NAME
                        + "("
                        + StaffPermissions.STAFF_ID + ","
                        + StaffPermissions.DESIGNATION + ","
//                        + StaffPermissions.PERMIT_TAXI_PROFILE_UPDATE + ","
                        + StaffPermissions.PERMIT_TAXI_REGISTRATION_AND_RENEWAL + ""
                        + ") values(?,?,?,?)"
                        + " ON CONFLICT (" + StaffPermissions.STAFF_ID + ")"
                        + " DO UPDATE "
                        + " SET "
                        + StaffPermissions.DESIGNATION + "= excluded." + StaffPermissions.DESIGNATION + " , "
//                        + StaffPermissions.PERMIT_TAXI_PROFILE_UPDATE + "= excluded." + StaffPermissions.PERMIT_TAXI_PROFILE_UPDATE + " , "
                        + StaffPermissions.PERMIT_TAXI_REGISTRATION_AND_RENEWAL + "= excluded." + StaffPermissions.PERMIT_TAXI_REGISTRATION_AND_RENEWAL;




        Connection connection = null;
        PreparedStatement statement = null;

        int rowCountUpdated = 0;

        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);



            statement = connection.prepareStatement(updateStatement);

            int i = 0;

            statement.setString(++i,user.getName());
            statement.setObject(++i,user.getGender());

            statement.setString(++i,user.getProfileImagePath());
            statement.setObject(++i,user.isAccountPrivate());
            statement.setString(++i,user.getAbout());
            statement.setObject(++i,user.isVerified());

            statement.setObject(++i,user.getUserID());


            rowCountUpdated = statement.executeUpdate();
            System.out.println("Total rows updated: " + rowCountUpdated);


            statement = connection.prepareStatement(insertStaffPermissions,PreparedStatement.RETURN_GENERATED_KEYS);
            i = 0;

            StaffPermissions permissions = user.getRt_staff_permissions();

            if(permissions!=null)
            {
                statement.setObject(++i,user.getUserID());
                statement.setString(++i,permissions.getDesignation());
//                statement.setObject(++i,permissions.isPermitTaxiProfileUpdate());
                statement.setObject(++i,permissions.isPermitTaxiRegistrationAndRenewal());


                statement.executeUpdate();
            }






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




    public int updateStaffLocation(StaffPermissions permissions)
    {


        String insertStaffPermissions =

                "INSERT INTO " + StaffPermissions.TABLE_NAME
                        + "("
                        + StaffPermissions.STAFF_ID + ","
                        + StaffPermissions.LAT_CURRENT + ","
                        + StaffPermissions.LON_CURRENT + ""
                        + ") values(?,?,?)"
                        + " ON CONFLICT (" + StaffPermissions.STAFF_ID + ")"
                        + " DO UPDATE "
                        + " SET "
                        + StaffPermissions.LAT_CURRENT + "= excluded." + StaffPermissions.LAT_CURRENT + " , "
                        + StaffPermissions.LON_CURRENT + "= excluded." + StaffPermissions.LON_CURRENT;




        Connection connection = null;
        PreparedStatement statement = null;

        int rowCountUpdated = 0;

        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            statement = connection.prepareStatement(insertStaffPermissions,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;


            if(permissions!=null)
            {
                statement.setObject(++i,permissions.getStaffUserID());
                statement.setObject(++i,permissions.getLatCurrent());
                statement.setObject(++i,permissions.getLonCurrent());

                rowCountUpdated = statement.executeUpdate();
            }


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



    public StaffPermissions getStaffPermissions(int staffID)
    {

        boolean isFirst = true;

        String query = "SELECT "

                + StaffPermissions.STAFF_ID + ","
                + StaffPermissions.PERMIT_TAXI_REGISTRATION_AND_RENEWAL + ""
//                + StaffPermissions.PERMIT_TAXI_PROFILE_UPDATE + ""

                + " FROM "  + StaffPermissions.TABLE_NAME
                + " WHERE " + StaffPermissions.STAFF_ID  + " = ? ";



        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;


        //Distributor distributor = null;
        StaffPermissions permissions = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);

            int i = 0;


            statement.setObject(++i,staffID); // username


            rs = statement.executeQuery();

            while(rs.next())
            {
                permissions = new StaffPermissions();

                permissions.setStaffUserID(rs.getInt(StaffPermissions.STAFF_ID));
                permissions.setPermitTaxiRegistrationAndRenewal(rs.getBoolean(StaffPermissions.PERMIT_TAXI_REGISTRATION_AND_RENEWAL));
//                permissions.setPermitTaxiProfileUpdate(rs.getBoolean(StaffPermissions.PERMIT_TAXI_PROFILE_UPDATE));
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

        return permissions;
    }












    public UserEndpoint getStaffForAdmin(
            Double latPickUp, Double lonPickUp,
            Boolean permitProfileUpdate,
            Boolean permitRegistrationAndRenewal,
            Boolean gender,
            String sortBy,
            Integer limit, Integer offset,
            boolean getRowCount,
            boolean getOnlyMetadata
    ) {


        boolean isfirst = true;

        String queryCount = "";


        String queryJoin = "SELECT DISTINCT "


                + "6371 * acos( cos( radians("
                + latPickUp + ")) * cos( radians(" +  StaffPermissions.LAT_CURRENT +  ") ) * cos(radians(" + StaffPermissions.LON_CURRENT +  ") - radians("
                + lonPickUp + "))"
                + " + sin( radians(" + latPickUp + ")) * sin(radians(" + StaffPermissions.LAT_CURRENT + "))) as distance" + ","

                + User.TABLE_NAME + "." + User.USER_ID + ","
                + User.TABLE_NAME + "." + User.USERNAME + ","
                + User.TABLE_NAME + "." + User.E_MAIL + ","
                + User.TABLE_NAME + "." + User.PHONE + ","

                + User.TABLE_NAME + "." + User.NAME + ","
                + User.TABLE_NAME + "." + User.GENDER + ","

                + User.TABLE_NAME + "." + User.PROFILE_IMAGE_URL + ","
                + User.TABLE_NAME + "." + User.IS_ACCOUNT_PRIVATE + ","
                + User.TABLE_NAME + "." + User.ABOUT + ","

                + User.TABLE_NAME + "." + User.TIMESTAMP_CREATED + ","
                + User.TABLE_NAME + "." + User.IS_VERIFIED + ","

                + StaffPermissions.TABLE_NAME + "." + StaffPermissions.DESIGNATION + ","
                + StaffPermissions.TABLE_NAME + "." + StaffPermissions.PERMIT_TAXI_REGISTRATION_AND_RENEWAL + ","
//                + StaffPermissions.TABLE_NAME + "." + StaffPermissions.PERMIT_TAXI_PROFILE_UPDATE + ""

                + " FROM " + User.TABLE_NAME
                + " LEFT OUTER JOIN " + StaffPermissions.TABLE_NAME + " ON (" + StaffPermissions.TABLE_NAME + "." + StaffPermissions.STAFF_ID + " = " + User.TABLE_NAME + "." + User.USER_ID + ")"
                + " WHERE TRUE "
                + " AND ( " + User.TABLE_NAME + "." + User.ROLE + " = " + GlobalConstants.ROLE_STAFF_CODE
                + " OR "
                + User.TABLE_NAME + "." + User.ROLE + " = " + GlobalConstants.ROLE_ADMIN_CODE + " ) ";




        if(gender != null)
        {
            queryJoin = queryJoin + " AND " + User.TABLE_NAME + "." + User.GENDER + " = ?";
        }
//


        if(permitProfileUpdate!=null && permitProfileUpdate)
        {
//            queryJoin = queryJoin + " AND " + StaffPermissions.TABLE_NAME + "." + StaffPermissions.PERMIT_TAXI_PROFILE_UPDATE + " = TRUE ";
        }


        if(permitRegistrationAndRenewal !=null && permitRegistrationAndRenewal)
        {
            queryJoin = queryJoin + " AND " + StaffPermissions.TABLE_NAME + "." + StaffPermissions.PERMIT_TAXI_REGISTRATION_AND_RENEWAL + " = TRUE ";
        }





        // all the non-aggregate columns which are present in select must be present in group by also.
        queryJoin = queryJoin

                + " group by "
                + StaffPermissions.TABLE_NAME + "." + StaffPermissions.PERMISSION_ID + ","
                + User.TABLE_NAME + "." + User.USER_ID;


        queryCount = queryJoin;



        if(sortBy!=null)
        {
            if(!sortBy.equals(""))
            {
                String queryPartSortBy = " ORDER BY " + sortBy;

//				queryNormal = queryNormal + queryPartSortBy;
                queryJoin = queryJoin + queryPartSortBy;
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


//			queryNormal = queryNormal + queryPartLimitOffset;
            queryJoin = queryJoin + queryPartLimitOffset;
        }






		/*

		Applying filters Ends

		 */

        // Applying filters




        queryCount = "SELECT COUNT(*) as item_count FROM (" + queryCount + ") AS temp";


        UserEndpoint endPoint = new UserEndpoint();

        ArrayList<User> itemList = new ArrayList<>();
        Connection connection = null;

        PreparedStatement statement = null;
        ResultSet rs = null;

        PreparedStatement statementCount = null;
        ResultSet resultSetCount = null;

        try {

            connection = dataSource.getConnection();

            int i = 0;


            if(!getOnlyMetadata)
            {
                statement = connection.prepareStatement(queryJoin);


                if(gender!=null)
                {
                    statement.setObject(++i,gender);
                }


                rs = statement.executeQuery();

                while(rs.next())
                {

                    User user = new User();

                    user.setUserID(rs.getInt(User.USER_ID));
                    user.setUsername(rs.getString(User.USERNAME));
                    user.setEmail(rs.getString(User.E_MAIL));
                    user.setPhone(rs.getString(User.PHONE));


                    user.setName(rs.getString(User.NAME));
                    user.setGender(rs.getBoolean(User.GENDER));


                    user.setProfileImagePath(rs.getString(User.PROFILE_IMAGE_URL));
                    user.setAccountPrivate(rs.getBoolean(User.IS_ACCOUNT_PRIVATE));
                    user.setAbout(rs.getString(User.ABOUT));

                    user.setTimestampCreated(rs.getTimestamp(User.TIMESTAMP_CREATED));
                    user.setVerified(rs.getBoolean(User.IS_VERIFIED));

                    StaffPermissions permissions = new StaffPermissions();

                    permissions.setDesignation(rs.getString(StaffPermissions.DESIGNATION));
                    permissions.setPermitTaxiRegistrationAndRenewal(rs.getBoolean(StaffPermissions.PERMIT_TAXI_REGISTRATION_AND_RENEWAL));
//                    permissions.setPermitTaxiProfileUpdate(rs.getBoolean(StaffPermissions.PERMIT_TAXI_PROFILE_UPDATE));

                    user.setRt_staff_permissions(permissions);

                    itemList.add(user);
                }

                endPoint.setResults(itemList);

            }


            if(getRowCount)
            {
                statementCount = connection.prepareStatement(queryCount);

                i = 0;

                if(gender!=null)
                {
                    statementCount.setObject(++i,gender);
                }


                resultSetCount = statementCount.executeQuery();

                while(resultSetCount.next())
                {
                    endPoint.setItemCount(resultSetCount.getInt("item_count"));
                }
            }






        }
        catch (SQLException e) {
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
                if(resultSetCount!=null)
                {resultSetCount.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(statementCount!=null)
                {statementCount.close();}
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






    public UserEndpoint getStaffListPublic(
            Double latPickUp, Double lonPickUp,
            Boolean permitProfileUpdate,
            Boolean permitRegistrationAndRenewal,
            Boolean gender,
            String sortBy,
            Integer limit, Integer offset,
            boolean getRowCount,
            boolean getOnlyMetadata
    ) {


        boolean isfirst = true;

        String queryCount = "";


        String queryJoin = "SELECT DISTINCT "


                + "6371 * acos( cos( radians("
                + latPickUp + ")) * cos( radians(" +  StaffPermissions.LAT_CURRENT +  ") ) * cos(radians(" + StaffPermissions.LON_CURRENT +  ") - radians("
                + lonPickUp + "))"
                + " + sin( radians(" + latPickUp + ")) * sin(radians(" + StaffPermissions.LAT_CURRENT + "))) as distance" + ","


                + User.TABLE_NAME + "." + User.USER_ID + ","
                + User.TABLE_NAME + "." + User.USERNAME + ","
                + User.TABLE_NAME + "." + User.E_MAIL + ","
                + User.TABLE_NAME + "." + User.PHONE + ","

                + User.TABLE_NAME + "." + User.NAME + ","
                + User.TABLE_NAME + "." + User.GENDER + ","

                + User.TABLE_NAME + "." + User.PROFILE_IMAGE_URL + ","
                + User.TABLE_NAME + "." + User.IS_ACCOUNT_PRIVATE + ","
                + User.TABLE_NAME + "." + User.ABOUT + ","

                + User.TABLE_NAME + "." + User.TIMESTAMP_CREATED + ","
                + User.TABLE_NAME + "." + User.IS_VERIFIED + ","

                + StaffPermissions.TABLE_NAME + "." + StaffPermissions.DESIGNATION + ","
                + StaffPermissions.TABLE_NAME + "." + StaffPermissions.PERMIT_TAXI_REGISTRATION_AND_RENEWAL + ","
//                + StaffPermissions.TABLE_NAME + "." + StaffPermissions.PERMIT_TAXI_PROFILE_UPDATE + ""

                + " FROM " + User.TABLE_NAME
                + " LEFT OUTER JOIN " + StaffPermissions.TABLE_NAME + " ON (" + StaffPermissions.TABLE_NAME + "." + StaffPermissions.STAFF_ID + " = " + User.TABLE_NAME + "." + User.USER_ID + ")"
                + " WHERE TRUE "
                + " AND ( " + User.TABLE_NAME + "." + User.ROLE + " = " + GlobalConstants.ROLE_STAFF_CODE
                + " OR "
                + User.TABLE_NAME + "." + User.ROLE + " = " + GlobalConstants.ROLE_ADMIN_CODE + " ) ";


//        + " AND " + User.TABLE_NAME + "." + User.ROLE + " = " + GlobalConstantsNBS.ROLE_STAFF_CODE;




        if(gender != null)
        {
            queryJoin = queryJoin + " AND " + User.TABLE_NAME + "." + User.GENDER + " = ? ";
        }
//


        if(permitProfileUpdate!=null && permitProfileUpdate)
        {
//            queryJoin = queryJoin + " AND " + StaffPermissions.TABLE_NAME + "." + StaffPermissions.PERMIT_TAXI_PROFILE_UPDATE + " = TRUE ";
        }


        if(permitRegistrationAndRenewal !=null && permitRegistrationAndRenewal)
        {
            queryJoin = queryJoin + " AND " + StaffPermissions.TABLE_NAME + "." + StaffPermissions.PERMIT_TAXI_REGISTRATION_AND_RENEWAL + " = TRUE ";
        }




        // all the non-aggregate columns which are present in select must be present in group by also.
        queryJoin = queryJoin

                + " group by "
                + StaffPermissions.TABLE_NAME + "." + StaffPermissions.PERMISSION_ID + ","
                + User.TABLE_NAME + "." + User.USER_ID;


        queryCount = queryJoin;



        if(sortBy!=null)
        {
            if(!sortBy.equals(""))
            {
                String queryPartSortBy = " ORDER BY " + sortBy;

//				queryNormal = queryNormal + queryPartSortBy;
                queryJoin = queryJoin + queryPartSortBy;
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


//			queryNormal = queryNormal + queryPartLimitOffset;
            queryJoin = queryJoin + queryPartLimitOffset;
        }






		/*

		Applying filters Ends

		 */

        // Applying filters




        queryCount = "SELECT COUNT(*) as item_count FROM (" + queryCount + ") AS temp";


        UserEndpoint endPoint = new UserEndpoint();

        ArrayList<User> itemList = new ArrayList<>();
        Connection connection = null;

        PreparedStatement statement = null;
        ResultSet rs = null;

        PreparedStatement statementCount = null;
        ResultSet resultSetCount = null;

        try {

            connection = dataSource.getConnection();

            int i = 0;


            if(!getOnlyMetadata)
            {
                statement = connection.prepareStatement(queryJoin);


                if(gender!=null)
                {
                    statement.setObject(++i,gender);
                }


                rs = statement.executeQuery();

                while(rs.next())
                {

                    User user = new User();

                    user.setUserID(rs.getInt(User.USER_ID));
                    user.setUsername(rs.getString(User.USERNAME));
                    user.setEmail(rs.getString(User.E_MAIL));
                    user.setPhone(rs.getString(User.PHONE));


                    user.setName(rs.getString(User.NAME));
                    user.setGender(rs.getBoolean(User.GENDER));


                    user.setProfileImagePath(rs.getString(User.PROFILE_IMAGE_URL));
                    user.setAccountPrivate(rs.getBoolean(User.IS_ACCOUNT_PRIVATE));
                    user.setAbout(rs.getString(User.ABOUT));

                    user.setTimestampCreated(rs.getTimestamp(User.TIMESTAMP_CREATED));
                    user.setVerified(rs.getBoolean(User.IS_VERIFIED));



                    StaffPermissions permissions = new StaffPermissions();

                    permissions.setDesignation(rs.getString(StaffPermissions.DESIGNATION));
                    permissions.setPermitTaxiRegistrationAndRenewal(rs.getBoolean(StaffPermissions.PERMIT_TAXI_REGISTRATION_AND_RENEWAL));
//                    permissions.setPermitTaxiProfileUpdate(rs.getBoolean(StaffPermissions.PERMIT_TAXI_PROFILE_UPDATE));

                    permissions.setRt_distance(rs.getFloat("distance"));

                    user.setRt_staff_permissions(permissions);

                    itemList.add(user);
                }

                endPoint.setResults(itemList);

            }





            if(getRowCount)
            {
                statementCount = connection.prepareStatement(queryCount);

                i = 0;

                if(gender!=null)
                {
                    statementCount.setObject(++i,gender);
                }


                resultSetCount = statementCount.executeQuery();

                while(resultSetCount.next())
                {
                    endPoint.setItemCount(resultSetCount.getInt("item_count"));
                }
            }






        }
        catch (SQLException e) {
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
                if(resultSetCount!=null)
                {resultSetCount.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(statementCount!=null)
                {statementCount.close();}
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
