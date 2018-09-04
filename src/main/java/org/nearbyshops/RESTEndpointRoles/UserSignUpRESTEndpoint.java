package org.nearbyshops.RESTEndpointRoles;

import net.sargue.mailgun.Mail;
import org.nearbyshops.DAORoles.DAOUserSignUp;
import org.nearbyshops.Globals.GlobalConstants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Globals.SendSMS;
import org.nearbyshops.ModelRoles.*;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * Created by sumeet on 14/8/17.
 */

@Path("/api/v1/User/SignUp")
public class UserSignUpRESTEndpoint {

    private DAOUserSignUp daoUser = Globals.daoUserSignUp;


    /* Sign Up */

//    driverRegistration(User user)
//    endUserRegistration(User user)
//    staffRegistration(User user)
//    public Response checkUsername(@PathParam("username")String username)
//    public Response checkEmailVerificationCode(
//    public Response sendVerificationEmail(@PathParam("email")String email)





    @POST
    @Path("/EndUserRegistration")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertEndUser(User user)
    {
        return userRegistration(user,GlobalConstants.ROLE_END_USER_CODE);
    }







    @POST
    @Path("/DeliveryGuySelfRegistration")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN})
    public Response deliveryGuySelfRegistration(User user)
    {
        if(user==null)
        {
            throw new WebApplicationException();
        }


        User shopAdmin = ((User) Globals.accountApproved);

        int shopID = Globals.shopDAO.getShopIDForShopAdmin(shopAdmin.getUserID()).getShopID();

        user.setRole(GlobalConstants.ROLE_DELIVERY_GUY_SELF_CODE);

        DeliveryGuyData deliveryGuyData = new DeliveryGuyData();
        deliveryGuyData.setShopID(shopID);
        deliveryGuyData.setEmployedByShop(true);
        user.setRt_delivery_guy_data(deliveryGuyData);

//        ShopStaffPermissions permissions = new ShopStaffPermissions();
//        permissions.setShopID(shopID);
//        user.setRt_shop_staff_permissions(permissions);




        int idOfInsertedRow =-1;



        if(user.getRt_registration_mode()==User.REGISTRATION_MODE_EMAIL)
        {

        }
        else if(user.getRt_registration_mode()==User.REGISTRATION_MODE_PHONE)
        {
            idOfInsertedRow = daoUser.registerUsingPhoneNoCredits(user,false);


            System.out.println("Phone : " + user.getPhone()
                    + "\nEmail : " + user.getEmail()
                    + "\nPassword : " + user.getPassword()
                    + "\nRegistration Mode : " + user.getRt_registration_mode()
                    + "\nName : " + user.getName()
                    + "\nInsert Count : " + idOfInsertedRow
                    + "\nVerificationCode : " + user.getRt_phone_verification_code()
            );

            // send notification to the mobile number via SMS

            if(idOfInsertedRow>=1)
            {

                SendSMS.sendSMS("Congratulations your account has been registered with Nearby Shops.",
                        user.getPhone());
            }

        }


        user.setUserID(idOfInsertedRow);


        if(idOfInsertedRow >=1)
        {

            return Response.status(Response.Status.CREATED)
                    .entity(user)
                    .build();

        }else if(idOfInsertedRow <= 0)
        {

            return Response.status(Response.Status.NOT_MODIFIED)
                    .build();
        }





        return null;
    }








    @POST
    @Path("/DeliveryGuyRegistration")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_ADMIN})
    public Response deliveryGuyRegistration(User user)
    {
        if(user==null)
        {
            throw new WebApplicationException();
        }


        user.setRole(GlobalConstants.ROLE_DELIVERY_GUY_CODE);

        DeliveryGuyData deliveryGuyData = new DeliveryGuyData();
        deliveryGuyData.setShopID(0);
        deliveryGuyData.setEmployedByShop(false);
        user.setRt_delivery_guy_data(deliveryGuyData);



        int idOfInsertedRow =-1;



        if(user.getRt_registration_mode()==User.REGISTRATION_MODE_EMAIL)
        {

        }
        else if(user.getRt_registration_mode()==User.REGISTRATION_MODE_PHONE)
        {
            idOfInsertedRow = daoUser.registerUsingPhoneNoCredits(user,false);



            System.out.println("Phone : " + user.getPhone()
                    + "\nEmail : " + user.getEmail()
                    + "\nPassword : " + user.getPassword()
                    + "\nRegistration Mode : " + user.getRt_registration_mode()
                    + "\nName : " + user.getName()
                    + "\nInsert Count : " + idOfInsertedRow
                    + "\nVerificationCode : " + user.getRt_phone_verification_code()
            );

            // send notification to the mobile number via SMS

            if(idOfInsertedRow>=1)
            {

                SendSMS.sendSMS("Congratulations your account has been registered with Nearby Shops.",
                        user.getPhone());
            }

        }




        user.setUserID(idOfInsertedRow);


        if(idOfInsertedRow >=1)
        {

            return Response.status(Response.Status.CREATED)
                    .entity(user)
                    .build();

        }else if(idOfInsertedRow <= 0)
        {

            return Response.status(Response.Status.NOT_MODIFIED)
                    .build();
        }



        return null;
    }





    @POST
    @Path("/ShopStaffRegistration")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN})
    public Response shopStaffRegistration(User user)
    {

        if(user==null)
        {
            throw new WebApplicationException();
        }


        User shopAdmin = ((User) Globals.accountApproved);

        int shopID = Globals.shopDAO.getShopIDForShopAdmin(shopAdmin.getUserID()).getShopID();

        user.setRole(GlobalConstants.ROLE_SHOP_STAFF_CODE);

        ShopStaffPermissions permissions = new ShopStaffPermissions();
        permissions.setShopID(shopID);
        user.setRt_shop_staff_permissions(permissions);




        int idOfInsertedRow =-1;



        if(user.getRt_registration_mode()==User.REGISTRATION_MODE_EMAIL)
        {

        }
        else if(user.getRt_registration_mode()==User.REGISTRATION_MODE_PHONE)
        {
            idOfInsertedRow = daoUser.registerUsingPhoneNoCredits(user,false);


            System.out.println("Phone : " + user.getPhone()
                    + "\nEmail : " + user.getEmail()
                    + "\nPassword : " + user.getPassword()
                    + "\nRegistration Mode : " + user.getRt_registration_mode()
                    + "\nName : " + user.getName()
                    + "\nInsert Count : " + idOfInsertedRow
                    + "\nVerificationCode : " + user.getRt_phone_verification_code()
            );

            // send notification to the mobile number via SMS

            if(idOfInsertedRow>=1)
            {

                SendSMS.sendSMS("Congratulations your account has been registered with Nearby Shops.",
                        user.getPhone());
            }

        }


        user.setUserID(idOfInsertedRow);


        if(idOfInsertedRow >=1)
        {

            return Response.status(Response.Status.CREATED)
                    .entity(user)
                    .build();

        }else if(idOfInsertedRow <= 0)
        {

            return Response.status(Response.Status.NOT_MODIFIED)
                    .build();
        }


        return null;

    }



    @POST
    @Path("/ShopAdminRegistration")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response shopAdminRegistration(User user)
    {
        if(user==null)
        {
            throw new WebApplicationException();
        }


        user.setRole(GlobalConstants.ROLE_SHOP_ADMIN_CODE);


        int idOfInsertedRow =-1;



        if(user.getRt_registration_mode()==User.REGISTRATION_MODE_EMAIL)
        {
            idOfInsertedRow = Globals.daoUserSignUp.registerUsingEmail(user,false);


            System.out.println("Email : " + user.getEmail()
                    + "\nPassword : " + user.getPassword()
                    + "\nRegistration Mode : " + user.getRt_registration_mode()
                    + "\nName : " + user.getName()
                    + "\nInsert Count : " + idOfInsertedRow
                    + "\nVerificationCode : " + user.getRt_email_verification_code()
            );


            if(idOfInsertedRow>=1)
            {
                // registration successful therefore send email to notify the user
                Mail.using(Globals.getMailgunConfiguration())
                        .body()
                        .h1("Registration successful for your account")
                        .p("Your account has been Created.")
                        .h3("Your E-mail : " + user.getEmail())
                        .p("You can login with your email and password that you have provided. Thank you for registering with Taxi Referral Service (TRS).")
                        .mail()
                        .to(user.getEmail())
                        .subject("Taxi Referral Service : Account Registered")
                        .from("Taxi Referral Service","noreply@taxireferral.org")
                        .build()
                        .send();





            }
        }
        else if(user.getRt_registration_mode()==User.REGISTRATION_MODE_PHONE)
        {

            idOfInsertedRow = daoUser.registerUsingPhone(user,false,100,100,false);


            System.out.println("Phone : " + user.getPhone()
                    + "\nEmail : " + user.getEmail()
                    + "\nPassword : " + user.getPassword()
                    + "\nRegistration Mode : " + user.getRt_registration_mode()
                    + "\nName : " + user.getName()
                    + "\nInsert Count : " + idOfInsertedRow
                    + "\nVerificationCode : " + user.getRt_phone_verification_code()
            );

            // send notification to the mobile number via SMS

            if(idOfInsertedRow>=1)
            {
                if(user.getRole()==GlobalConstants.ROLE_SHOP_ADMIN_CODE)
                {

                    String message = "Thank you for registering with Nearby Shops.";
                    SendSMS.sendSMS(message, user.getPhone());


                }
                else
                {
                    SendSMS.sendSMS("Congratulations your account has been registered with Taxi Referral Service.",
                            user.getPhone());
                }
            }

        }


        user.setUserID(idOfInsertedRow);


        if(idOfInsertedRow >=1)
        {

            return Response.status(Response.Status.CREATED)
                    .entity(user)
                    .build();

        }else if(idOfInsertedRow <= 0)
        {

            return Response.status(Response.Status.NOT_MODIFIED)
                    .build();
        }


        return null;
    }



    @POST
    @Path("/StaffRegistration")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_ADMIN})
    public Response insertStaff(User user)
    {
        user.setRole(GlobalConstants.ROLE_STAFF_CODE);
        return userRegistration(user,GlobalConstants.ROLE_STAFF_CODE);
    }







    private Response userRegistration(User user, int role)
    {
        if(user==null)
        {
            throw new WebApplicationException();
        }


        user.setRole(role);


        int idOfInsertedRow =-1;



        if(user.getRt_registration_mode()==User.REGISTRATION_MODE_EMAIL)
        {
            idOfInsertedRow = Globals.daoUserSignUp.registerUsingEmail(user,false);


            System.out.println("Email : " + user.getEmail()
                    + "\nPassword : " + user.getPassword()
                    + "\nRegistration Mode : " + user.getRt_registration_mode()
                    + "\nName : " + user.getName()
                    + "\nInsert Count : " + idOfInsertedRow
                    + "\nVerificationCode : " + user.getRt_email_verification_code()
            );


            if(idOfInsertedRow>=1)
            {
                // registration successful therefore send email to notify the user
                Mail.using(Globals.getMailgunConfiguration())
                        .body()
                        .h1("Registration successful for your account")
                        .p("Your account has been Created.")
                        .h3("Your E-mail : " + user.getEmail())
                        .p("You can login with your email and password that you have provided. Thank you for registering with Taxi Referral Service (TRS).")
                        .mail()
                        .to(user.getEmail())
                        .subject("Taxi Referral Service : Account Registered")
                        .from("Taxi Referral Service","noreply@taxireferral.org")
                        .build()
                        .send();





            }
        }
        else if(user.getRt_registration_mode()==User.REGISTRATION_MODE_PHONE)
        {

            if(user.getRole()==GlobalConstants.ROLE_STAFF_CODE)
            {
                idOfInsertedRow = daoUser.registerUsingPhoneNoCredits(user,false);
            }
            else
            {
                idOfInsertedRow = daoUser.registerUsingPhone(user,false,100,100,false);
            }



            System.out.println("Phone : " + user.getPhone()
                    + "\nEmail : " + user.getEmail()
                    + "\nPassword : " + user.getPassword()
                    + "\nRegistration Mode : " + user.getRt_registration_mode()
                    + "\nName : " + user.getName()
                    + "\nInsert Count : " + idOfInsertedRow
                    + "\nVerificationCode : " + user.getRt_phone_verification_code()
            );

            // send notification to the mobile number via SMS

            if(idOfInsertedRow>=1)
            {
                if(user.getRole()==GlobalConstants.ROLE_SHOP_ADMIN_CODE)
                {

                    String message = "Thank you for registering with Nearby Shops.";
                            SendSMS.sendSMS(message, user.getPhone());


                }
                else
                {
                    SendSMS.sendSMS("Congratulations your account has been registered with Taxi Referral Service.",
                            user.getPhone());
                }
            }

        }


        user.setUserID(idOfInsertedRow);


        if(idOfInsertedRow >=1)
        {

            return Response.status(Response.Status.CREATED)
                    .entity(user)
                    .build();

        }else if(idOfInsertedRow <= 0)
        {

            return Response.status(Response.Status.NOT_MODIFIED)
                    .build();
        }


        return null;
    }








    @GET
    @Path("/CheckUsernameExists/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkUsername(@PathParam("username")String username)
    {
        // Roles allowed not used for this method due to performance and effeciency requirements. Also
        // this endpoint doesnt required to be secured as it does not expose any confidential information

        boolean result = daoUser.checkUsernameExists(username);

//        System.out.println(username);


//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


        if(result)
        {
            return Response.status(Response.Status.OK)
                    .build();

        } else
        {
            return Response.status(Response.Status.NO_CONTENT)
                    .build();
        }
    }






    @GET
    @Path("/CheckEmailVerificationCode/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkEmailVerificationCode(
            @PathParam("email")String email,
            @QueryParam("VerificationCode")String verificationCode
    )
    {
        // Roles allowed not used for this method due to performance and effeciency requirements. Also
        // this endpoint doesnt required to be secured as it does not expose any confidential information

        boolean result = Globals.daoEmailVerificationCodes.checkEmailVerificationCode(email,verificationCode);

//        System.out.println(email);


//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


        if(result)
        {
            return Response.status(Response.Status.OK)
                    .build();

        } else
        {
            return Response.status(Response.Status.NO_CONTENT)
                    .build();
        }
    }





    @PUT
    @Path("/SendEmailVerificationCode/{email}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sendEmailVerificationCode(@PathParam("email")String email)
    {

        int rowCount = 0;


        EmailVerificationCode verificationCode = Globals.daoEmailVerificationCodes.checkEmailVerificationCode(email);

        if(verificationCode==null)
        {
            // verification code not generated for this email so generate one and send this to the user

            String emailVerificationCode = new BigInteger(30, Globals.random).toString(32);

            Timestamp timestampExpiry
                    = new Timestamp(
                    System.currentTimeMillis()
                            + GlobalConstants.EMAIL_VERIFICATION_CODE_EXPIRY_MINUTES *60*1000
            );


            rowCount = Globals.daoEmailVerificationCodes.insertEmailVerificationCode(
                    email,emailVerificationCode,timestampExpiry,true
            );


            if(rowCount==1)
            {
                // saved successfully


                Mail.using(Globals.getMailgunConfiguration())
                        .body()
                        .h1("Your E-mail Verification Code is given below")
                        .p("You have requested to verify your e-mail. If you did not request the e-mail verification please ignore this e-mail message.")
                        .h3("The e-mail verification code is : " + emailVerificationCode)
                        .p("This verification code will expire at " + timestampExpiry.toLocaleString() + ". Please use this code before it expires.")
                        .mail()
                        .to(email)
                        .subject("E-mail Verification Code for Taxi Referral Service (TRS)")
                        .from("Taxi Referral Service","noreply@taxireferral.org")
                        .build()
                        .send();

            }


        }
        else
        {

            System.out.println("Email Verification Code : " + verificationCode.getVerificationCode());

            Mail.using(Globals.getMailgunConfiguration())
                    .body()
                    .h1("Your E-mail Verification Code is given below")
                    .p("You have requested to verify your e-mail. If you did not request the e-mail verification please ignore this e-mail message.")
                    .h3("The e-mail verification code is : " + verificationCode.getVerificationCode())
                    .p("This verification code will expire at " + verificationCode.getTimestampExpires().toLocaleString() + ". Please use this code before it expires.")
                    .mail()
                    .to(email)
                    .subject("E-mail Verification Code for Taxi Referral Service (TRS)")
                    .from("Taxi Referral Service","noreply@taxireferral.org")
                    .build()
                    .send();


            rowCount = 1;
        }




//
//        // verification code has been generated : we need to check if it has expired or not . If the code is
//        // not expired send the existing code.
//        // if the code is expired then generate the new code and save this code and then send it to the user
//
//        if(verificationCode.getTimestampExpires().before(new Timestamp(System.currentTimeMillis())))
//        {
//            // code is expired
//
//            String emailVerificationCode = new BigInteger(30, Globals.random).toString(32);
//            Timestamp timestampExpiry
//                    = new Timestamp(
//                    System.currentTimeMillis()
//                            + GlobalConstants.EMAIL_VERIFICATION_CODE_EXPIRY_MINUTES *60*1000
//            );
//
//            rowCount = Globals.daoEmailVerificationCodes.updateEmailVerificationCode(
//                    email,emailVerificationCode,timestampExpiry);
//
//            if(rowCount==1)
//            {
//                // new code saved successful
//
//                System.out.println("Email Verification Code : " + emailVerificationCode);
//
//                Mail.using(Globals.configurationMailgun)
//                        .body()
//                        .h1("Your E-mail Verification Code is given below")
//                        .p("You have requested to verify your e-mail. If you did not request the e-mail verification please ignore this e-mail message.")
//                        .h3("The e-mail verification code is : " + emailVerificationCode)
//                        .p("This verification code will expire at " + timestampExpiry.toLocaleString() + ". Please use this code before it expires.")
//                        .mail()
//                        .to(email)
//                        .subject("E-mail Verification Code for Taxi Referral Service (TRS)")
//                        .from("Taxi Referral Service","noreply@taxireferral.org")
//                        .build()
//                        .send();
//
////                    .p("Please use this code within 15 minutes because it will expire after 15 minutes.")
//
//            }
//
//
//        }
//        else
//        {
//            // code is not expired : email the existing code
//
//
//        }


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






    /* Methods for Phone Verification for Sign-Up */





    @GET
    @Path("/CheckPhoneVerificationCode/{phone}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkPhoneVerificationCode(
            @PathParam("phone")String phone,
            @QueryParam("VerificationCode")String verificationCode
    )
    {
        // Roles allowed not used for this method due to performance and effeciency requirements. Also
        // this endpoint doesnt required to be secured as it does not expose any confidential information

        boolean result = Globals.daoPhoneVerificationCodes.checkPhoneVerificationCode(phone,verificationCode);

//        System.out.println(phone);
//
//
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


        if(result)
        {
            return Response.status(Response.Status.OK)
                    .build();

        } else
        {
            return Response.status(Response.Status.NO_CONTENT)
                    .build();
        }
    }





    @PUT
    @Path("/SendPhoneVerificationCode/{phone}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sendPhoneVerificationCode(@PathParam("phone")String phone)
    {

        int rowCount = 0;


        PhoneVerificationCode verificationCode = Globals.daoPhoneVerificationCodes.checkPhoneVerificationCode(phone);

        if(verificationCode==null)
        {
            // verification code not generated for this email so generate one and send this to the user


            BigInteger phoneCode = new BigInteger(15, Globals.random);

//            String emailVerificationCode = new BigInteger(30, Globals.random).toString(32);

            int phoneOTP = phoneCode.intValue();

            Timestamp timestampExpiry
                    = new Timestamp(
                    System.currentTimeMillis()
                            + GlobalConstants.PHONE_OTP_EXPIRY_MINUTES *60*1000
            );


            rowCount = Globals.daoPhoneVerificationCodes.insertPhoneVerificationCode(
                    phone,String.valueOf(phoneOTP),timestampExpiry,true
            );


            if(rowCount==1)
            {
                // saved successfully

                System.out.println("Phone Verification Code : " + phoneOTP);

//                Mail.using(Globals.configurationMailgun)
//                        .body()
//                        .h1("Your Phone Verification Code is given below")
//                        .p("You have requested to verify your phone. If you did not request the phone verification please ignore this e-mail message.")
//                        .h3("The e-mail verification code is : " + emailVerificationCode)
//                        .p("This verification code will expire at " + timestampExpiry.toLocaleString() + ". Please use this code before it expires.")
//                        .mail()
//                        .to(phone)
//                        .subject("E-mail Verification Code for Taxi Referral Service (TRS)")
//                        .from("Taxi Referral Service","noreply@taxireferral.org")
//                        .build()
//                        .send();


                SendSMS.sendOTP(String.valueOf(phoneOTP),phone);

            }


        }
        else
        {

            System.out.println("Phone Verification Code : " + verificationCode.getVerificationCode());


//            Mail.using(Globals.configurationMailgun)
//                    .body()
//                    .h1("Your E-mail Verification Code is given below")
//                    .p("You have requested to verify your e-mail. If you did not request the e-mail verification please ignore this e-mail message.")
//                    .h3("The e-mail verification code is : " + verificationCode.getVerificationCode())
//                    .p("This verification code will expire at " + verificationCode.getTimestampExpires().toLocaleString() + ". Please use this code before it expires.")
//                    .mail()
//                    .to(email)
//                    .subject("E-mail Verification Code for Taxi Referral Service (TRS)")
//                    .from("Taxi Referral Service","noreply@taxireferral.org")
//                    .build()
//                    .send();




            SendSMS.sendOTP(verificationCode.getVerificationCode(),phone);


            rowCount = 1;
        }



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
