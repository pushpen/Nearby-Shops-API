package org.nearbyshops.RESTEndpointRoles;


import org.nearbyshops.Globals.GlobalConstants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.ModelRoles.DeliveryGuyData;
import org.nearbyshops.ModelRoles.Endpoints.UserEndpoint;
import org.nearbyshops.ModelRoles.ShopStaffPermissions;
import org.nearbyshops.ModelRoles.User;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.nearbyshops.Globals.Globals.daoDeliveryGuy;
import static org.nearbyshops.Globals.Globals.daoShopStaff;

/**
 * Created by sumeet on 30/8/17.
 */


@Path("/api/v1/User/DeliveryGuy")
public class DeliveryGuyLoginRESTEndpoint {



    @PUT
    @Path("/UpdateProfileBySelf")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_DELIVERY_GUY_SELF,GlobalConstants.ROLE_DELIVERY_GUY})
    public Response updateProfileBySelf(User user)
    {
//        /{UserID}
//        @PathParam("UserID")int userID

        user.setUserID(((User) Globals.accountApproved).getUserID());
        int rowCount = daoDeliveryGuy.updateDeliveryGuyBySelf(user);


        if(rowCount >= 1)
        {

            return Response.status(Response.Status.OK)
                    .build();
        }
        if(rowCount == 0)
        {

            return Response.status(Response.Status.NOT_MODIFIED)
                    .build();
        }

        return null;
    }




    @PUT
    @Path("/{UserID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN})
    public Response updateDeliveryGuyByAdmin(User user, @PathParam("UserID")int userID)
    {

//        user.setUserID(userID);
        int rowCount = daoDeliveryGuy.updateDeliveryGuyByAdmin(user);


        if(rowCount >= 1)
        {

            return Response.status(Response.Status.OK)
                    .build();
        }
        if(rowCount == 0)
        {

            return Response.status(Response.Status.NOT_MODIFIED)
                    .build();
        }

        return null;
    }





    @PUT
    @Path("/UpdateStaffLocation")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_DELIVERY_GUY,GlobalConstants.ROLE_DELIVERY_GUY_SELF})
    public Response updateLocation(DeliveryGuyData deliveryGuyData)
    {

        deliveryGuyData.setStaffUserID(((User)Globals.accountApproved).getUserID());
        int rowCount = daoDeliveryGuy.updateDeliveryGuyLocation(deliveryGuyData);


        if(rowCount >= 1)
        {
            return Response.status(Response.Status.OK)
                    .build();
        }
        if(rowCount == 0)
        {

            return Response.status(Response.Status.NOT_MODIFIED)
                    .build();
        }

        return null;
    }





    @GET
    @Path("/GetDeliveryGuyForShopAdmin")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN})
    public Response getDeliveryGuyForShopAdmin(
            @QueryParam("latCurrent") Double latPickUp, @QueryParam("lonCurrent") Double lonPickUp,
            @QueryParam("Gender") Boolean gender,
            @QueryParam("SortBy") String sortBy,
            @QueryParam("Limit")Integer limit, @QueryParam("Offset")Integer offset,
            @QueryParam("GetRowCount")boolean getRowCount,
            @QueryParam("MetadataOnly")boolean getOnlyMetaData)
    {


        User user = (User) Globals.accountApproved;

        int shopID = Globals.daoDeliveryGuy.getShopIDforDeliveryGuy(user.getUserID());

//        System.out.println("Get Shop Staff !");


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
        else
        {
            limit = GlobalConstants.max_limit;
        }



        UserEndpoint endPoint = daoDeliveryGuy.getDeliveryGuyForShopAdmin(
                latPickUp,lonPickUp,
                gender,
                shopID,
                sortBy,limit,offset,
                getRowCount,getOnlyMetaData
        );



        endPoint.setLimit(limit);
        endPoint.setOffset(offset);
        endPoint.setMax_limit(GlobalConstants.max_limit);


        //Marker
        return Response.status(Response.Status.OK)
                .entity(endPoint)
                .build();
    }



}
