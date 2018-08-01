package org.nearbyshops.RESTEndpointReviewItem;

import org.nearbyshops.DAOPreparedReviewItem.ItemReviewThanksDAOPrepared;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.ModelEndpointReview.ItemReviewThanksEndpoint;
import org.nearbyshops.ModelReviewItem.ItemReviewThanks;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

/**
 * Created by sumeet on 9/8/16.
 */

@Path("/v1/ItemReviewThanks")
public class ItemReviewThanksResource {


    private ItemReviewThanksDAOPrepared thanksDAOPrepared = Globals.itemReviewThanksDAOPrepared;



        @POST
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_JSON)
        public Response saveItemReviewThanks(ItemReviewThanks itemReviewThanks)
        {
            int idOfInsertedRow = thanksDAOPrepared.saveItemReviewThanks(itemReviewThanks);

//            shopReviewThanks.setItemID(idOfInsertedRow);

            if(idOfInsertedRow >=1)
            {
                Response response = Response.status(Response.Status.CREATED)
                        .location(URI.create("/api/v1/ItemReviewThanks/" + idOfInsertedRow))
                        .entity(itemReviewThanks)
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
        public Response deleteItem(@QueryParam("ItemReviewID")Integer itemReviewID,
                                   @QueryParam("EndUserID")Integer endUserID)
        {

            int rowCount = thanksDAOPrepared.deleteItemReviewThanks(itemReviewID,endUserID);


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
        public Response getItemReviewThanks(
                @QueryParam("ItemReviewID")Integer itemReviewID,
                @QueryParam("EndUserID")Integer endUserID,
                @QueryParam("ItemID")Integer itemID,
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

            ItemReviewThanksEndpoint endPoint = thanksDAOPrepared.getEndPointMetadata(itemReviewID,endUserID);

            endPoint.setLimit(set_limit);
            endPoint.setMax_limit(max_limit);
            endPoint.setOffset(set_offset);

            List<ItemReviewThanks> list = null;


            if(metaonly==null || (!metaonly)) {

                list =
                        thanksDAOPrepared.getItemReviewThanks(
                                itemReviewID,itemID,endUserID,
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
