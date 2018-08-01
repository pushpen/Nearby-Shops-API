package org.nearbyshops.DAOPreparedReviewShop;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.ModelEndpointReview.ShopReviewThanksEndpoint;
import org.nearbyshops.ModelReviewShop.ShopReview;
import org.nearbyshops.ModelReviewShop.ShopReviewThanks;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by sumeet on 8/8/16.
 */
public class ShopReviewThanksDAOPrepared {

    private HikariDataSource dataSource = Globals.getDataSource();

        @Override
        protected void finalize() throws Throwable {
            // TODO Auto-generated method stub
            super.finalize();
        }


        public int saveShopReviewThanks(ShopReviewThanks shopReviewThanks)
        {


            Connection conn = null;
            PreparedStatement statement = null;
            int idOfInsertedRow = 0;


            String insertStatement = "INSERT INTO "
                    + ShopReviewThanks.TABLE_NAME
                    + "("
                    + ShopReviewThanks.SHOP_REVIEW_ID + ","
                    + ShopReviewThanks.END_USER_ID
                    + ") VALUES(?,?)";

            try {

                conn = dataSource.getConnection();
                statement = conn.prepareStatement(insertStatement,PreparedStatement.RETURN_GENERATED_KEYS);


                statement.setInt(1,shopReviewThanks.getShopReviewID());
                statement.setInt(2,shopReviewThanks.getEndUserID());

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



        public int deleteShopReviewThanks(int shopReviewID, int endUserID)
        {

            String deleteStatement = "DELETE FROM " + ShopReviewThanks.TABLE_NAME
                    + " WHERE " + ShopReviewThanks.SHOP_REVIEW_ID + " = ?"
                    + " AND " + ShopReviewThanks.END_USER_ID + " = ?";

            Connection conn= null;
            PreparedStatement statement = null;
            int rowCountDeleted = 0;
            try {


                conn = dataSource.getConnection();
                statement = conn.prepareStatement(deleteStatement);

                statement.setInt(1,shopReviewID);
                statement.setInt(2,endUserID);

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



        

        public List<ShopReviewThanks> getShopReviewThanks(
                Integer shopReviewID,
                Integer shopID,
                Integer endUserID,
                String sortBy,
                Integer limit, Integer offset
        ) {


            boolean isFirst = true;


            String query = "";

            String queryNormal = "SELECT * FROM " + ShopReviewThanks.TABLE_NAME;


            String queryJoin = "SELECT "

                    + ShopReviewThanks.TABLE_NAME + "." + ShopReviewThanks.SHOP_REVIEW_ID + ","
                    + ShopReviewThanks.TABLE_NAME + "." + ShopReviewThanks.END_USER_ID + ""

                    + " FROM "
                    + ShopReviewThanks.TABLE_NAME;


            if(shopID !=null)
            {
                queryJoin = queryJoin + " INNER JOIN " + ShopReview.TABLE_NAME
                            + " ON( " + ShopReviewThanks.TABLE_NAME + "." + ShopReviewThanks.SHOP_REVIEW_ID
                            + " = " + ShopReview.TABLE_NAME + "." + ShopReview.SHOP_REVIEW_ID + ") ";

            }


            if(shopID!=null)
            {

                String queryPartShopID = "";
                queryPartShopID = ShopReview.TABLE_NAME + "." + ShopReview.SHOP_ID + " = " + shopID;

                if(isFirst)
                {
                    queryJoin = queryJoin + " WHERE " + queryPartShopID;
                    isFirst = false;
                }
                else
                {
                    queryJoin = queryJoin + " AND " + queryPartShopID;
                }
            }




            if(shopReviewID != null)
            {

                String queryPartShopReview;

                queryPartShopReview = ShopReviewThanks.TABLE_NAME
                                        + "."
                                        + ShopReviewThanks.SHOP_REVIEW_ID + " = " + shopReviewID;


                if(isFirst)
                {
                    queryJoin = queryJoin + " WHERE " + queryPartShopReview;
                    queryNormal = queryNormal + " WHERE " + queryPartShopReview;

                    isFirst = false;
                }
                else
                {
                    queryJoin = queryJoin + " AND " + queryPartShopReview;
                    queryNormal = queryNormal + " AND " + queryPartShopReview;

                }
            }



            if(endUserID!=null){

                String queryPartMemberID;

                queryPartMemberID = ShopReviewThanks.TABLE_NAME
                                    + "."
                                    + ShopReviewThanks.END_USER_ID + " = " + endUserID;

                if(isFirst)
                {
                    queryJoin = queryJoin + " WHERE " + queryPartMemberID;
                    queryNormal = queryNormal + " WHERE " + queryPartMemberID;

                    isFirst = false;

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



            ArrayList<ShopReviewThanks> thanksList = new ArrayList<ShopReviewThanks>();


            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;

            try {


                conn = dataSource.getConnection();
                stmt = conn.createStatement();


//                System.out.println("Favourite Books " + query);

                rs = stmt.executeQuery(query);

                while(rs.next())
                {
                    ShopReviewThanks reviewThanks = new ShopReviewThanks();

                    reviewThanks.setEndUserID(rs.getInt(ShopReviewThanks.END_USER_ID));
                    reviewThanks.setShopReviewID(rs.getInt(ShopReviewThanks.SHOP_REVIEW_ID));

                    thanksList.add(reviewThanks);
                }



                System.out.println("ShopReview Thanks List Size : " + thanksList.size());

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

            return thanksList;
        }






        public ShopReviewThanksEndpoint getEndPointMetadata(
                Integer shopReviewID,
                Integer endUserID)
        {


            boolean isFirst = true;

            String query = "";

            String queryNormal = "SELECT "
                    + "count(*) as item_count" + ""
                    + " FROM " + ShopReviewThanks.TABLE_NAME;




            if(shopReviewID != null)
            {
            /*    queryJoin = queryJoin + " WHERE "
                        + FavouriteBook.TABLE_NAME
                        + "."
                        + FavouriteBook.BOOK_ID + " = " + bookID;



            */

                queryNormal = queryNormal + " WHERE "
                        + ShopReviewThanks.TABLE_NAME
                        + "."
                        + ShopReviewThanks.SHOP_REVIEW_ID + " = " + shopReviewID;

                isFirst = false;
            }



            if(endUserID != null){

                String queryPartMemberID;

                queryPartMemberID = ShopReviewThanks.TABLE_NAME
                        + "."
                        + ShopReviewThanks.END_USER_ID + " = " + endUserID;

                if(isFirst)
                {
//                    queryJoin = queryJoin + " WHERE " + queryPartMemberID;
                    queryNormal = queryNormal + " WHERE " + queryPartMemberID;

                }else
                {
//                    queryJoin = queryJoin + " AND " + queryPartMemberID;
                    queryNormal = queryNormal + " AND " + queryPartMemberID;
                }

            }




            // Applying filters





		/*
		Applying filters Ends
		 */



            query = queryNormal;


            ShopReviewThanksEndpoint endPoint = new ShopReviewThanksEndpoint();


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

}
