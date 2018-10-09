package org.nearbyshops.RESTEndpointsOrderHD;

import org.nearbyshops.DAOPushNotifications.DAOOneSignal;
import org.nearbyshops.Globals.GlobalConstants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Order;
import org.nearbyshops.ModelEndpoint.OrderEndPoint;
import org.nearbyshops.ModelOrderStatus.OrderStatusHomeDelivery;
import org.nearbyshops.ModelRoles.User;

import javax.annotation.security.RolesAllowed;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.ArrayList;
import java.util.List;

import static org.nearbyshops.Globals.Globals.oneSignalNotifications;


@Singleton
@Path("/api/Order/DeliveryGuySelf")
public class OrderEndpointDeliveryGuySelf {


	public OrderEndpointDeliveryGuySelf() {
		super();
		// TODO Auto-generated constructor stub
	}


//	@GET
//	@Path("/Notifications/{ShopID}")
//	@Produces(SseFeature.SERVER_SENT_EVENTS)
//	public EventOutput listenToBroadcast(@PathParam("ShopID")int shopID) {
//		final EventOutput eventOutput = new EventOutput();
//
//		if(Globals.broadcasterMap.get(shopID)!=null)
//		{
//			SseBroadcaster broadcasterOne = Globals.broadcasterMap.get(shopID);
//			broadcasterOne.add(eventOutput);
//		}
//		else
//		{
//			SseBroadcaster broadcasterTwo = new SseBroadcaster();
//			broadcasterTwo.add(eventOutput);
//			Globals.broadcasterMap.put(shopID,broadcasterTwo);
//		}
//
//		return eventOutput;
//	}







