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




public class OrderStatusHomeDelivery {

    public static final int ORDER_PLACED = 1; // Confirm (Staff)
    public static final int ORDER_CONFIRMED = 2; // Pack (Staff)
    public static final int ORDER_PACKED = 3; // handover to delivery (Staff)


    public static final int HANDOVER_REQUESTED = 4; // handover requested | Accept Package : Decline (Delivery Guy)
    public static final int OUT_FOR_DELIVERY = 5;// out for delivery | Return : Delivered (Delivery Guy)
//    public static final int PENDING_DELIVERY = 6;


    public static final int RETURN_REQUESTED = 6;// Return Requested | Accept Return (Staff)
    public static final int RETURNED_ORDERS = 7;// Returned Orders | Unpack : HandoverToDelivery (Staff)


    public static final int DELIVERED = 8;// Delivered | Payment Received (Staff)
    public static final int PAYMENT_RECEIVED = 9;// Payment Received-Complete


    public static final int RETURN_REQUESTED_BY_USER = 10;// Return-Requested     | Accept (Shop Staff) | Decline (Shop Staff)
    public static final int ORDER_RETURN_REQUEST_WAITING_FOR_ASSIGN = 11;     // |Accept (Delivery Guy) | Decline (Delivery Guy)
    public static final int COLLECTING_RETURN = 12;     // | Received (Delivery Guy) | Decline (Delivery Guy)
    public static final int WAITING_FOR_HANDOVER_TO_SHOP = 13;     // | Received (Shop Staff)

    public static final int RETURN_RECEIVED = 14;     // | Refund with OTP (Shop Staff)
    public static final int REFUND_COMPLETE = 15;     // | Unpack (Shop Staff)
    

//    public static final int CANCELLED_BY_SHOP = 19;
    public static final int CANCELLED_BY_USER = 20;

    public static final int CANCELLED_WITH_DELIVERY_GUY = 19;
    public static final int CANCELLED = 20;






}
