package org.nearbyshops.DAOPreparedReviewItem;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.ModelEndpointReview.ItemReviewEndPoint;
import org.nearbyshops.ModelReviewItem.ItemReview;
import org.nearbyshops.ModelReviewItem.ItemReviewStatRow;
import org.nearbyshops.ModelReviewItem.ItemReviewThanks;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by sumeet on 8/8/16.
 */
public class ItemReviewDAOPrepared {


        private HikariDataSource dataSource = Globals.getDataSource();


        @Override
        protected void finalize() throws Throwable {
            // TODO Auto-generated method stub
            super.finalize();
        }


        public int saveItemReview(ItemReview itemReview)
        {


            Connection connection = null;
            PreparedStatement statement = null;
            int idOfInsertedRow = 0;



            String insertStatement = "INSERT INTO "
                    + ItemReview.TABLE_NAME
                    + "("
                    + ItemReview.ITEM_ID + ","
                    + ItemReview.END_USER_ID + ","
                    + ItemReview.RATING + ","

                    + ItemReview.REVIEW_TEXT + ","
                    + ItemReview.REVIEW_TITLE + ""

                    + ") VALUES(?,?,?,?,?)";

            try {

                connection = dataSource.getConnection();

                statement = connection.prepareStatement(insertStatement,PreparedStatement.RETURN_GENERATED_KEYS);

                statement.setObject(1,itemReview.getItemID());
                statement.setObject(2,itemReview.getEndUserID());
                statement.setObject(3,itemReview.getRating());

                statement.setString(4,itemReview.getReviewText());
                statement.setString(5,itemReview.getReviewTitle());

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

                    if(connection!=null)
                    {connection.close();}
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            return idOfInsertedRow;

        }






        public int updateItemReview(ItemReview itemReview)
        {


            String updateStatement = "UPDATE " + ItemReview.TABLE_NAME

                    + " SET "
                    + ItemReview.ITEM_ID + " = ?,"
                    + ItemReview.END_USER_ID + " = ?,"
                    + ItemReview.RATING + " = ?,"

                    + ItemReview.REVIEW_TEXT + " = ?,"
                    + ItemReview.REVIEW_TITLE + " = ?"


                    + " WHERE "
                    + ItemReview.ITEM_REVIEW_ID + " = ?";


            Connection connection = null;
            PreparedStatement statement = null;

            int rowCountUpdated = 0;

            try {


                connection = dataSource.getConnection();
                statement = connection.prepareStatement(updateStatement);

                statement.setInt(1,itemReview.getItemID());
                statement.setInt(2,itemReview.getEndUserID());
                statement.setInt(3,itemReview.getRating());

                statement.setString(4,itemReview.getReviewText());
                statement.setString(5,itemReview.getReviewTitle());

                statement.setInt(6,itemReview.getItemReviewID());


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



        public int deleteItemReview(int itemReviewID)
        {

            String deleteStatement = "DELETE FROM " + ItemReview.TABLE_NAME
                    + " WHERE " + ItemReview.ITEM_REVIEW_ID + " = ?";

            Connection connection= null;
            PreparedStatement statement = null;
            int rowCountDeleted = 0;
            try {


                connection = dataSource.getConnection();
                statement = connection.prepareStatement(deleteStatement);

                statement.setInt(1,itemReviewID);

                rowCountDeleted = statement.executeUpdate();

                System.out.println("Rows Deleted: " + rowCountDeleted);


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





        public List<ItemReview> getItemReviews(
                Integer itemID,
                Integer endUserID,
                String sortBy,
                Integer limit, Integer offset
        ) {



            boolean isFirst = true;

            String query = "";

            String queryNormal = "SELECT * FROM " + ItemReview.TABLE_NAME;


            String queryJoin = "SELECT "

                    + ItemReview.TABLE_NAME + "." + ItemReview.ITEM_REVIEW_ID + ","
                    + ItemReview.TABLE_NAME + "." + ItemReview.ITEM_ID + ","
                    + ItemReview.TABLE_NAME + "." + ItemReview.END_USER_ID + ","
                    + ItemReview.TABLE_NAME + "." + ItemReview.RATING + ","
                    + ItemReview.TABLE_NAME + "." + ItemReview.REVIEW_TEXT + ","
                    + ItemReview.TABLE_NAME + "." + ItemReview.REVIEW_DATE + ","
                    + ItemReview.TABLE_NAME + "." + ItemReview.REVIEW_TITLE + ","
                    + " count(" + ItemReviewThanks.TABLE_NAME + "." + ItemReviewThanks.ITEM_REVIEW_ID + ") as thanks_count "

                    + " FROM " + ItemReview.TABLE_NAME + " LEFT OUTER JOIN " + ItemReviewThanks.TABLE_NAME
                    + " ON (" + ItemReview.TABLE_NAME + "." + ItemReview.ITEM_REVIEW_ID
                    + " = " + ItemReviewThanks.TABLE_NAME + "." + ItemReviewThanks.ITEM_REVIEW_ID + ") ";


            if(itemID != null)
            {
                queryJoin = queryJoin + " WHERE "
                        + ItemReview.TABLE_NAME
                        + "."
                        + ItemReview.ITEM_ID + " = " + itemID;


                queryNormal = queryNormal + " WHERE "
                        + ItemReview.TABLE_NAME
                        + "."
                        + ItemReview.ITEM_ID + " = " + itemID;

                isFirst = false;
            }


            if(endUserID != null)
            {

                String queryPartMember =
                        ItemReview.TABLE_NAME
                                + "."
                        + ItemReview.END_USER_ID + " = " + endUserID;

                if(isFirst)
                {
                    queryJoin = queryJoin + " WHERE " + queryPartMember;
                    queryNormal = queryNormal + " WHERE " + queryPartMember;

                }else
                {
                    queryJoin = queryJoin + " AND " + queryPartMember;
                    queryNormal = queryNormal + " AND " + queryPartMember;
                }


                isFirst = false;

            }


            queryJoin = queryJoin

                    + " group by "

                    + ItemReview.TABLE_NAME + "." + ItemReview.ITEM_REVIEW_ID + ","
                    + ItemReview.TABLE_NAME + "." + ItemReview.ITEM_ID + ","
                    + ItemReview.TABLE_NAME + "." + ItemReview.END_USER_ID + ","
                    + ItemReview.TABLE_NAME + "." + ItemReview.RATING + ","
                    + ItemReview.TABLE_NAME + "." + ItemReview.REVIEW_TEXT + ","
                    + ItemReview.TABLE_NAME + "." + ItemReview.REVIEW_DATE + ","
                    + ItemReview.TABLE_NAME + "." + ItemReview.REVIEW_TITLE ;




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



            query = queryJoin;

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



            ArrayList<ItemReview> itemReviewsList = new ArrayList<ItemReview>();


            Connection connection = null;
            Statement statement = null;
            ResultSet rs = null;

            try {

                connection = dataSource.getConnection();
                statement = connection.createStatement();
                rs = statement.executeQuery(query);

                while(rs.next())
                {
                    ItemReview itemReview = new ItemReview();

                    itemReview.setItemReviewID(rs.getInt(ItemReview.ITEM_REVIEW_ID));
                    itemReview.setItemID(rs.getInt(ItemReview.ITEM_ID));
                    itemReview.setEndUserID(rs.getInt(ItemReview.END_USER_ID));
                    itemReview.setRating(rs.getInt(ItemReview.RATING));
                    itemReview.setReviewText(rs.getString(ItemReview.REVIEW_TEXT));

                    itemReview.setReviewTitle(rs.getString(ItemReview.REVIEW_TITLE));
                    itemReview.setReviewDate(rs.getTimestamp(ItemReview.REVIEW_DATE));

                    itemReview.setRt_thanks_count(rs.getInt("thanks_count"));
                    itemReviewsList.add(itemReview);
                }



                System.out.println("books By CategoryID " + itemReviewsList.size());

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

            return itemReviewsList;
        }



        public ItemReviewEndPoint getEndPointMetadata(
                Integer itemID,
                Integer endUserID)
        {


            boolean isFirst = true;

            String query = "";

            String queryNormal = "SELECT "
                    + "count( DISTINCT " + ItemReview.ITEM_REVIEW_ID + ") as item_count" + ""
                    + " FROM " + ItemReview.TABLE_NAME;




            if(itemID != null)
            {

                queryNormal = queryNormal + " WHERE "
                        + ItemReview.TABLE_NAME
                        + "."
                        + ItemReview.ITEM_ID + " = " + itemID;

                isFirst = false;
            }


            if(endUserID != null)
            {

                String queryPartMember =
                        ItemReview.TABLE_NAME
                                + "."
                                + ItemReview.END_USER_ID + " = " + endUserID;

                if(isFirst)
                {
                    queryNormal = queryNormal + " WHERE " + queryPartMember;

                }else
                {
                    queryNormal = queryNormal + " AND " + queryPartMember;
                }


                isFirst = false;

            }




/*
            if(bookID != null)
            {
*//*
                queryJoin = queryJoin + " AND "
                        + ItemContract.TABLE_NAME
                        + "."
                        + ItemContract.ITEM_CATEGORY_ID + " = " + itemCategoryID;

*//*


                //" WHERE ITEM_CATEGORY_ID = " + itemCategoryID

                queryNormal = queryNormal + " WHERE "
                        + BookReview.TABLE_NAME
                        + "."
                        + BookReview.BOOK_ID + " = " + bookID;
            }



            if(memberID != null)
            {
*//*
                queryJoin = queryJoin + " AND "
                        + ItemContract.TABLE_NAME
                        + "."
                        + ItemContract.ITEM_CATEGORY_ID + " = " + itemCategoryID;

*//*


                //" WHERE ITEM_CATEGORY_ID = " + itemCategoryID

                queryNormal = queryNormal + " WHERE "
                        + BookReview.TABLE_NAME
                        + "."
                        + BookReview.MEMBER_ID + " = " + memberID;
            }*/



            // Applying filters





		/*
		Applying filters Ends
		 */



            query = queryNormal;


            ItemReviewEndPoint endPoint = new ItemReviewEndPoint();


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




                System.out.println("Item Count : " + endPoint.getItemCount());


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





        public ItemReview getItemReview(int itemReviewID)
        {

            String query = "SELECT * FROM " +  ItemReview.TABLE_NAME
                        + " WHERE " + ItemReview.ITEM_REVIEW_ID + " = " + itemReviewID;


            Connection connection = null;
            Statement statement = null;
            ResultSet rs = null;


            //ItemCategory itemCategory = new ItemCategory();
            ItemReview itemReview = null;

            try {

                connection = dataSource.getConnection();
                statement = connection.createStatement();
                rs = statement.executeQuery(query);



                while(rs.next())
                {
                    itemReview = new ItemReview();

                    itemReview.setItemReviewID(rs.getInt(ItemReview.ITEM_REVIEW_ID));
                    itemReview.setItemID(rs.getInt(ItemReview.ITEM_ID));
                    itemReview.setEndUserID(rs.getInt(ItemReview.END_USER_ID));
                    itemReview.setRating(rs.getInt(ItemReview.RATING));
                    itemReview.setReviewText(rs.getString(ItemReview.REVIEW_TEXT));

                    itemReview.setReviewTitle(rs.getString(ItemReview.REVIEW_TITLE));
                    itemReview.setReviewDate(rs.getTimestamp(ItemReview.REVIEW_DATE));

                    System.out.println("Get BookReview by DELIVERY_GUY_SELF_ID : " + itemReview.getItemID());
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

            return itemReview;
        }




        public List<ItemReviewStatRow> getStats(Integer itemID)
        {
            //select rating, count(book_review_id) as reviews_count from book_review group by rating

            String query = "SELECT " + ItemReview.RATING + ", count(" + ItemReview.ITEM_REVIEW_ID + ") as reviews_count "
                        + " FROM " +  ItemReview.TABLE_NAME;


            if(itemID!=null)
            {
                query = query + " WHERE " + ItemReview.ITEM_ID + " = " + itemID;
            }

            query = query + " GROUP BY " + ItemReview.RATING;

            Connection connection = null;
            Statement statement = null;
            ResultSet rs = null;




//            ShopReviewStats shopReviewStats = new ShopReviewStats();

            ArrayList<ItemReviewStatRow> rowList = new ArrayList<>();

            try {

                connection = dataSource.getConnection();
                statement = connection.createStatement();
                rs = statement.executeQuery(query);



                while(rs.next())
                {

                    ItemReviewStatRow row = new ItemReviewStatRow();

                    row.setRating(rs.getInt(ItemReview.RATING));
                    row.setReviews_count(rs.getInt("reviews_count"));

                    rowList.add(row);

//                    System.out.println("Get BookReview by DELIVERY_GUY_SELF_ID : " + shopReview.getItemID());
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


            return rowList;
        }


}
