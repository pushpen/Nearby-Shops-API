package org.nearbyshops.RESTEndpointsCart;

import org.nearbyshops.DAOsPrepared.ItemDAO;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Cart;
import org.nearbyshops.Model.CartItem;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;


@Path("/api/CartItem")
public class CartItemResource {


	private ItemDAO itemDAO = Globals.itemDAO;


	public CartItemResource() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createCartItem(CartItem cartItem,
                                   @QueryParam("EndUserID") int endUserID,
                                   @QueryParam("ShopID") int shopID)
	{


		System.out.println("End User DELIVERY_GUY_SELF_ID : " + endUserID + " ShopID : " + shopID);

		if(endUserID>0 && shopID>0)
		{

			// Check if the Cart exists if not then create one
			List<Cart> list = Globals.cartService.readCarts(endUserID,shopID);

			if(list.size()==0)
			{
				// cart does not exist so create one

				Cart cart = new Cart();

				cart.setEndUserID(endUserID);
				cart.setShopID(shopID);
				int idOfInsertedCart = Globals.cartService.saveCart(cart);

				cartItem.setCartID(idOfInsertedCart);
			}
			else if(list.size()==1)
			{
				// cart exists

				cartItem.setCartID(list.get(0).getCartID());
			}

		}

		int rowCount = Globals.cartItemService.saveCartItem(cartItem);


		if(rowCount == 1)
		{


			return Response.status(Status.CREATED)
					.entity(null)
					.build();
			
		}else if(rowCount <= 0)
		{

			return Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();
		}

		return null;
	}

	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateCartItem(CartItem cartItem,
                                   @QueryParam("EndUserID") int endUserID,
                                   @QueryParam("ShopID") int shopID)
	{


		if(endUserID>0 && shopID>0)
		{

			// Check if the Cart exists if not then create one
			List<Cart> list = Globals.cartService.readCarts(endUserID,shopID);

			if(list.size()==0)
			{
				// cart does not exist so create one

				Cart cart = new Cart();

				cart.setEndUserID(endUserID);
				cart.setShopID(shopID);
				int idOfInsertedCart = Globals.cartService.saveCart(cart);

				cartItem.setCartID(idOfInsertedCart);
			}
			else if(list.size()==1)
			{
				// cart exists

				cartItem.setCartID(list.get(0).getCartID());
			}

		}


		int rowCount = Globals.cartItemService.updateCartItem(cartItem);


		if(rowCount >= 1)
		{
			Response response = Response.status(Status.OK)
					.entity(null)
					.build();
			
			return response;
		}
		if(rowCount == 0)
		{

			return Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();
		}


		return null;
	}


	@DELETE
	public Response deleteCartItem(@QueryParam("CartID")int cartID, @QueryParam("ItemID") int itemID,
                                   @QueryParam("EndUserID") int endUserID,
                                   @QueryParam("ShopID") int shopID)
	{



		if(endUserID>0 && shopID>0)
		{

			// Check if the Cart exists if not then create one
			List<Cart> list = Globals.cartService.readCarts(endUserID,shopID);

			if(list.size()==1)
			{
				cartID = list.get(0).getCartID();
			}

		}


		int rowCount = Globals.cartItemService.deleteCartItem(itemID,cartID);



		if(rowCount>=1)
		{

			// if the cart item is the last item then delete the cart also.


			System.out.println("Cart Items : " + cartID + " : " + Globals.cartItemService.getCartItem(cartID,0,0).size());

			if(Globals.cartItemService.getCartItem(cartID,null,null).size() == 0)
			{
				System.out.println("Inside Cart Item Delete " + Globals.cartItemService.getCartItem(cartID,null,null).size());

				Globals.cartService.deleteCart(cartID);
			}


			return Response.status(Status.OK)
					.entity(null)
					.build();
		}
		
		if(rowCount == 0)
		{

			return Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();
		}
		
		
		return null;
	}
	


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCartItem(@QueryParam("CartID")Integer cartID,
                                @QueryParam("ItemID")Integer itemID,
                                @QueryParam("EndUserID") Integer endUserID,
                                @QueryParam("ShopID") Integer shopID,
                                @QueryParam("GetItems") Boolean getItems,
                                @QueryParam("SortBy") String sortBy,
                                @QueryParam("Limit")Integer limit, @QueryParam("Offset")Integer offset,
                                @QueryParam("metadata_only")Boolean metaonly)
	{


		List<CartItem> cartList;

		if(shopID != null)
		{

			cartList = Globals.cartItemService
					.getCartItemRefined(endUserID, shopID, sortBy,limit,offset);

/*

			for(CartItem cartItem: cartList)
			{


				if(itemID == null && getItems!=null && getItems)
				{
					cartItem.setItem(itemDAO.getItem(cartItem.getItemID()));
				}

//				cartItem.setItem(itemDAO.getItem(cartItem.getItemID()));
			}
*/


		}else
		{

			cartList = Globals.cartItemService.getCartItem(cartID,itemID,endUserID);

			for(CartItem cartItem: cartList)
			{
				if(cartID == null)
				{
					cartItem.setCart(Globals.cartService.readCart(cartItem.getCartID()));
				}

				/*
				if(itemID == null && getItems!=null && getItems)
				{
					cartItem.setItem(itemDAO.getItem(cartItem.getItemID()));
				}*/

			}

		}


		GenericEntity<List<CartItem>> listEntity = new GenericEntity<List<CartItem>>(cartList){
		};
	
		
		if(cartList.size()<=0)
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
