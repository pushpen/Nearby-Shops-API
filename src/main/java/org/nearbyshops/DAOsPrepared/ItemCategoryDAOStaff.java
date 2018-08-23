package org.nearbyshops.DAOsPrepared;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Item;
import org.nearbyshops.Model.ItemCategory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by sumeet on 22/11/16.
 */
public class ItemCategoryDAOStaff {


    private HikariDataSource dataSource = Globals.getDataSource();


    public ArrayList<ItemCategory> getItemCategories(
            Integer parentID, Boolean parentIsNull,
            String sortBy,
            Integer limit, Integer offset)
    {

        String query = "";

//        String queryNormal = "SELECT * FROM " + ItemCategory.TABLE_NAME;


//        boolean queryNormalFirst = true;

//        if(parentID!=null)
//        {
//            queryNormal = queryNormal + " WHERE "
//                    + ItemCategory.PARENT_CATEGORY_ID
//                    + "=" + parentID ;
//
//            queryNormalFirst = false;
//        }


//        if(parentIsNull!=null&& parentIsNull)
//        {
//
//            String queryNormalPart = ItemCategory.PARENT_CATEGORY_ID + " IS NULL";
//
//            if(queryNormalFirst)
//            {
//                queryNormal = queryNormal + " WHERE " + queryNormalPart;
//
//            }else
//            {
//                queryNormal = queryNormal + " AND " + queryNormalPart;
//
//            }
//        }



        // a recursive CTE (Common table Expression) query. This query is used for retrieving hierarchical / tree set data.


        String withRecursiveStart = "WITH RECURSIVE category_tree("
                + ItemCategory.ITEM_CATEGORY_ID + ","
                + ItemCategory.PARENT_CATEGORY_ID + ","
                + ItemCategory.IMAGE_PATH + ","
                + ItemCategory.ITEM_CATEGORY_DESCRIPTION + ","

                + ItemCategory.ITEM_CATEGORY_DESCRIPTION_SHORT + ","
                + ItemCategory.IS_ABSTRACT + ","

                + ItemCategory.IS_LEAF_NODE + ","
                + ItemCategory.ITEM_CATEGORY_NAME
                + ") AS (";


        String queryJoin = "SELECT DISTINCT "

                + ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_ID + ","
                + ItemCategory.TABLE_NAME + "." + ItemCategory.PARENT_CATEGORY_ID + ","
                + ItemCategory.TABLE_NAME + "." + ItemCategory.IMAGE_PATH + ","
                + ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_DESCRIPTION + ","

                + ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_DESCRIPTION_SHORT + ","
                + ItemCategory.TABLE_NAME + "." + ItemCategory.IS_ABSTRACT + ","

                + ItemCategory.TABLE_NAME + "." + ItemCategory.IS_LEAF_NODE + ","
                + ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_NAME

                + " FROM "
                + Item.TABLE_NAME + "," + ItemCategory.TABLE_NAME
                + " WHERE "
                + Item.TABLE_NAME + "." + Item.ITEM_CATEGORY_ID + "=" + ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_ID;




        String union = " UNION ";

        String querySelect = " SELECT "

                + "cat." + ItemCategory.ITEM_CATEGORY_ID + ","
                + "cat." + ItemCategory.PARENT_CATEGORY_ID + ","
                + "cat." + ItemCategory.IMAGE_PATH + ","
                + "cat." + ItemCategory.ITEM_CATEGORY_DESCRIPTION + ","

                + "cat." + ItemCategory.ITEM_CATEGORY_DESCRIPTION_SHORT + ","
                + "cat." + ItemCategory.IS_ABSTRACT + ","

                + "cat." + ItemCategory.IS_LEAF_NODE + ","
                + "cat." + ItemCategory.ITEM_CATEGORY_NAME

                + " FROM category_tree tempCat," + 	ItemCategory.TABLE_NAME + " cat"
                + " WHERE cat." + ItemCategory.ITEM_CATEGORY_ID
                + " = tempcat." + ItemCategory.PARENT_CATEGORY_ID
                + " )";


        String queryLast = " SELECT "
                + ItemCategory.ITEM_CATEGORY_ID + ","
                + ItemCategory.PARENT_CATEGORY_ID + ","
                + ItemCategory.IMAGE_PATH + ","
                + ItemCategory.ITEM_CATEGORY_DESCRIPTION + ","

                + ItemCategory.ITEM_CATEGORY_DESCRIPTION_SHORT + ","
                + ItemCategory.IS_ABSTRACT + ","

                + ItemCategory.IS_LEAF_NODE + ","
                + ItemCategory.ITEM_CATEGORY_NAME
                + " FROM category_tree";


        if(parentID!=null)
        {
            queryLast = queryLast + " WHERE "
                    + ItemCategory.PARENT_CATEGORY_ID
                    + "=" + parentID ;
        }

        String queryRecursive = withRecursiveStart + queryJoin + union + querySelect +  queryLast;




        if(sortBy!=null)
        {
            if(!sortBy.equals(""))
            {
                String queryPartSortBy = " ORDER BY " + sortBy;

//                queryNormal = queryNormal + queryPartSortBy;
                queryRecursive = queryRecursive + queryPartSortBy;
            }
        }



        if(limit !=null)
        {

            String queryPartLimitOffset = "";

            if(offset!=null)
            {
                queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + offset;

            }else
            {
                queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + 0;
            }


//            queryNormal = queryNormal + queryPartLimitOffset;
            queryRecursive = queryRecursive + queryPartLimitOffset;
        }



//        if(shopID==null && latCenter == null && lonCenter == null)
//        {
//            query = queryNormal;
//
//        }else
//        {
//            query = queryRecursive;
//        }


        query = queryRecursive;

//		System.out.println(query);


        ArrayList<ItemCategory> itemCategoryList = new ArrayList<ItemCategory>();


        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        try {

            connection = dataSource.getConnection();

            statement = connection.createStatement();

            rs = statement.executeQuery(query);

            while(rs.next())
            {
                ItemCategory itemCategory = new ItemCategory();

                itemCategory.setItemCategoryID(rs.getInt(ItemCategory.ITEM_CATEGORY_ID));
                itemCategory.setParentCategoryID(rs.getInt(ItemCategory.PARENT_CATEGORY_ID));
                itemCategory.setIsLeafNode(rs.getBoolean(ItemCategory.IS_LEAF_NODE));
                itemCategory.setImagePath(rs.getString(ItemCategory.IMAGE_PATH));
                itemCategory.setCategoryName(rs.getString(ItemCategory.ITEM_CATEGORY_NAME));

                itemCategory.setisAbstractNode(rs.getBoolean(ItemCategory.IS_ABSTRACT));
                itemCategory.setDescriptionShort(rs.getString(ItemCategory.ITEM_CATEGORY_DESCRIPTION_SHORT));

                itemCategory.setCategoryDescription(rs.getString(ItemCategory.ITEM_CATEGORY_DESCRIPTION));
                itemCategoryList.add(itemCategory);
            }



            if(parentIsNull!=null&& parentIsNull)
            {
                // exclude the root category
                for(ItemCategory itemCategory : itemCategoryList)
                {
                    if(itemCategory.getItemCategoryID()==1)
                    {
                        itemCategoryList.remove(itemCategory);
                        break;
                    }
                }
            }


//			conn.close();

//            System.out.println("Total itemCategories queried " + itemCategoryList.size());

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

        return itemCategoryList;
    }
}
