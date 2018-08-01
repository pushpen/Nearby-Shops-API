package org.nearbyshops.RESTEndpointsOrderHD;

import org.nearbyshops.Globals.GlobalConstants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Order;
import org.nearbyshops.Model.OrderItem;
import org.nearbyshops.Model.Shop;
import org.nearbyshops.ModelEndpoint.OrderItemEndPoint;
import org.nearbyshops.ModelRoles.User;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;


@Path("/OrderItem")
public class OrderItemResource {


	public OrderItemResource() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
//	@POST
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response createOrderItem(OrderItem orderItem)
//	{
//
//		int rowCount = Globals.orderItemService.saveOrderItem(orderItem);
//
//
//
//		if(rowCount == 1)
//		{
//
//
//			return Response.status(Status.CREATED)
//					.entity(null)
//					.build();
//
//		}else if(rowCount <= 0)
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
//
//
//	@PUT
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response updateOrderItem(OrderItem orderItem)
//	{
//
//		int rowCount = Globals.orderItemService.updateOrderItem(orderItem);
//
//		if(rowCount >= 1)
//		{
//
//			return Response.status(Status.OK)
//					.entity(null)
//					.build();
//		}
//		if(rowCount == 0)
//		{
//
//			return Response.status(Status.NOT_MODIFIED)
//					.entity(null)
//					.build();
//		}
//
//
//		return null;
//	}
//
//
//	@DELETE
//	public Response deleteOrderItem(@QueryParam("OrderID")int orderID, @QueryParam("ItemID") int itemID)
//	{
//
//		int rowCount = Globals.orderItemService.deleteOrderItem(itemID,orderID);
//
//
//		if(rowCount>=1)
//		{
//
//			return Response.status(Status.OK)
//					.entity(null)
//					.build();
//		}
//
//		if(rowCount == 0)
//		{
//
//			return Response.status(Status.NOT_MODIFIED)
//					.entity(null)
//					.build();
//		}
//
//
//		return null;
//	}
//


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN, GlobalConstants.ROLE_SHOP_STAFF, GlobalConstants.ROLE_END_USER})
	public Response getOrderItem(@QueryParam("OrderID")Integer orderID,
                                 @QueryParam("ItemID")Integer itemID,
                                 @QueryParam("SearchString")String searchString,
                                 @QueryParam("SortBy") String sortBy,
                                 @QueryParam("Limit")Integer limit, @QueryParam("Offset")Integer offset,
                                 @QueryParam("metadata_only")Boolean metaonly)
	{

		Order order = Globals.orderService.readStatusHomeDelivery(orderID);

		User user = (User) Globals.accountApproved;

		if(user.getRole()==GlobalConstants.ROLE_SHOP_ADMIN_CODE)
		{
			Shop shop = Globals.shopDAO.getShopIDForShopAdmin(user.getUserID());

			if(order.getShopID()!=shop.getShopID())
			{
				throw new ForbiddenException("Not Permitted !");
			}

		}
		else if(user.getRole()==GlobalConstants.ROLE_SHOP_STAFF_CODE)
		{
			int shopIDForStaff = Globals.daoShopStaff.getShopIDforShopStaff(user.getUserID());

			if(order.getShopID()!=shopIDForStaff)
			{
				throw new ForbiddenException("Not Permitted !");
			}
		}
		else if(user.getRole()==GlobalConstants.ROLE_END_USER_CODE)
		{

			if(order.getEndUserID()!=user.getUserID())
			{
				throw new ForbiddenException("Not Permitted !");
			}

		}
		else
		{
			throw new ForbiddenException("Not Permitted !");
		}

//		System.out.println("Entered ...");

/*
		List<OrderItemPFS> orderList = Globals.orderItemService.getOrderItem(orderID,itemID);

		GenericEntity<List<OrderItemPFS>> listEntity = new GenericEntity<List<OrderItemPFS>>(orderList){
		};

		if(orderList.size()<=0)
		{

			return Response.status(Status.NO_CONTENT)
					.entity(listEntity)
					.build();
			
		}else
		{

			return Response.status(Status.OK)
					.entity(listEntity)
					.build();
		}
*/


		////////////////////////////

		int set_limit = 30;
		int set_offset = 0;
		final int max_limit = 100;

		if(limit!= null)
		{

			if (limit >= max_limit) {

				set_limit = max_limit;
			}
			else
			{

				set_limit = limit;
			}

		}

		if(offset!=null)
		{
			set_offset = offset;
		}


		OrderItemEndPoint endPoint = Globals.orderItemService.getEndPointMetadata(orderID,itemID);

		endPoint.setLimit(set_limit);
		endPoint.setMax_limit(max_limit);
		endPoint.setOffset(set_offset);

		List<OrderItem> list = null;


		if(metaonly==null || (!metaonly)) {

			list =
					Globals.orderItemService.getOrderItem(
							orderID,itemID,
							searchString,sortBy,limit,offset
					);

			endPoint.setResults(list);
		}


		/*try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/

		//Marker

		return Response.status(Status.OK)
				.entity(endPoint)
				.build();
	}

}
