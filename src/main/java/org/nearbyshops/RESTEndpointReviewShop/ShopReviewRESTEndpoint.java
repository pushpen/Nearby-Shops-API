package org.nearbyshops.RESTEndpointReviewShop;

import org.nearbyshops.DAOPreparedReviewShop.ShopReviewDAOPrepared;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.ModelEndpointReview.ShopReviewEndPoint;
import org.nearbyshops.ModelReviewShop.ShopReview;
import org.nearbyshops.ModelReviewShop.ShopReviewStatRow;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

/**
 * Created by sumeet on 9/8/16.
 */

@Path("/api/v1/ShopReview")
public class ShopReviewRESTEndpoint {


    private ShopReviewDAOPrepared shopReviewDAOPrepared = Globals.shopReviewDAOPrepared;

//    BookReviewDAO bookReviewDAO;

    public ShopReviewRESTEndpoint() {

    }

        @POST
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_JSON)
        public Response saveBookReview(ShopReview shopReview)
        {
//            int idOfInsertedRow = Globals.bookDAO.saveBook(book);

            int idOfInsertedRow = shopReviewDAOPrepared.saveShopReview(shopReview);

            shopReview.setShopReviewID(idOfInsertedRow);

            if(idOfInsertedRow >=1)
            {
                Response response = Response.status(Response.Status.CREATED)
                        .location(URI.create("/api/BookReview/" + idOfInsertedRow))
                        .entity(shopReview)
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
        @Path("/{ShopReviewID}")
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_JSON)
        public Response updateItem(ShopReview shopReview, @PathParam("ShopReviewID")int shopReviewID)
        {

            shopReview.setShopReviewID(shopReviewID);

//            int rowCount = Globals.bookDAO.updateBook(book);

            int rowCount = shopReviewDAOPrepared.updateShopReview(shopReview);


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
        public Response updateReviewsBulk(List<ShopReview> bookReviewsList)
        {
            int rowCountSum = 0;

            for(ShopReview item : bookReviewsList)
            {
                rowCountSum = rowCountSum + shopReviewDAOPrepared.updateShopReview(item);
            }

            if(rowCountSum ==  bookReviewsList.size())
            {
                Response response = Response.status(Response.Status.OK)
                        .entity(null)
                        .build();

                return response;
            }
            else if( rowCountSum < bookReviewsList.size() && rowCountSum > 0)
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
        @Path("/{ShopReviewID}")
        @Produces(MediaType.APPLICATION_JSON)
        public Response deleteItem(@PathParam("ShopReviewID")int shopReviewID)
        {

            int rowCount = shopReviewDAOPrepared.deleteShopReview(shopReviewID);



            if(rowCount>=1)
            {
                Response response = Response.status(Response.Status.OK)
                        .build();

                return response;
            }
            else if(rowCount == 0)
            {
                Response response = Response.status(Response.Status.NOT_MODIFIED)
                        .build();

                return response;
            }

            return null;
        }



        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public Response getBookReviews(
                @QueryParam("ShopID")Integer shopID,
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

            ShopReviewEndPoint endPoint = shopReviewDAOPrepared.getEndPointMetadata(shopID,endUserID);

            endPoint.setLimit(set_limit);
            endPoint.setMax_limit(max_limit);
            endPoint.setOffset(set_offset);

            List<ShopReview> list = null;


            if(metaonly==null || (!metaonly)) {

                list =
                        shopReviewDAOPrepared.getShopReviews(
                                shopID,endUserID,
                                sortBy,set_limit,set_offset
                        );


                if(getEndUser!=null && getEndUser)
                {
                    for(ShopReview shopReview: list)
                    {
                        shopReview.setRt_end_user_profile(Globals.daoUserNew.getProfile(shopReview.getEndUserID()));
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
        @Path("/{ShopReviewID}")
        @Produces(MediaType.APPLICATION_JSON)
        public Response getItem(@PathParam("ShopReviewID")int shopReviewID)
        {
            System.out.println("BookReviewID=" + shopReviewID);


            //marker

            ShopReview item = shopReviewDAOPrepared.getBookReview(shopReviewID);

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
    @Path("/Stats/{ShopID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStats(@PathParam("ShopID")int shopID)
    {

        List<ShopReviewStatRow> rowList = shopReviewDAOPrepared.getStats(shopID);


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
