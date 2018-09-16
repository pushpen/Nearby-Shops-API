package org.nearbyshops.DAOsPrepared;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.ItemImage;
import org.nearbyshops.ModelEndpoint.ItemImageEndPoint;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by sumeet on 28/2/17.
 */

public class ItemImagesDAO {

    private HikariDataSource dataSource = Globals.getDataSource();


    @Override
    protected void finalize() throws Throwable {
        // TODO Auto-generated method stub
        super.finalize();
    }



    public int saveItemImage(ItemImage itemImage, boolean getRowCount)
    {


        Connection connection = null;
        PreparedStatement statement = null;
        int idOfInsertedRow = -1;
        int rowCount = 0;


        String insertItemCategory = "INSERT INTO "
                + ItemImage.TABLE_NAME
                + "("
                + ItemImage.ITEM_ID + ","
                + ItemImage.IMAGE_FILENAME + ","
                + ItemImage.CAPTION_TITLE + ","
                + ItemImage.CAPTION + ","
                + ItemImage.IMAGE_COPYRIGHTS + ","
                + ItemImage.IMAGE_ORDER + ","
                + ItemImage.TIMESTAMP_UPDATED + ""

                + ") VALUES(?,?,? ,?,?,? ,?)";




        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(insertItemCategory,PreparedStatement.RETURN_GENERATED_KEYS);

            int i = 0;
            statement.setObject(++i,itemImage.getItemID());
            statement.setString(++i,itemImage.getImageFilename());
            statement.setString(++i,itemImage.getCaptionTitle());
            statement.setString(++i,itemImage.getCaption());
            statement.setString(++i,itemImage.getImageCopyrights());
            statement.setObject(++i,itemImage.getImageOrder());
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





    public int updateItemImage(ItemImage itemImage)
    {

        String updateStatement = "UPDATE " + ItemImage.TABLE_NAME

                + " SET "

                + ItemImage.ITEM_ID + "=?,"
                + ItemImage.IMAGE_FILENAME + "=?,"
                + ItemImage.CAPTION_TITLE + "=?,"
                + ItemImage.CAPTION + "=?,"
                + ItemImage.IMAGE_COPYRIGHTS + "=?,"
                + ItemImage.IMAGE_ORDER + "=?,"
                + ItemImage.TIMESTAMP_UPDATED + "=?"

                + " WHERE " + ItemImage.IMAGE_ID + "=?";


        Connection connection = null;
        PreparedStatement statement = null;

        int rowCountUpdated = 0;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);



            int i = 0;
            statement.setObject(++i,itemImage.getItemID());
            statement.setString(++i,itemImage.getImageFilename());
            statement.setString(++i,itemImage.getCaptionTitle());
            statement.setString(++i,itemImage.getCaption());
            statement.setString(++i,itemImage.getImageCopyrights());
            statement.setObject(++i,itemImage.getImageOrder());
            statement.setTimestamp(++i,new Timestamp(System.currentTimeMillis()));

            statement.setObject(++i,itemImage.getImageID());


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




    public int deleteItemImage(int imageID)
    {

        String deleteStatement = "DELETE FROM " + ItemImage.TABLE_NAME + " WHERE " + ItemImage.IMAGE_ID + " = ?";

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








    public List<ItemImage> getItemImages(
            Integer itemID,
            String sortBy,
            Integer limit, Integer offset
    ) {


        boolean isfirst = true;

        String query = "";


        String queryJoin = "SELECT DISTINCT "

                + ItemImage.TABLE_NAME + "." + ItemImage.IMAGE_ID + ","
                + ItemImage.TABLE_NAME + "." + ItemImage.ITEM_ID + ","
                + ItemImage.TABLE_NAME + "." + ItemImage.IMAGE_FILENAME + ","
                + ItemImage.TABLE_NAME + "." + ItemImage.TIMESTAMP_CREATED + ","
                + ItemImage.TABLE_NAME + "." + ItemImage.TIMESTAMP_UPDATED + ","
                + ItemImage.TABLE_NAME + "." + ItemImage.CAPTION_TITLE + ","
                + ItemImage.TABLE_NAME + "." + ItemImage.CAPTION + ","
                + ItemImage.TABLE_NAME + "." + ItemImage.IMAGE_COPYRIGHTS + ","
                + ItemImage.TABLE_NAME + "." + ItemImage.IMAGE_ORDER + ""


                + " FROM " + ItemImage.TABLE_NAME;






        if(itemID != null)
        {
            queryJoin = queryJoin + " WHERE "
                    + ItemImage.TABLE_NAME
                    + "."
                    + ItemImage.ITEM_ID + " = ?";

            isfirst = false;
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




        query = queryJoin;

        ArrayList<ItemImage> itemImagesList = new ArrayList<ItemImage>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);

            int i = 0;
            if(itemID!=null)
            {
                statement.setObject(++i,itemID);
            }


//            System.out.println(query);
            rs = statement.executeQuery();

            while(rs.next())
            {
                ItemImage itemImage = new ItemImage();

                itemImage.setImageID(rs.getInt(ItemImage.IMAGE_ID));
                itemImage.setItemID(rs.getInt(ItemImage.ITEM_ID));
                itemImage.setImageFilename(rs.getString(ItemImage.IMAGE_FILENAME));
                itemImage.setTimestampCreated(rs.getTimestamp(ItemImage.TIMESTAMP_CREATED));
                itemImage.setTimestampUpdated(rs.getTimestamp(ItemImage.TIMESTAMP_UPDATED));
                itemImage.setCaptionTitle(rs.getString(ItemImage.CAPTION_TITLE));
                itemImage.setCaption(rs.getString(ItemImage.CAPTION));
                itemImage.setImageCopyrights(rs.getString(ItemImage.IMAGE_COPYRIGHTS));
                itemImage.setImageOrder(rs.getInt(ItemImage.IMAGE_ORDER));

                itemImagesList.add(itemImage);
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

        return itemImagesList;
    }




    public ItemImageEndPoint getEndPointMetadata(
            Integer itemID)
    {


        boolean isfirst = true;

        String query = "";


        String queryJoin = "SELECT "

                + "count( DISTINCT " + ItemImage.TABLE_NAME + "." + ItemImage.IMAGE_ID + ") as item_count" + ""
                + " FROM " +  ItemImage.TABLE_NAME;


        if(itemID != null)
        {
            queryJoin = queryJoin + " WHERE "
                    + ItemImage.TABLE_NAME
                    + "."
                    + ItemImage.ITEM_ID + " = ?";

            isfirst = false;

        }




        // Applying filters

        query = queryJoin;


        ItemImageEndPoint endPoint = new ItemImageEndPoint();


        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);

            int i = 0;
            if(itemID!=null)
            {
                statement.setObject(++i,itemID);
            }

            rs = statement.executeQuery();

            while(rs.next())
            {
                endPoint.setItemCount(rs.getInt("item_count"));
            }

//            System.out.println("Item Count : " + endPoint.getItemCount());

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







    public ItemImage getItemImageForItemID(
            Integer imageID
    ) {


        boolean isfirst = true;

        String query = "";


        query = "SELECT DISTINCT "

                + ItemImage.TABLE_NAME + "." + ItemImage.IMAGE_ID + ","
                + ItemImage.TABLE_NAME + "." + ItemImage.ITEM_ID + ","
                + ItemImage.TABLE_NAME + "." + ItemImage.IMAGE_FILENAME + ""
//                + ItemImage.TABLE_NAME + "." + ItemImage.GIDB_IMAGE_ID + ","
//                + ItemImage.TABLE_NAME + "." + ItemImage.GIDB_SERVICE_URL + ","
//                + ItemImage.TABLE_NAME + "." + ItemImage.TIMESTAMP_CREATED + ","
//                + ItemImage.TABLE_NAME + "." + ItemImage.CAPTION_TITLE + ","
//                + ItemImage.TABLE_NAME + "." + ItemImage.CAPTION + ","

                + " FROM " + ItemImage.TABLE_NAME
                + " WHERE " + ItemImage.TABLE_NAME + "." + ItemImage.IMAGE_ID + " = ? ";



//        ItemImage itemImagesList = new ItemImage();
        ItemImage itemImage = null;
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
                itemImage = new ItemImage();

                itemImage.setImageID(rs.getInt(ItemImage.IMAGE_ID));
                itemImage.setItemID(rs.getInt(ItemImage.ITEM_ID));
                itemImage.setImageFilename(rs.getString(ItemImage.IMAGE_FILENAME));
//                itemImage.setGidbImageID(rs.getInt(ItemImage.GIDB_IMAGE_ID));
//                itemImage.setGidbServiceURL(rs.getString(ItemImage.GIDB_SERVICE_URL));
//                itemImage.setTimestampCreated(rs.getTimestamp(ItemImage.TIMESTAMP_CREATED));
//                itemImage.setCaptionTitle(rs.getString(ItemImage.CAPTION_TITLE));
//                itemImage.setCaption(rs.getString(ItemImage.CAPTION));

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

        return itemImage;
    }





}
