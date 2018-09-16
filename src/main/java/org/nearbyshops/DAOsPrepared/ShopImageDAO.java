package org.nearbyshops.DAOsPrepared;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.ShopImage;
import org.nearbyshops.ModelEndpoint.ShopImageEndPoint;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShopImageDAO {

    private HikariDataSource dataSource = Globals.getDataSource();

    @Override
    protected void finalize() throws Throwable {
        // TODO Auto-generated method stub
        super.finalize();
    }


    public int saveShopImage(ShopImage shopImage, boolean getRowCount)
    {


        Connection connection = null;
        PreparedStatement statement = null;
        int idOfInsertedRow = -1;
        int rowCount = 0;


        String insertItemCategory = "INSERT INTO "
                + ShopImage.TABLE_NAME
                + "("
                + ShopImage.SHOP_ID + ","
                + ShopImage.IMAGE_FILENAME + ","
                + ShopImage.CAPTION_TITLE + ","

                + ShopImage.CAPTION + ","
                + ShopImage.COPYRIGHTS + ","
                + ShopImage.IMAGE_ORDER + ","

                + ShopImage.TIMESTAMP_UPDATED + ""

                + ") VALUES(?,?,? ,?,?,? ,?)";




        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(insertItemCategory,PreparedStatement.RETURN_GENERATED_KEYS);

            int i = 0;
            statement.setObject(++i,shopImage.getShopID());
            statement.setString(++i,shopImage.getImageFilename());
            statement.setString(++i,shopImage.getCaptionTitle());

            statement.setString(++i,shopImage.getCaption());
            statement.setString(++i,shopImage.getCopyrights());
            statement.setObject(++i,shopImage.getImageOrder());

            statement.setTimestamp(++i,new Timestamp(System.currentTimeMillis()));


            rowCount = statement.executeUpdate();

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


        if(getRowCount)
        {
            return rowCount;
        }
        else
        {
            return idOfInsertedRow;
        }

    }





    public int updateShopImage(ShopImage shopImage)
    {

        String updateStatement = "UPDATE " + ShopImage.TABLE_NAME

                + " SET "

                + ShopImage.SHOP_ID + "=?,"
                + ShopImage.IMAGE_FILENAME + "=?,"
                + ShopImage.CAPTION_TITLE + "=?,"
                + ShopImage.CAPTION + "=?,"
                + ShopImage.COPYRIGHTS + "=?,"
                + ShopImage.IMAGE_ORDER + "=?,"
                + ShopImage.TIMESTAMP_UPDATED + "=?"

                + " WHERE " + ShopImage.SHOP_IMAGE_ID + "=?";


        Connection connection = null;
        PreparedStatement statement = null;

        int rowCountUpdated = 0;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);



            int i = 0;
            statement.setObject(++i,shopImage.getShopID());
            statement.setString(++i,shopImage.getImageFilename());
            statement.setString(++i,shopImage.getCaptionTitle());
            statement.setString(++i,shopImage.getCaption());
            statement.setString(++i,shopImage.getCopyrights());
            statement.setObject(++i,shopImage.getImageOrder());
            statement.setTimestamp(++i,new Timestamp(System.currentTimeMillis()));

            statement.setObject(++i,shopImage.getShopImageID());


            rowCountUpdated = statement.executeUpdate();
//            System.out.println("Total rows updated: " + rowCountUpdated);


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




    public int deleteShopImage(int imageID)
    {

        String deleteStatement = "DELETE FROM " + ShopImage.TABLE_NAME
                                + " WHERE " + ShopImage.SHOP_IMAGE_ID + " = ?";

        Connection connection= null;
        PreparedStatement statement = null;
        int rowCountDeleted = 0;
        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(deleteStatement);
            statement.setInt(1,imageID);

            rowCountDeleted = statement.executeUpdate();

//            System.out.println("Rows Deleted: " + rowCountDeleted);

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










    public ShopImageEndPoint getShopImages(
            Integer shopID,
            String sortBy,
            Integer limit, Integer offset,
            boolean getRowCount,
            boolean getOnlyMetadata
    ) {



        String queryCount = "";

        String queryJoin = "SELECT DISTINCT "

                + ShopImage.TABLE_NAME + "." + ShopImage.SHOP_IMAGE_ID + ","
                + ShopImage.TABLE_NAME + "." + ShopImage.SHOP_ID + ","
                + ShopImage.TABLE_NAME + "." + ShopImage.IMAGE_FILENAME + ","
                + ShopImage.TABLE_NAME + "." + ShopImage.TIMESTAMP_CREATED + ","
                + ShopImage.TABLE_NAME + "." + ShopImage.TIMESTAMP_UPDATED + ","
                + ShopImage.TABLE_NAME + "." + ShopImage.CAPTION_TITLE + ","
                + ShopImage.TABLE_NAME + "." + ShopImage.CAPTION + ","
                + ShopImage.TABLE_NAME + "." + ShopImage.COPYRIGHTS + ","
                + ShopImage.TABLE_NAME + "." + ShopImage.IMAGE_ORDER + ""

                + " FROM " + ShopImage.TABLE_NAME
                + " WHERE TRUE ";






        if(shopID != null)
        {
            queryJoin = queryJoin + " AND "
                    + ShopImage.TABLE_NAME
                    + "."
                    + ShopImage.SHOP_ID + " = ?";
        }




        // all the non-aggregate columns which are present in select must be present in group by also.
//		queryJoin = queryJoin
//
//				+ " group by "
//				+ Item.TABLE_NAME + "." + Item.ITEM_CATEGORY_ID + ","
//				+ Item.TABLE_NAME + "." + Item.ITEM_ID ;


//				+ ","
//				+ Item.TABLE_NAME + "." + Item.ITEM_IMAGE_URL + ","
//				+ Item.TABLE_NAME + "." + Item.ITEM_NAME + ","
//				+ Item.TABLE_NAME + "." + Item.ITEM_DESC


        queryCount = queryJoin;


        if(sortBy!=null)
        {
            if(!sortBy.equals(""))
            {
                String queryPartSortBy = " ORDER BY " + sortBy;

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


            queryJoin = queryJoin + queryPartLimitOffset;
        }




        queryCount = "SELECT COUNT(*) as item_count FROM (" + queryCount + ") AS temp";



        ShopImageEndPoint endPoint = new ShopImageEndPoint();


        ArrayList<ShopImage> shopImagesList = new ArrayList<>();
        Connection connection = null;


        PreparedStatement statement = null;
        ResultSet rs = null;


        PreparedStatement statementCount = null;
        ResultSet resultSetCount = null;

        try {

            connection = dataSource.getConnection();

            int i = 0;


            if(!getOnlyMetadata) {

                statement = connection.prepareStatement(queryJoin);


                if (shopID != null) {
                    statement.setObject(++i, shopID);
                }


//            System.out.println(query);
                rs = statement.executeQuery();

                while (rs.next()) {
                    ShopImage shopImage = new ShopImage();

                    shopImage.setShopImageID(rs.getInt(ShopImage.SHOP_IMAGE_ID));
                    shopImage.setShopID(rs.getInt(ShopImage.SHOP_ID));
                    shopImage.setImageFilename(rs.getString(ShopImage.IMAGE_FILENAME));
                    shopImage.setTimestampCreated(rs.getTimestamp(ShopImage.TIMESTAMP_CREATED));
                    shopImage.setTimestampUpdated(rs.getTimestamp(ShopImage.TIMESTAMP_UPDATED));
                    shopImage.setCaptionTitle(rs.getString(ShopImage.CAPTION_TITLE));
                    shopImage.setCaption(rs.getString(ShopImage.CAPTION));
                    shopImage.setCopyrights(rs.getString(ShopImage.COPYRIGHTS));
                    shopImage.setImageOrder(rs.getInt(ShopImage.IMAGE_ORDER));

                    shopImagesList.add(shopImage);
                }


                endPoint.setResults(shopImagesList);
            }



            if(getRowCount)
            {
                statementCount = connection.prepareStatement(queryCount);

                i = 0;


                if (shopID != null) {
                    statementCount.setObject(++i, shopID);
                }


                resultSetCount = statementCount.executeQuery();

                while(resultSetCount.next())
                {
                    endPoint.setItemCount(resultSetCount.getInt("item_count"));

                    System.out.println("Item Count : " + String.valueOf(endPoint.getItemCount()));
                }
            }



//            System.out.println("Item By CategoryID " + itemImagesList.size());

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









    public ShopImage getShopImageForImageID(
            Integer imageID
    ) {


        boolean isfirst = true;

        String query = "";


        query = "SELECT DISTINCT "

                + ShopImage.TABLE_NAME + "." + ShopImage.SHOP_IMAGE_ID + ","
                + ShopImage.TABLE_NAME + "." + ShopImage.SHOP_ID + ","
                + ShopImage.TABLE_NAME + "." + ShopImage.IMAGE_FILENAME + ""

                + " FROM " + ShopImage.TABLE_NAME
                + " WHERE " + ShopImage.TABLE_NAME + "." + ShopImage.SHOP_IMAGE_ID + " = ? ";



//        ItemImage itemImagesList = new ItemImage();
        ShopImage shopImage = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);

            int i = 0;
            statement.setObject(++i,imageID);


//            System.out.println(query);
            rs = statement.executeQuery();

            while(rs.next())
            {
                shopImage = new ShopImage();

                shopImage.setShopImageID(rs.getInt(ShopImage.SHOP_IMAGE_ID));
                shopImage.setShopID(rs.getInt(ShopImage.SHOP_ID));
                shopImage.setImageFilename(rs.getString(ShopImage.IMAGE_FILENAME));
            }


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

        return shopImage;
    }








}
