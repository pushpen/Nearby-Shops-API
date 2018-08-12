package org.nearbyshops.RESTEndpointReviewShop;

import org.nearbyshops.DAOPreparedReviewShop.ShopReviewThanksDAOPrepared;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.ModelEndpointReview.ShopReviewThanksEndpoint;
import org.nearbyshops.ModelReviewShop.ShopReviewThanks;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

/**
 * Created by sumeet on 9/8/16.
 */

@Path("/api/v1/ShopReviewThanks")
public class ShopReviewThanksResource {


    private ShopReviewThanksDAOPrepared thanksDAOPrepared = Globals.shopReviewThanksDAO;



        @POST
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_JSON)
        public Response saveShopReviewThanks(ShopReviewThanks shopReviewThanks)
        {
            int idOfInsertedRow = thanksDAOPrepared.saveShopReviewThanks(shopReviewThanks);

//            shopReviewThanks.setItemID(idOfInsertedRow);

            if(idOfInsertedRow >=1)
            {
                Response response = Response.status(Response.Status.CREATED)
                        .location(URI.create("/api/ShopReviewThanks/" + idOfInsertedRow))
                        .entity(shopReviewThanks)
                        .build();

                return response;

            }else if(idOfInsertedRow <= 0)
            {
                Response response = Response.status(Response.Status.NOT_MODIFIED)
                        .entity(null)
                        .build();

                return response;
            }


            return null;
        }



        @DELETE
        @Produces(MediaType.APPLICATION_JSON)
        public Response deleteItem(@QueryParam("ShopReviewID")Integer shopReviewID,
                                   @QueryParam("EndUserID")Integer endUserID)
        {

            int rowCount = thanksDAOPrepared.deleteShopReviewThanks(shopReviewID,endUserID);


            if(rowCount>=1)
            {
                Response response = Response.status(Response.Status.OK)
                        .entity(null)
                        .build();

                return response;
            }

            if(rowCount == 0)
            {
                Response response = Response.status(Response.Status.NOT_MODIFIED)
                        .entity(null)
                        .build();

                return response;
            }

            return null;
        }



        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public Response getShopReviewThanks(
                @QueryParam("ShopReviewID")Integer shopReviewID,
                @QueryParam("EndUserID")Integer endUserID,
                @QueryParam("ShopID")Integer shopID,
                @QueryParam("SortBy") String sortBy,
                @QueryParam("Limit")Integer limit, @QueryParam("Offset")Integer offset,
                @QueryParam("metadata_only")Boolean metaonly)
        {

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


            ShopReviewThanksEndpoint endPoint = thanksDAOPrepared.getEndPointMetadata(shopReviewID,endUserID);

            endPoint.setLimit(set_limit);
            endPoint.setMax_limit(max_limit);
            endPoint.setOffset(set_offset);

            List<ShopReviewThanks> list = null;


            if(metaonly==null || (!metaonly)) {

                list =
                        thanksDAOPrepared.getShopReviewThanks(
                                shopReviewID,shopID,endUserID,
                                sortBy,set_limit,set_offset
                        );

                endPoint.setResults(list);
            }


            //Marker
            return Response.status(Response.Status.OK)
                    .entity(endPoint)
                    .build();

        }

}
