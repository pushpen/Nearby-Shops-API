package org.nearbyshops.RESTEndpointBilling;


import org.nearbyshops.Globals.GlobalConstants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.ModelBilling.TransactionEndpoint;
import org.nearbyshops.ModelRoles.User;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by sumeet on 13/8/17.
 */

@Path("/api/v1/Transaction")
public class TransactionRESTEndpoint {




    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_END_USER,GlobalConstants.ROLE_SHOP_ADMIN})
    public Response getTransactions(
            @QueryParam("IsCredit") Boolean isCredit,
            @QueryParam("TransactionType") Integer transactionType,
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





        TransactionEndpoint endpoint = Globals.daoTransaction.getTransactions(
                ((User) Globals.accountApproved).getUserID(),isCredit,transactionType,
                sortBy,limit,offset,getRowCount,getOnlyMetaData
        );



//
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }



        //Marker
        return Response.status(Response.Status.OK)
                .entity(endpoint)
                .build();
    }





}
