package org.nearbyshops.RESTEndpointsCart;

import org.nearbyshops.Globals.Globals;
import org.nearbyshops.ModelDelivery.DeliveryAddress;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.net.URI;
import java.util.List;


@Path("/DeliveryAddress")
public class DeliveryAddressResource {


	public DeliveryAddressResource() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createAddress(DeliveryAddress address)
	{

		int idOfInsertedRow = Globals.deliveryAddressService.saveAddress(address);

		address.setId(idOfInsertedRow);


		if(idOfInsertedRow >=1)
		{


			return Response.status(Status.CREATED)
					.location(URI.create("/api/DeliveryAddress/" + idOfInsertedRow))
					.entity(address)
					.build();
			
		}else if(idOfInsertedRow <=0)
		{

			//Response.status(Status.CREATED).location(arg0)
			
			return Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();
		}
		
		return null;
		
	}

	
	@PUT
	@Path("/{DeliveryAddressID}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateCart(@PathParam("DeliveryAddressID")int addressID, DeliveryAddress deliveryAddress)
	{

		deliveryAddress.setId(addressID);

		int rowCount = Globals.deliveryAddressService.updateAddress(deliveryAddress);


		if(rowCount >= 1)
		{
			Response response = Response.status(Status.OK)
					.entity(null)
					.build();
			
			return response;
		}
		if(rowCount == 0)
		{
			Response response = Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();
			
			return response;
		}


		return null;
	}


	@DELETE
	@Path("/{id}")
	public Response deleteCart(@PathParam("id")int addressID)
	{

		int rowCount = Globals.deliveryAddressService.deleteAddress(addressID);
		
		
		if(rowCount>=1)
		{

			return Response.status(Status.OK)
					.entity(null)
					.build();
		}
		else if(rowCount == 0)
		{

			return Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();
		}
		
		
		return null;
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDeliveryAddresses(@QueryParam("EndUserID")int endUserID)

	{

		//List<Cart> cartList = Globals.cartService.readCarts(endUserID,shopID);
		List<DeliveryAddress> addressesList = Globals.deliveryAddressService.readAddresses(endUserID);

		GenericEntity<List<DeliveryAddress>> listEntity = new GenericEntity<List<DeliveryAddress>>(addressesList){
			
		};
	
		
		if(addressesList.size()<=0)
		{
			Response response = Response.status(Status.NO_CONTENT)
					.entity(listEntity)
					.build();
			
			return response;
			
		}else
		{
			Response response = Response.status(Status.OK)
					.entity(listEntity)
					.build();
			
			return response;
		}
		
	}
	
	
	@GET
	@Path("/{DeliveryAddressID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDeliveryAddress(@PathParam("DeliveryAddressID")int addressID)
	{

		DeliveryAddress deliveryAddress = Globals.deliveryAddressService.readAddress(addressID);
		
		if(deliveryAddress != null)
		{
			Response response = Response.status(Status.OK)
			.entity(deliveryAddress)
			.build();
			
			return response;
			
		} else 
		{
			
			Response response = Response.status(Status.NO_CONTENT)
					.entity(deliveryAddress)
					.build();
			
			return response;
			
		}
		
	}	
}
