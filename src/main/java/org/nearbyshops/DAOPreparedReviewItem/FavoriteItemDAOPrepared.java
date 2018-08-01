package org.nearbyshops.DAOPreparedReviewItem;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.ModelEndpointReview.FavouriteItemEndpoint;
import org.nearbyshops.ModelReviewItem.FavouriteItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by sumeet on 8/8/16.
 */
public class FavoriteItemDAOPrepared {


    private HikariDataSource dataSource = Globals.getDataSource();

        @Override
        protected void finalize() throws Throwable {
            // TODO Auto-generated method stub
            super.finalize();
        }


        public int saveFavouriteItem(FavouriteItem favouriteItem)
        {


            Connection conn = null;
            PreparedStatement statement = null;
            int idOfInsertedRow = 0;


            String insertStatement = "INSERT INTO "
                    + FavouriteItem.TABLE_NAME
                    + "("
                    + FavouriteItem.ITEM_ID + ","
                    + FavouriteItem.END_USER_ID
                    + ") VALUES(?,?)";

            try {

                conn = dataSource.getConnection();
                statement = conn.prepareStatement(insertStatement,PreparedStatement.RETURN_GENERATED_KEYS);

                statement.setInt(1,favouriteItem.getItemID());
                statement.setInt(2,favouriteItem.getEndUserID());

                idOfInsertedRow = statement.executeUpdate();

                ResultSet rs = statement.getGeneratedKeys();

                if(rs.next())
                {
                    idOfInsertedRow = rs.getInt(1);
                }


            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            finally
            {

                try {

                    if(statement!=null)
                    {statement.close();}

                }
                catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                try {

                    if(conn!=null)
                    {conn.close();}
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            return idOfInsertedRow;

        }



        public int deleteFavouriteItem(int itemID, int endUserID)
        {

            String deleteStatement = "DELETE FROM " + FavouriteItem.TABLE_NAME
                    + " WHERE " + FavouriteItem.ITEM_ID + " = ?"
                    + " AND " + FavouriteItem.END_USER_ID + " = ?";

            Connection connection= null;
            PreparedStatement statement = null;
            int rowCountDeleted = 0;
            try {


                connection = dataSource.getConnection();
                statement = connection.prepareStatement(deleteStatement);

                statement.setInt(1,itemID);
                statement.setInt(2,endUserID);

                rowCountDeleted = statement.executeUpdate();
                System.out.println("Rows Deleted Favourite Item: " + rowCountDeleted);


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

            return rowCountDeleted;
        }





        public List<FavouriteItem> getFavouriteItem(
                Integer itemID,
                Integer endUserID,
                String sortBy,
                Integer limit, Integer offset
        ) {


            boolean isFirst = true;


            String query = "";

            String queryNormal = "SELECT * FROM " + FavouriteItem.TABLE_NAME;


            String queryJoin = "SELECT "

                    + FavouriteItem.TABLE_NAME + "." + FavouriteItem.ITEM_ID + ","
                    + FavouriteItem.TABLE_NAME + "." + FavouriteItem.END_USER_ID + ""

                    + " FROM "
                    + FavouriteItem.TABLE_NAME;


            if(itemID != null)
            {
                queryJoin = queryJoin + " WHERE "
                        + FavouriteItem.TABLE_NAME
                        + "."
                        + FavouriteItem.ITEM_ID + " = " + itemID;



                //" WHERE ITEM_CATEGORY_ID = " + itemCategoryID

                queryNormal = queryNormal + " WHERE "
                        + FavouriteItem.TABLE_NAME
                        + "."
                        + FavouriteItem.ITEM_ID + " = " + itemID;

                isFirst = false;
            }



            if(endUserID!=null){

                String queryPartMemberID;

                queryPartMemberID = FavouriteItem.TABLE_NAME
                                    + "."
                                    + FavouriteItem.END_USER_ID + " = " + endUserID;

                if(isFirst)
                {
                    queryJoin = queryJoin + " WHERE " + queryPartMemberID;
                    queryNormal = queryNormal + " WHERE " + queryPartMemberID;

                }else
                {
                    queryJoin = queryJoin + " AND " + queryPartMemberID;
                    queryNormal = queryNormal + " AND " + queryPartMemberID;
                }

            }





            // Applying filters



            if(sortBy!=null)
            {
                if(!sortBy.equals(""))
                {
                    String queryPartSortBy = " ORDER BY " + sortBy;

                    queryNormal = queryNormal + queryPartSortBy;
                    queryJoin = queryJoin + queryPartSortBy;
                }
            }



            if(limit != null)
            {

                String queryPartLimitOffset = "";

                if(offset>0)
                {
                    queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + offset;

                }else
                {
                    queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + 0;
                }


                queryNormal = queryNormal + queryPartLimitOffset;
                queryJoin = queryJoin + queryPartLimitOffset;
            }






		/*
		Applying filters Ends
		 */



            query = queryNormal;

            /*

            if(bookCategoryID!=null)
            {
                query = queryJoin;
                isJoinQuery = true;

            }else
            {
                query = queryNormal;
            }

            */



            ArrayList<FavouriteItem> itemsList = new ArrayList<FavouriteItem>();


            Connection connection = null;
            Statement statement = null;
            ResultSet rs = null;

            try {


                connection = dataSource.getConnection();
                statement = connection.createStatement();


//                System.out.println("Favourite Books " + query);

                rs = statement.executeQuery(query);

                while(rs.next())
                {
                    FavouriteItem item = new FavouriteItem();

                    item.setEndUserID(rs.getInt(FavouriteItem.END_USER_ID));
                    item.setItemID(rs.getInt(FavouriteItem.ITEM_ID));

                    itemsList.add(item);
                }



                System.out.println("Favourite Items List Size " + itemsList.size());

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

            return itemsList;
        }




        public FavouriteItemEndpoint getEndPointMetadata(
                Integer itemID,
                Integer endUserID)
        {


            boolean isFirst = true;


            String query = "";

            String queryNormal = "SELECT "
                    + "count(*) as item_count" + ""
                    + " FROM " + FavouriteItem.TABLE_NAME;




            if(itemID != null)
            {
            /*    queryJoin = queryJoin + " WHERE "
                        + FavouriteBook.TABLE_NAME
                        + "."
                        + FavouriteBook.BOOK_ID + " = " + bookID;



            */

                queryNormal = queryNormal + " WHERE "
                        + FavouriteItem.TABLE_NAME
                        + "."
                        + FavouriteItem.ITEM_ID + " = " + itemID;

                isFirst = false;
            }



            if(endUserID!=null){

                String queryPartEndUserID;

                queryPartEndUserID = FavouriteItem.TABLE_NAME
                        + "."
                        + FavouriteItem.END_USER_ID + " = " + endUserID;

                if(isFirst)
                {
//                    queryJoin = queryJoin + " WHERE " + queryPartMemberID;
                    queryNormal = queryNormal + " WHERE " + queryPartEndUserID;

                }else
                {
//                    queryJoin = queryJoin + " AND " + queryPartMemberID;
                    queryNormal = queryNormal + " AND " + queryPartEndUserID;
                }

            }




            // Applying filters





		/*
		Applying filters Ends
		 */



            query = queryNormal;


            FavouriteItemEndpoint endPoint = new FavouriteItemEndpoint();


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




                System.out.println("Item Count Favourite Item : " + endPoint.getItemCount());


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
