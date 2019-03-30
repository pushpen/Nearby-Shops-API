package org.nearbyshops.RESTEndpointAnalytics;


import org.nearbyshops.DAOAnalytics.DAOItemAnalytics;
import org.nearbyshops.Globals.GlobalConstants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.ModelAnalytics.ItemAnalytics;
import org.nearbyshops.ModelAnalytics.ItemAnalyticsEndpoint;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.sql.Timestamp;

@Path("/api/v1/ItemAnalytics")
public class ItemAnalyticsRESTEndpoint {


    private DAOItemAnalytics itemAnalyticsDAO = Globals.daoItemAnalytics;





    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItemAnalytics(
            @QueryParam("ItemID")Integer itemID,
            @QueryParam("UserID")Integer userID,
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





        ItemAnalyticsEndpoint endPoint  = itemAnalyticsDAO.getItemAnalytics(
                itemID, userID,
                sortBy,limit,offset,
                getRowCount,getOnlyMetaData
        );






        if(limit!=null)
        {
            endPoint.setLimit(limit);
            endPoint.setOffset(offset);
            endPoint.setMax_limit(GlobalConstants.max_limit);
        }


//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}

        //Marker

        return Response.status(Response.Status.OK)
                .entity(endPoint)
                .build();

    }






    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response upsertItemAnalytics(ItemAnalytics itemAnalytics)
    {


        int rowCount = 0;


        rowCount = itemAnalyticsDAO.upsertItemAnalytics(
                            itemAnalytics,true
        );



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


}