	@PUT
	@Path("/AcceptOrder/{OrderID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_DELIVERY_GUY_SELF,GlobalConstants.ROLE_DELIVERY_GUY})
	public Response acceptOrder(@PathParam("OrderID")int orderID)
	{

//		Order order = Globals.orderService.readStatusHomeDelivery(orderID);
//		User user = (User) Globals.accountApproved;


//		order.setStatusHomeDelivery(OrderStatusHomeDelivery.OUT_FOR_DELIVERY);
		int rowCount = Globals.daoOrderDeliveryGuy.acceptOrder(orderID);


//		try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}


		if(rowCount >= 1)
		{

//				Globals.broadcastMessageToEndUser("Order Out For Delivery (Home Delivery)","Order Number " + String.valueOf(orderID) + " (HD) is Out for Delivery !",order.getEndUserID());
			return Response.status(Status.OK)
					.build();
		}
		if(rowCount <= 0)
		{

			return Response.status(Status.NOT_MODIFIED)
					.build();
		}


		return null;
	}



	@PUT
	@Path("/DeclineOrder/{OrderID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_DELIVERY_GUY_SELF,GlobalConstants.ROLE_DELIVERY_GUY})
	public Response declineOrder(@PathParam("OrderID")int orderID)
	{

//		Order order = Globals.orderService.readStatusHomeDelivery(orderID);
//		User user = (User) Globals.accountApproved;
//		order.setStatusHomeDelivery(OrderStatusHomeDelivery.OUT_FOR_DELIVERY);


		int rowCount = Globals.daoOrderDeliveryGuy.declineOrder(orderID);

		if(rowCount >= 1)
		{

//				Globals.broadcastMessageToEndUser("Order Out For Delivery (Home Delivery)","Order Number " + String.valueOf(orderID) + " (HD) is Out for Delivery !",order.getEndUserID());
			return Response.status(Status.OK)
					.build();
		}
		if(rowCount <= 0)
		{

			return Response.status(Status.NOT_MODIFIED)
					.build();
		}


		return null;
	}





	@PUT
	@Path("/ReturnPackage/{OrderID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_DELIVERY_GUY_SELF,GlobalConstants.ROLE_DELIVERY_GUY})
	public Response returnOrderPackage(@PathParam("OrderID")int orderID)
	{
//		Order order = Globals.orderService.readStatusHomeDelivery(orderID);

		int rowCount = Globals.daoOrderDeliveryGuy.returnOrder(orderID);

//		try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}


		if(rowCount >= 1)
		{
			Order orderResult = Globals.orderService.readSingleOrder(orderID);

//			String shopAdminPlayerID = oneSignalNotifications.getPlayerIDforShopAdmin(orderResult.getShopID());
			ArrayList<String> playerIDs =  Globals.oneSignalNotifications.getPlayerIDsForShopStaff(orderResult.getShopID(),
					null,null,null,null,true);


//			playerIDs.add(shopAdminPlayerID);



			Globals.oneSignalNotifications.sendNotificationToUser(
					playerIDs,
					GlobalConstants.ONE_SIGNAL_APP_ID_SHOP_OWNER_APP,
					GlobalConstants.ONE_SIGNAL_API_KEY_SHOP_OWNER_APP,
					"https://i1.wp.com/nearbyshops.org/wp-content/uploads/2017/02/cropped-backdrop_play_store-1.png?w=250&ssl=1",
					null,
					null,
					10,
					"Order Return Requested",
					"Return is requested for order number " + String.valueOf(orderID) + " !",
					1,
					DAOOneSignal.ORDER_RETURNED,
					null
			);


			return Response.status(Status.OK)
					.build();
		}
		if(rowCount <= 0)
		{

			return Response.status(Status.NOT_MODIFIED)
					.build();
		}


		return null;
	}










	@PUT
	@Path("/HandoverToUser/{OrderID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_DELIVERY_GUY_SELF,GlobalConstants.ROLE_DELIVERY_GUY})
	public Response deliverOrder(@PathParam("OrderID")int orderID)
	{

//		Order order = Globals.orderService.readStatusHomeDelivery(orderID);
//		order.setStatusHomeDelivery(OrderStatusHomeDelivery.HANDOVER_REQUESTED);

		int rowCount = Globals.daoOrderDeliveryGuy.deliverOrder(orderID);

//
//		try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}

		if (rowCount >= 1) {


			Order orderResult = Globals.orderService.readSingleOrder(orderID);

//			String shopAdminPlayerID = oneSignalNotifications.getPlayerIDforShopAdmin(orderResult.getShopID());
			ArrayList<String> playerIDs =  Globals.oneSignalNotifications.getPlayerIDsForShopStaff(orderResult.getShopID(),
					null,null,null,true,null);


//			playerIDs.add(shopAdminPlayerID);



			Globals.oneSignalNotifications.sendNotificationToUser(
					playerIDs,
					GlobalConstants.ONE_SIGNAL_APP_ID_SHOP_OWNER_APP,
					GlobalConstants.ONE_SIGNAL_API_KEY_SHOP_OWNER_APP,
					"https://i1.wp.com/nearbyshops.org/wp-content/uploads/2017/02/cropped-backdrop_play_store-1.png?w=250&ssl=1",
					null,
					null,
					10,
					"Order Delivered",
					"Order number " + String.valueOf(orderID) + " is Delivered !",
					1,
					DAOOneSignal.ORDER_RETURNED,
					null
			);



			return Response.status(Status.OK)
					.build();
		}
		if (rowCount <= 0) {
			return Response.status(Status.NOT_MODIFIED)
					.build();
		}

		return null;
	}










	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_SHOP_STAFF, GlobalConstants.ROLE_SHOP_ADMIN, GlobalConstants.ROLE_DELIVERY_GUY_SELF,GlobalConstants.ROLE_DELIVERY_GUY})
	public Response getOrders(@QueryParam("OrderID")Integer orderID,
                              @QueryParam("EndUserID")Integer endUserID,
                              @QueryParam("ShopID")Integer shopID,
                              @QueryParam("PickFromShop") Boolean pickFromShop,
                              @QueryParam("StatusHomeDelivery")Integer homeDeliveryStatus,
                              @QueryParam("StatusPickFromShopStatus")Integer pickFromShopStatus,
                              @QueryParam("DeliveryGuyID")Integer deliveryGuyID,
                              @QueryParam("latCenter")Double latCenter, @QueryParam("lonCenter")Double lonCenter,
                              @QueryParam("PendingOrders") Boolean pendingOrders,
                              @QueryParam("SearchString") String searchString,
                              @QueryParam("SortBy") String sortBy,
                              @QueryParam("Limit")Integer limit, @QueryParam("Offset")Integer offset,
							  @QueryParam("GetRowCount")boolean getRowCount,
							  @QueryParam("MetadataOnly")boolean getOnlyMetaData)


	{

		// *********************** second Implementation


		if(limit!=null)
		{
			if(limit >= GlobalConstants.max_limit)
			{
				limit = GlobalConstants.max_limit;
			}

			if(offset==null)
			{
				offset = 0;
			}
		}



		getRowCount=true;


		OrderEndPoint endpoint = Globals.orderService.readOrders(orderID,
							endUserID,shopID, pickFromShop,
							homeDeliveryStatus,pickFromShopStatus,
							deliveryGuyID,
							latCenter,lonCenter,
							pendingOrders,
							searchString,
							sortBy,limit,offset,
							getRowCount,getOnlyMetaData);




		if(limit!=null)
		{
			endpoint.setLimit(limit);
			endpoint.setOffset(offset);
			endpoint.setMax_limit(GlobalConstants.max_limit);
		}



/*
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
*/

		//Marker

		return Response.status(Status.OK)
				.entity(endpoint)
				.build();
	}







	//############################# CODE ENDS HERE ############################################







//	// requires authentication by the Distributor
//	@PUT
//	@Path("/ReturnOrder/{OrderID}")
//	public Response returnOrder(@PathParam("OrderID")int orderID)
//	{
//
//		int rowCount = Globals.orderService.returnOrderByDeliveryGuy(orderID);
//
//		if(rowCount >= 1)
//		{
//
//			return Response.status(Status.OK)
//					.entity(null)
//					.build();
//		}
//		if(rowCount <= 0)
//		{
//
//			return Response.status(Status.NOT_MODIFIED)
//					.entity(null)
//					.build();
//		}
//
//		return null;
//	}
//



}
