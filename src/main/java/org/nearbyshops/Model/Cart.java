package org.nearbyshops.Model;

import org.nearbyshops.ModelRoles.User;

/**
 * Created by sumeet on 30/5/16.
 */
public class Cart {


    // Table Name
    public static final String TABLE_NAME = "CART";

    // column Names
    public static final String CART_ID = "CART_ID";
    public static final String END_USER_ID = "END_USER_ID";
    public static final String SHOP_ID = "SHOP_ID";


    // create table query
    public static final String createTableCartPostgres =

            "CREATE TABLE IF NOT EXISTS " + Cart.TABLE_NAME + "("
            + " " + Cart.CART_ID + " SERIAL PRIMARY KEY,"
            + " " + Cart.END_USER_ID + " INT,"
            + " " + Cart.SHOP_ID + " INT,"
            + " FOREIGN KEY(" + Cart.END_USER_ID +") REFERENCES " + User.TABLE_NAME + "(" + User.USER_ID + "),"
            + " FOREIGN KEY(" + Cart.SHOP_ID +") REFERENCES " + Shop.TABLE_NAME + "(" + Shop.SHOP_ID + "),"
            + " UNIQUE (" + Cart.END_USER_ID + "," + Cart.SHOP_ID + ")"
            + ")";





    // instance Variables
    private int cartID;
    private int endUserID;
    private int shopID;






    // getter and setter methods

    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
    }

    public int getEndUserID() {
        return endUserID;
    }

    public void setEndUserID(int endUserID) {
        this.endUserID = endUserID;
    }

    public int getShopID() {
        return shopID;
    }

    public void setShopID(int shopID) {
        this.shopID = shopID;
    }
}
