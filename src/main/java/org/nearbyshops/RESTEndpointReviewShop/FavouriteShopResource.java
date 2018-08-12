package org.nearbyshops.RESTEndpointReviewShop;

import org.nearbyshops.DAOPreparedReviewShop.FavoriteBookDAOPrepared;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.ModelEndpointReview.FavouriteShopEndpoint;
import org.nearbyshops.ModelReviewShop.FavouriteShop;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

/**
 * Created by sumeet on 9/8/16.
 */

@Path("/api/v1/FavouriteShop")
public class FavouriteShopResource {


    private FavoriteBookDAOPrepared favoriteBookDAOPrepared = Globals.favoriteBookDAOPrepared;



        @POST
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_JSON)
        public Response saveFavouriteBook(FavouriteShop shop)
        {
            int idOfInsertedRow = favoriteBookDAOPrepared.saveBook(shop);

            shop.setShopID(idOfInsertedRow);

            if(idOfInsertedRow >=1)
            {
                Response response = Response.status(Response.Status.CREATED)
                        .location(URI.create("/api/FavouriteShop/" + idOfInsertedRow))
                        .entity(shop)
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
        public Response deleteItem(@QueryParam("ShopID")Integer shopID,
                                   @QueryParam("EndUserID")Integer endUserID)
        {

            int rowCount = favoriteBookDAOPrepared.deleteFavouriteBook(shopID,endUserID);


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
        public Response getBooks(
                @QueryParam("ShopID")Integer shopID,
                @QueryParam("EndUserID")Integer endUserID,
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


            FavouriteShopEndpoint endPoint = favoriteBookDAOPrepared.getEndPointMetadata(shopID,endUserID);

            endPoint.setLimit(set_limit);
            endPoint.setMax_limit(max_limit);
            endPoint.setOffset(set_offset);

            List<FavouriteShop> list = null;


            if(metaonly==null || (!metaonly)) {

                list =
                        favoriteBookDAOPrepared.getFavouriteBook(
                                shopID,endUserID,
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
