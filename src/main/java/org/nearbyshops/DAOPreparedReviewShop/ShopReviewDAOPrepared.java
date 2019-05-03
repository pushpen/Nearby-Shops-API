package org.nearbyshops.DAOPreparedReviewShop;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.ModelEndpointReview.ShopReviewEndPoint;
import org.nearbyshops.ModelReviewShop.ShopReview;
import org.nearbyshops.ModelReviewShop.ShopReviewStatRow;
import org.nearbyshops.ModelReviewShop.ShopReviewThanks;
import org.nearbyshops.ModelRoles.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by sumeet on 8/8/16.
 */
public class ShopReviewDAOPrepared {


        private HikariDataSource dataSource = Globals.getDataSource();


        @Override
        protected void finalize() throws Throwable {
            // TODO Auto-generated method stub
            super.finalize();
        }


        public int saveShopReview(ShopReview shopReview)
        {


            Connection conn = null;
            PreparedStatement statement = null;
            int idOfInsertedRow = 0;



            String insertStatement = "INSERT INTO "
                    + ShopReview.TABLE_NAME
                    + "("
                    + ShopReview.SHOP_ID + ","
                    + ShopReview.END_USER_ID + ","
                    + ShopReview.RATING + ","

                    + ShopReview.REVIEW_TEXT + ","
                    + ShopReview.REVIEW_TITLE + ""

                    + ") VALUES(?,?,?,?,?)";

            try {

                conn = dataSource.getConnection();

                statement = conn.prepareStatement(insertStatement,PreparedStatement.RETURN_GENERATED_KEYS);

                statement.setObject(1,shopReview.getShopID());
                statement.setObject(2,shopReview.getEndUserID());
                statement.setObject(3,shopReview.getRating());

                statement.setString(4,shopReview.getReviewText());
                statement.setString(5,shopReview.getReviewTitle());

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






        public int updateShopReview(ShopReview shopReview)
        {


            String updateStatement = "UPDATE " + ShopReview.TABLE_NAME

                    + " SET "
                    + ShopReview.SHOP_ID + " = ?,"
                    + ShopReview.END_USER_ID + " = ?,"
                    + ShopReview.RATING + " = ?,"

                    + ShopReview.REVIEW_TEXT + " = ?,"
                    + ShopReview.REVIEW_TITLE + " = ?"


                    + " WHERE "
                    + ShopReview.SHOP_REVIEW_ID + " = ?";


            Connection conn = null;
            PreparedStatement statement = null;

            int rowCountUpdated = 0;

            try {


                conn = dataSource.getConnection();
                statement = conn.prepareStatement(updateStatement);

                statement.setInt(1,shopReview.getShopID());
                statement.setInt(2,shopReview.getEndUserID());
                statement.setInt(3,shopReview.getRating());

                statement.setString(4,shopReview.getReviewText());
                statement.setString(5,shopReview.getReviewTitle());

                statement.setInt(6,shopReview.getShopReviewID());


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

                    if(conn!=null)
                    {conn.close();}
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            return rowCountUpdated;
        }



        public int deleteShopReview(int bookReviewID)
        {

            String deleteStatement = "DELETE FROM " + ShopReview.TABLE_NAME
                    + " WHERE " + ShopReview.SHOP_REVIEW_ID + " = ?";

            Connection conn= null;
            PreparedStatement statement = null;
            int rowCountDeleted = 0;
            try {


                conn = dataSource.getConnection();
                statement = conn.prepareStatement(deleteStatement);

                statement.setInt(1,bookReviewID);

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

                    if(conn!=null)
                    {conn.close();}
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            return rowCountDeleted;
        }






        public List<ShopReview> getShopReviews(
                Integer shopID,
                Integer endUserID,
                String sortBy,
                Integer limit, Integer offset
        ) {



//            boolean isFirst = true;

            String query = "";

//            String queryNormal = "SELECT * FROM " + ShopReview.TABLE_NAME;


            String queryJoin = "SELECT "

                    + ShopReview.TABLE_NAME + "." + ShopReview.SHOP_REVIEW_ID + ","
                    + ShopReview.TABLE_NAME + "." + ShopReview.SHOP_ID + ","
                    + ShopReview.TABLE_NAME + "." + ShopReview.END_USER_ID + ","
                    + ShopReview.TABLE_NAME + "." + ShopReview.RATING + ","
                    + ShopReview.TABLE_NAME + "." + ShopReview.REVIEW_TEXT + ","
                    + ShopReview.TABLE_NAME + "." + ShopReview.REVIEW_DATE + ","
                    + ShopReview.TABLE_NAME + "." + ShopReview.REVIEW_TITLE + ","
                    + " count(" + ShopReviewThanks.TABLE_NAME + "." + ShopReviewThanks.SHOP_REVIEW_ID + ") as thanks_count "

                    + " FROM " + ShopReview.TABLE_NAME
                    + " LEFT OUTER JOIN " + ShopReviewThanks.TABLE_NAME + " ON (" + ShopReview.TABLE_NAME + "." + ShopReview.SHOP_REVIEW_ID + " = " + ShopReviewThanks.TABLE_NAME + "." + ShopReviewThanks.SHOP_REVIEW_ID + ") "
                    + " LEFT OUTER JOIN " + User.TABLE_NAME + " ON ( " + User.TABLE_NAME + "." + User.USER_ID + " = " + ShopReview.TABLE_NAME + "." + ShopReview.END_USER_ID + " )";




            if(shopID != null)
            {
                queryJoin = queryJoin + " WHERE "
                        + ShopReview.TABLE_NAME
                        + "."
                        + ShopReview.SHOP_ID + " = " + shopID;


//                queryNormal = queryNormal + " WHERE "
//                        + ShopReview.TABLE_NAME
//                        + "."
//                        + ShopReview.SHOP_ID + " = " + shopID;

//                isFirst = false;
            }


            if(endUserID != null)
            {

                String queryPartMember =
                        ShopReview.TABLE_NAME
                                + "."
                        + ShopReview.END_USER_ID + " = " + endUserID;

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

                    + ShopReview.TABLE_NAME + "." + ShopReview.SHOP_REVIEW_ID + ","
                    + ShopReview.TABLE_NAME + "." + ShopReview.SHOP_ID + ","
                    + ShopReview.TABLE_NAME + "." + ShopReview.END_USER_ID + ","
                    + ShopReview.TABLE_NAME + "." + ShopReview.RATING + ","
                    + ShopReview.TABLE_NAME + "." + ShopReview.REVIEW_TEXT + ","
                    + ShopReview.TABLE_NAME + "." + ShopReview.REVIEW_DATE + ","
                    + ShopReview.TABLE_NAME + "." + ShopReview.REVIEW_TITLE ;




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



            ArrayList<ShopReview> shopReviewsList = new ArrayList<ShopReview>();


            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;

            try {

                conn = dataSource.getConnection();
                stmt = conn.createStatement();
                rs = stmt.executeQuery(query);

                while(rs.next())
                {
                    ShopReview shopReview = new ShopReview();

                    shopReview.setShopReviewID(rs.getInt(ShopReview.SHOP_REVIEW_ID));
                    shopReview.setShopID(rs.getInt(ShopReview.SHOP_ID));
                    shopReview.setEndUserID(rs.getInt(ShopReview.END_USER_ID));
                    shopReview.setRating(rs.getInt(ShopReview.RATING));
                    shopReview.setReviewText(rs.getString(ShopReview.REVIEW_TEXT));

                    shopReview.setReviewTitle(rs.getString(ShopReview.REVIEW_TITLE));
                    shopReview.setReviewDate(rs.getTimestamp(ShopReview.REVIEW_DATE));

                    shopReview.setRt_thanks_count(rs.getInt("thanks_count"));
                    shopReviewsList.add(shopReview);
                }



                System.out.println("books By CategoryID " + shopReviewsList.size());

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

                    if(stmt!=null)
                    {stmt.close();}
                } catch (SQLException e) {
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

            return shopReviewsList;
        }



        public ShopReviewEndPoint getEndPointMetadata(
                Integer shopID,
                Integer endUserID)
        {


            boolean isFirst = true;

            String query = "";

            String queryNormal = "SELECT "
                    + "count( DISTINCT " + ShopReview.SHOP_REVIEW_ID + ") as item_count" + ""
                    + " FROM " + ShopReview.TABLE_NAME;




            if(shopID != null)
            {

                queryNormal = queryNormal + " WHERE "
                        + ShopReview.TABLE_NAME
                        + "."
                        + ShopReview.SHOP_ID + " = " + shopID;

                isFirst = false;
            }


            if(endUserID != null)
            {

                String queryPartMember =
                        ShopReview.TABLE_NAME
                                + "."
                                + ShopReview.END_USER_ID + " = " + endUserID;

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


            ShopReviewEndPoint endPoint = new ShopReviewEndPoint();


            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;

            try {


                conn = dataSource.getConnection();
                stmt = conn.createStatement();
                rs = stmt.executeQuery(query);

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

                    if(stmt!=null)
                    {stmt.close();}
                } catch (SQLException e) {
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

            return endPoint;
        }





        public ShopReview getBookReview(int shopReviewID)
        {

            String query = "SELECT * FROM " +  ShopReview.TABLE_NAME
                        + " WHERE " + ShopReview.SHOP_REVIEW_ID + " = " + shopReviewID;


            Connection connection = null;
            Statement statement = null;
            ResultSet rs = null;


            //ItemCategory itemCategory = new ItemCategory();
            ShopReview shopReview = null;

            try {

                connection = dataSource.getConnection();
                statement = connection.createStatement();
                rs = statement.executeQuery(query);



                while(rs.next())
                {
                    shopReview = new ShopReview();

                    shopReview.setShopReviewID(rs.getInt(ShopReview.SHOP_REVIEW_ID));
                    shopReview.setShopID(rs.getInt(ShopReview.SHOP_ID));
                    shopReview.setEndUserID(rs.getInt(ShopReview.END_USER_ID));
                    shopReview.setRating(rs.getInt(ShopReview.RATING));
                    shopReview.setReviewText(rs.getString(ShopReview.REVIEW_TEXT));

                    shopReview.setReviewTitle(rs.getString(ShopReview.REVIEW_TITLE));
                    shopReview.setReviewDate(rs.getTimestamp(ShopReview.REVIEW_DATE));

                    System.out.println("Get BookReview by DELIVERY_GUY_SELF_ID : " + shopReview.getShopID());
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

            return shopReview;
        }




        public List<ShopReviewStatRow> getStats(Integer shopID)
        {
            //select rating, count(book_review_id) as reviews_count from book_review group by rating

            String query = "SELECT " + ShopReview.RATING + ", count(" + ShopReview.SHOP_REVIEW_ID + ") as reviews_count "
                        + " FROM " +  ShopReview.TABLE_NAME;


            if(shopID!=null)
            {
                query = query + " WHERE " + ShopReview.SHOP_ID + " = " + shopID;
            }

            query = query + " GROUP BY " + ShopReview.RATING;

            Connection connection = null;
            Statement statement = null;
            ResultSet rs = null;




//            ShopReviewStats shopReviewStats = new ShopReviewStats();

            ArrayList<ShopReviewStatRow> rowList = new ArrayList<>();

            try {

                connection = dataSource.getConnection();
                statement = connection.createStatement();
                rs = statement.executeQuery(query);



                while(rs.next())
                {

                    ShopReviewStatRow row = new ShopReviewStatRow();

                    row.setRating(rs.getInt(ShopReview.RATING));
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
