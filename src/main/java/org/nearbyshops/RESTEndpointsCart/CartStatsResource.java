package org.nearbyshops.RESTEndpointsCart;

import org.nearbyshops.DAOsPrepared.ShopDAO;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.ModelStats.CartStats;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;


@Path("/CartStats")
public class CartStatsResource {

	private ShopDAO shopDAO = Globals.shopDAO;

	public CartStatsResource() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	@GET
	@Path("/{EndUserID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCartStats(@PathParam("EndUserID")int endUserID, @QueryParam("CartID") Integer cartID,
                                 @QueryParam("ShopID") Integer shopID,
                                 @QueryParam("GetShopDetails") Boolean getShopDetails,
                                 @QueryParam("latCenter")double latCenter, @QueryParam("lonCenter")double lonCenter)
	{

		List<CartStats> cartStats = Globals.cartStatsDAO.getCartStats(endUserID,cartID, shopID);

		if(getShopDetails!=null && getShopDetails)
		{
			for(CartStats cartStatsItem: cartStats)
			{
				cartStatsItem.setShop(shopDAO.getShop(cartStatsItem.getShopID(),latCenter,lonCenter));
			}

		}



		GenericEntity<List<CartStats>> listEntity = new GenericEntity<List<CartStats>>(cartStats){
		};
		
		if(cartStats.size()<=0)
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

	}
}
