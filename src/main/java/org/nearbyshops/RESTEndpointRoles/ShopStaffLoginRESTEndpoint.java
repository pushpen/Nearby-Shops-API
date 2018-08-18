package org.nearbyshops.RESTEndpointRoles;


import org.nearbyshops.Globals.GlobalConstants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.ModelRoles.Endpoints.UserEndpoint;
import org.nearbyshops.ModelRoles.ShopStaffPermissions;
import org.nearbyshops.ModelRoles.StaffPermissions;
import org.nearbyshops.ModelRoles.User;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.nearbyshops.Globals.Globals.daoShopStaff;
import static org.nearbyshops.Globals.Globals.daoStaff;

/**
 * Created by sumeet on 30/8/17.
 */


@Path("/api/v1/User/ShopStaffLogin")
public class ShopStaffLoginRESTEndpoint {



    @PUT
    @Path("/UpdateProfileStaff")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_SHOP_STAFF})
    public Response updateProfileStaff(User user)
    {
//        /{UserID}
//        @PathParam("UserID")int userID

        user.setUserID(((User) Globals.accountApproved).getUserID());
        int rowCount = daoShopStaff.updateShopStaffProfile(user);


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
    public Response updateStaffByAdmin(User user, @PathParam("UserID")int userID)
    {

//        user.setUserID(userID);
        int rowCount = daoShopStaff.updateShopStaffByAdmin(user);


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
    @RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN,GlobalConstants.ROLE_SHOP_STAFF})
    public Response updateStaffLocation(ShopStaffPermissions permissions)
    {

        permissions.setStaffUserID(((User)Globals.accountApproved).getUserID());
        int rowCount = daoShopStaff.updateShopStaffLocation(permissions);


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
    @Path("/GetShopStaffForShopAdmin")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN})
    public Response getShopStaffForShopAdmin(
            @QueryParam("latCurrent") Double latPickUp, @QueryParam("lonCurrent") Double lonPickUp,
            @QueryParam("PermitProfileUpdate") Boolean permitProfileUpdate,
            @QueryParam("PermitRegistrationAndRenewal") Boolean permitRegistrationAndRenewal,
            @QueryParam("Gender") Boolean gender,
            @QueryParam("SortBy") String sortBy,
            @QueryParam("Limit")Integer limit, @QueryParam("Offset")Integer offset,
            @QueryParam("GetRowCount")boolean getRowCount,
            @QueryParam("MetadataOnly")boolean getOnlyMetaData)
    {


        User user = (User) Globals.accountApproved;

        int shopID = Globals.shopDAO.getShopIDForShopAdmin(user.getUserID()).getShopID();

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



        UserEndpoint endPoint = daoShopStaff.getShopStaffForShopAdmin(
                latPickUp,lonPickUp,
                permitProfileUpdate,permitRegistrationAndRenewal,
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






    @GET
    @Path("/GetShopStaffListPublic")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStaffListPublic(
            @QueryParam("latCurrent") Double latPickUp, @QueryParam("lonCurrent") Double lonPickUp,
            @QueryParam("PermitProfileUpdate") Boolean permitProfileUpdate,
            @QueryParam("PermitRegistrationAndRenewal") Boolean permitRegistrationAndRenewal,
            @QueryParam("PermitAcceptPayments") Boolean permitAcceptPayments,
            @QueryParam("Gender") Boolean gender,
            @QueryParam("SortBy") String sortBy,
            @QueryParam("Limit")Integer limit, @QueryParam("Offset")Integer offset,
            @QueryParam("GetRowCount")boolean getRowCount,
            @QueryParam("MetadataOnly")boolean getOnlyMetaData)
    {


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



        UserEndpoint endPoint = daoShopStaff.getShopStaffListPublic(
                latPickUp,lonPickUp,
                permitProfileUpdate,
                permitRegistrationAndRenewal,
                gender,
                sortBy,limit,offset,
                getRowCount,getOnlyMetaData
        );





        if(limit!=null)
        {
            endPoint.setLimit(limit);
            endPoint.setOffset(offset);
            endPoint.setMax_limit(GlobalConstants.max_limit);
        }




        //Marker
        return Response.status(Response.Status.OK)
                .entity(endPoint)
                .build();
    }


}
