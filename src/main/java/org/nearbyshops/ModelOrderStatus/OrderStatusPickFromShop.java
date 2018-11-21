package org.nearbyshops.ModelOrderStatus;

/**
 * Created by sumeet on 12/6/16.
 */





// :: staff functions
// confirmOrder()
// setOrderPacked()
// handoverToDelivery()
// acceptReturn()
// unpackOrder()
// paymentReceived()


// delivery guy functions
// AcceptPackage() | DeclinePackage()
// Return() | Deliver()






public class OrderStatusPickFromShop {

    public static final int ORDER_PLACED = 1; // Confirm (Staff)
    public static final int ORDER_CONFIRMED = 2; // Pack (Staff)
    public static final int ORDER_PACKED = 3; // payment-received(staff)

    public static final int DELIVERED = 4;// Payment Received-Complete



    public static final int CANCELLED_BY_SHOP = 19;
    public static final int CANCELLED_BY_USER = 20;

}
