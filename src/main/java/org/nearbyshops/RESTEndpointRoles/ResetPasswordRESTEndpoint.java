package org.nearbyshops.RESTEndpointRoles;


import net.sargue.mailgun.Mail;
import org.nearbyshops.Globals.GlobalConstants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Globals.SendSMS;
import org.nearbyshops.ModelRoles.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * Created by sumeet on 14/8/17.
 */




@Path("/api/v1/User/ResetPassword")
public class ResetPasswordRESTEndpoint {




    /* Reset Password */
//    public Response resetPassword(User user)
//    public Response checkPasswordResetCode(@PathParam("emailOrPhone")String emailOrPhone,
//                                           @QueryParam("ResetCode")String resetCode)
//    public Response generateResetCode(User user)




    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response resetPassword(User user)
    {

        //        @Path("/ResetPassword")

//        System.out.println(
//                "Email : " + user.getEmail() +
//                        "Phone : " + user.getPhone() +
//                        "\nReset Code : " + user.getPasswordResetCode() +
//                        "\nResult : "
//        );

        int rowCount = Globals.daoResetPassword.resetPassword(user);

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






//    , @PathParam("EmailOrPhone") String emailOrPhone
//    /{EmailOrPhone}







    @GET
    @Path("/CheckPasswordResetCode/{emailOrPhone}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkPasswordResetCode(
            @PathParam("emailOrPhone")String emailOrPhone,
            @QueryParam("ResetCode")String resetCode
    )
    {
        // Roles allowed not used for this method due to performance and effeciency requirements. Also
        // this endpoint doesnt required to be secured as it does not expose any confidential information

        boolean result = Globals.daoResetPassword.checkPasswordResetCode(emailOrPhone,resetCode);

//        System.out.println(
//                "EmailOrPhone : " + emailOrPhone +
//                        "\nReset Code : " + resetCode +
//                        "\nResult : " + Boolean.toString(result)
//        );


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
    @Path("/GenerateResetCode")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response generateResetCode(User user)
    {

        int rowCount = 0;
        String resetCode = "";
        Timestamp timestampExpiry = null;



        if(!Globals.daoResetPassword.checkPasswordResetCodeExpired(user))
        {
            // code is expired

//            resetCode = new BigInteger(30, Globals.random).toString(32);
            BigInteger phoneCode = new BigInteger(15, Globals.random);
            int phoneOTP = phoneCode.intValue();
            resetCode = String.valueOf(phoneOTP);


            timestampExpiry
                    = new Timestamp(
                    System.currentTimeMillis()
                            + GlobalConstants.PASSWORD_RESET_CODE_EXPIRY_MINUTES * 60 * 1000
            );


            rowCount = Globals.daoResetPassword.updateResetCode(user,resetCode,timestampExpiry);


            if(rowCount==1)
            {
                // saved successfully


                if(user.getRt_registration_mode()==User.REGISTRATION_MODE_EMAIL)
                {

                    Mail.using(Globals.getMailgunConfiguration())
                            .body()
                            .h1("Your E-mail Verification Code is given below")
                            .p("You have requested to verify your e-mail. If you did not request the e-mail verification please ignore this e-mail message.")
                            .h3("The e-mail verification code is : " + resetCode)
                            .p("This verification code will expire at " + timestampExpiry.toLocaleString() + ". Please use this code before it expires.")
                            .mail()
                            .to(user.getEmail())
                            .subject("E-mail Verification Code for Taxi Referral Service (TRS)")
                            .from("Taxi Referral Service","noreply@taxireferral.org")
                            .build()
                            .send();
                }
                else if(user.getRt_registration_mode()==User.REGISTRATION_MODE_PHONE)
                {
                    SendSMS.sendOTP(resetCode,user.getPhone());
                }


            }



        }
        else
        {
            // code is not expired


            User user_credentials = Globals.daoResetPassword.getResetCode(user);


            if(user.getRt_registration_mode()==User.REGISTRATION_MODE_EMAIL)
            {
                Mail.using(Globals.getMailgunConfiguration())
                        .body()
                        .h1("Your E-mail Verification Code is given below")
                        .p("You have requested to verify your e-mail. If you did not request the e-mail verification please ignore this e-mail message.")
                        .h3("The e-mail verification code is : " + user_credentials.getPasswordResetCode())
                        .p("This verification code will expire at " + user_credentials.getResetCodeExpires().toLocaleString() + ". Please use this code before it expires.")
                        .mail()
                        .to(user.getEmail())
                        .subject("E-mail Verification Code for Taxi Referral Service (TRS)")
                        .from("Taxi Referral Service","noreply@taxireferral.org")
                        .build()
                        .send();
            }
            else if(user.getRt_registration_mode()==User.REGISTRATION_MODE_PHONE)
            {
                SendSMS.sendOTP(user_credentials.getPasswordResetCode(),user.getPhone());
            }



            rowCount=1;
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
