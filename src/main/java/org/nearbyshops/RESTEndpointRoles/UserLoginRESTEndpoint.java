package org.nearbyshops.RESTEndpointRoles;

import net.coobird.thumbnailator.Thumbnails;
import org.nearbyshops.DAORoles.DAOUserNew;
import org.nearbyshops.Globals.GlobalConstants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Image;
import org.nearbyshops.ModelRoles.StaffPermissions;
import org.nearbyshops.ModelRoles.User;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.StringTokenizer;

/**
 * Created by sumeet on 21/4/17.
 */






@Path("/api/v1/User")
public class UserLoginRESTEndpoint {

    private DAOUserNew daoUser = Globals.daoUserNew;

    /* Methods */

    // updateFirebaseID(String firebaseID)


/* Update and Login */
//    updateUser(User user, @PathParam("UserID")int userID)
//    updateEmail(User user)
//    updatePhone(User user)
//    public Response changePassword(User user, @PathParam("OldPassword")String oldPassword)
//    public Response getProfile(@HeaderParam("Authorization")String headerParam)
//    public Response getToken(@HeaderParam("Authorization")String headerParam)




    //    GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_STAFF,GlobalConstants.ROLE_END_USER



    @PUT
    @Path("/UpdateProfileEndUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_END_USER})
    public Response updateProfileEndUser(User user)
    {
//        /{UserID}
//        @PathParam("UserID")int userID

        user.setUserID(((User)Globals.accountApproved).getUserID());
        int rowCount = daoUser.updateEndUser(user);


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
    @Path("/UpdateProfileDriver")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN})
    public Response updateProfileDriver(User user)
    {
//        /{UserID}
//        , @PathParam("UserID")int userID

        user.setUserID(((User)Globals.accountApproved).getUserID());
        int rowCount = daoUser.updateDriver(user);


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








//    @PUT
//    @Path("/UpdateFirebaseID/{FirebaseID}")
//    @RolesAllowed({GlobalConstants.ROLE_END_USER,GlobalConstants.ROLE_SHOP_ADMIN,GlobalConstants.ROLE_STAFF,GlobalConstants.ROLE_ADMIN})
//    public Response updateFirebaseID(@PathParam("FirebaseID") String firebaseID)
//    {
//
//        User user = (User) Globals.accountApproved;
//        user.setFirebaseID(firebaseID);
//
//        int rowCount = Globals.userNotifications.updateFirebaseID(user);
//
//
//        if(rowCount >= 1)
//        {
//
//            return Response.status(Response.Status.OK)
//                    .build();
//        }
//        if(rowCount == 0)
//        {
//
//            return Response.status(Response.Status.NOT_MODIFIED)
//                    .build();
//        }
//
//
//        return null;
//    }





    @PUT
    @Path("/UpdateOneSignalID/{OneSignalID}")
    @RolesAllowed({GlobalConstants.ROLE_END_USER,GlobalConstants.ROLE_SHOP_ADMIN,GlobalConstants.ROLE_STAFF,GlobalConstants.ROLE_ADMIN})
    public Response updateOneSignalID(@PathParam("OneSignalID") String oneSignalID)
    {

        User user = (User) Globals.accountApproved;
        user.setRt_oneSignalPlayerID(oneSignalID);


        int rowCount = 0;


//        rowCount = Globals.oneSignalNotifications.updateOneSignalID(
//                                    user.getUserID(),
//                                    user.getRt_oneSignalPlayerID());


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
    @Path("/UpdateEmail")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN,GlobalConstants.ROLE_END_USER,GlobalConstants.ROLE_STAFF,GlobalConstants.ROLE_ADMIN})
    public Response updateEmail(User user)
    {

        user.setUserID(((User)Globals.accountApproved).getUserID());
        int rowCount = daoUser.updateEmail(user);


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
    @Path("/UpdatePhone")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN,GlobalConstants.ROLE_END_USER,GlobalConstants.ROLE_STAFF,GlobalConstants.ROLE_ADMIN})
    public Response updatePhone(User user)
    {

        user.setUserID(((User)Globals.accountApproved).getUserID());
        int rowCount = daoUser.updatePhone(user);


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
    @Path("/ChangePassword/{OldPassword}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN,GlobalConstants.ROLE_END_USER,GlobalConstants.ROLE_STAFF,GlobalConstants.ROLE_ADMIN})
    public Response changePassword(User user, @PathParam("OldPassword")String oldPassword)
    {

        int rowCount = daoUser.updatePassword(user,oldPassword);


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
    @Path("/GetProfile")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_END_USER,GlobalConstants.ROLE_SHOP_ADMIN,GlobalConstants.ROLE_STAFF,GlobalConstants.ROLE_ADMIN})
    public Response getProfile()
    {


        User profileValidated = (User) Globals.accountApproved;
        User user = daoUser.getProfile(profileValidated.getUserID());


        if(user!=null)
        {

            return Response.status(Response.Status.OK)
                    .entity(user)
                    .build();

        }
        else
        {
            return Response.status(Response.Status.NOT_MODIFIED)
                    .build();
        }

    }




    @GET
    @Path("/GetProfileWithLogin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProfileWithLogin(@HeaderParam("Authorization")String headerParam)
    {


        final String encodedUserPassword = headerParam.replaceFirst(AUTHENTICATION_SCHEME + " ", "");

        //Decode username and password
        String usernameAndPassword = new String(Base64.getDecoder().decode(encodedUserPassword.getBytes()));

        //Split username and password tokens
        final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
        final String username = tokenizer.nextToken();
        final String password = tokenizer.nextToken();

        //Verifying Username and password
//        System.out.println(username);
//        System.out.println(password);



        String token = new BigInteger(130, Globals.random).toString(32);
        Timestamp timestampExpiry = new Timestamp(System.currentTimeMillis() + GlobalConstants.TOKEN_DURATION_MINUTES * 60 * 10);

        User user = daoUser.getProfile(username,password);




        if(user!=null)
        {

            user.setToken(token);
            user.setTimestampTokenExpires(timestampExpiry);

            // password is required for updating the token
            user.setPassword(password);

            int rowsUpdated = daoUser.updateToken(user);

            // we choose not to send password over the wire for security reasons
            user.setPassword(null);



            if(user.getRole() == GlobalConstants.ROLE_STAFF_CODE)
            {
                StaffPermissions permissions = Globals.daoStaff.getStaffPermissions(user.getUserID());
                user.setRt_staff_permissions(permissions);
            }
            else if (user.getRole() == GlobalConstants.ROLE_SHOP_ADMIN_CODE)
            {

                // if role is driver then add vehicle if it exists
    //                user.setRt_vehicle(Globals.);
            }



//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }


            if(rowsUpdated==1)
            {

                return Response.status(Response.Status.OK)
                        .entity(user)
                        .build();
            }
            else
            {
                return Response.status(Response.Status.NOT_MODIFIED)
                        .build();
            }


        }
        else
        {
            return Response.status(Response.Status.NOT_MODIFIED)
                    .build();
        }

    }




    private static final String AUTHENTICATION_SCHEME = "Basic";


    @GET
    @Path("/GetToken")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getToken(@HeaderParam("Authorization")String headerParam)
    {

//        @Context HttpHeaders headers

//        List<String> headerString = headers.getRequestHeader(HttpHeaders.AUTHORIZATION);
//
//        String headerFull = "";
//
//        for(String str : headerString)
//        {
//            headerFull = headerFull + " : " +  str;
//        }


        final String encodedUserPassword = headerParam.replaceFirst(AUTHENTICATION_SCHEME + " ", "");

        //Decode username and password
        String usernameAndPassword = new String(Base64.getDecoder().decode(encodedUserPassword.getBytes()));
        System.out.println("Username:Password" + usernameAndPassword);

        //Split username and password tokens
        final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
        final String username = tokenizer.nextToken();
        final String password = tokenizer.nextToken();

        //Verifying Username and password
        System.out.println(username);
        System.out.println(password);


        String token = new BigInteger(130, Globals.random).toString(32);

        Timestamp timestampExpiry = new Timestamp(System.currentTimeMillis() + GlobalConstants.TOKEN_DURATION_MINUTES*60*1000);


//        Date dt = new Date();
//        DateTime dtOrg = new DateTime(System.currentTimeMillis());
//        DateTime dtPlusOne = dtOrg.plusDays(1);
//        timestamp  = new Timestamp(dtPlusOne.getMillis());


        User user = new User();
        user.setUsername(username);
        user.setPhone(username); // username could be phone number
        user.setEmail(username); // username could be email

        user.setPassword(password);
        user.setToken(token);
        user.setTimestampTokenExpires(timestampExpiry);

        int rowsUpdated = daoUser.updateToken(user);

        user.setPassword(null);


        if(rowsUpdated==1)
        {

            return Response.status(Response.Status.OK)
                    .entity(user)
                    .build();
        }
        else if(rowsUpdated==0)
        {
            return Response.status(Response.Status.NOT_MODIFIED)
                    .build();
        }
        else
        {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }

    }









    // Image MEthods

    private static final java.nio.file.Path BASE_DIR = Paths.get("./images/User");
    private static final double MAX_IMAGE_SIZE_MB = 2;


    @POST
    @Path("/Image")
    @Consumes({MediaType.APPLICATION_OCTET_STREAM})
    @RolesAllowed({GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_SHOP_ADMIN,GlobalConstants.ROLE_STAFF,GlobalConstants.ROLE_END_USER})
    public Response uploadImage(InputStream in, @HeaderParam("Content-Length") long fileSize,
                                @QueryParam("PreviousImageName") String previousImageName
    ) throws Exception
    {


        if(previousImageName!=null)
        {
            Files.deleteIfExists(BASE_DIR.resolve(previousImageName));
            Files.deleteIfExists(BASE_DIR.resolve("three_hundred_" + previousImageName + ".jpg"));
            Files.deleteIfExists(BASE_DIR.resolve("five_hundred_" + previousImageName + ".jpg"));
        }


        File theDir = new File(BASE_DIR.toString());

        // if the directory does not exist, create it
        if (!theDir.exists()) {

            System.out.println("Creating directory: " + BASE_DIR.toString());

            boolean result = false;

            try{
                theDir.mkdir();
                result = true;
            }
            catch(Exception se){
                //handle it
            }
            if(result) {
                System.out.println("DIR created");
            }
        }



        String fileName = "" + System.currentTimeMillis();

        // Copy the file to its location.
        long filesize = Files.copy(in, BASE_DIR.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

        if(filesize > MAX_IMAGE_SIZE_MB * 1048 * 1024)
        {
            // delete file if it exceeds the file size limit
            Files.deleteIfExists(BASE_DIR.resolve(fileName));

            return Response.status(Response.Status.EXPECTATION_FAILED).build();
        }


        createThumbnails(fileName);


        Image imageOld = new Image();
        imageOld.setPath(fileName);

        // Return a 201 Created response with the appropriate Location header.

        return Response.status(Response.Status.CREATED).location(URI.create("/api/Images/" + fileName)).entity(imageOld).build();
    }



    private void createThumbnails(String filename)
    {
        try {

            Thumbnails.of(BASE_DIR.toString() + "/" + filename)
                    .size(300,300)
                    .outputFormat("jpg")
                    .toFile(new File(BASE_DIR.toString() + "/" + "three_hundred_" + filename));

            //.toFile(new File("five-" + filename + ".jpg"));

            //.toFiles(Rename.PREFIX_DOT_THUMBNAIL);


            Thumbnails.of(BASE_DIR.toString() + "/" + filename)
                    .size(500,500)
                    .outputFormat("jpg")
                    .toFile(new File(BASE_DIR.toString() + "/" + "five_hundred_" + filename));



        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @GET
    @Path("/Image/{name}")
    @Produces("image/jpeg")
    public InputStream getImage(@PathParam("name") String fileName) {

        //fileName += ".jpg";
        java.nio.file.Path dest = BASE_DIR.resolve(fileName);

        if (!Files.exists(dest)) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }


        try {
            return Files.newInputStream(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }



    @DELETE
    @Path("/Image/{name}")
    @RolesAllowed({GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_SHOP_ADMIN,GlobalConstants.ROLE_STAFF,GlobalConstants.ROLE_END_USER})
    public Response deleteImageFile(@PathParam("name")String fileName)
    {

        boolean deleteStatus = false;

        Response response;

        System.out.println("Filename: " + fileName);

        try {


            //Files.delete(BASE_DIR.resolve(fileName));
            deleteStatus = Files.deleteIfExists(BASE_DIR.resolve(fileName));

            // delete thumbnails
            Files.deleteIfExists(BASE_DIR.resolve("three_hundred_" + fileName + ".jpg"));
            Files.deleteIfExists(BASE_DIR.resolve("five_hundred_" + fileName + ".jpg"));

            

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        if(!deleteStatus)
        {
            response = Response.status(Response.Status.NOT_MODIFIED).build();

        }else
        {
            response = Response.status(Response.Status.OK).build();
        }

        return response;
    }



}
