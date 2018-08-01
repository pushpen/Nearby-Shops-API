package org.nearbyshops.RESTEndpointReviewItem;



import org.nearbyshops.DAOPreparedReviewItem.ItemReviewDAOPrepared;
import org.nearbyshops.DAORoles.DAOUserNew;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.ModelEndpointReview.ItemReviewEndPoint;
import org.nearbyshops.ModelReviewItem.ItemReview;
import org.nearbyshops.ModelReviewItem.ItemReviewStatRow;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

/**
 * Created by sumeet on 9/8/16.
 */

@Path("/v1/ItemReview")
public class ItemReviewRESTEndpoint {


    private ItemReviewDAOPrepared itemReviewDAOPrepared = Globals.itemReviewDAOPrepared;
    private DAOUserNew endUserDAOPrepared = Globals.daoUserNew;


//    BookReviewDAO bookReviewDAO;

    public ItemReviewRESTEndpoint() {

    }

        @POST
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_JSON)
        public Response saveItemReview(ItemReview itemReview)
        {
//            int idOfInsertedRow = Globals.bookDAO.saveBook(book);

            int idOfInsertedRow = itemReviewDAOPrepared.saveItemReview(itemReview);

            itemReview.setItemReviewID(idOfInsertedRow);

            if(idOfInsertedRow >=1)
            {
                Response response = Response.status(Response.Status.CREATED)
                        .location(URI.create("/api/v1/ItemReview/" + idOfInsertedRow))
                        .entity(itemReview)
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


        @PUT
        @Path("/{ItemReviewID}")
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_JSON)
        public Response updateItem(ItemReview itemReview, @PathParam("ItemReviewID")int itemReviewID)
        {

            itemReview.setItemReviewID(itemReviewID);

//            int rowCount = Globals.bookDAO.updateBook(book);

            int rowCount = itemReviewDAOPrepared.updateItemReview(itemReview);


            if(rowCount >= 1)
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



        @PUT
        @Consumes(MediaType.APPLICATION_JSON)
        public Response updateReviewsBulk(List<ItemReview> itemReviewsList)
        {
            int rowCountSum = 0;

            for(ItemReview item : itemReviewsList)
            {
                rowCountSum = rowCountSum + itemReviewDAOPrepared.updateItemReview(item);
            }

            if(rowCountSum ==  itemReviewsList.size())
            {
                Response response = Response.status(Response.Status.OK)
                        .entity(null)
                        .build();

                return response;
            }
            else if( rowCountSum < itemReviewsList.size() && rowCountSum > 0)
            {
                Response response = Response.status(Response.Status.PARTIAL_CONTENT)
                        .entity(null)
                        .build();

                return response;
            }
            else if(rowCountSum == 0 ) {

                Response response = Response.status(Response.Status.NOT_MODIFIED)
                        .entity(null)
                        .build();

                return response;
            }

            return null;
        }


        @DELETE
        @Path("/{ItemReviewID}")
        @Produces(MediaType.APPLICATION_JSON)
        public Response deleteItem(@PathParam("ItemReviewID")int itemReviewID)
        {

            int rowCount = itemReviewDAOPrepared.deleteItemReview(itemReviewID);

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
        public Response getItemReviews(
                @QueryParam("ItemID")Integer itemID,
                @QueryParam("EndUserID")Integer endUserID,
                @QueryParam("GetEndUser")Boolean getEndUser,
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

            ItemReviewEndPoint endPoint = itemReviewDAOPrepared.getEndPointMetadata(itemID,endUserID);

            endPoint.setLimit(set_limit);
            endPoint.setMax_limit(max_limit);
            endPoint.setOffset(set_offset);

            List<ItemReview> list = null;


            if(metaonly==null || (!metaonly)) {

                list =
                        itemReviewDAOPrepared.getItemReviews(
                                itemID,endUserID,
                                sortBy,set_limit,set_offset
                        );


                if(getEndUser!=null && getEndUser)
                {
                    for(ItemReview itemReview: list)
                    {
                        itemReview.setRt_end_user_profile(endUserDAOPrepared.getProfile(itemReview.getEndUserID()));
                    }
                }

                endPoint.setResults(list);
            }


            //Marker

            return Response.status(Response.Status.OK)
                    .entity(endPoint)
                    .build();

        }





        @GET
        @Path("/{ItemReviewID}")
        @Produces(MediaType.APPLICATION_JSON)
        public Response getItem(@PathParam("ItemReviewID")int itemReviewID)
        {
            System.out.println("ItemReviewID=" + itemReviewID);


            //marker

            ItemReview item = itemReviewDAOPrepared.getItemReview(itemReviewID);

            if(item!= null)
            {
                Response response = Response.status(Response.Status.OK)
                        .entity(item)
                        .build();

                return response;

            } else
            {

                Response response = Response.status(Response.Status.NO_CONTENT)
                        .entity(item)
                        .build();

                return response;

            }
        }



    @GET
    @Path("/Stats/{ItemID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStats(@PathParam("ItemID")int itemID)
    {

        List<ItemReviewStatRow> rowList = itemReviewDAOPrepared.getStats(itemID);


        if(rowList.size()>0)
        {

            return Response.status(Response.Status.OK)
                    .entity(rowList)
                    .build();

        } else
        {

            return Response.status(Response.Status.NO_CONTENT)
                    .build();

        }
    }


}
