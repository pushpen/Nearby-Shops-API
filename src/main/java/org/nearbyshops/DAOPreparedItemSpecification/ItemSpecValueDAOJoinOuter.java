package org.nearbyshops.DAOPreparedItemSpecification;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Item;
import org.nearbyshops.Model.ItemCategory;
import org.nearbyshops.ModelItemSpecification.ItemSpecificationItem;
import org.nearbyshops.ModelItemSpecification.ItemSpecificationValue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sumeet on 4/3/17.
 */
public class ItemSpecValueDAOJoinOuter {


    private HikariDataSource dataSource = Globals.getDataSource();



    public List<ItemSpecificationValue> getItemSpecValue(
            Integer itemSpecID,
            Integer itemCatID,
            String sortBy,
            Integer limit, Integer offset
    ) {


        boolean isfirst = true;
        String query = "";

        String queryJoin = "SELECT DISTINCT "

                + " count (" + Item.TABLE_NAME + "." + Item.ITEM_ID + ") as item_count,"
                + ItemSpecificationValue.TABLE_NAME + "." + ItemSpecificationValue.ID + ","
                + ItemSpecificationValue.TABLE_NAME + "." + ItemSpecificationValue.ITEM_SPECIFICATION_NAME_ID + ","
                + ItemSpecificationValue.TABLE_NAME + "." + ItemSpecificationValue.TITLE + ","
                + ItemSpecificationValue.TABLE_NAME + "." + ItemSpecificationValue.DESCRIPTION + ","
                + ItemSpecificationValue.TABLE_NAME + "." + ItemSpecificationValue.IMAGE_FILENAME + ","
                + ItemSpecificationValue.TABLE_NAME + "." + ItemSpecificationValue.GIDB_ID + ","
                + ItemSpecificationValue.TABLE_NAME + "." + ItemSpecificationValue.GIDB_SERVICE_URL + ","
                + ItemSpecificationValue.TABLE_NAME + "." + ItemSpecificationValue.TIMESTAMP_CREATED + ""

                + " FROM " + ItemSpecificationValue.TABLE_NAME
                + " LEFT OUTER JOIN " + ItemSpecificationItem.TABLE_NAME + " ON ( " + ItemSpecificationItem.TABLE_NAME + "." + ItemSpecificationItem.ITEM_SPECIFICATION_VALUE_ID + " = " + ItemSpecificationValue.TABLE_NAME + "." + ItemSpecificationValue.ID + " ) "
                + " LEFT OUTER JOIN " + Item.TABLE_NAME + " ON ( " + Item.TABLE_NAME + "." + Item.ITEM_ID + " = "  + ItemSpecificationItem.TABLE_NAME + "." + ItemSpecificationItem.ITEM_ID + " ) "
                + " LEFT OUTER JOIN " + ItemCategory.TABLE_NAME + " ON ( " + ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_ID + " = " + Item.TABLE_NAME + "." + Item.ITEM_CATEGORY_ID + " ) ";



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
		queryJoin = queryJoin

				+ " group by " + ItemSpecificationValue.TABLE_NAME + "." + ItemSpecificationValue.ID ;


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

                itemSpecValue.setRt_item_count(rs.getInt("item_count"));

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
                + " LEFT OUTER JOIN " + ItemSpecificationItem.TABLE_NAME + " ON ( " + ItemSpecificationItem.TABLE_NAME + "." + ItemSpecificationItem.ITEM_SPECIFICATION_VALUE_ID + " = " + ItemSpecificationValue.TABLE_NAME + "." + ItemSpecificationValue.ID + " ) "
                + " LEFT OUTER JOIN " + Item.TABLE_NAME + " ON ( " + Item.TABLE_NAME + "." + Item.ITEM_ID + " = "  + ItemSpecificationItem.TABLE_NAME + "." + ItemSpecificationItem.ITEM_ID + " ) "
                + " LEFT OUTER JOIN " + ItemCategory.TABLE_NAME + " ON ( " + ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_ID + " = " + Item.TABLE_NAME + "." + Item.ITEM_CATEGORY_ID + " ) ";



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

            if(itemSpecID!=null)
            {
                statement.setObject(++i,itemSpecID);
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





    public ItemSpecificationValue getItemSpecValueImageFilename(
            int itemSpecValueID
    ) {


        boolean isfirst = true;
        String query = "";

        String queryJoin = "SELECT DISTINCT "

                + ItemSpecificationValue.TABLE_NAME + "." + ItemSpecificationValue.ID + ","
//                + ItemSpecificationName.TABLE_NAME + "." + ItemSpecificationName.TITLE + ","
//                + ItemSpecificationName.TABLE_NAME + "." + ItemSpecificationName.DESCRIPTION + ","
                + ItemSpecificationValue.TABLE_NAME + "." + ItemSpecificationValue.IMAGE_FILENAME + ","
//                + ItemSpecificationName.TABLE_NAME + "." + ItemSpecificationName.GIDB_ID + ","
//                + ItemSpecificationName.TABLE_NAME + "." + ItemSpecificationName.GIDB_SERVICE_URL + ","
//                + ItemSpecificationName.TABLE_NAME + "." + ItemSpecificationName.TIMESTAMP_CREATED + ""

                + " FROM " + ItemSpecificationValue.TABLE_NAME
                + " WHERE " + ItemSpecificationValue.ID + " = ?";


        query = queryJoin;

        ItemSpecificationValue itemSpecValue = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);

            int i = 0;
            statement.setObject(++i,itemSpecValueID);

            System.out.println(query);
            rs = statement.executeQuery();

            while(rs.next())
            {
                itemSpecValue = new ItemSpecificationValue();

                itemSpecValue.setId(rs.getInt(ItemSpecificationValue.ID));
                itemSpecValue.setImageFilename(rs.getString(ItemSpecificationValue.IMAGE_FILENAME));
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

        return itemSpecValue;
    }




}
