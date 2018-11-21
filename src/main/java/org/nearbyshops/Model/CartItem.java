package org.nearbyshops.Model;

/**
 * Created by sumeet on 30/5/16.
 */
public class CartItem {


    // Table Name
    public static final String TABLE_NAME = "CART_ITEM";

    // column Names
    public static final String CART_ID = "CART_ID";
    public static final String ITEM_ID = "ITEM_ID";
    public static final String ITEM_QUANTITY = "ITEM_QUANTITY";




    // create table statement
    public static final String createtableCartItemPostgres = "CREATE TABLE IF NOT EXISTS " + CartItem.TABLE_NAME + "("
            + " " + CartItem.ITEM_ID + " INT,"
            + " " + CartItem.CART_ID + " INT,"
            + " " + CartItem.ITEM_QUANTITY + " FLOAT NOT NULL Default 0,"
            + " FOREIGN KEY(" + CartItem.ITEM_ID +") REFERENCES " + Item.TABLE_NAME + "(" + Item.ITEM_ID + "),"
            + " FOREIGN KEY(" + CartItem.CART_ID +") REFERENCES " + Cart.TABLE_NAME + "(" + Cart.CART_ID + "),"
            + " PRIMARY KEY (" + CartItem.ITEM_ID + ", " + CartItem.CART_ID + ")"
            + ")";




    // instance variables
    private int cartID;
    private int itemID;

    private Cart cart;
    private Item item;

    private double itemQuantity;


    // rt stands for real time.
    // variables with "rt" prefix are those variables which are not stored in the database. They are computed in real time
    // when the api call is made.

    private int rt_availableItemQuantity;
    private double rt_itemPrice;
    private String rt_quantityUnit;









    // getter and setter methods


    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }


    public double getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(double itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public int getRt_availableItemQuantity() {
        return rt_availableItemQuantity;
    }

    public void setRt_availableItemQuantity(int rt_availableItemQuantity) {
        this.rt_availableItemQuantity = rt_availableItemQuantity;
    }

    public double getRt_itemPrice() {
        return rt_itemPrice;
    }

    public void setRt_itemPrice(double rt_itemPrice) {
        this.rt_itemPrice = rt_itemPrice;
    }

    public String getRt_quantityUnit() {
        return rt_quantityUnit;
    }

    public void setRt_quantityUnit(String rt_quantityUnit) {
        this.rt_quantityUnit = rt_quantityUnit;
    }
}
