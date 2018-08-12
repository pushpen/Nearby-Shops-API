package org.nearbyshops.DAOPreparedItemSpecification;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.ModelItemSpecification.ItemSpecificationItem;
import org.nearbyshops.ModelItemSpecification.ItemSpecificationName;
import org.nearbyshops.ModelItemSpecification.ItemSpecificationValue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sumeet on 5/3/17.
 */
public class ItemSpecificationItemDAO {

    private HikariDataSource dataSource = Globals.getDataSource();

    @Override
    protected void finalize() throws Throwable {
        // TODO Auto-generated method stub
        super.finalize();
    }



    public int saveItemSpecItem(ItemSpecificationItem itemSpecificationItem)
    {


        Connection connection = null;
        PreparedStatement statement = null;
        int idOfInsertedRow = -1;

        String insertItemCategory = "INSERT INTO "
                + ItemSpecificationItem.TABLE_NAME
                + "("

                + ItemSpecificationItem.ITEM_ID + ","
                + ItemSpecificationItem.ITEM_SPECIFICATION_VALUE_ID + ""
                + ") VALUES(?,?)";

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(insertItemCategory,PreparedStatement.RETURN_GENERATED_KEYS);

            int i = 0;
            statement.setObject(++i,itemSpecificationItem.getItemID());
            statement.setObject(++i,itemSpecificationItem.getItemSpecValueID());

            idOfInsertedRow = statement.executeUpdate();


//            ResultSet rs = statement.getGeneratedKeys();
//
//            if(rs.next())
//            {
//                idOfInsertedRow = rs.getInt(1);
//            }


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




    public int deleteItemSpecItem(int itemID,int itemSpecValueID)
    {

        String deleteStatement = "DELETE FROM " + ItemSpecificationItem.TABLE_NAME +
                " WHERE " + ItemSpecificationItem.ITEM_ID + " = ? "
                + " AND " + ItemSpecificationItem.ITEM_SPECIFICATION_VALUE_ID + " = ? ";

        Connection connection= null;
        PreparedStatement statement = null;
        int rowCountDeleted = 0;
        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(deleteStatement);
            statement.setInt(1,itemID);
            statement.setInt(2,itemSpecValueID);

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








    public List<ItemSpecificationItem> getItemSpecName(
            Integer itemSpecNameID,
            Integer itemID
    ) {


        boolean isfirst = true;
        String query = "";

        String queryJoin = "SELECT DISTINCT "

                + ItemSpecificationItem.TABLE_NAME + "." + ItemSpecificationItem.ITEM_ID + ","
                + ItemSpecificationItem.TABLE_NAME + "." + ItemSpecificationItem.ITEM_SPECIFICATION_VALUE_ID + ""

                + " FROM " + ItemSpecificationName.TABLE_NAME
                + " INNER JOIN " + ItemSpecificationValue.TABLE_NAME + " ON ( " + ItemSpecificationValue.TABLE_NAME + "." + ItemSpecificationValue.ITEM_SPECIFICATION_NAME_ID + " = " + ItemSpecificationName.TABLE_NAME + "." + ItemSpecificationName.ID + " ) "
                + " INNER JOIN " + ItemSpecificationItem.TABLE_NAME + " ON ( " + ItemSpecificationItem.TABLE_NAME + "." + ItemSpecificationItem.ITEM_SPECIFICATION_VALUE_ID + " = " + ItemSpecificationValue.TABLE_NAME + "." + ItemSpecificationValue.ID + " ) ";


        if(itemSpecNameID != null)
        {
            queryJoin = queryJoin + " WHERE "
                    + ItemSpecificationName.TABLE_NAME
                    + "."
                    + ItemSpecificationName.ID + " = ?";

            isfirst = false;
        }





        if(itemID != null)
        {
            String queryPart = ItemSpecificationItem.TABLE_NAME + "." + ItemSpecificationItem.ITEM_ID + " = ? ";

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





        query = queryJoin;

        ArrayList<ItemSpecificationItem> itemSpecNameList = new ArrayList<ItemSpecificationItem>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);

            int i = 0;
            if(itemSpecNameID != null)
            {
                statement.setObject(++i,itemSpecNameID);
            }

            if(itemID!=null)
            {
                statement.setObject(++i,itemID);
            }


            System.out.println(query);
            rs = statement.executeQuery();

            while(rs.next())
            {
                ItemSpecificationItem itemSpecItem = new ItemSpecificationItem();

                itemSpecItem.setItemID(rs.getInt(ItemSpecificationItem.ITEM_ID));
                itemSpecItem.setItemSpecValueID(rs.getInt(ItemSpecificationItem.ITEM_SPECIFICATION_VALUE_ID));

                itemSpecNameList.add(itemSpecItem);
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



}
