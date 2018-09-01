package org.nearbyshops.RESTEndpointsOrderHD;

import org.nearbyshops.Globals.GlobalConstants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Order;
import org.nearbyshops.Model.Shop;
import org.nearbyshops.ModelEndpoint.OrderEndPoint;
import org.nearbyshops.ModelOrderStatus.OrderStatusHomeDelivery;
import org.nearbyshops.ModelRoles.ShopStaffPermissions;
import org.nearbyshops.ModelRoles.User;

import javax.annotation.security.RolesAllowed;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;


@Singleton
@Path("/api/Order/ShopStaff")
public class OrderEndpointShopStaff {


	public OrderEndpointShopStaff() {
		super();
		// TODO Auto-generated constructor stub
	}

//
//	@GET
//	@Path("/Notifications/{ShopID}")
//	@Produces(SseFeature.SERVER_SENT_EVENTS)
//	@RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN})
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
	@Path("/SetConfirmed/{OrderID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN, GlobalConstants.ROLE_SHOP_STAFF})
	public Response confirmOrder(@PathParam("OrderID")int orderID)
	{
		Order order = Globals.orderService.readStatusHomeDelivery(orderID);

//		System.out.println("Set Confirmed : ShopID : Order : " + order.getShopID());

		User user = (User) Globals.accountApproved;

//		if(user.getRole()==GlobalConstants.ROLE_SHOP_ADMIN_CODE)
//		{
//
//			Shop shop = Globals.shopDAO.getShopIDForShopAdmin(user.getUserID());
//
////			System.out.println("ShopID : Order : " + order.getShopID() + "ShopID : Shop : " + shop.getShopID());
//
//			if(order.getShopID()!=shop.getShopID())
//			{
//				// An attempt to update an order for shop you do not own
//				throw new ForbiddenException("An attempt to update order for shop you do not own !");
//			}
//		}
//		else
//

 		if(user.getRole()==GlobalConstants.ROLE_SHOP_STAFF_CODE)
		{
			ShopStaffPermissions permissions = Globals.daoShopStaff.getShopStaffPermissions(user.getUserID());

			if(!permissions.isPermitConfirmOrders())
			{
				throw new ForbiddenException("Not Permitted !");
			}

			if(order.getShopID()!=permissions.getShopID())
			{
				// An attempt to update an order for shop you do not own
				throw new ForbiddenException("An attempt to update order for shop you do not own !");
			}
		}




		if(order.getStatusHomeDelivery()== OrderStatusHomeDelivery.ORDER_PLACED)
		{
			order.setStatusHomeDelivery(OrderStatusHomeDelivery.ORDER_CONFIRMED);
			int rowCount = Globals.orderService.updateStatusHomeDelivery(order);

			if(rowCount >= 1)
			{

//				Globals.broadcastMessageToEndUser("Order Confirmed (Home Delivery)","Order Number " + String.valueOf(orderID) + " (HD) is Confirmed !",order.getEndUserID());
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

//		order.setOrderID(orderID);
//		int rowCount = Globals.orderService.updateOrder(order);


		return null;
	}






	@PUT
	@Path("/SetOrderPacked/{OrderID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN, GlobalConstants.ROLE_SHOP_STAFF})
	public Response setOrderPacked(@PathParam("OrderID")int orderID)
	{
		Order order = Globals.orderService.readStatusHomeDelivery(orderID);

		User user = (User) Globals.accountApproved;

//		if(Globals.accountApproved instanceof ShopAdmin)
//		{
//			ShopAdmin shopAdmin = (ShopAdmin) Globals.accountApproved;
//			Shop shop = Globals.shopDAO.getShopIDForShopAdmin(shopAdmin.getShopAdminID());
//
//			if(order.getShopID()!=shop.getShopID())
//			{
//				// An attempt to update an order for shop you do not own
//				throw new ForbiddenException("An attempt to update order for shop you do not own !");
//			}
//		}
//		else if(Globals.accountApproved instanceof ShopStaff)
//		{
//			ShopStaff shopStaff = (ShopStaff) Globals.accountApproved;
//
//			if(!shopStaff.isSetOrdersPacked())
//			{
//				throw new ForbiddenException("Not Permitted !");
//			}
//
//			if(order.getShopID()!=shopStaff.getShopID())
//			{
//				// An attempt to update an order for shop you do not own
//				throw new ForbiddenException("An attempt to update order for shop you do not own !");
//			}
//		}
//		else
//		{
//			throw new ForbiddenException("Not Permitted !");
//		}





		if(user.getRole()==GlobalConstants.ROLE_SHOP_STAFF_CODE)
		{
			ShopStaffPermissions permissions = Globals.daoShopStaff.getShopStaffPermissions(user.getUserID());

			if(!permissions.isPermitSetOrdersPacked())
			{
				throw new ForbiddenException("Not Permitted !");
			}

			if(order.getShopID()!=permissions.getShopID())
			{
				// An attempt to update an order for shop you do not own
				throw new ForbiddenException("An attempt to update order for shop you do not own !");
			}
		}







		if(order.getStatusHomeDelivery()== OrderStatusHomeDelivery.ORDER_CONFIRMED)
		{
			order.setStatusHomeDelivery(OrderStatusHomeDelivery.ORDER_PACKED);

			int rowCount = Globals.orderService.updateStatusHomeDelivery(order);


			if(rowCount >= 1)
			{

				return Response.status(Status.OK)
						.entity(null)
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

//		order.setOrderID(orderID);
//		int rowCount = Globals.orderService.updateOrder(order);


		return null;
	}


	@PUT
	@Path("/HandoverToDelivery/{DeliveryGuySelfID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN,GlobalConstants.ROLE_SHOP_STAFF})
	public Response handoverToDelivery(@PathParam("DeliveryGuySelfID")int deliveryGuyID, List<Order> ordersList)
	{

		int rowCount = 0;
		Shop shop = null;

//		order.setDeliveryGuySelfID(orderReceived.getDeliveryGuySelfID());
//		DeliveryGuySelf deliveryGuySelf = Globals.deliveryGuySelfDAO.getShopIDForDeliveryGuy(deliveryGuyID);
//
//		if(Globals.accountApproved instanceof ShopAdmin) {
//			ShopAdmin shopAdmin = (ShopAdmin) Globals.accountApproved;
//			shop = Globals.shopDAO.getShopIDForShopAdmin(shopAdmin.getShopAdminID());
//
//		}
//		else if(Globals.accountApproved instanceof ShopStaff)
//		{
//			ShopStaff shopStaff = (ShopStaff) Globals.accountApproved;
//			shop = new Shop();
//			shop.setShopID(shopStaff.getShopID());
//
//			if(!shopStaff.isHandoverToDelivery())
//			{
//				throw new ForbiddenException("Not Permitted !");
//			}
//		}
//		else
//		{
//			throw new ForbiddenException("Not Permitted !");
//		}


		// verify the account of delivery guy being assigned
//		if(deliveryGuySelf!=null)
//		{
//			if(deliveryGuySelf.getShopID()!= shop.getShopID())
//			{
//				// an attempt to assign a delivery guy which doesnt belong to the shop for the given order
//				throw new ForbiddenException("an attempt to assign a delivery guy which doesnt belong to the shop for the given order !");
//			}
//
//		}
//		else
//		{
//			throw new ForbiddenException("Unable to verify Delivery DELIVERY_GUY_SELF_ID");
//		}


		for(Order orderReceived : ordersList)
		{
			Order order = Globals.orderService.readStatusHomeDelivery(orderReceived.getOrderID());

			if(order.getShopID()!=shop.getShopID())
			{
				// An attempt to update an order for shop you do not own
//					ForbiddenOperations activity = new ForbiddenOperations();
//					activity.setShopAdminID(shopAdmin.getShopAdminID());
//					activity.setActivityInfo("An attempt to update order for shop you do not own !");
//					activity.setEndpointInfo("PUT : /OrderPFS/ShopStaff/HandoverToDelivery/{OrderID}");
//					Globals.forbiddenOperationsDAO.saveForbiddenActivity(activity);

				throw new ForbiddenException("An attempt to update order for shop you do not own !");
			}


			if(order.getStatusHomeDelivery()== OrderStatusHomeDelivery.ORDER_PACKED) {

				order.setStatusHomeDelivery(OrderStatusHomeDelivery.PENDING_HANDOVER);
				order.setDeliveryGuySelfID(deliveryGuyID);
				rowCount = Globals.orderService.updateDeliveryGuySelfID(order) + rowCount;
			}


		}





		if(rowCount==ordersList.size())
		{

			return Response.status(Status.OK)
					.build();

		}
		else if (rowCount>0 && rowCount<ordersList.size())
		{
			return Response.status(Status.PARTIAL_CONTENT)
					.build();

		}
		else
		{
			return Response.status(Status.NOT_MODIFIED)
					.build();
		}
	}






	@PUT
	@Path("/UndoHandover/{OrderID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN, GlobalConstants.ROLE_SHOP_STAFF})
	public Response undoHandover(@PathParam("OrderID")int orderID)
	{
		Order order = Globals.orderService.readStatusHomeDelivery(orderID);

		User user = (User) Globals.accountApproved;


//		if(Globals.accountApproved instanceof ShopAdmin)
//		{
//			ShopAdmin shopAdmin = (ShopAdmin) Globals.accountApproved;
//			Shop shop = Globals.shopDAO.getShopIDForShopAdmin(shopAdmin.getShopAdminID());
//
//			if(order.getShopID()!=shop.getShopID())
//			{
//				// An attempt to update an order for shop you do not own
//				throw new ForbiddenException("An attempt to update order for shop you do not own !");
//			}
//		}
//		else if(Globals.accountApproved instanceof ShopStaff)
//		{
//			ShopStaff shopStaff = (ShopStaff) Globals.accountApproved;
//
//			if(!shopStaff.isHandoverToDelivery())
//			{
//				throw new ForbiddenException("Not Permitted !");
//			}
//
//			if(order.getShopID()!=shopStaff.getShopID())
//			{
//				// An attempt to update an order for shop you do not own
//				throw new ForbiddenException("An attempt to update order for shop you do not own !");
//			}
//		}
//		else
//		{
//			throw new ForbiddenException("Not Permitted !");
//		}




		if(user.getRole()==GlobalConstants.ROLE_SHOP_STAFF_CODE)
		{
			ShopStaffPermissions permissions = Globals.daoShopStaff.getShopStaffPermissions(user.getUserID());

			if(!permissions.isPermitHandoverToDelivery())
			{
				throw new ForbiddenException("Not Permitted !");
			}

			if(order.getShopID()!=permissions.getShopID())
			{
				// An attempt to update an order for shop you do not own
				throw new ForbiddenException("An attempt to update order for shop you do not own !");
			}
		}



		if(order.getStatusHomeDelivery()== OrderStatusHomeDelivery.PENDING_HANDOVER)
		{
			order.setStatusHomeDelivery(OrderStatusHomeDelivery.ORDER_PACKED);
			order.setDeliveryGuySelfID(null);

			int rowCount = Globals.orderService.updateDeliveryGuySelfID(order);


			if(rowCount >= 1)
			{

				return Response.status(Status.OK)
						.entity(null)
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

//		order.setOrderID(orderID);
//		int rowCount = Globals.orderService.updateOrder(order);


		return null;
	}




	@PUT
	@Path("/MarkDelivered/{OrderID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN, GlobalConstants.ROLE_SHOP_STAFF})
	public Response markDelivered(@PathParam("OrderID")int orderID)
	{
		Order order = Globals.orderService.readStatusHomeDelivery(orderID);

//		if(Globals.accountApproved instanceof ShopAdmin)
//		{
//			ShopAdmin shopAdmin = (ShopAdmin) Globals.accountApproved;
//			Shop shop = Globals.shopDAO.getShopIDForShopAdmin(shopAdmin.getShopAdminID());
//
//			if(order.getShopID()!=shop.getShopID())
//			{
//				// An attempt to update an order for shop you do not own
//				throw new ForbiddenException("An attempt to update order for shop you do not own !");
//			}
//		}
//		else if(Globals.accountApproved instanceof ShopStaff)
//		{
//			ShopStaff shopStaff = (ShopStaff) Globals.accountApproved;
//
//			if(!shopStaff.isMarkOrdersDelivered())
//			{
//				throw new ForbiddenException("Not Permitted !");
//			}
//
//			if(order.getShopID()!=shopStaff.getShopID())
//			{
//				// An attempt to update an order for shop you do not own
//				throw new ForbiddenException("An attempt to update order for shop you do not own !");
//			}
//
//		}
//		else
//		{
//			throw new ForbiddenException("Not Permitted !");
//		}



		User user = (User) Globals.accountApproved;

		if(user.getRole()==GlobalConstants.ROLE_SHOP_STAFF_CODE)
		{
			ShopStaffPermissions permissions = Globals.daoShopStaff.getShopStaffPermissions(user.getUserID());

			if(!permissions.isPermitMarkOrdersDelivered())
			{
				throw new ForbiddenException("Not Permitted !");
			}

			if(order.getShopID()!=permissions.getShopID())
			{
				// An attempt to update an order for shop you do not own
				throw new ForbiddenException("An attempt to update order for shop you do not own !");
			}
		}




		if(order.getStatusHomeDelivery()== OrderStatusHomeDelivery.PENDING_DELIVERY_ACCEPT_PENDING_PAYMENT)
		{
//			order.setStatusHomeDelivery(OrderStatusHomeDelivery.ORDER_PACKED);
//			order.setDeliveryGuySelfID(null);
			order.setDeliveryReceived(true);

			int rowCount = Globals.orderService.updateDeliveryReceived(order);


			if(rowCount >= 1)
			{


//				Globals.broadcastMessageToEndUser("Order Delivered (Home Delivery)","Order Number " + String.valueOf(orderID) + " (HD) is Delivered !",order.getEndUserID());
				return Response.status(Status.OK)
						.entity(null)
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

//		order.setOrderID(orderID);
//		int rowCount = Globals.orderService.updateOrder(order);


		return null;
	}




	@PUT
	@Path("/PaymentReceived/{OrderID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN, GlobalConstants.ROLE_SHOP_STAFF})
	public Response paymentReceived(@PathParam("OrderID")int orderID)
	{
		Order order = Globals.orderService.readStatusHomeDelivery(orderID);

//		if(Globals.accountApproved instanceof ShopAdmin)
//		{
//			ShopAdmin shopAdmin = (ShopAdmin) Globals.accountApproved;
//			Shop shop = Globals.shopDAO.getShopIDForShopAdmin(shopAdmin.getShopAdminID());
//
//			if(order.getShopID()!=shop.getShopID())
//			{
//				// An attempt to update an order for shop you do not own
//				throw new ForbiddenException("An attempt to update order for shop you do not own !");
//			}
//		}
//		else if(Globals.accountApproved instanceof ShopStaff)
//		{
//			ShopStaff shopStaff = (ShopStaff) Globals.accountApproved;
//
//			if(!shopStaff.isAcceptPaymentsFromDelivery())
//			{
//				throw new ForbiddenException("Not Permitted !");
//			}
//
//			if(order.getShopID()!=shopStaff.getShopID())
//			{
//				// An attempt to update an order for shop you do not own
//				throw new ForbiddenException("An attempt to update order for shop you do not own !");
//			}
//		}
//		else
//		{
//			throw new ForbiddenException("Not Permitted !");
//		}


		User user = (User) Globals.accountApproved;


		if(user.getRole()==GlobalConstants.ROLE_SHOP_STAFF_CODE)
		{
			ShopStaffPermissions permissions = Globals.daoShopStaff.getShopStaffPermissions(user.getUserID());

			if(!permissions.isPermitHandoverToDelivery())
			{
				throw new ForbiddenException("Not Permitted !");
			}

			if(order.getShopID()!=permissions.getShopID())
			{
				// An attempt to update an order for shop you do not own
				throw new ForbiddenException("An attempt to update order for shop you do not own !");
			}
		}




		if(order.getStatusHomeDelivery()== OrderStatusHomeDelivery.PENDING_DELIVERY_ACCEPT_PENDING_PAYMENT)
		{
//			order.setStatusHomeDelivery(OrderStatusHomeDelivery.ORDER_PACKED);
//			order.setDeliveryGuySelfID(null);
//			order.setDeliveryReceived(true);
			order.setPaymentReceived(true);

			int rowCount = Globals.orderService.updatePaymentReceived(order);


			if(rowCount >= 1)
			{

				return Response.status(Status.OK)
						.entity(null)
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

//		order.setOrderID(orderID);
//		int rowCount = Globals.orderService.updateOrder(order);


		return null;
	}



	@PUT
	@Path("/PaymentReceived")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN, GlobalConstants.ROLE_SHOP_STAFF})
	public Response paymentReceivedBulk(List<Order> ordersList)
	{
		int rowCount = 0;
		Shop shop = null;

//		if(Globals.accountApproved instanceof ShopAdmin)
//		{
//			ShopAdmin shopAdmin = (ShopAdmin) Globals.accountApproved;
//			shop = Globals.shopDAO.getShopIDForShopAdmin(shopAdmin.getShopAdminID());
//
////			for(OrderPFS orderItem : ordersList)
////			{
////				OrderPFS order = Globals.orderService.readStatusHomeDelivery(orderItem.getOrderID());
////
////				if(order.getShopID()!=shop.getShopID())
////				{
////					// An attempt to update an order for shop you do not own
////					throw new ForbiddenException("An attempt to update order for shop you do not own !");
////				}
////			}
//		}
//		else if(Globals.accountApproved instanceof ShopStaff)
//		{
//			ShopStaff shopStaff = (ShopStaff) Globals.accountApproved;
//			shop = new Shop();
//			shop.setShopID(shopStaff.getShopID());
//
//			if(!shopStaff.isAcceptPaymentsFromDelivery())
//			{
//				throw new ForbiddenException("Not Permitted !");
//			}
//
//		}
//		else
//		{
//			throw new ForbiddenException("Not Permitted !");
//		}




		User user = (User) Globals.accountApproved;

		if(user.getRole()==GlobalConstants.ROLE_SHOP_STAFF_CODE)
		{
			ShopStaffPermissions permissions = Globals.daoShopStaff.getShopStaffPermissions(user.getUserID());

			if(!permissions.isPermitHandoverToDelivery())
			{
				throw new ForbiddenException("Not Permitted !");
			}

		}




		for(Order order : ordersList)
		{

			if(order.getShopID()!=shop.getShopID())
			{
				throw new ForbiddenException("Not Permitted !");
			}

			if(order.getStatusHomeDelivery()== OrderStatusHomeDelivery.PENDING_DELIVERY_ACCEPT_PENDING_PAYMENT)
			{
//			order.setStatusHomeDelivery(OrderStatusHomeDelivery.ORDER_PACKED);
//			order.setDeliveryGuySelfID(null);
//				order.setDeliveryReceived(true);
				order.setPaymentReceived(true);
				rowCount = Globals.orderService.updatePaymentReceived(order) + rowCount;
			}

		}



		if(rowCount==ordersList.size())
		{

			return Response.status(Status.OK)
					.build();

		}
		else if (rowCount>0 && rowCount<ordersList.size())
		{
			return Response.status(Status.PARTIAL_CONTENT)
					.build();

		}
		else if(rowCount<=0)
		{
			return Response.status(Status.NOT_MODIFIED)
					.build();
		}


		return null;
	}




	// requires authentication by the Distributor
	@PUT
	@Path("/CancelByShop/{OrderID}")
	@RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN, GlobalConstants.ROLE_SHOP_STAFF})
	public Response cancelledByShop(@PathParam("OrderID")int orderID)
	{
		Order order = Globals.orderService.readStatusHomeDelivery(orderID);

//		if(Globals.accountApproved instanceof ShopAdmin)
//		{
//			ShopAdmin shopAdmin = (ShopAdmin) Globals.accountApproved;
//			Shop shop = Globals.shopDAO.getShopIDForShopAdmin(shopAdmin.getShopAdminID());
//
//			if(order.getShopID()!=shop.getShopID())
//			{
//				// An attempt to update an order for shop you do not own
//				throw new ForbiddenException("An attempt to update order for shop you do not own !");
//			}
//		}
//		else if(Globals.accountApproved instanceof ShopStaff)
//		{
//			ShopStaff shopStaff = (ShopStaff) Globals.accountApproved;
//
//			if(!shopStaff.isCancelOrders())
//			{
//				throw new ForbiddenException("Not Permitted !");
//			}
//
//			if(order.getShopID()!=shopStaff.getShopID())
//			{
//				// An attempt to update an order for shop you do not own
//				throw new ForbiddenException("An attempt to update order for shop you do not own !");
//			}
//		}
//		else
//		{
//			throw new ForbiddenException("Not Permitted !");
//		}


		User user = (User) Globals.accountApproved;


		if(user.getRole()==GlobalConstants.ROLE_SHOP_STAFF_CODE)
		{
			ShopStaffPermissions permissions = Globals.daoShopStaff.getShopStaffPermissions(user.getUserID());

			if(!permissions.isPermitHandoverToDelivery())
			{
				throw new ForbiddenException("Not Permitted !");
			}

			if(order.getShopID()!=permissions.getShopID())
			{
				// An attempt to update an order for shop you do not own
				throw new ForbiddenException("An attempt to update order for shop you do not own !");
			}
		}







		int rowCount = Globals.orderService.orderCancelledByShop(orderID);

		if(rowCount >= 1)
		{

//			Globals.broadcastMessageToEndUser("Order Cancelled (Home Delivery)","Order Number " + String.valueOf(orderID) + " (HD) is Cancelled By Shop !",order.getEndUserID());
			return Response.status(Status.OK)
					.entity(null)
					.build();
		}
		if(rowCount <= 0)
		{

			return Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();
		}

		return null;
	}




	@PUT
	@Path("/AcceptReturnCancelledByShop/{OrderID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN, GlobalConstants.ROLE_SHOP_STAFF})
	public Response acceptReturnCancelledByShop(@PathParam("OrderID")int orderID)
	{
		Order order = Globals.orderService.readStatusHomeDelivery(orderID);

//		if(Globals.accountApproved instanceof ShopAdmin)
//		{
//			ShopAdmin shopAdmin = (ShopAdmin) Globals.accountApproved;
//			Shop shop = Globals.shopDAO.getShopIDForShopAdmin(shopAdmin.getShopAdminID());
//
//			if(order.getShopID()!=shop.getShopID())
//			{
//				// An attempt to update an order for shop you do not own
//				throw new ForbiddenException("An attempt to update order for shop you do not own !");
//			}
//		}
//		else if(Globals.accountApproved instanceof ShopStaff)
//		{
//			ShopStaff shopStaff = (ShopStaff) Globals.accountApproved;
//
//			// check permission
//			if(!shopStaff.isAcceptReturns())
//			{
//				throw new ForbiddenException("Not Permitted !");
//			}
//
//			// check shopID
//			if(order.getShopID()!=shopStaff.getShopID())
//			{
//				// An attempt to update an order for shop you do not own
//				throw new ForbiddenException("An attempt to update order for shop you do not own !");
//			}
//		}
//		else
//		{
//			throw new ForbiddenException("Not Permitted !");
//		}


		User user = (User) Globals.accountApproved;

		if(user.getRole()==GlobalConstants.ROLE_SHOP_STAFF_CODE)
		{
			ShopStaffPermissions permissions = Globals.daoShopStaff.getShopStaffPermissions(user.getUserID());

			if(!permissions.isPermitAcceptReturns())
			{
				throw new ForbiddenException("Not Permitted !");
			}

			if(order.getShopID()!=permissions.getShopID())
			{
				// An attempt to update an order for shop you do not own
				throw new ForbiddenException("An attempt to update order for shop you do not own !");
			}
		}



		if(order.getStatusHomeDelivery()== OrderStatusHomeDelivery.CANCELLED_BY_SHOP_RETURN_PENDING)
		{
			order.setStatusHomeDelivery(OrderStatusHomeDelivery.CANCELLED_BY_SHOP);

			int rowCount = Globals.orderService.updateStatusHomeDelivery(order);


			if(rowCount >= 1)
			{

				return Response.status(Status.OK)
						.entity(null)
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

//		order.setOrderID(orderID);
//		int rowCount = Globals.orderService.updateOrder(order);


		return null;
	}



	@PUT
	@Path("/AcceptReturnCancelledByUser/{OrderID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN, GlobalConstants.ROLE_SHOP_STAFF})
	public Response acceptReturnCancelledByUser(@PathParam("OrderID")int orderID)
	{
		Order order = Globals.orderService.readStatusHomeDelivery(orderID);

//		if(Globals.accountApproved instanceof ShopAdmin)
//		{
//			ShopAdmin shopAdmin = (ShopAdmin) Globals.accountApproved;
//			Shop shop = Globals.shopDAO.getShopIDForShopAdmin(shopAdmin.getShopAdminID());
//
//			if(order.getShopID()!=shop.getShopID())
//			{
//				// An attempt to update an order for shop you do not own
//				throw new ForbiddenException("An attempt to update order for shop you do not own !");
//			}
//		}
//		else if(Globals.accountApproved instanceof ShopStaff)
//		{
//			ShopStaff shopStaff = (ShopStaff) Globals.accountApproved;
//
//			// check permission
//			if(!shopStaff.isAcceptReturns())
//			{
//				throw new ForbiddenException("Not Permitted !");
//			}
//
//			// check shopID
//			if(order.getShopID()!=shopStaff.getShopID())
//			{
//				// An attempt to update an order for shop you do not own
//				throw new ForbiddenException("An attempt to update order for shop you do not own !");
//			}
//		}
//		else
//		{
//			throw new ForbiddenException("Not Permitted !");
//		}




		User user = (User) Globals.accountApproved;

		if(user.getRole()==GlobalConstants.ROLE_SHOP_STAFF_CODE)
		{
			ShopStaffPermissions permissions = Globals.daoShopStaff.getShopStaffPermissions(user.getUserID());

			if(!permissions.isPermitAcceptReturns())
			{
				throw new ForbiddenException("Not Permitted !");
			}

			if(order.getShopID()!=permissions.getShopID())
			{
				// An attempt to update an order for shop you do not own
				throw new ForbiddenException("An attempt to update order for shop you do not own !");
			}
		}




		if(order.getStatusHomeDelivery()== OrderStatusHomeDelivery.CANCELLED_BY_USER_RETURN_PENDING)
		{
			order.setStatusHomeDelivery(OrderStatusHomeDelivery.CANCELLED_BY_USER);

			int rowCount = Globals.orderService.updateStatusHomeDelivery(order);


			if(rowCount >= 1)
			{

				return Response.status(Status.OK)
						.entity(null)
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

//		order.setOrderID(orderID);
//		int rowCount = Globals.orderService.updateOrder(order);


		return null;
	}



	@PUT
	@Path("/AcceptReturn/{OrderID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN, GlobalConstants.ROLE_SHOP_STAFF})
	public Response acceptReturn(@PathParam("OrderID")int orderID)
	{
		Order order = Globals.orderService.readStatusHomeDelivery(orderID);

//		if(Globals.accountApproved instanceof ShopAdmin)
//		{
//			ShopAdmin shopAdmin = (ShopAdmin) Globals.accountApproved;
//			Shop shop = Globals.shopDAO.getShopIDForShopAdmin(shopAdmin.getShopAdminID());
//
//			if(order.getShopID()!=shop.getShopID())
//			{
//				// An attempt to update an order for shop you do not own
//				throw new ForbiddenException("An attempt to update order for shop you do not own !");
//			}
//		}
//		else if(Globals.accountApproved instanceof ShopStaff)
//		{
//			ShopStaff shopStaff = (ShopStaff) Globals.accountApproved;
//
//			// check permission
//			if(!shopStaff.isAcceptReturns())
//			{
//				throw new ForbiddenException("Not Permitted !");
//			}
//
//			// check shopID
//			if(order.getShopID()!=shopStaff.getShopID())
//			{
//				// An attempt to update an order for shop you do not own
//				throw new ForbiddenException("An attempt to update order for shop you do not own !");
//			}
//		}
//		else
//		{
//			throw new ForbiddenException("Not Permitted !");
//		}



		User user = (User) Globals.accountApproved;

		if(user.getRole()==GlobalConstants.ROLE_SHOP_STAFF_CODE)
		{
			ShopStaffPermissions permissions = Globals.daoShopStaff.getShopStaffPermissions(user.getUserID());

			if(!permissions.isPermitAcceptReturns())
			{
				throw new ForbiddenException("Not Permitted !");
			}

			if(order.getShopID()!=permissions.getShopID())
			{
				// An attempt to update an order for shop you do not own
				throw new ForbiddenException("An attempt to update order for shop you do not own !");
			}
		}



		if(order.getStatusHomeDelivery()== OrderStatusHomeDelivery.RETURN_PENDING)
		{
			order.setStatusHomeDelivery(OrderStatusHomeDelivery.RETURNED);

			int rowCount = Globals.orderService.updateStatusHomeDelivery(order);


			if(rowCount >= 1)
			{

				return Response.status(Status.OK)
						.entity(null)
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

//		order.setOrderID(orderID);
//		int rowCount = Globals.orderService.updateOrder(order);

		return null;
	}



	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN, GlobalConstants.ROLE_SHOP_STAFF})
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

		User user = (User) Globals.accountApproved;

		if(user.getRole()==GlobalConstants.ROLE_SHOP_ADMIN_CODE)
		{
			Shop shop = Globals.shopDAO.getShopIDForShopAdmin(user.getUserID());
			shopID = shop.getShopID();
		}
		else if(user.getRole()==GlobalConstants.ROLE_SHOP_STAFF_CODE)
		{
			shopID = Globals.daoShopStaff.getShopIDforShopStaff(user.getUserID());
		}
		else
		{
			throw new ForbiddenException("Not Permitted !");
		}


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


//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}

		//Marker

		return Response.status(Status.OK)
				.entity(endPoint)
				.build();
	}






	// Permissions : General
	// Submit Item Categories
	// Submit Items
	// Add / Remove Items From Shop
	// Update Stock

	// Permissions : Home Delivery Inventory
	// 0. Cancel OrderPFS's
	// 1. Confirm OrderPFS's
	// 2. Set OrderPFS's Packed
	// 3. Handover to Delivery
	// 4. Mark OrderPFS Delivered
	// 5. Payment Received | Collect Payments from Delivery Guy
	// 6. Accept Return's | Cancelled By Shop

	// 7. Accept Return | Returned by Delivery Guy | Not required

}
