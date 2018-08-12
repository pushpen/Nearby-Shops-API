package org.nearbyshops.RESTEndpointsOrderHD;

import org.nearbyshops.Globals.GlobalConstants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Order;
import org.nearbyshops.Model.Shop;
import org.nearbyshops.ModelEndpoint.OrderEndPoint;
import org.nearbyshops.ModelOrderStatus.OrderStatusHomeDelivery;
import org.nearbyshops.ModelRoles.User;

import javax.annotation.security.RolesAllowed;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;


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
	@RolesAllowed({GlobalConstants.ROLE_DELIVERY_GUY})
	public Response acceptOrder(@PathParam("OrderID")int orderID)
	{
		Order order = Globals.orderService.readStatusHomeDelivery(orderID);

		System.out.println("Accept OrderPFS : ShopID : OrderPFS : " + order.getShopID());

		User user = (User) Globals.accountApproved;



//		if(user.getRole()==GlobalConstants.ROLE_DELIVERY_GUY_CODE)
//		{
////			DeliveryGuySelf deliveryGuySelf = (DeliveryGuySelf) Globals.accountApproved;
//
//
//			if(deliveryGuySelf.getShopID()!=order.getShopID())
//			{
//				// An attempt to update an order for shop you do not own
//				throw new ForbiddenException("An attempt to update order for shop you do not own !");
//			}
//
//			if(order.getDeliveryGuySelfID() != deliveryGuySelf.getDeliveryGuyID())
//			{
//				throw new ForbiddenException("An attempt to update order for shop you do not own !");
//			}
//
//		}
//		else
//		{
//			throw new ForbiddenException("Not Permitted !");
//		}


		if(order.getStatusHomeDelivery()== OrderStatusHomeDelivery.PENDING_HANDOVER)
		{
			order.setStatusHomeDelivery(OrderStatusHomeDelivery.HANDOVER_ACCEPTED);
			int rowCount = Globals.orderService.updateStatusHomeDelivery(order);

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

		}
		else
		{
			throw new ForbiddenException("Invalid operation !");
		}

		return null;
	}





	@PUT
	@Path("/ReturnPackage/{OrderID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_DELIVERY_GUY})
	public Response returnOrderPackage(@PathParam("OrderID")int orderID)
	{
		Order order = Globals.orderService.readStatusHomeDelivery(orderID);

		System.out.println("Return OrderPFS : ShopID : OrderPFS : " + order.getShopID());

//		if(Globals.accountApproved instanceof DeliveryGuySelf)
//		{
//			DeliveryGuySelf deliveryGuySelf = (DeliveryGuySelf) Globals.accountApproved;
//
//			if(deliveryGuySelf.getShopID()!=order.getShopID())
//			{
//				// An attempt to update an order for shop you do not own
//				throw new ForbiddenException("An attempt to update order for shop you do not own !");
//			}
//			else if(order.getDeliveryGuySelfID() != deliveryGuySelf.getDeliveryGuyID())
//			{
//				throw new ForbiddenException("An attempt to update order for shop you do not own !");
//			}
//
//		}
//		else
//		{
//			throw new ForbiddenException("Not Permitted !");
//		}


		int rowCount = Globals.orderService.returnOrderByDeliveryGuy(orderID);

		if(rowCount >= 1)
		{

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
	@RolesAllowed({GlobalConstants.ROLE_DELIVERY_GUY})
	public Response handoverToUser(@PathParam("OrderID")int orderID)
	{
		Order order = Globals.orderService.readStatusHomeDelivery(orderID);

		System.out.println("Handover OrderPFS : ShopID : OrderPFS : " + order.getShopID());

//		if(Globals.accountApproved instanceof DeliveryGuySelf)
//		{
//			DeliveryGuySelf deliveryGuySelf = (DeliveryGuySelf) Globals.accountApproved;
//
//			if(deliveryGuySelf.getShopID()!=order.getShopID())
//			{
//				// An attempt to update an order for shop you do not own
//				throw new ForbiddenException("An attempt to update order for shop you do not own !");
//			}
//			else if(order.getDeliveryGuySelfID() != deliveryGuySelf.getDeliveryGuyID())
//			{
//				throw new ForbiddenException("An attempt to update order for shop you do not own !");
//			}
//
//		}
//		else
//		{
//			throw new ForbiddenException("Not Permitted !");
//		}


		if(order.getStatusHomeDelivery() == OrderStatusHomeDelivery.HANDOVER_ACCEPTED) {

			order.setStatusHomeDelivery(OrderStatusHomeDelivery.PENDING_DELIVERY_ACCEPT_PENDING_PAYMENT);

			int rowCount = Globals.orderService.updateStatusHomeDelivery(order);

			if (rowCount >= 1) {
				return Response.status(Status.OK)
						.build();
			}
			if (rowCount <= 0) {
				return Response.status(Status.NOT_MODIFIED)
						.build();
			}

		}
		else
		{
			throw new ForbiddenException("Invalid operation !");
		}

		return null;
	}





	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_SHOP_STAFF, GlobalConstants.ROLE_SHOP_ADMIN, GlobalConstants.ROLE_DELIVERY_GUY})
	public Response getOrders(@QueryParam("OrderID")Integer orderID,
                              @QueryParam("EndUserID")Integer endUserID,
                              @QueryParam("ShopID")Integer shopID,
                              @QueryParam("PickFromShop") Boolean pickFromShop,
                              @QueryParam("StatusHomeDelivery")Integer homeDeliveryStatus,
                              @QueryParam("StatusPickFromShopStatus")Integer pickFromShopStatus,
                              @QueryParam("DeliveryGuyID")Integer deliveryGuyID,
                              @QueryParam("PaymentsReceived") Boolean paymentsReceived,
                              @QueryParam("DeliveryReceived") Boolean deliveryReceived,
                              @QueryParam("latCenter")Double latCenter, @QueryParam("lonCenter")Double lonCenter,
                              @QueryParam("PendingOrders") Boolean pendingOrders,
                              @QueryParam("SearchString") String searchString,
                              @QueryParam("SortBy") String sortBy,
                              @QueryParam("Limit")Integer limit, @QueryParam("Offset")Integer offset,
                              @QueryParam("metadata_only")Boolean metaonly)

	{

		// *********************** second Implementation


//		if(Globals.accountApproved instanceof ShopAdmin)
//		{
//			ShopAdmin shopAdmin = (ShopAdmin) Globals.accountApproved;
//			Shop shop = Globals.shopDAO.getShopIDForShopAdmin(shopAdmin.getShopAdminID());
//			shopID = shop.getShopID();
//
//			DeliveryGuySelf deliveryGuySelf = Globals.deliveryGuySelfDAO.getShopIDForDeliveryGuy(deliveryGuyID);
//
//			if(deliveryGuySelf.getShopID()!=shop.getShopID())
//			{
//				throw new ForbiddenException("Not Permitted !");
//			}
//
//			System.out.println("Shop Admin Approved !");
//		}
//		else if(Globals.accountApproved instanceof ShopStaff)
//		{
//			shopID = ((ShopStaff) Globals.accountApproved).getShopID();
//
//			DeliveryGuySelf deliveryGuySelf = Globals.deliveryGuySelfDAO.getShopIDForDeliveryGuy(deliveryGuyID);
//
//			if(deliveryGuySelf.getShopID()!=shopID)
//			{
//				throw new ForbiddenException("Not Permitted !");
//			}
//
//			System.out.println("Shop Staff Approved !");
//
//		}
//		else if(Globals.accountApproved instanceof DeliveryGuySelf)
//		{
//			shopID = ((DeliveryGuySelf) Globals.accountApproved).getShopID();
//			deliveryGuyID = ((DeliveryGuySelf) Globals.accountApproved).getDeliveryGuyID();
//
//			System.out.println("Delivery Guy Approved !");
//		}
//		else
//		{
//			throw new ForbiddenException("Not Permitted !");
//		}


		final int max_limit = 100;

		if(limit!=null)
		{
			if(limit>=max_limit)
			{
				limit = max_limit;
			}
		}
		else
		{
			limit = 30;
		}


		if(offset==null)
		{
			offset = 0;
		}


		OrderEndPoint endPoint = Globals.orderService.endPointMetaDataOrders(orderID,
				endUserID,shopID, pickFromShop,
				homeDeliveryStatus,pickFromShopStatus,
				deliveryGuyID,
				paymentsReceived,deliveryReceived,pendingOrders,searchString
		);

		endPoint.setLimit(limit);
		endPoint.setMax_limit(max_limit);
		endPoint.setOffset(offset);

		List<Order> list = null;


		if(metaonly==null || (!metaonly)) {

			list =
					Globals.orderService.readOrders(orderID,
							endUserID,shopID, pickFromShop,
							homeDeliveryStatus,pickFromShopStatus,
							deliveryGuyID,
							paymentsReceived,deliveryReceived,
							latCenter,lonCenter,
							pendingOrders,
							searchString,
							sortBy,limit,offset);


			endPoint.setResults(list);
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
				.entity(endPoint)
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
