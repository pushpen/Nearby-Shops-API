package org.nearbyshops.DAOPreparedItemSpecification;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Item;
import org.nearbyshops.Model.ItemCategory;
import org.nearbyshops.ModelItemSpecification.ItemSpecificationItem;
import org.nearbyshops.ModelItemSpecification.ItemSpecificationValue;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sumeet on 2/3/17.
 */
public class ItemSpecificationValueDAO {

    private HikariDataSource dataSource = Globals.getDataSource();

    @Override
    protected void finalize() throws Throwable {
        // TODO Auto-generated method stub
        super.finalize();
    }



    public int saveItemSpecValue(ItemSpecificationValue itemSpecificationValue)
    {


        Connection connection = null;
        PreparedStatement statement = null;
        int idOfInsertedRow = -1;

        String insertItemCategory = "INSERT INTO "
                + ItemSpecificationValue.TABLE_NAME
                + "("
                + ItemSpecificationValue.ITEM_SPECIFICATION_NAME_ID + ","
                + ItemSpecificationValue.TITLE + ","
                + ItemSpecificationValue.DESCRIPTION + ","
                + ItemSpecificationValue.IMAGE_FILENAME + ","
                + ItemSpecificationValue.GIDB_ID + ","
                + ItemSpecificationValue.GIDB_SERVICE_URL + ","

                + ItemSpecificationValue.TIMESTAMP_UPDATED + ""
                + ") VALUES(?, ?,?,? ,?,?,?)";

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(insertItemCategory,PreparedStatement.RETURN_GENERATED_KEYS);

            int i = 0;
            statement.setObject(++i,itemSpecificationValue.getItemSpecNameID());
            statement.setString(++i,itemSpecificationValue.getTitle());
            statement.setString(++i,itemSpecificationValue.getDescription());
            statement.setString(++i,itemSpecificationValue.getImageFilename());
            statement.setObject(++i,itemSpecificationValue.getGidbID());
            statement.setString(++i,itemSpecificationValue.getGidbServiceURL());

            statement.setTimestamp(++i,new Timestamp(System.currentTimeMillis()));

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



    public int updateItemSpecName(ItemSpecificationValue itemSpecificationValue)
    {

        String updateStatement = "UPDATE " + ItemSpecificationValue.TABLE_NAME

                + " SET "

                + ItemSpecificationValue.ITEM_SPECIFICATION_NAME_ID + "=?,"
                + ItemSpecificationValue.TITLE + "=?,"
                + ItemSpecificationValue.DESCRIPTION + "=?,"
                + ItemSpecificationValue.IMAGE_FILENAME + "=?,"
                + ItemSpecificationValue.GIDB_ID + "=?,"
                + ItemSpecificationValue.GIDB_SERVICE_URL + "=?,"

                + ItemSpecificationValue.TIMESTAMP_UPDATED + "=?"

                + " WHERE " + ItemSpecificationValue.ID + "=?";


        Connection connection = null;
        PreparedStatement statement = null;

        int rowCountUpdated = 0;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);



            int i = 0;
            statement.setObject(++i,itemSpecificationValue.getItemSpecNameID());
            statement.setString(++i,itemSpecificationValue.getTitle());
            statement.setString(++i,itemSpecificationValue.getDescription());
            statement.setString(++i,itemSpecificationValue.getImageFilename());
            statement.setObject(++i,itemSpecificationValue.getGidbID());
            statement.setString(++i,itemSpecificationValue.getGidbServiceURL());

            statement.setTimestamp(++i,new Timestamp(System.currentTimeMillis()));

            statement.setObject(++i,itemSpecificationValue.getId());

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





    public int deleteItemSpecValue(int itemSpecValueID)
    {

        String deleteStatement = "DELETE FROM " + ItemSpecificationValue.TABLE_NAME +
                " WHERE " + ItemSpecificationValue.ID + " = ?";

        Connection connection= null;
        PreparedStatement statement = null;
        int rowCountDeleted = 0;
        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(deleteStatement);
            statement.setInt(1,itemSpecValueID);

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






    public List<ItemSpecificationValue> getItemSpecValue(
            Integer itemSpecID,
            Integer itemCatID,
            String sortBy,
            Integer limit, Integer offset
    ) {


        boolean isfirst = true;
        String query = "";

        String queryJoin = "SELECT DISTINCT "

                + ItemSpecificationValue.TABLE_NAME + "." + ItemSpecificationValue.ID + ","
                + ItemSpecificationValue.TABLE_NAME + "." + ItemSpecificationValue.ITEM_SPECIFICATION_NAME_ID + ","
                + ItemSpecificationValue.TABLE_NAME + "." + ItemSpecificationValue.TITLE + ","
                + ItemSpecificationValue.TABLE_NAME + "." + ItemSpecificationValue.DESCRIPTION + ","
                + ItemSpecificationValue.TABLE_NAME + "." + ItemSpecificationValue.IMAGE_FILENAME + ","
                + ItemSpecificationValue.TABLE_NAME + "." + ItemSpecificationValue.GIDB_ID + ","
                + ItemSpecificationValue.TABLE_NAME + "." + ItemSpecificationValue.GIDB_SERVICE_URL + ","
                + ItemSpecificationValue.TABLE_NAME + "." + ItemSpecificationValue.TIMESTAMP_CREATED + ""

                + " FROM " + ItemSpecificationValue.TABLE_NAME
                + " INNER JOIN " + ItemSpecificationItem.TABLE_NAME + " ON ( " + ItemSpecificationItem.TABLE_NAME + "." + ItemSpecificationItem.ITEM_SPECIFICATION_VALUE_ID + " = " + ItemSpecificationValue.TABLE_NAME + "." + ItemSpecificationValue.ID + " ) "
                + " INNER JOIN " + Item.TABLE_NAME + " ON ( " + Item.TABLE_NAME + "." + Item.ITEM_ID + " = "  + ItemSpecificationItem.TABLE_NAME + "." + ItemSpecificationItem.ITEM_ID + " ) "
                + " INNER JOIN " + ItemCategory.TABLE_NAME + " ON ( " + ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_ID + " = " + Item.TABLE_NAME + "." + Item.ITEM_CATEGORY_ID + " ) ";



        if(itemCatID != null)
        {
            queryJoin = queryJoin + " WHERE "
                    + ItemCategory.TABLE_NAME
                    + "."
                    + ItemCategory.ITEM_CATEGORY_ID + " = ?";

            isfirst = false;
        }


        if(itemSpecID != null)
        {
            String queryPart = ItemSpecificationValue.TABLE_NAME + "." + ItemSpecificationValue.ITEM_SPECIFICATION_NAME_ID + " = ? ";

            if(isfirst)
            {
                queryJoin = queryJoin + " WHERE " + queryPart;
            }
            else
            {
                queryJoin = queryJoin + " AND " + queryPart;
            }

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

        ArrayList<ItemSpecificationValue> itemSpecNameList = new ArrayList<ItemSpecificationValue>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);

            int i = 0;
            if(itemCatID!=null)
            {
                statement.setObject(++i,itemCatID);
            }

            if(itemSpecID!=null)
            {
                statement.setObject(++i,itemSpecID);
            }



            System.out.println(query);
            rs = statement.executeQuery();

            while(rs.next())
            {
//                ItemImage itemImage = new ItemImage();
                ItemSpecificationValue itemSpecValue = new ItemSpecificationValue();

                itemSpecValue.setId(rs.getInt(ItemSpecificationValue.ID));
                itemSpecValue.setItemSpecNameID(rs.getInt(ItemSpecificationValue.ITEM_SPECIFICATION_NAME_ID));
                itemSpecValue.setTitle(rs.getString(ItemSpecificationValue.TITLE));
                itemSpecValue.setDescription(rs.getString(ItemSpecificationValue.DESCRIPTION));
                itemSpecValue.setImageFilename(rs.getString(ItemSpecificationValue.IMAGE_FILENAME));
                itemSpecValue.setGidbID(rs.getInt(ItemSpecificationValue.GIDB_ID));
                itemSpecValue.setGidbServiceURL(rs.getString(ItemSpecificationValue.GIDB_SERVICE_URL));

                itemSpecNameList.add(itemSpecValue);
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

        return itemSpecNameList;
    }





    public int getRowCount(
            Integer itemSpecID,
            Integer itemCatID
    )
    {

        boolean isfirst = true;
        String query = "";

        String queryJoin = "SELECT DISTINCT "

                + ItemSpecificationValue.TABLE_NAME + "." + ItemSpecificationValue.ID + ""

                + " FROM " + ItemSpecificationValue.TABLE_NAME
                + " INNER JOIN " + ItemSpecificationItem.TABLE_NAME + " ON ( " + ItemSpecificationItem.TABLE_NAME + "." + ItemSpecificationItem.ITEM_SPECIFICATION_VALUE_ID + " = " + ItemSpecificationValue.TABLE_NAME + "." + ItemSpecificationValue.ID + " ) "
                + " INNER JOIN " + Item.TABLE_NAME + " ON ( " + Item.TABLE_NAME + "." + Item.ITEM_ID + " = "  + ItemSpecificationItem.TABLE_NAME + "." + ItemSpecificationItem.ITEM_ID + " ) "
                + " INNER JOIN " + ItemCategory.TABLE_NAME + " ON ( " + ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_ID + " = " + Item.TABLE_NAME + "." + Item.ITEM_CATEGORY_ID + " ) ";




        if(itemCatID != null)
        {
            queryJoin = queryJoin + " WHERE "
                    + ItemCategory.TABLE_NAME
                    + "."
                    + ItemCategory.ITEM_CATEGORY_ID + " = ?";

            isfirst = false;
        }


        if(itemSpecID != null)
        {
            String queryPart = ItemSpecificationValue.TABLE_NAME + "." + ItemSpecificationValue.ITEM_SPECIFICATION_NAME_ID + " = ? ";

            if(isfirst)
            {
                queryJoin = queryJoin + " WHERE " + queryPart;
            }
            else
            {
                queryJoin = queryJoin + " AND " + queryPart;
            }

            isfirst = false;
        }




        // Applying filters

        query = queryJoin;


        query = "SELECT COUNT(*) as item_count FROM (" + query + ") AS temp";



        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        int itemCount = -1;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);

            int i = 0;
            if(itemCatID!=null)
            {
                statement.setObject(++i,itemCatID);
            }

            rs = statement.executeQuery();

            while(rs.next())
            {
                itemCount = rs.getInt("item_count");
            }

            System.out.println("Item Count (Item Spec Name) : " + itemCount);

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

        return itemCount;
    }



    public ItemSpecificationValue checkItemSpecValueByGidbURL(String gidbURL, int gidbID) {

        String queryJoin = "SELECT DISTINCT "

                + ItemSpecificationValue.TABLE_NAME + "." + ItemSpecificationValue.ID + ","
                + ItemSpecificationValue.TABLE_NAME + "." + ItemSpecificationValue.ITEM_SPECIFICATION_NAME_ID + ","
                + ItemSpecificationValue.TABLE_NAME + "." + ItemSpecificationValue.GIDB_ID + ","
                + ItemSpecificationValue.TABLE_NAME + "." + ItemSpecificationValue.GIDB_SERVICE_URL + ","
                + ItemSpecificationValue.TABLE_NAME + "." + ItemSpecificationValue.TIMESTAMP_UPDATED + ""

                + " FROM " + ItemSpecificationValue.TABLE_NAME
                + " WHERE " + ItemSpecificationValue.GIDB_SERVICE_URL + " = ?"
                + " AND " + ItemSpecificationValue.GIDB_ID + " = ?";


        ItemSpecificationValue itemSpecValue = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(queryJoin);

            int i = 0;
            statement.setString(++i,gidbURL);
            statement.setObject(++i,gidbID);

            rs = statement.executeQuery();

            while (rs.next()) {

                itemSpecValue = new ItemSpecificationValue();


                itemSpecValue.setId(rs.getInt(ItemSpecificationValue.ID));
                itemSpecValue.setItemSpecNameID(rs.getInt(ItemSpecificationValue.ITEM_SPECIFICATION_NAME_ID));
//                itemSpecValue.setTitle(rs.getString(ItemSpecificationValue.TITLE));
//                itemSpecValue.setDescription(rs.getString(ItemSpecificationValue.DESCRIPTION));
//                itemSpecValue.setImageFilename(rs.getString(ItemSpecificationValue.IMAGE_FILENAME));
                itemSpecValue.setGidbID(rs.getInt(ItemSpecificationValue.GIDB_ID));
                itemSpecValue.setGidbServiceURL(rs.getString(ItemSpecificationValue.GIDB_SERVICE_URL));
                itemSpecValue.setTimestampUpdated(rs.getTimestamp(ItemSpecificationValue.TIMESTAMP_UPDATED));

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

                if(connection!=null)
                {connection.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return itemSpecValue;
    }



}
