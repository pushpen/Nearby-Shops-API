package org.nearbyshops.DAOPreparedItemSpecification;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Item;
import org.nearbyshops.Model.ItemCategory;
import org.nearbyshops.ModelItemSpecification.ItemSpecificationItem;
import org.nearbyshops.ModelItemSpecification.ItemSpecificationName;
import org.nearbyshops.ModelItemSpecification.ItemSpecificationValue;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.stream.Collectors;

/**
 * Created by sumeet on 2/3/17.
 */
public class ItemSpecificationNameDAO {

    private HikariDataSource dataSource = Globals.getDataSource();



    @Override
    protected void finalize() throws Throwable {
        // TODO Auto-generated method stub
        super.finalize();
    }



    public int saveItemSpecName(ItemSpecificationName itemSpecificationName)
    {


        Connection connection = null;
        PreparedStatement statement = null;
        int idOfInsertedRow = -1;

        String insertItemCategory = "INSERT INTO "
                + ItemSpecificationName.TABLE_NAME
                + "("
                + ItemSpecificationName.TITLE + ","
                + ItemSpecificationName.DESCRIPTION + ","
                + ItemSpecificationName.IMAGE_FILENAME + ","
                + ItemSpecificationName.GIDB_ID + ","
                + ItemSpecificationName.GIDB_SERVICE_URL + ","
                + ItemSpecificationName.TIMESTAMP_UPDATED + ""

                + ") VALUES(?,?,? ,?,?,?)";

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(insertItemCategory,PreparedStatement.RETURN_GENERATED_KEYS);

            int i = 0;
            statement.setString(++i,itemSpecificationName.getTitle());
            statement.setString(++i,itemSpecificationName.getDescription());
            statement.setString(++i,itemSpecificationName.getImageFilename());
            statement.setObject(++i,itemSpecificationName.getGidbID());
            statement.setString(++i,itemSpecificationName.getGidbServiceURL());
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





    public int updateItemSpecName(ItemSpecificationName itemSpecificationName)
    {

        String updateStatement = "UPDATE " + ItemSpecificationName.TABLE_NAME

                + " SET "

                + ItemSpecificationName.TITLE + "=?,"
                + ItemSpecificationName.DESCRIPTION + "=?,"
                + ItemSpecificationName.IMAGE_FILENAME + "=?,"
                + ItemSpecificationName.GIDB_ID + "=?,"
                + ItemSpecificationName.GIDB_SERVICE_URL + "=?,"
                + ItemSpecificationName.TIMESTAMP_UPDATED + "=?"

                + " WHERE " + ItemSpecificationName.ID + "=?";


        Connection connection = null;
        PreparedStatement statement = null;

        int rowCountUpdated = 0;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);



            int i = 0;
            statement.setString(++i,itemSpecificationName.getTitle());
            statement.setString(++i,itemSpecificationName.getDescription());
            statement.setString(++i,itemSpecificationName.getImageFilename());
            statement.setObject(++i,itemSpecificationName.getGidbID());
            statement.setString(++i,itemSpecificationName.getGidbServiceURL());

            statement.setTimestamp(++i,new Timestamp(System.currentTimeMillis()));

            statement.setObject(++i,itemSpecificationName.getId());

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






    public int deleteItemSpecName(int itemSpecNameID)
    {

        String deleteStatement = "DELETE FROM " + ItemSpecificationName.TABLE_NAME +
                                " WHERE " + ItemSpecificationName.ID + " = ?";

        Connection connection= null;
        PreparedStatement statement = null;
        int rowCountDeleted = 0;
        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(deleteStatement);
            statement.setInt(1,itemSpecNameID);

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








    public List<ItemSpecificationName> getItemSpecName(
            Integer itemCatID,
            Integer itemID,
            String sortBy,
            Integer limit, Integer offset
    ) {


        boolean isfirst = true;
        String query = "";

        String queryJoin = "SELECT DISTINCT "

                + ItemSpecificationName.TABLE_NAME + "." + ItemSpecificationName.ID + ","
                + ItemSpecificationName.TABLE_NAME + "." + ItemSpecificationName.TITLE + ","
//                + ItemSpecificationName.TABLE_NAME + "." + ItemSpecificationName.DESCRIPTION + ","
//                + ItemSpecificationName.TABLE_NAME + "." + ItemSpecificationName.IMAGE_FILENAME + ","
//                + ItemSpecificationName.TABLE_NAME + "." + ItemSpecificationName.GIDB_ID + ","
//                + ItemSpecificationName.TABLE_NAME + "." + ItemSpecificationName.GIDB_SERVICE_URL + ","
//                + ItemSpecificationName.TABLE_NAME + "." + ItemSpecificationName.TIMESTAMP_CREATED + ","


                + ItemSpecificationValue.TABLE_NAME + "." + ItemSpecificationValue.TITLE + " as item_spec_value "

                + " FROM " + ItemSpecificationName.TABLE_NAME
                + " INNER JOIN " + ItemSpecificationValue.TABLE_NAME + " ON ( " + ItemSpecificationValue.TABLE_NAME + "." + ItemSpecificationValue.ITEM_SPECIFICATION_NAME_ID + " = " + ItemSpecificationName.TABLE_NAME + "." + ItemSpecificationName.ID + " ) "
                + " INNER JOIN " + ItemSpecificationItem.TABLE_NAME + " ON ( " + ItemSpecificationItem.TABLE_NAME + "." + ItemSpecificationItem.ITEM_SPECIFICATION_VALUE_ID + " = " + ItemSpecificationValue.TABLE_NAME + "." + ItemSpecificationValue.ID + " ) "
                + " INNER JOIN " + Item.TABLE_NAME + " ON ( " + Item.TABLE_NAME + "." + Item.ITEM_ID + " = "  + ItemSpecificationItem.TABLE_NAME + "." + ItemSpecificationItem.ITEM_ID + " ) ";


//        + " INNER JOIN " + ItemCategory.TABLE_NAME + " ON ( " + ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_ID + " = " + Item.TABLE_NAME + "." + Item.ITEM_CATEGORY_ID + " ) ";


        if(itemCatID != null)
        {
            queryJoin = queryJoin + " WHERE "
                    + ItemCategory.TABLE_NAME
                    + "."
                    + ItemCategory.ITEM_CATEGORY_ID + " = ?";

            isfirst = false;
        }


        if(itemID != null)
        {
            String queryPart = Item.TABLE_NAME + "." + Item.ITEM_ID + " = ? ";

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
		queryJoin = queryJoin

				+ " group by "
				+ ItemSpecificationValue.TABLE_NAME + "." + ItemSpecificationValue.ID + ","
                + ItemSpecificationName.TABLE_NAME + "." + ItemSpecificationName.ID;

//                + ","
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



        Map<Integer,ItemSpecificationName> map = new HashMap<>();


        query = queryJoin;

        ArrayList<ItemSpecificationName> itemSpecNameList = new ArrayList<ItemSpecificationName>();
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

            if(itemID!=null)
            {
                statement.setObject(++i,itemID);
            }



//            System.out.println(query);

            rs = statement.executeQuery();

            while(rs.next())
            {
//                ItemImage itemImage = new ItemImage();
                ItemSpecificationName itemSpecName = new ItemSpecificationName();

                itemSpecName.setId(rs.getInt(ItemSpecificationName.ID));
                itemSpecName.setTitle(rs.getString(ItemSpecificationName.TITLE));
//                itemSpecName.setDescription(rs.getString(ItemSpecificationName.DESCRIPTION));
//                itemSpecName.setImageFilename(rs.getString(ItemSpecificationName.IMAGE_FILENAME));
//                itemSpecName.setGidbID(rs.getInt(ItemSpecificationName.GIDB_ID));
//                itemSpecName.setGidbServiceURL(rs.getString(ItemSpecificationName.GIDB_SERVICE_URL));


                ItemSpecificationValue itemSpecValue = new ItemSpecificationValue();
                itemSpecValue.setTitle(rs.getString("item_spec_value"));

//                itemSpecName.setRt_itemSpecificationValue(itemSpecValue);



                if(!map.containsKey(itemSpecName.getId()))
                {
                    map.put(itemSpecName.getId(),itemSpecName);
                }


//                map.get(itemSpecName.getId()).setRt_itemSpecificationValue(new ArrayList<>());
                map.get(itemSpecName.getId()).getRt_itemSpecificationValue().add(itemSpecValue);

            }



//            itemSpecNameList.addAll(map.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList()));
            itemSpecNameList.addAll(map.values());


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



    public int getEndPointMetadata(
            Integer itemCatID,
            Integer itemID)
    {

        boolean isfirst = true;
        String query = "";

        String queryJoin = "SELECT DISTINCT "

                + ItemSpecificationName.TABLE_NAME + "." + ItemSpecificationName.ID + ""

                + " FROM " + ItemSpecificationName.TABLE_NAME
                + " INNER JOIN " + ItemSpecificationValue.TABLE_NAME + " ON ( " + ItemSpecificationValue.TABLE_NAME + "." + ItemSpecificationValue.ITEM_SPECIFICATION_NAME_ID + " = " + ItemSpecificationName.TABLE_NAME + "." + ItemSpecificationName.ID + " ) "
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




        if(itemID != null)
        {
            String queryPart = Item.TABLE_NAME + "." + Item.ITEM_ID + " = ? ";

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
        queryJoin = queryJoin

                + " group by "
                + ItemSpecificationValue.TABLE_NAME + "." + ItemSpecificationValue.ID;


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

            if(itemID!=null)
            {
                statement.setObject(++i,itemID);
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



    public ItemSpecificationName checkItemSpecNameByGidbURL(String gidbURL, int gidbID) {

        String queryJoin = "SELECT DISTINCT "

                + ItemSpecificationName.TABLE_NAME + "." + ItemSpecificationName.ID + ","
//                + ItemSpecificationName.TABLE_NAME + "." + ItemSpecificationName.TITLE + ","
//                + ItemSpecificationName.TABLE_NAME + "." + ItemSpecificationName.DESCRIPTION + ","
//                + ItemSpecificationName.TABLE_NAME + "." + ItemSpecificationName.IMAGE_FILENAME + ","
                + ItemSpecificationName.TABLE_NAME + "." + ItemSpecificationName.GIDB_ID + ","
                + ItemSpecificationName.TABLE_NAME + "." + ItemSpecificationName.GIDB_SERVICE_URL + ","
                + ItemSpecificationName.TABLE_NAME + "." + ItemSpecificationName.TIMESTAMP_UPDATED + ""

                + " FROM " + ItemSpecificationName.TABLE_NAME
                + " WHERE " + ItemSpecificationName.GIDB_SERVICE_URL + " = ?"
                + " AND " + ItemSpecificationName.GIDB_ID + " = ?";


        ItemSpecificationName itemSpecName = null;
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

                itemSpecName = new ItemSpecificationName();


                itemSpecName.setId(rs.getInt(ItemSpecificationName.ID));
//                itemSpecName.setTitle(rs.getString(ItemSpecificationName.TITLE));
//                itemSpecName.setDescription(rs.getString(ItemSpecificationName.DESCRIPTION));
//                itemSpecName.setImageFilename(rs.getString(ItemSpecificationName.IMAGE_FILENAME));
                itemSpecName.setGidbID(rs.getInt(ItemSpecificationName.GIDB_ID));
                itemSpecName.setGidbServiceURL(rs.getString(ItemSpecificationName.GIDB_SERVICE_URL));
                itemSpecName.setTimestampUpdated(rs.getTimestamp(ItemSpecificationName.TIMESTAMP_UPDATED));

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

        return itemSpecName;
    }



}
