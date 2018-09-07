package org.nearbyshops;




import org.nearbyshops.DAORoles.DAOUserNew;
import org.nearbyshops.Globals.GlobalConstants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.ModelRoles.User;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by sumeet on 9/9/16.
 */




@Provider
public class AuthenticationFilter implements ContainerRequestFilter {


    private DAOUserNew daoUser = Globals.daoUserNew;


    @Context
    private ResourceInfo resourceInfo;

    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Basic";


    @Override
    public void filter(ContainerRequestContext requestContext) {
        Method method = resourceInfo.getResourceMethod();

//        System.out.println("Security Fileter");

            if (method.isAnnotationPresent(DenyAll.class)) {

                throw new ForbiddenException("Access is ErrorNBSAPI !");
            }



        //Verify user access
        if (method.isAnnotationPresent(RolesAllowed.class)) {
            RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
            Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));

            //Get request headers
            final MultivaluedMap<String, String> headers = requestContext.getHeaders();

            //Fetch authorization header
            final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

            //If no authorization information present; block access
            if (authorization == null || authorization.isEmpty()) {
//                requestContext.abortWith(ACCESS_DENIED);

//                System.out.println("Access Denied : Auth header empty ! ");
                throw new NotAuthorizedException("Access is Denied ! Credentials not present");
            }


            final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

            //Decode username and password
            String usernameAndPassword = new String(Base64.getDecoder().decode(encodedUserPassword.getBytes()));


//            System.out.println("Username:Password | " + usernameAndPassword);

            //Split username and password tokens
            final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
            final String username = tokenizer.nextToken();
            final String password = tokenizer.nextToken();

            //Verifying Username and password
//            System.out.println(username);
//            System.out.println(password);

            Globals.accountApproved = isUserAllowed(username, password, rolesSet);

            
            }
    }






    private Object isUserAllowed(final String username, final String password, final Set<String> rolesSet)
    {

        User user = daoUser.verifyUser(username,password);

        if(user == null)
        {
//            System.out.println("User NULL ");
            throw new NotAuthorizedException("Not Permitted");
        }



//        if(user.isBlocked())
//        {
////            System.out.println("User Blocked : ID :  " + user.getUserID());
//
//            throw new WebApplicationException("Message Response",Response.Status.NO_CONTENT);
////
////            return Response.status(Response.Status.NOT_MODIFIED)
////                    .build();
//        }


        
        //        boolean isAllowed = false;

        //        boolean isEnabled = false;

        //Step 1. Fetch password from database and match with password in argument
        //If both match then get the defined role for user from database and continue; else return isAllowed [false]
        //Access the database and do this part yourself
        //String userRole = userMgr.getUserRole(username);

        int roleID = -1;

        for(String role : rolesSet)
        {
//                System.out.println("Shop Admin null ...");


            if(role.equals(GlobalConstants.ROLE_ADMIN))
            {
                roleID = GlobalConstants.ROLE_ADMIN_CODE;

                if(user.getRole()==GlobalConstants.ROLE_ADMIN_CODE ||
                        user.getRole()==GlobalConstants.ROLE_STAFF_CODE)
                {
                    return user;
                }

            }
            else if(role.equals(GlobalConstants.ROLE_STAFF))
            {
                roleID = GlobalConstants.ROLE_STAFF_CODE;
            }
            else if(role.equals(GlobalConstants.ROLE_SHOP_ADMIN))
            {
                roleID = GlobalConstants.ROLE_SHOP_ADMIN_CODE;

                if(user.getRole()==GlobalConstants.ROLE_SHOP_ADMIN_CODE ||
                        user.getRole()==GlobalConstants.ROLE_SHOP_STAFF_CODE ||
                        user.getRole()==GlobalConstants.ROLE_DELIVERY_GUY_CODE)
                {
                    return user;
                }

            }
            else if(role.equals(GlobalConstants.ROLE_DELIVERY_GUY_SELF))
            {
                roleID=GlobalConstants.ROLE_DELIVERY_GUY_SELF_CODE;
            }
            else if(role.equals(GlobalConstants.ROLE_SHOP_STAFF))
            {
                roleID = GlobalConstants.ROLE_SHOP_STAFF_CODE;
            }
            else if(role.equals(GlobalConstants.ROLE_DELIVERY_GUY))
            {
                roleID = GlobalConstants.ROLE_DELIVERY_GUY_CODE;
            }
            else if(role.equals(GlobalConstants.ROLE_END_USER))
            {

                roleID = GlobalConstants.ROLE_END_USER_CODE;

                // any role can login in place of end user
                return user;
            }








//            System.out.println("User ROLE ID  = " + roleID);

            if(user.getRole()==roleID)
            {

                return user;

                // do not check for whether account is enabled
//                if(user.getRole()==GlobalConstants.ROLE_ADMIN_CODE)
//                {
//
//                    return user;
//                }
//                else if(user.isEnabled())
//                {
//                    return user;
//                }
//                else
//                {
//                    System.out.println("Access Denied  :  account not enabled ");
//                    throw new ForbiddenException("Not Permitted");
//                }

            }
        }




//        System.out.println("Access Denied  : Role not allowed ");
        throw new NotAuthorizedException("Access is Denied ! We are not able to Identify you. ");
    }






}


